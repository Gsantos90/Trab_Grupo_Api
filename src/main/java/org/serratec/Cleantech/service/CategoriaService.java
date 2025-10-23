package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Categoria;
import org.serratec.Cleantech.dto.CategoriaDTO;
import org.serratec.Cleantech.repository.CategoriaRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional; 

@Service
public class CategoriaService {
    
    @Autowired 
    private CategoriaRepository categoriaRepository; 

    public Categoria inserir(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        
        return categoriaRepository.save(categoria); 
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria atualizar(Long id, CategoriaDTO dto) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setNome(dto.getNome());
            
            return categoriaRepository.save(categoria); 
        }
        
        return null; 
    }

    public void deletar(Long id) {
         categoriaRepository.deleteById(id);
    }
}