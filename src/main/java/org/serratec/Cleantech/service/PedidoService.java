package org.serratec.Cleantech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.serratec.Cleantech.Domain.*;
import org.serratec.Cleantech.repository.*;
import org.serratec.Cleantech.dto.*;
import org.serratec.Cleantech.exception.ResourceNotFoundException; 

import java.time.Instant;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ItemPedidoRepository itemPedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProdutoRepository produtoRepository; 
    // GET
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id)); 
    }
    
    // POST
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

        for (ItemPedidoDTO itemDto : dto.getItens()) {
            
            Optional<Produto> produtoOpt = produtoRepository.findById(itemDto.getProdutoId());
            Produto produto = produtoOpt.orElseThrow(() -> 
                new ResourceNotFoundException("Produto não encontrado com ID: " + itemDto.getProdutoId()));

            ItemPedido item = new ItemPedido();
            item.setPedido(pedidoSalvo);
            item.setProduto(produto);

            // Setar o preço de venda e os detalhes do DTO
            item.setValorVenda(produto.getPreco()); 
            item.setQuantidade(itemDto.getQuantidade());
            item.setDesconto(itemDto.getDesconto());
            
            itemPedidoRepository.save(item);
            pedidoSalvo.getItens().add(item);
        }

        return pedidoSalvo;
    }
}