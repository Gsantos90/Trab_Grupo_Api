package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
   
	Optional<Cliente> findByCpfAndAtivoTrue(String cpf);

	Optional<Cliente> findByEmailAndAtivoTrue(String email);
     
	List<Cliente> findByAtivoTrue();

	Optional<Cliente> findByIdAndAtivoTrue(Long id);

}