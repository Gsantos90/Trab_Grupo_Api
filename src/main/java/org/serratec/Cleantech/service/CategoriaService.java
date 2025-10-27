package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Categoria;
import org.serratec.Cleantech.dto.CategoriaDTO; 
import org.serratec.Cleantech.repository.CategoriaRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors; 

@Service
public class CategoriaService {
    
    @Autowired 
    private CategoriaRepository categoriaRepository; 

    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }

    public CategoriaDTO inserir(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        
        Categoria categoriaSalva = categoriaRepository.save(categoria); 
        
        return toDTO(categoriaSalva); 
    }

    public List<CategoriaDTO> listarTodosDTO() {
        List<Categoria> categorias = categoriaRepository.findAll();
        
        return categorias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList()); 
    }


    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setNome(dto.getNome());
            
            Categoria categoriaAtualizada = categoriaRepository.save(categoria); 
            
            return toDTO(categoriaAtualizada);
        }
        
        return null; 
    }

    public void deletar(Long id) {
         categoriaRepository.deleteById(id);
    }
}