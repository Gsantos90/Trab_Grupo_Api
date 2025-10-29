package org.serratec.Cleantech.repository;

import java.util.Optional;

import org.serratec.Cleantech.Domain.CepCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CepCacheRepository extends JpaRepository<CepCacheEntity, Long> {
    Optional<CepCacheEntity> findByCep(String cep);
}