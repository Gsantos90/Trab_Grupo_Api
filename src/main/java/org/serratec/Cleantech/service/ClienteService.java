package org.serratec.Cleantech.service;

import java.util.List;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.dto.EnderecoViaCepDTO;
import org.serratec.Cleantech.dto.ClienteDTO;
import org.serratec.Cleantech.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository repo;
    private final ViaCepService viaCepService;

    // Injeção por construtor (recomendada)
    public ClienteService(ClienteRepository repo, ViaCepService viaCepService) {
        this.repo = repo;
        this.viaCepService = viaCepService;
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        // valida cep nulo/ vazio (proteção básica)
        if (cliente.getCep() == null || cliente.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }

        // 1. Busca o endereço no ViaCep (pode lançar exceções específicas)
        EnderecoViaCepDTO enderecoDto = viaCepService.buscarEnderecoPorCep(cliente.getCep());

        // 2. Mapeia os campos do DTO para a entidade Cliente
        // OBS: ajuste os nomes dos setters conforme sua entidade Cliente
        cliente.setLogradouro(enderecoDto.getLogradouro());
        cliente.setBairro(enderecoDto.getBairro());
        cliente.setCidade(enderecoDto.getLocalidade());
        cliente.setUf(enderecoDto.getUf());

        // 3. Salva o cliente no repositório
        return repo.save(cliente);
    }

    public Cliente salvar(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCep(dto.getCep());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        // se Cliente tiver campo endereco tipo Endereco, mapear conforme
        return salvar(cliente);
    }

    public Cliente inserir(ClienteDTO dto) {
        return salvar(dto);
    }

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    public Cliente atualizar(Long id, ClienteDTO dto) {
        Cliente cliente = repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCep(dto.getCep());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        return salvar(cliente);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
