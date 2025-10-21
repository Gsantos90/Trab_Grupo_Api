package org.serratec.Papelaria.repository;

import org.serratec.Papelaria.Domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
