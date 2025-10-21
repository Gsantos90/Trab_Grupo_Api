package org.serratec.Cleantech.repository;

import org.serratec.Cleantech.Domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
