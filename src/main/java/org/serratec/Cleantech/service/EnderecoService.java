package org.serratec.Cleantech.service;

import java.util.List;
import java.util.Optional;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.Domain.Endereco;
import org.serratec.Cleantech.dto.EnderecoDTO;
import org.serratec.Cleantech.dto.EnderecoViaCepDTO;
import org.serratec.Cleantech.exception.ResourceNotFoundException;
import org.serratec.Cleantech.repository.ClienteRepository;
import org.serratec.Cleantech.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCepService viaCepService;

    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    public Endereco salvar(EnderecoDTO dto) {
        EnderecoViaCepDTO viaCep = viaCepService.buscar(dto.getCep());
        Optional<Cliente> cliente = clienteRepository.findById(dto.getClienteId());

        if (cliente.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setLogradouro(viaCep.getLogradouro());
        endereco.setBairro(viaCep.getBairro());
        endereco.setCidade(viaCep.getLocalidade());
        endereco.setUf(viaCep.getUf());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCliente(cliente.get());

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Long id, EnderecoDTO dto) {
        Endereco endereco = buscarPorId(id);

        if (dto.getCep() != null) {
            EnderecoViaCepDTO viaCep = viaCepService.buscar(dto.getCep());
            endereco.setCep(dto.getCep());
            endereco.setLogradouro(viaCep.getLogradouro());
            endereco.setBairro(viaCep.getBairro());
            endereco.setCidade(viaCep.getLocalidade());
            endereco.setUf(viaCep.getUf());
        }

        endereco.setComplemento(dto.getComplemento());
        return enderecoRepository.save(endereco);
    }

    public void deletar(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }
}

