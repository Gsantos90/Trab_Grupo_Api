package org.serratec.Cleantech.Domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant dataPedido; 
    
    @ManyToOne 
    @JoinColumn(name = "cliente_id") 
    private Cliente cliente; 

    @Enumerated(EnumType.STRING)
    private StatusPedido status; 

    private BigDecimal valorTotal; 
    private BigDecimal percentualDesconto; 
    private BigDecimal valorBruto;
    private BigDecimal valorDesconto;


    public BigDecimal getValorBruto() {
		return valorBruto;
	}


	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}


	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}


	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ItemPedido> itens = new HashSet<>(); 

    
    public BigDecimal calcularValorBruto() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {

            if (item.getSubTotal() != null) { 
                total = total.add(item.getSubTotal());
            }
        }
        return total;
    }
    
    
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
}