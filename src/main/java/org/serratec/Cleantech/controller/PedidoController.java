package org.serratec.Cleantech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.serratec.Cleantech.dto.PedidoDTO;
import org.serratec.Cleantech.Domain.Pedido;
import org.serratec.Cleantech.Domain.StatusPedido;
import org.serratec.Cleantech.service.PedidoService;

import java.util.List; 

@RestController
@RequestMapping("/pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> salvar(@RequestBody PedidoDTO pedidoDto) {
        Pedido novoPedido = pedidoService.salvar(pedidoDto);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {

        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestBody StatusPedido novoStatus) {

        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}