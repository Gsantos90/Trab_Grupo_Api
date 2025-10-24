package org.serratec.Cleantech.controller;

import java.util.List;

import org.serratec.Cleantech.Domain.Endereco;
import org.serratec.Cleantech.dto.EnderecoDTO;
import org.serratec.Cleantech.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        return ResponseEntity.ok(enderecoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Endereco> salvar(@RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(enderecoService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(enderecoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}