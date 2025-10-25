package org.serratec.Cleantech.repository;

import org.serratec.Cleantech.Domain.EmailMetrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailMetricaRepository extends JpaRepository<EmailMetrica, Long> {

    List<EmailMetrica> findByDestinatario(String destinatario);
    List<EmailMetrica> findByDataEnvioBetween(LocalDateTime inicio, LocalDateTime fim);
    long countByEnviadoComSucessoTrue();
    long countByEnviadoComSucessoFalse();
    
    @Query("SELECT COUNT(e) FROM EmailMetrica e WHERE e.tipoEmail = :tipoEmail AND e.enviadoComSucesso = true")
    long countSucessoPorTipo(String tipoEmail);
    
    @Query("SELECT e.tipoEmail, COUNT(e) FROM EmailMetrica e GROUP BY e.tipoEmail")
    List<Object[]> countPorTipoEmail();
}