package org.serratec.Cleantech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.serratec.Cleantech.Domain.Produto; 

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}