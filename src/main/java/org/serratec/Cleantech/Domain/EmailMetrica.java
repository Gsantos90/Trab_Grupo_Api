package org.serratec.Cleantech.Domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_metrica")
public class EmailMetrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false)
    private String tipoEmail;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private boolean enviadoComSucesso;

    private String erro;


    public EmailMetrica() {
    }

    public EmailMetrica(String destinatario, String tipoEmail, LocalDateTime dataEnvio, boolean enviadoComSucesso, String erro) {
        this.destinatario = destinatario;
        this.tipoEmail = tipoEmail;
        this.dataEnvio = dataEnvio;
        this.enviadoComSucesso = enviadoComSucesso;
        this.erro = erro;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getTipoEmail() { return tipoEmail; }
    public void setTipoEmail(String tipoEmail) { this.tipoEmail = tipoEmail; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
    public boolean isEnviadoComSucesso() { return enviadoComSucesso; }
    public void setEnviadoComSucesso(boolean enviadoComSucesso) { this.enviadoComSucesso = enviadoComSucesso; }
    public String getErro() { return erro; }
    public void setErro(String erro) { this.erro = erro; }
}