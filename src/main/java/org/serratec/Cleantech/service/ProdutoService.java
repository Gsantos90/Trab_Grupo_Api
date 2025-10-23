package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Produto;
import org.serratec.Cleantech.Domain.Categoria;
import org.serratec.Cleantech.dto.ProdutoDTO;
import org.serratec.Cleantech.repository.ProdutoRepository; 
import org.serratec.Cleantech.repository.CategoriaRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional; 


@Service
public class ProdutoService {
    
    @Autowired 
    private ProdutoRepository produtoRepository; 
    
    @Autowired 
    private CategoriaRepository categoriaRepository; 


    public Produto inserir(ProdutoDTO dto) {

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

        
        return produtoRepository.save(produto); 
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    public Produto atualizar(Long id, ProdutoDTO dto) {
        
        Optional<Produto> produtoOpt = produtoRepository.findById(id);    
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setNome(dto.getNome());
            produto.setPreco(dto.getPreco());
            
            return produtoRepository.save(produto); 
        }
        
        return null; 
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public void deletarTodos() {
        produtoRepository.deleteAll();
    }
}