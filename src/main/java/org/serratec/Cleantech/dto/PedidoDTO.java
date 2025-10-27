package org.serratec.Cleantech.dto;

import java.time.Instant;
import java.util.List;

public class PedidoDTO {
    
    private Long clienteId;
    private Instant data; 
    
    // Lista de Itens de ENTRADA (Input)
    private List<ItemPedidoDTO> itens; 
    
    // --- Getters e Setters ---

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}