
// src/main/java/org/serratec/Papelaria/controller/ClienteController.java
package org.serratec.Papelaria.controller;

import org.serratec.Papelaria.dto.ClienteDTO;
import org.serratec.Papelaria.Domain.Cliente;
import org.serratec.Papelaria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping
    public Cliente inserir(@RequestBody ClienteDTO dto) {
        return service.inserir(dto);
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
