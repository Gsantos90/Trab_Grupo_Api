package org.serratec.Cleantech.repository;

import org.serratec.Cleantech.Domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByIdAndClienteId(Long id, Long clienteId);

    List<Endereco> findByClienteId(Long clienteId);
}