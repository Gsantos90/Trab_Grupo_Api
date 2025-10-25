package org.serratec.Cleantech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.serratec.Cleantech.Domain.*;
import org.serratec.Cleantech.repository.*;
import org.serratec.Cleantech.dto.*;
import org.serratec.Cleantech.exception.ResourceNotFoundException; 

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List; 
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ItemPedidoRepository itemPedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProdutoRepository produtoRepository; 
    
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id)); 
    }
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    

    public BigDecimal calcularDescontoProgressivo(BigDecimal valorBruto) {
        if (valorBruto.compareTo(new BigDecimal("1000.00")) >= 0) {
            return new BigDecimal("0.20"); // 20%
        } else if (valorBruto.compareTo(new BigDecimal("500.00")) >= 0) {
            return new BigDecimal("0.15"); // 15%
        } else if (valorBruto.compareTo(new BigDecimal("300.00")) >= 0) {
            return new BigDecimal("0.10"); // 10%
        } else if (valorBruto.compareTo(new BigDecimal("100.00")) >= 0) {
            return new BigDecimal("0.05"); // 5%
        } else {
            return BigDecimal.ZERO; // 0%
        }
    }


    @Transactional
    public Pedido salvar(PedidoDTO dto) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.getClienteId());
        Cliente cliente = clienteOpt.orElseThrow(() -> 
            new ResourceNotFoundException("Cliente não encontrado com ID: " + dto.getClienteId()));


        Pedido pedido = new Pedido();
        pedido.setDataPedido(dto.getData() != null ? dto.getData() : Instant.now());
        pedido.setCliente(cliente); 
        pedido.setStatus(StatusPedido.NOVO); 
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        BigDecimal valorTotalBruto = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDto : dto.getItens()) {
            
            Optional<Produto> produtoOpt = produtoRepository.findById(itemDto.getProdutoId());
            Produto produto = produtoOpt.orElseThrow(() -> 
                new ResourceNotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId()));

            ItemPedido item = new ItemPedido();
            item.setPedido(pedidoSalvo);
            item.setProduto(produto);

            BigDecimal precoUnitario = produto.getPreco();
            
            item.setValorVenda(precoUnitario); 
            item.setQuantidade(itemDto.getQuantidade());
            item.setDesconto(itemDto.getDesconto());
            
            BigDecimal valorItemBruto = precoUnitario.multiply(new BigDecimal(itemDto.getQuantidade()));
            valorTotalBruto = valorTotalBruto.add(valorItemBruto);

            itemPedidoRepository.save(item);
            pedidoSalvo.getItens().add(item);
        }
        
        System.out.println("DEBUG: Valor Bruto ANTES do Desconto: " + valorTotalBruto); 

        BigDecimal percentualDescontoProgressivo = calcularDescontoProgressivo(valorTotalBruto);
        BigDecimal valorDescontoProgressivo = valorTotalBruto.multiply(percentualDescontoProgressivo);      
        BigDecimal valorTotalLiquido = valorTotalBruto.subtract(valorDescontoProgressivo);
        
        pedidoSalvo.setPercentualDesconto(percentualDescontoProgressivo);
        
        pedidoSalvo.setValorTotal(valorTotalLiquido);

        return pedidoRepository.save(pedidoSalvo);
    }
    
    @Transactional
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        
        Pedido pedido = buscarPorId(id); 
        pedido.setStatus(novoStatus); 
        
        return pedidoRepository.save(pedido);
    }
}
