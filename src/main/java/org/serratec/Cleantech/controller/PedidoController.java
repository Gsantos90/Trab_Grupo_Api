package org.serratec.Cleantech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.serratec.Cleantech.dto.PedidoDTO;
import org.serratec.Cleantech.dto.PedidoResponseDTO;
import org.serratec.Cleantech.Domain.StatusPedido;
import org.serratec.Cleantech.service.PedidoService;

import java.util.List; 

@RestController
@RequestMapping("/pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvar(@RequestBody PedidoDTO pedidoDto) { // Retorna DTO
        PedidoResponseDTO novoPedido = pedidoService.salvar(pedidoDto);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) { // Retorna DTO
        PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() { // Retorna Lista de DTOs
        List<PedidoResponseDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestBody StatusPedido novoStatus) { // Retorna DTO
        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}