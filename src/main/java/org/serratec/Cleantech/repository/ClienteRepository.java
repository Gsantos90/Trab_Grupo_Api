package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpf(String cpf);

	Optional<Cliente> findByEmail(String email);

	List<Cliente> findByAtivoTrue();

	Optional<Cliente> findByIdAndAtivoTrue(Long id);

}