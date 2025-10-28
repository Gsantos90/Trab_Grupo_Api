package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Produto;
import org.serratec.Cleantech.Domain.Categoria;
import org.serratec.Cleantech.dto.ProdutoDTO;
import org.serratec.Cleantech.dto.ProdutoResponseDTO; 
import org.serratec.Cleantech.repository.ProdutoRepository; 
import org.serratec.Cleantech.repository.CategoriaRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors; 

@Service
public class ProdutoService {
    
    @Autowired 
    private ProdutoRepository produtoRepository; 
    
    @Autowired 
    private CategoriaRepository categoriaRepository; 

    private ProdutoResponseDTO toDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        
        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        }
        
        return dto;
    }


    public ProdutoResponseDTO inserir(ProdutoDTO dto) {

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());

        Optional<Categoria> categoriaOpt = categoriaRepository.findById(dto.getCategoriaId());

        if (categoriaOpt.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Categoria com ID " + dto.getCategoriaId() + " não encontrada para associação."
            ); 
        }
        
        produto.setCategoria(categoriaOpt.get());
        
        Produto produtoSalvo = produtoRepository.save(produto); 
        
        return toDTO(produtoSalvo); 
    }

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoResponseDTO atualizar(Long id, ProdutoDTO dto) {
        
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + id));

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> 
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Categoria com ID " + dto.getCategoriaId() + " não encontrada para atualização."
                )
            );
            produto.setCategoria(categoria);
        }
        
        Produto produtoAtualizado = produtoRepository.save(produto); 
        
        return toDTO(produtoAtualizado);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public void deletarTodos() { 
        produtoRepository.deleteAll();
    } 
}