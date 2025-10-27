package org.serratec.Cleantech.dto;

import java.math.BigDecimal;

public class ItemPedidoDTO {
    
    private Long produtoId;
    
    private Integer quantidade;
    
    private BigDecimal desconto; 


    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}