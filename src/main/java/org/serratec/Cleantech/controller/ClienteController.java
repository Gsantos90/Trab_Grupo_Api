package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.ClienteResponseDTO;
import org.serratec.Cleantech.dto.ClienteDTO;
import org.serratec.Cleantech.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping
    public ClienteResponseDTO inserir(@RequestBody ClienteDTO dto) {
        return service.inserir(dto);
    }

    @GetMapping
    public List<ClienteResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}