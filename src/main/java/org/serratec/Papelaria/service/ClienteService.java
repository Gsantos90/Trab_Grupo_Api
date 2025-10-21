package org.serratec.Papelaria.service;

import java.util.List;

import org.serratec.Papelaria.Domain.Cliente;
import org.serratec.Papelaria.dto.ClienteDTO;
import org.serratec.Papelaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;

	public Cliente salvar(Cliente cliente) {
		// Consulta ViaCEP
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
		// Outros campos conforme necessário

		return salvar(cliente); // Reutiliza o método existente

	}

	public Cliente inserir(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCep(dto.getCep());
		// outros campos
		return repo.save(cliente);
	}

	public List<Cliente> listarTodos() {
		return repo.findAll();
	}

	public Cliente atualizar(Long id, ClienteDTO dto) {
		Cliente cliente = repo.findById(id).orElseThrow();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCep(dto.getCep());
		// outros campos
		return repo.save(cliente);
	}

	public void deletar(Long id) {
		repo.deleteById(id);
	}

}
