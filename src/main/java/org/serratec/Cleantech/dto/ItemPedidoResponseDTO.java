package org.serratec.Cleantech.dto;

import java.math.BigDecimal;

public class ItemPedidoResponseDTO {
    
    private Long id; // ID do ItemPedido (gerado pelo banco)
    private Long produtoId;
    private String produtoNome; // Adicionamos o nome para ser informativo na resposta
    private Integer quantidade;
    private BigDecimal valorVenda; // Preço unitário do produto no momento da venda
    private BigDecimal desconto; // Desconto aplicado a este item

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) { // Corrigindo: 'void' aqui deveria ser 'setQuantidade'
        this.quantidade = quantidade;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}