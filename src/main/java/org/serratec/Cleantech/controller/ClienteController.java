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
import java.util.HashMap; // <<< Import necessário para o Map
import java.util.Map;     // <<< Import necessário para o Map

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

    // MÉTODO ALTERADO PARA RETORNAR 200 OK COM JSON DE MENSAGEM
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarComMensagem
    (@PathVariable Long id) {
        // 1. Executa o Soft Delete
        clienteService.desativarCliente(id); 
        
        // 2. Cria o JSON de sucesso
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("mensagem", "Cliente ID " + id + " desativado com sucesso.");
        
        // 3. Retorna o status 200 OK com o corpo JSON
        return ResponseEntity.ok(response);
    }
}