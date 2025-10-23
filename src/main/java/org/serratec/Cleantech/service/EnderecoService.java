package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.Domain.Endereco;
import org.serratec.Cleantech.dto.EnderecoRequestDTO;
import org.serratec.Cleantech.dto.EnderecoResponseDTO;
import org.serratec.Cleantech.exception.ResourceNotFoundException;
import org.serratec.Cleantech.repository.ClienteRepository;
import org.serratec.Cleantech.repository.EnderecoRepository;
import org.serratec.Cleantech.dto.EnderecoViaCepDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCepService viaCepService;


    @Transactional
    public EnderecoResponseDTO inserir(Long clienteId, EnderecoRequestDTO dto) throws ResourceNotFoundException {

        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente nÃ£o encontrado com ID: " + clienteId));

        EnderecoViaCepDTO dadosViaCep = viaCepService.buscarEnderecoPorCep(dto.getCep());

        if (dadosViaCep == null || dadosViaCep.getCep() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP nÃ£o encontrado ou invÃ¡lido pelo ViaCEP.");
        }

        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep(dto.getCep());
        novoEndereco.setNumero(dto.getNumero());
        novoEndereco.setComplemento(dto.getComplemento());
        novoEndereco.setLogradouro(dadosViaCep.getLogradouro());
        novoEndereco.setBairro(dadosViaCep.getBairro());
        novoEndereco.setLocalidade(dadosViaCep.getLocalidade());
        novoEndereco.setUf(dadosViaCep.getUf());
        novoEndereco.setCliente(cliente);

        novoEndereco = enderecoRepository.save(novoEndereco);

        return new EnderecoResponseDTO(novoEndereco);
    }


    public List<EnderecoResponseDTO> buscarPorCliente(Long clienteId) throws ResourceNotFoundException {

        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente nÃ£o encontrado com ID: " + clienteId);
        }

        List<Endereco> enderecos = enderecoRepository.findByClienteId(clienteId);

        return enderecos.stream()
                .map(EnderecoResponseDTO::new)
                .collect(Collectors.toList());
    }

 
    @Transactional
    public EnderecoResponseDTO atualizar(Long clienteId, Long enderecoId, EnderecoRequestDTO dto) throws ResourceNotFoundException {

        Endereco enderecoExistente = enderecoRepository.findByIdAndClienteId(enderecoId, clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("EndereÃ§o nÃ£o encontrado ou nÃ£o pertence ao Cliente com ID: " + clienteId));

        EnderecoViaCepDTO dadosViaCep = viaCepService.buscarEnderecoPorCep(dto.getCep());

        if (dadosViaCep == null || dadosViaCep.getCep() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Novo CEP nÃ£o encontrado ou invÃ¡lido pelo ViaCEP.");
        }

        enderecoExistente.setCep(dto.getCep());
        enderecoExistente.setNumero(dto.getNumero());
        enderecoExistente.setComplemento(dto.getComplemento());
        enderecoExistente.setLogradouro(dadosViaCep.getLogradouro());
        enderecoExistente.setBairro(dadosViaCep.getBairro());
        enderecoExistente.setLocalidade(dadosViaCep.getLocalidade());
        enderecoExistente.setUf(dadosViaCep.getUf());

        enderecoExistente = enderecoRepository.save(enderecoExistente);

        return new EnderecoResponseDTO(enderecoExistente);
    }


    @Transactional
    public void deletar(Long clienteId, Long enderecoId) throws ResourceNotFoundException {

        Endereco endereco = enderecoRepository.findByIdAndClienteId(enderecoId, clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("EndereÃ§o nÃ£o encontrado ou nÃ£o pertence ao Cliente com ID: " + clienteId));

        enderecoRepository.delete(endereco);
    }
}