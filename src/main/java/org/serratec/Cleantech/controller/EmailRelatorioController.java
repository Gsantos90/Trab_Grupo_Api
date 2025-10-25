package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.InsightEmailDTO;
import org.serratec.Cleantech.service.EmailRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Cleantech/relatorios-email")
public class EmailRelatorioController {

    @Autowired
    private EmailRelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<InsightEmailDTO> getInsightsCompletos() {
        return ResponseEntity.ok(relatorioService.gerarInsightsCompletos());
    }

    @GetMapping("/periodo")
    public ResponseEntity<Object> getRelatorioPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(relatorioService.gerarRelatorioPorPeriodo(inicio, fim));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        InsightEmailDTO insights = relatorioService.gerarInsightsCompletos();
        
        Map<String, Object> dashboard = new HashMap<>();
        
        // Estatísticas básicas
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("total", insights.getTotalEmails());
        estatisticas.put("sucesso", insights.getEmailsSucesso());
        estatisticas.put("falha", insights.getEmailsFalha());
        estatisticas.put("taxaSucesso", insights.getTaxaSucessoFormatada());
        dashboard.put("estatisticas", estatisticas);
   
        if (insights.getMetricasTempoReal() != null) {
            Map<String, Object> metricasTempoReal = new HashMap<>();
            metricasTempoReal.put("emailsHoje", insights.getMetricasTempoReal().getEmailsHoje());
            metricasTempoReal.put("emailsUltimaHora", insights.getMetricasTempoReal().getEmailsUltimaHora());
            dashboard.put("metricasTempoReal", metricasTempoReal);
        }
        
        
        if (insights.getTopDestinatarios() != null) {
            dashboard.put("topDestinatarios", 
                insights.getTopDestinatarios().stream()
                    .limit(5)
                    .map(dest -> {
                        Map<String, Object> destMap = new HashMap<>();
                        destMap.put("email", dest.getEmail());
                        destMap.put("total", dest.getTotal());
                        destMap.put("sucessos", dest.getSucessos());
                        destMap.put("taxaSucesso", dest.getTaxaSucesso());
                        return destMap;
                    })
                    .toList()
            );
        }
        
     
        if (insights.getRecomendacoes() != null) {
            Map<String, Object> recomendacoes = new HashMap<>();
            recomendacoes.put("alertas", insights.getRecomendacoes().getAlertas());
            recomendacoes.put("melhorias", insights.getRecomendacoes().getMelhorias());
            recomendacoes.put("sugestoes", insights.getRecomendacoes().getSugestoes());
            recomendacoes.put("totalRecomendacoes", 
                insights.getRecomendacoes().getAlertas().size() +
                insights.getRecomendacoes().getMelhorias().size() +
                insights.getRecomendacoes().getSugestoes().size()
            );
            dashboard.put("recomendacoes", recomendacoes);
        }
        
       
        if (insights.getTendencias() != null) {
            Map<String, Object> tendenciasResumo = new HashMap<>();
            tendenciasResumo.put("tipoMaisUtilizado", insights.getTendencias().getTipoMaisUtilizado());
            tendenciasResumo.put("periodoAnalisado", insights.getTendencias().getPeriodoAnalisado());
            
            if (insights.getTendencias().getEnviosUltimaSemana() != null) {
                tendenciasResumo.put("totalEnviosPeriodo", 
                    insights.getTendencias().getEnviosUltimaSemana().values().stream()
                        .mapToLong(Long::longValue)
                        .sum()
                );
                tendenciasResumo.put("diasAnalisados", insights.getTendencias().getEnviosUltimaSemana().size());
            }
            
            dashboard.put("tendencias", tendenciasResumo);
        }
        
        return ResponseEntity.ok(dashboard);
    }
}