package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.CategoriaDTO;
import org.serratec.Cleantech.Domain.Categoria; 
import org.serratec.Cleantech.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService service;

    @PostMapping
    public CategoriaDTO inserir(@RequestBody CategoriaDTO dto) { 
        return service.inserir(dto);
    }

    @GetMapping
    public List<CategoriaDTO> listarTodos() {
        return service.listarTodosDTO();
    }

    @PutMapping("/{id}")
    public CategoriaDTO atualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) { // <<< MUDANÇA: Retorna DTO
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}