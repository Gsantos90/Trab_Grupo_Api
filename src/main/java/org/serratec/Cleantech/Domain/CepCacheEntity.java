package org.serratec.Cleantech.Domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cep_cache")
public class CepCacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 9, nullable = false, unique = true)
    private String cep;

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    @Column(name = "ultima_consulta")
    private LocalDateTime ultimaConsulta;

    // Construtores
    public CepCacheEntity() {}

    public CepCacheEntity(String cep, String logradouro, String bairro, String localidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.ultimaConsulta = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public LocalDateTime getUltimaConsulta() { return ultimaConsulta; }
    public void setUltimaConsulta(LocalDateTime ultimaConsulta) { this.ultimaConsulta = ultimaConsulta; }
}
