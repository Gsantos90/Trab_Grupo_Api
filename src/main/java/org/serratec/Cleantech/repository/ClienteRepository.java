package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.Cliente; 

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}