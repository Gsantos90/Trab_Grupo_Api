package org.serratec.Cleantech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.serratec.Cleantech.dto.PedidoDTO;
import org.serratec.Cleantech.Domain.Pedido;
import org.serratec.Cleantech.service.PedidoService;

@RestController
@RequestMapping("/pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> inserir(@RequestBody PedidoDTO pedidoDto) {
        Pedido novoPedido = pedidoService.salvar(pedidoDto);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
}