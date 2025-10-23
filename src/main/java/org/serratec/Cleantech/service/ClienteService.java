package org.serratec.Cleantech.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.Cleantech.Domain.Cliente;
import org.serratec.Cleantech.dto.ClienteDTO;
import org.serratec.Cleantech.dto.ClienteResponseDTO;
import org.serratec.Cleantech.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;

    public static class ViaCepResponse {
        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;
        
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }
        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
        public String getComplemento() { return complemento; }
        public void setComplemento(String complemento) { this.complemento = complemento; }
        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }
        public String getLocalidade() { return localidade; }
        public void setLocalidade(String localidade) { this.localidade = localidade; }
        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
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
        dto.setComplemento(cliente.getComplemento()); 
        dto.setBairro(cliente.getBairro());
        dto.setLocalidade(cliente.getLocalidade());
        dto.setUf(cliente.getUf());
        dto.setNumero(cliente.getNumero());
        
        return dto;
    }

    public Cliente salvar(Cliente cliente) {
        if (cliente.getCep() == null || cliente.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser nulo ou vazio");
        }

        String cep = cliente.getCep().replaceAll("\\D", "");
      
        if (cep.length() != 8) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos");
        }
        
        try {
            RestTemplate rest = new RestTemplate();
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            
            ViaCepResponse enderecoViaCep = rest.getForObject(url, ViaCepResponse.class);
           
            if (enderecoViaCep != null && enderecoViaCep.getCep() != null) {
              
                cliente.setLogradouro(enderecoViaCep.getLogradouro());
              
                cliente.setBairro(enderecoViaCep.getBairro());
                cliente.setLocalidade(enderecoViaCep.getLocalidade());
                cliente.setUf(enderecoViaCep.getUf());
                
           
                String enderecoFormatado = formatarEndereco(enderecoViaCep, cliente.getNumero(), cliente.getComplemento());
                cliente.setEndereco(enderecoFormatado);
            } else {
                throw new IllegalArgumentException("CEP não encontrado: " + cep);
            }
            
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("CEP não encontrado: " + cep);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ViaCEP: " + e.getMessage());
        }

        return repo.save(cliente);
    }

    private String formatarEndereco(ViaCepResponse enderecoViaCep, String numeroCliente, String complementoCliente) {
        StringBuilder sb = new StringBuilder();
        
        if (enderecoViaCep.getLogradouro() != null) {
            sb.append(enderecoViaCep.getLogradouro());
        }
        
        
        if (numeroCliente != null && !numeroCliente.trim().isEmpty()) {
            sb.append(", ").append(numeroCliente);
        }
        
        
        if (complementoCliente != null && !complementoCliente.trim().isEmpty()) {
            sb.append(" - ").append(complementoCliente);
        }

        else if (enderecoViaCep.getComplemento() != null && !enderecoViaCep.getComplemento().isEmpty()) {
            sb.append(" - ").append(enderecoViaCep.getComplemento());
        }
        
        if (enderecoViaCep.getBairro() != null && !enderecoViaCep.getBairro().isEmpty()) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(enderecoViaCep.getBairro());
        }
        
        if (enderecoViaCep.getLocalidade() != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(enderecoViaCep.getLocalidade());
        }
        
        if (enderecoViaCep.getUf() != null) {
            if (sb.length() > 0) sb.append(" - ");
            sb.append(enderecoViaCep.getUf());
        }
        
        return sb.toString();
    }

    public ClienteResponseDTO salvar(ClienteDTO dto) {
        if (dto.getCep() == null || dto.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCep(dto.getCep());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento()); 

        Cliente clienteSalvo = salvar(cliente);
        return toResponseDTO(clienteSalvo);
    }

    public ClienteResponseDTO inserir(ClienteDTO dto) {
        return salvar(dto);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repo.findById(id).orElseThrow();
        return toResponseDTO(cliente);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteDTO dto) {
        if (dto.getCep() == null || dto.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        
        Cliente cliente = repo.findById(id).orElseThrow();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCep(dto.getCep());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento()); 

        Cliente clienteAtualizado = salvar(cliente);
        return toResponseDTO(clienteAtualizado);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}