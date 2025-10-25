package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.ClienteRequestDTO; 
import org.serratec.Cleantech.dto.ClienteResponseDTO;
import org.serratec.Cleantech.service.ClienteService;
import org.serratec.Cleantech.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; 
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;
    
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.salvar(dto);

        // Enviar email de boas-vindas assíncrono
        new Thread(() -> {
            try {
                emailService.enviarEmailBoasVindas(response.getEmail(), response.getNome());
            } catch (Exception e) {
                System.err.println("Erro ao enviar email de boas-vindas: " + e.getMessage());
            }
        }).start();

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
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody ClienteRequestDTO dto,
            HttpServletRequest request) {
        
        ClienteResponseDTO clienteAntes = clienteService.buscarPorId(id);
        ClienteResponseDTO response = clienteService.editar(id, dto);
        
        List<String> alteracoes = identificarAlteracoes(clienteAntes, response);
     
        String ipCliente = request.getRemoteAddr();
        
        new Thread(() -> {
            try {
                emailService.enviarEmailAlteracaoDados(response.getEmail(), response.getNome(), alteracoes, ipCliente);
            } catch (Exception e) {
                System.err.println("Erro ao enviar email de alteração: " + e.getMessage());
            }
        }).start();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarComMensagem(@PathVariable Long id) {
        clienteService.desativarCliente(id); 
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCESSO");
        response.put("mensagem", "Cliente ID " + id + " desativado com sucesso.");
        
        return ResponseEntity.ok(response);
    }

    private List<String> identificarAlteracoes(ClienteResponseDTO antes, ClienteResponseDTO depois) {
        List<String> alteracoes = new ArrayList<>();
        
        if (!antes.getNome().equals(depois.getNome())) {
            alteracoes.add("Nome: " + antes.getNome() + " → " + depois.getNome());
        }
        if (!antes.getEmail().equals(depois.getEmail())) {
            alteracoes.add("Email: " + antes.getEmail() + " → " + depois.getEmail());
        }
        if (!antes.getCpf().equals(depois.getCpf())) {
            alteracoes.add("CPF: " + antes.getCpf() + " → " + depois.getCpf());
        }
        if (!antes.getTelefone().equals(depois.getTelefone())) {
            alteracoes.add("Telefone: " + antes.getTelefone() + " → " + depois.getTelefone());
        }
    
        return alteracoes;
    }
}