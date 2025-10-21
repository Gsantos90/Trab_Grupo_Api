
// src/main/java/org/serratec/Papelaria/dto/PedidoDTO.java
package org.serratec.Papelaria.dto;

public class PedidoDTO {
    private Long id;
    private String descricao;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
