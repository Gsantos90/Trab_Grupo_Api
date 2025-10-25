package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.EmailMetrica;
import org.serratec.Cleantech.dto.InsightEmailDTO;
import org.serratec.Cleantech.repository.EmailMetricaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailRelatorioService {

    @Autowired
    private EmailMetricaRepository metricaRepository;

    public InsightEmailDTO gerarInsightsCompletos() {
        Long total = metricaRepository.count();
        Long sucesso = metricaRepository.countByEnviadoComSucessoTrue();
        Long falha = metricaRepository.countByEnviadoComSucessoFalse();
        
        return new InsightEmailDTO(
            total, sucesso, falha,
            gerarRelatoriosPorTipo(),
            gerarMetricasTempoReal(),
            gerarTopDestinatarios(10),
            gerarPrincipaisFalhas(),
            gerarRecomendacoes(sucesso, total),
            gerarTendencias()
        );
    }

    private List<InsightEmailDTO.RelatorioTipoEmail> gerarRelatoriosPorTipo() {
        return metricaRepository.countPorTipoEmail().stream()
            .map(result -> {
                String tipo = (String) result[0];
                Long total = (Long) result[1];
                Long sucessos = metricaRepository.countSucessoPorTipo(tipo);
                double taxaSucesso = total > 0 ? (double) sucessos / total * 100 : 0.0;
                
                return new InsightEmailDTO.RelatorioTipoEmail(
                    tipo,
                    total,
                    sucessos,
                    total - sucessos,
                    String.format("%.2f%%", taxaSucesso)
                );
            })
            .collect(Collectors.toList());
    }

    private InsightEmailDTO.MetricasTempoReal gerarMetricasTempoReal() {
        LocalDateTime inicioHoje = LocalDate.now().atStartOfDay();
        LocalDateTime fimHoje = LocalDateTime.now();
        
        List<EmailMetrica> metricasHoje = metricaRepository.findByDataEnvioBetween(inicioHoje, fimHoje);
        long emailsHoje = metricasHoje.stream().filter(EmailMetrica::isEnviadoComSucesso).count();
        
        LocalDateTime umaHoraAtras = LocalDateTime.now().minusHours(1);
        List<EmailMetrica> metricasUltimaHora = metricaRepository.findByDataEnvioBetween(umaHoraAtras, LocalDateTime.now());
        long ultimaHora = metricasUltimaHora.stream().filter(EmailMetrica::isEnviadoComSucesso).count();
        
        return new InsightEmailDTO.MetricasTempoReal(emailsHoje, ultimaHora);
    }

    private List<InsightEmailDTO.TopDestinatario> gerarTopDestinatarios(int limite) {
        Map<String, List<EmailMetrica>> agrupadoPorEmail = metricaRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(EmailMetrica::getDestinatario));

        return agrupadoPorEmail.entrySet().stream()
            .map(entry -> {
                String email = entry.getKey();
                List<EmailMetrica> metricas = entry.getValue();
                long total = metricas.size();
                long sucessos = metricas.stream().filter(EmailMetrica::isEnviadoComSucesso).count();
                double taxaSucesso = total > 0 ? (double) sucessos / total * 100 : 0.0;
                
                return new InsightEmailDTO.TopDestinatario(
                    email,
                    total,
                    sucessos,
                    String.format("%.2f%%", taxaSucesso)
                );
            })
            .sorted((d1, d2) -> Long.compare(d2.getTotal(), d1.getTotal()))
            .limit(limite)
            .collect(Collectors.toList());
    }

    private List<InsightEmailDTO.FalhaEmail> gerarPrincipaisFalhas() {
        return metricaRepository.findAll().stream()
            .filter(m -> !m.isEnviadoComSucesso())
            .sorted((m1, m2) -> m2.getDataEnvio().compareTo(m1.getDataEnvio()))
            .limit(10)
            .map(m -> new InsightEmailDTO.FalhaEmail(
                m.getDestinatario(),
                m.getTipoEmail(),
                m.getErro() != null ? m.getErro() : "Erro desconhecido",
                m.getDataEnvio()
            ))
            .collect(Collectors.toList());
    }

    private InsightEmailDTO.Recomendacoes gerarRecomendacoes(Long sucesso, Long total) {
        List<String> alertas = new ArrayList<>();
        List<String> melhorias = new ArrayList<>();
        List<String> sugestoes = Arrays.asList(
            "Monitorar taxa de abertura dos emails",
            "Implementar segmentação por comportamento",
            "Criar campanhas de re-engajamento"
        );

        if (total > 0) {
            double taxaSucesso = (double) sucesso / total * 100;
            if (taxaSucesso < 80) {
                alertas.add("Taxa de sucesso abaixo de 80%");
                melhorias.add("Implementar sistema de retentativa");
            }
        }

        return new InsightEmailDTO.Recomendacoes(alertas, melhorias, sugestoes);
    }

    private InsightEmailDTO.Tendencias gerarTendencias() {
        LocalDate hoje = LocalDate.now();
        Map<String, Long> enviosUltimaSemana = new LinkedHashMap<>();
        
        for (int i = 6; i >= 0; i--) {
            LocalDate data = hoje.minusDays(i);
            LocalDateTime inicio = data.atStartOfDay();
            LocalDateTime fim = data.atTime(LocalTime.MAX);
            
            Long totalDia = (long) metricaRepository.findByDataEnvioBetween(inicio, fim).size();
            enviosUltimaSemana.put(data.toString(), totalDia);
        }

        String tipoMaisUtilizado = metricaRepository.countPorTipoEmail().isEmpty() ? 
            "Nenhum" : (String) metricaRepository.countPorTipoEmail().get(0)[0];

        return new InsightEmailDTO.Tendencias(enviosUltimaSemana, tipoMaisUtilizado, "Últimos 7 dias");
    }

	public Object gerarRelatorioPorPeriodo(LocalDate inicio, LocalDate fim) {
		// TODO Auto-generated method stub
		return null;
	}
}