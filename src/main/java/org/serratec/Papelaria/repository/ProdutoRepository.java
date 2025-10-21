package org.serratec.Papelaria.repository;

import org.serratec.Papelaria.Domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
