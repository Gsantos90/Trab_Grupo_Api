package org.serratec.Cleantech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoRequestDTO {


    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos")
    @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter apenas números")
    private String cep;


    @NotBlank(message = "O número do endereço é obrigatório")
    private String numero;

    private String complemento; 


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
}