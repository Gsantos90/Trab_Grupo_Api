package org.serratec.Cleantech.repository;

import org.serratec.Cleantech.Domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
