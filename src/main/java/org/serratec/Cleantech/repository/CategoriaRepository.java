package org.serratec.Cleantech.repository;

import org.serratec.Cleantech.Domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}