package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.Domain.Produto;
import org.serratec.Cleantech.dto.ProdutoDTO;
import org.serratec.Cleantech.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;

    @PostMapping
    public Produto inserir(@RequestBody ProdutoDTO dto) {
        return service.inserir(dto);
    }
    
    @GetMapping
    public List<Produto> listarTodos() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
    
    @DeleteMapping 
    public ResponseEntity<Void> deletarTodos() {
        service.deletarTodos(); 

        return ResponseEntity.noContent().build(); 
    }
}