
// src/main/java/org/serratec/Papelaria/service/PedidoService.java
package org.serratec.Papelaria.service;

import org.serratec.Papelaria.dto.PedidoDTO;
import org.serratec.Papelaria.Domain.Pedido;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private List<Pedido> pedidos = new ArrayList<>();

    public Pedido inserir(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setDescricao(dto.getDescricao());
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    public Pedido atualizar(Long id, PedidoDTO dto) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equals(id)) {
                pedido.setDescricao(dto.getDescricao());
                return pedido;
            }
        }
        return null;
    }

    public void deletar(Long id) {
        pedidos.removeIf(pedido -> pedido.getId().equals(id));
    }
}
