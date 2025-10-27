package org.serratec.Cleantech.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.serratec.Cleantech.Domain.StatusPedido; // Se o StatusPedido for uma enum

public class PedidoResponseDTO {
    
    private Long id;
    private Instant dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal; 
    private BigDecimal percentualDesconto;

    // Dados do Cliente (Apenas ID e Nome, para segurança)
    private Long clienteId;
    private String clienteNome;
    
    // Lista de Itens de SAÍDA (Output)
    private List<ItemPedidoResponseDTO> itens;

    // --- Getters e Setters ---

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Instant dataPedido) {
		this.dataPedido = dataPedido;
	}
    
    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public List<ItemPedidoResponseDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoResponseDTO> itens) {
		this.itens = itens;
	} 

}