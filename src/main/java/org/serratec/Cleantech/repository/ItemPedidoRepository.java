package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}