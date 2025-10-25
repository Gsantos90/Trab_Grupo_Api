package org.serratec.Cleantech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
    
    @NotBlank(message = "Destinatário é obrigatório")
    @Email(message = "Email deve ser válido")
    private String to;
    
    private String subject;
    private String body;
    private String nome;

    public EmailDTO() {
    }

    public EmailDTO(String to, String subject, String body, String nome) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.nome = nome;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}