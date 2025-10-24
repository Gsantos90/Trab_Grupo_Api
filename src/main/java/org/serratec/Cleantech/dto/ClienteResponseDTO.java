package org.serratec.Cleantech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteResponseDTO {
	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private String cpf;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String numero;
	private boolean ativo;

	public ClienteResponseDTO() {
	}

	public ClienteResponseDTO(
		Long id, String nome, String telefone, String email, String cpf, String cep,
		String logradouro, String complemento, String bairro, String cidade, String uf, String numero,
		boolean ativo) {

		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.numero = numero;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getEnderecoCompleto() {

		if (logradouro == null && cidade == null) {
			return null;
		}

		StringBuilder enderecoCompleto = new StringBuilder();
		if (logradouro != null) {
			enderecoCompleto.append(logradouro);
		}
		if (numero != null && !numero.trim().isEmpty()) {
			enderecoCompleto.append(", ").append(numero);
		}
		if (complemento != null && !complemento.isEmpty()) {
			enderecoCompleto.append(" - ").append(complemento);
		}
		if (bairro != null) {
			enderecoCompleto.append(" - ").append(bairro);
		}
		if (cidade != null) {
			enderecoCompleto.append(", ").append(cidade);
		}
		if (uf != null) {
			enderecoCompleto.append(" - ").append(uf);
		}

		return enderecoCompleto.toString();
	}
}