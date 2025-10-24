package org.serratec.Cleantech.service;

import org.serratec.Cleantech.Domain.StatusPedido;
import org.serratec.Cleantech.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class RelatorioService {
    private final PedidoRepository pedidoRepository;

    public RelatorioService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // método existente sem filtro temporal (mantido se quiser)
    public Map<String, Integer> getStatusRelatorio() {
        Map<String, Integer> relatorio = new HashMap<>();
        for (Object[] obj : pedidoRepository.countPedidosByStatus()) {
            Object statusObj = obj[0];
            Long count = (Long) obj[1];
            String key = (statusObj != null) ? statusObj.toString() : "NULL";
            relatorio.put(key, count.intValue());
        }
        return relatorio;
    }

    // Novo: conta por status para uma data específica
    public Map<String, Integer> getStatusRelatorioPorData(LocalDate data) {
        // intervalo: [data 00:00, data+1 00:00)
        LocalDateTime start = data.atStartOfDay();
        LocalDateTime end = data.plusDays(1).atStartOfDay();

        List<Object[]> rows = pedidoRepository.countPedidosByStatusBetween(start, end);

        // Inicializa com 0 para todos os possíveis status (opcional, mas costuma ser útil)
        Map<String, Integer> relatorio = new HashMap<>();
        for (StatusPedido s : StatusPedido.values()) {
            relatorio.put(s.name(), 0);
        }

        // Preenche com os resultados da query
        for (Object[] obj : rows) {
            Object statusObj = obj[0]; // geralmente é StatusPedido
            Long count = (Long) obj[1];
            String key = (statusObj != null) ? statusObj.toString() : "NULL";
            relatorio.put(key, count.intValue());
        }

        return relatorio;
    }
}
