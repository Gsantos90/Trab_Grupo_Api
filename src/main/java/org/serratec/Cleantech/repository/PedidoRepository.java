package org.serratec.Cleantech.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.serratec.Cleantech.Domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p.status, COUNT(p) FROM Pedido p GROUP BY p.status")
    List<Object[]> countPedidosByStatus();

       

    @Query("SELECT p.status, COUNT(p) FROM Pedido p WHERE p.dataPedido BETWEEN :start AND :end GROUP BY p.status")
    List<Object[]> countPedidosByStatusBetween(Instant start, Instant end);

}
