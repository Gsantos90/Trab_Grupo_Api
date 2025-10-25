package org.serratec.Cleantech.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.dto.ClienteRequestDTO;
import org.serratec.Cleantech.dto.ClienteResponseDTO;
import org.serratec.Cleantech.dto.EnderecoViaCepDTO;
import org.serratec.Cleantech.exception.ResourceNotFoundException; 
import org.serratec.Cleantech.exception.ValidationException; 
import org.serratec.Cleantech.repository.ClienteRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ViaCepService viaCepService;
	@Autowired
	private EmailService emailService;

 
	private Cliente toEntity(ClienteRequestDTO dto) {
		Cliente cliente = new Cliente();

		cliente.setNome(dto.getNome());
		cliente.setTelefone(dto.getTelefone());
		cliente.setEmail(dto.getEmail());
		cliente.setCpf(dto.getCpf());
		cliente.setCep(dto.getCep());
		cliente.setNumero(dto.getNumero());
		cliente.setComplemento(dto.getComplemento());

		return cliente;
	}

	private ClienteResponseDTO toResponseDTO(Cliente cliente) {
		ClienteResponseDTO dto = new ClienteResponseDTO();
		dto.setId(cliente.getId());
		dto.setNome(cliente.getNome());
		dto.setTelefone(cliente.getTelefone());
		dto.setEmail(cliente.getEmail());
		dto.setCpf(cliente.getCpf());
		dto.setCep(cliente.getCep());
		dto.setLogradouro(cliente.getLogradouro());
		dto.setBairro(cliente.getBairro());
		dto.setCidade(cliente.getCidade());
		dto.setUf(cliente.getUf());
		dto.setNumero(cliente.getNumero());
		dto.setComplemento(cliente.getComplemento());
		dto.setAtivo(cliente.isAtivo()); 
		return dto;
	}


	@Transactional 
	public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
		if (clienteRepository.findByCpfAndAtivoTrue(dto.getCpf()).isPresent()) {
			throw new ValidationException("CPF já cadastrado em um cliente ativo. Utilize o PUT para atualizar.");
		}
		if (clienteRepository.findByEmailAndAtivoTrue(dto.getEmail()).isPresent()) {
			throw new ValidationException("E-mail já cadastrado em um cliente ativo.");
		}

		Cliente cliente = toEntity(dto);

		EnderecoViaCepDTO enderecoViaCep = viaCepService.buscarEnderecoPorCep(cliente.getCep());

		cliente.setLogradouro(enderecoViaCep.getLogradouro());
		cliente.setBairro(enderecoViaCep.getBairro());
		cliente.setCidade(enderecoViaCep.getLocalidade());
		cliente.setUf(enderecoViaCep.getUf());

		Cliente clienteSalvo = clienteRepository.save(cliente);
		emailService.enviarEmailNovoCliente(clienteSalvo.getEmail(), clienteSalvo.getNome());

		return toResponseDTO(clienteSalvo);
	}

	public ClienteResponseDTO buscarPorId(Long id) {
		Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id).orElseThrow(
				() -> new ResourceNotFoundException("Cliente ID " + id + " não encontrado ou está inativo."));

		return toResponseDTO(cliente);
	}

	public List<ClienteResponseDTO> listarTodos() {
		return clienteRepository.findByAtivoTrue().stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Transactional 
	public ClienteResponseDTO editar(Long id, ClienteRequestDTO dto) {
		Cliente clienteExistente = clienteRepository.findByIdAndAtivoTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente ID " + id + " não encontrado ou inativo."));

		clienteExistente.setNome(dto.getNome());
		clienteExistente.setTelefone(dto.getTelefone());
		clienteExistente.setCpf(dto.getCpf());
		clienteExistente.setEmail(dto.getEmail());
		clienteExistente.setNumero(dto.getNumero());
		clienteExistente.setComplemento(dto.getComplemento());

		if (!clienteExistente.getCep().equals(dto.getCep())) {
			clienteExistente.setCep(dto.getCep());
			EnderecoViaCepDTO enderecoViaCep = viaCepService.buscarEnderecoPorCep(dto.getCep());
			clienteExistente.setLogradouro(enderecoViaCep.getLogradouro());
			clienteExistente.setBairro(enderecoViaCep.getBairro());
			clienteExistente.setCidade(enderecoViaCep.getLocalidade());
			clienteExistente.setUf(enderecoViaCep.getUf());
		}

		Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
		return toResponseDTO(clienteAtualizado);
	}
   
	@Transactional 
	public void desativarCliente(Long id) {
		Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id).orElseThrow(
				() -> new ResourceNotFoundException("Cliente ID " + id + " não encontrado ou já está inativo."));

		cliente.setAtivo(false);
		clienteRepository.save(cliente);
	} 
}