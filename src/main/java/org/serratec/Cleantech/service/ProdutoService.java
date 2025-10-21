
package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Produto;
import org.serratec.Cleantech.dto.ProdutoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {
    private List<Produto> produtos = new ArrayList<>();

    public Produto inserir(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produtos.add(produto);
        return produto;
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public Produto atualizar(Long id, ProdutoDTO dto) {
        for (Produto produto : produtos) {
            if (produto.getId().equals(id)) {
                produto.setNome(dto.getNome());
                produto.setPreco(dto.getPreco());
                return produto;
            }
        }
        return null; // ou lance uma exceção se preferir
    }

    public void deletar(Long id) {
        produtos.removeIf(produto -> produto.getId().equals(id));
    }
}
