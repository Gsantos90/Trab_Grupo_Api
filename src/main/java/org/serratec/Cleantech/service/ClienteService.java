package org.serratec.Cleantech.service;

import java.util.List;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.dto.ClienteDTO;
import org.serratec.Cleantech.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;

	public Cliente salvar(Cliente cliente) {
		// Consulta ViaCEP para preencher o campo endereco com o retorno JSON bruto
		RestTemplate rest = new RestTemplate();
		String url = "https://viacep.com.br/ws/" + cliente.getCep() + "/json/";
		String endereco = rest.getForObject(url, String.class);
		cliente.setEndereco(endereco);

		// Enviar e-mail (mock)
		// emailService.enviar(cliente.getEmail(), "Cadastro atualizado!");

		return repo.save(cliente);
	}

	public Cliente salvar(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCep(dto.getCep());
		cliente.setTelefone(dto.getTelefone()); // copia telefone do DTO
		cliente.setCpf(dto.getCpf()); // copia cpf do DTO
		// Outros campos conforme necessário

		return salvar(cliente); // Reutiliza o método existente para preencher endereco e salvar
	}

	public Cliente inserir(ClienteDTO dto) {
		// Delegar para salvar(dto) garante que endereco será preenchido via ViaCEP
		return salvar(dto);
	}

	public List<Cliente> listarTodos() {
		return repo.findAll();
	}

	public Cliente atualizar(Long id, ClienteDTO dto) {
		Cliente cliente = repo.findById(id).orElseThrow();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCep(dto.getCep());
		cliente.setTelefone(dto.getTelefone()); // atualiza telefone
		cliente.setCpf(dto.getCpf()); // atualiza cpf
		// outros campos se necessário

		// Reaplica a lógica de salvar para preencher endereco via ViaCEP e persistir
		return salvar(cliente);
	}

	public void deletar(Long id) {
		repo.deleteById(id);
	}
	
}