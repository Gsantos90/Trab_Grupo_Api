package org.serratec.Cleantech.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InsightEmailDTO {
    private Long totalEmails;
    private Long emailsSucesso;
    private Long emailsFalha;
    private Double taxaSucesso;
    
    // Substituindo Map por classes específicas
    private List<RelatorioTipoEmail> relatoriosPorTipo;
    private MetricasTempoReal metricasTempoReal;
    private List<TopDestinatario> topDestinatarios;
    private List<FalhaEmail> principaisFalhas;
    private Recomendacoes recomendacoes;
    private Tendencias tendencias;

    // Construtor básico
    public InsightEmailDTO(Long totalEmails, Long emailsSucesso, Long emailsFalha) {
        this.totalEmails = totalEmails;
        this.emailsSucesso = emailsSucesso;
        this.emailsFalha = emailsFalha;
        this.taxaSucesso = totalEmails > 0 ? (double) emailsSucesso / totalEmails * 100 : 0.0;
    }

    // Construtor completo
    public InsightEmailDTO(Long totalEmails, Long emailsSucesso, Long emailsFalha,
                          List<RelatorioTipoEmail> relatoriosPorTipo,
                          MetricasTempoReal metricasTempoReal,
                          List<TopDestinatario> topDestinatarios,
                          List<FalhaEmail> principaisFalhas,
                          Recomendacoes recomendacoes,
                          Tendencias tendencias) {
        this(totalEmails, emailsSucesso, emailsFalha);
        this.relatoriosPorTipo = relatoriosPorTipo;
        this.metricasTempoReal = metricasTempoReal;
        this.topDestinatarios = topDestinatarios;
        this.principaisFalhas = principaisFalhas;
        this.recomendacoes = recomendacoes;
        this.tendencias = tendencias;
    }

    // Getters e Setters
    public Long getTotalEmails() { return totalEmails; }
    public void setTotalEmails(Long totalEmails) { this.totalEmails = totalEmails; }

    public Long getEmailsSucesso() { return emailsSucesso; }
    public void setEmailsSucesso(Long emailsSucesso) { this.emailsSucesso = emailsSucesso; }

    public Long getEmailsFalha() { return emailsFalha; }
    public void setEmailsFalha(Long emailsFalha) { this.emailsFalha = emailsFalha; }

    public Double getTaxaSucesso() { return taxaSucesso; }
    public void setTaxaSucesso(Double taxaSucesso) { this.taxaSucesso = taxaSucesso; }

    public List<RelatorioTipoEmail> getRelatoriosPorTipo() { return relatoriosPorTipo; }
    public void setRelatoriosPorTipo(List<RelatorioTipoEmail> relatoriosPorTipo) { this.relatoriosPorTipo = relatoriosPorTipo; }

    public MetricasTempoReal getMetricasTempoReal() { return metricasTempoReal; }
    public void setMetricasTempoReal(MetricasTempoReal metricasTempoReal) { this.metricasTempoReal = metricasTempoReal; }

    public List<TopDestinatario> getTopDestinatarios() { return topDestinatarios; }
    public void setTopDestinatarios(List<TopDestinatario> topDestinatarios) { this.topDestinatarios = topDestinatarios; }

    public List<FalhaEmail> getPrincipaisFalhas() { return principaisFalhas; }
    public void setPrincipaisFalhas(List<FalhaEmail> principaisFalhas) { this.principaisFalhas = principaisFalhas; }

    public Recomendacoes getRecomendacoes() { return recomendacoes; }
    public void setRecomendacoes(Recomendacoes recomendacoes) { this.recomendacoes = recomendacoes; }

    public Tendencias getTendencias() { return tendencias; }
    public void setTendencias(Tendencias tendencias) { this.tendencias = tendencias; }

    public String getTaxaSucessoFormatada() {
        return String.format("%.2f%%", taxaSucesso != null ? taxaSucesso : 0.0);
    }

    public void atualizarTaxaSucesso() {
        this.taxaSucesso = totalEmails > 0 ? (double) emailsSucesso / totalEmails * 100 : 0.0;
    }

    // Classes internas para substituir os Map

    public static class RelatorioTipoEmail {
        private String tipo;
        private Long total;
        private Long sucessos;
        private Long falhas;
        private String taxaSucesso;

        public RelatorioTipoEmail() {}

        public RelatorioTipoEmail(String tipo, Long total, Long sucessos, Long falhas, String taxaSucesso) {
            this.tipo = tipo;
            this.total = total;
            this.sucessos = sucessos;
            this.falhas = falhas;
            this.taxaSucesso = taxaSucesso;
        }

        // Getters e Setters
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public Long getTotal() { return total; }
        public void setTotal(Long total) { this.total = total; }

        public Long getSucessos() { return sucessos; }
        public void setSucessos(Long sucessos) { this.sucessos = sucessos; }

        public Long getFalhas() { return falhas; }
        public void setFalhas(Long falhas) { this.falhas = falhas; }

        public String getTaxaSucesso() { return taxaSucesso; }
        public void setTaxaSucesso(String taxaSucesso) { this.taxaSucesso = taxaSucesso; }
    }

    public static class MetricasTempoReal {
        private Long emailsHoje;
        private Long emailsUltimaHora;

        public MetricasTempoReal() {}

        public MetricasTempoReal(Long emailsHoje, Long emailsUltimaHora) {
            this.emailsHoje = emailsHoje;
            this.emailsUltimaHora = emailsUltimaHora;
        }

        // Getters e Setters
        public Long getEmailsHoje() { return emailsHoje; }
        public void setEmailsHoje(Long emailsHoje) { this.emailsHoje = emailsHoje; }

        public Long getEmailsUltimaHora() { return emailsUltimaHora; }
        public void setEmailsUltimaHora(Long emailsUltimaHora) { this.emailsUltimaHora = emailsUltimaHora; }
    }

    public static class TopDestinatario {
        private String email;
        private Long total;
        private Long sucessos;
        private String taxaSucesso;

        public TopDestinatario() {}

        public TopDestinatario(String email, Long total, Long sucessos, String taxaSucesso) {
            this.email = email;
            this.total = total;
            this.sucessos = sucessos;
            this.taxaSucesso = taxaSucesso;
        }

        // Getters e Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Long getTotal() { return total; }
        public void setTotal(Long total) { this.total = total; }

        public Long getSucessos() { return sucessos; }
        public void setSucessos(Long sucessos) { this.sucessos = sucessos; }

        public String getTaxaSucesso() { return taxaSucesso; }
        public void setTaxaSucesso(String taxaSucesso) { this.taxaSucesso = taxaSucesso; }
    }

    public static class FalhaEmail {
        private String destinatario;
        private String tipo;
        private String erro;
        private LocalDateTime data;

        public FalhaEmail() {}

        public FalhaEmail(String destinatario, String tipo, String erro, LocalDateTime data) {
            this.destinatario = destinatario;
            this.tipo = tipo;
            this.erro = erro;
            this.data = data;
        }

        // Getters e Setters
        public String getDestinatario() { return destinatario; }
        public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public String getErro() { return erro; }
        public void setErro(String erro) { this.erro = erro; }

        public LocalDateTime getData() { return data; }
        public void setData(LocalDateTime data) { this.data = data; }
    }

    public static class Recomendacoes {
        private List<String> alertas;
        private List<String> melhorias;
        private List<String> sugestoes;

        public Recomendacoes() {}

        public Recomendacoes(List<String> alertas, List<String> melhorias, List<String> sugestoes) {
            this.alertas = alertas;
            this.melhorias = melhorias;
            this.sugestoes = sugestoes;
        }

        // Getters e Setters
        public List<String> getAlertas() { return alertas; }
        public void setAlertas(List<String> alertas) { this.alertas = alertas; }

        public List<String> getMelhorias() { return melhorias; }
        public void setMelhorias(List<String> melhorias) { this.melhorias = melhorias; }

        public List<String> getSugestoes() { return sugestoes; }
        public void setSugestoes(List<String> sugestoes) { this.sugestoes = sugestoes; }
    }

    public static class Tendencias {
        private Map<String, Long> enviosUltimaSemana; // Mantido como Map por ser estrutura chave-valor natural
        private String tipoMaisUtilizado;
        private String periodoAnalisado;

        public Tendencias() {}

        public Tendencias(Map<String, Long> enviosUltimaSemana, String tipoMaisUtilizado, String periodoAnalisado) {
            this.enviosUltimaSemana = enviosUltimaSemana;
            this.tipoMaisUtilizado = tipoMaisUtilizado;
            this.periodoAnalisado = periodoAnalisado;
        }

        // Getters e Setters
        public Map<String, Long> getEnviosUltimaSemana() { return enviosUltimaSemana; }
        public void setEnviosUltimaSemana(Map<String, Long> enviosUltimaSemana) { this.enviosUltimaSemana = enviosUltimaSemana; }

        public String getTipoMaisUtilizado() { return tipoMaisUtilizado; }
        public void setTipoMaisUtilizado(String tipoMaisUtilizado) { this.tipoMaisUtilizado = tipoMaisUtilizado; }

        public String getPeriodoAnalisado() { return periodoAnalisado; }
        public void setPeriodoAnalisado(String periodoAnalisado) { this.periodoAnalisado = periodoAnalisado; }
    }
}