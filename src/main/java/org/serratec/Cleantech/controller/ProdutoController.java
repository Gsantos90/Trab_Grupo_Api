package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.ProdutoDTO;
import org.serratec.Cleantech.dto.ProdutoResponseDTO;
import org.serratec.Cleantech.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;

    @PostMapping
    public ProdutoResponseDTO inserir(@RequestBody ProdutoDTO dto) { 
        return service.inserir(dto);
    }
    
    @GetMapping
    public List<ProdutoResponseDTO> listarTodos() { 
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) { 
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}