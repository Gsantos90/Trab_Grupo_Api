package org.serratec.Cleantech.dto;

public class CategoriaDTO {
    private Long id;
    private String nome;

    public CategoriaDTO() {
        super();
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
}