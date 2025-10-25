package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.ClienteRequestDTO; 
import org.serratec.Cleantech.dto.ClienteResponseDTO;
import org.serratec.Cleantech.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; 

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService; 
    
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.salvar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }
    
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<ClienteResponseDTO> response = clienteService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar
    (@PathVariable Long id, 
    @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.editar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar
    (@PathVariable Long id) {
        clienteService.desativarCliente(id); 
        return ResponseEntity.noContent().build(); 
    }
}