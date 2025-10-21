package org.serratec.Papelaria.controller;

import org.serratec.Papelaria.dto.ProdutoDTO;
import org.serratec.Papelaria.Domain.Produto;
import org.serratec.Papelaria.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // PUT - Atualiza um produto existente
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return service.atualizar(id, dto);
    }

    // DELETE - Remove um produto pelo id
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
