
// src/main/java/org/serratec/Papelaria/controller/PedidoController.java
package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.Domain.Pedido;
import org.serratec.Cleantech.dto.PedidoDTO;
import org.serratec.Cleantech.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @PostMapping
    public Pedido inserir(@RequestBody PedidoDTO dto) {
        return service.inserir(dto);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody PedidoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
