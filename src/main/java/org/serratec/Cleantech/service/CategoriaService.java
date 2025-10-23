

package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.Categoria;
import org.serratec.Cleantech.dto.CategoriaDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    private List<Categoria> categorias = new ArrayList<>();

    public Categoria inserir(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categorias.add(categoria);
        return categoria;
    }

    public List<Categoria> listarTodos() {
        return categorias;
    }

    public Categoria atualizar(Long id, CategoriaDTO dto) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id)) {
                categoria.setNome(dto.getNome());
                return categoria;
            }
        }
        return null;
    }

    public void deletar(Long id) {
        categorias.removeIf(categoria -> categoria.getId().equals(id));
    }
}
