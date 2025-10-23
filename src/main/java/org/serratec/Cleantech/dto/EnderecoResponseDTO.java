package org.serratec.Cleantech.dto;

import org.serratec.Cleantech.Domain.Endereco;

public class EnderecoResponseDTO {
    
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    

    private Long clienteId; 


    public EnderecoResponseDTO(Endereco entidade) {
        this.id = entidade.getId();
        this.cep = entidade.getCep();
        this.logradouro = entidade.getLogradouro();
        this.numero = entidade.getNumero();
        this.complemento = entidade.getComplemento();
        this.bairro = entidade.getBairro();
        this.localidade = entidade.getLocalidade();
        this.uf = entidade.getUf();
        this.clienteId = entidade.getCliente().getId(); 
    }


    public EnderecoResponseDTO() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}