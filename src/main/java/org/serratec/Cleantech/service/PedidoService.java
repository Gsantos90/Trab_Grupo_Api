package org.serratec.Cleantech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.serratec.Cleantech.Domain.*;
import org.serratec.Cleantech.repository.*;
import org.serratec.Cleantech.dto.*; 
import org.serratec.Cleantech.dto.ItemPedidoResponseDTO; 
import org.serratec.Cleantech.exception.ResourceNotFoundException; 

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors; 

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ItemPedidoRepository itemPedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProdutoRepository produtoRepository; 

    private ItemPedidoResponseDTO toItemPedidoDTO(ItemPedido item) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setId(item.getId());
        dto.setValorVenda(item.getValorVenda());
        dto.setQuantidade(item.getQuantidade());
        dto.setDesconto(item.getDesconto());
        
        if (item.getProduto() != null) {
            dto.setProdutoId(item.getProduto().getId());
            dto.setProdutoNome(item.getProduto().getNome());
        }
        return dto;
    }
    
    private PedidoResponseDTO toPedidoDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setPercentualDesconto(pedido.getPercentualDesconto());
        
        dto.setValorBruto(pedido.getValorBruto());
        dto.setValorDesconto(pedido.getValorDesconto());
        
        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNome(pedido.getCliente().getNome());
        }
        
        if (pedido.getItens() != null) {
            List<ItemPedidoResponseDTO> itensDto = pedido.getItens().stream()
                                                    .map(this::toItemPedidoDTO)
                                                    .collect(Collectors.toList());
            dto.setItens(itensDto);
        }
        
        return dto;
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
        return toPedidoDTO(pedido);
    }
    
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toPedidoDTO)
                .collect(Collectors.toList());
    }
    

    public BigDecimal calcularDescontoProgressivo(BigDecimal valorBruto) {
        if (valorBruto.compareTo(new BigDecimal("1000.00")) >= 0) {
            return new BigDecimal("0.20");
        } else if (valorBruto.compareTo(new BigDecimal("500.00")) >= 0) {
            return new BigDecimal("0.15");
        } else if (valorBruto.compareTo(new BigDecimal("300.00")) >= 0) {
            return new BigDecimal("0.10");
        } else if (valorBruto.compareTo(new BigDecimal("100.00")) >= 0) {
            return new BigDecimal("0.05");
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Transactional
<<<<<<< HEAD
    public PedidoResponseDTO salvar(PedidoDTO dto) {
=======
    public Pedido salvar(PedidoDTO dto) {
        if (dto.getClienteId() == null) {
            throw new IllegalArgumentException("O campo 'clienteId' é obrigatório.");
        }
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter ao menos um item.");
        }
>>>>>>> f09ef77b2a4ad3cf1fd815a444e4f22c5c85fcb7

        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.getClienteId());
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("O cliente informado não existe. Verifique o 'clienteId'.");
        }
        Cliente cliente = clienteOpt.get();

        Pedido pedido = new Pedido();
        pedido.setDataPedido(dto.getData() != null ? dto.getData() : Instant.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.NOVO);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        BigDecimal valorTotalBruto = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDto : dto.getItens()) {
            if (itemDto.getProdutoId() == null) {
                throw new IllegalArgumentException("O campo 'produtoId' é obrigatório em cada item do pedido.");
            }
            if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("O campo 'quantidade' deve ser maior que zero em cada item do pedido.");
            }

            Optional<Produto> produtoOpt = produtoRepository.findById(itemDto.getProdutoId());
            if (produtoOpt.isEmpty()) {
                throw new IllegalArgumentException("O produto informado não existe. Verifique o 'produtoId'.");
            }
            Produto produto = produtoOpt.get();

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
<<<<<<< HEAD
        
        pedidoSalvo.setValorBruto(valorTotalBruto);
        pedidoSalvo.setValorDesconto(valorDescontoProgressivo);
=======
>>>>>>> f09ef77b2a4ad3cf1fd815a444e4f22c5c85fcb7

        pedidoSalvo.setPercentualDesconto(percentualDescontoProgressivo);
        pedidoSalvo.setValorTotal(valorTotalLiquido);

        Pedido pedidoFinal = pedidoRepository.save(pedidoSalvo);
        
        return toPedidoDTO(pedidoFinal); 
    }
    
    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id)); 
        
        pedido.setStatus(novoStatus); 
        
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        
        return toPedidoDTO(pedidoAtualizado);
    }
}