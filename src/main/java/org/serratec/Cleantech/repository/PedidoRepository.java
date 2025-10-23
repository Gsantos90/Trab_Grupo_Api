package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}