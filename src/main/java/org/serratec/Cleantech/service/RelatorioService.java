
package org.serratec.Cleantech.service;

import org.serratec.Cleantech.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {
    private final PedidoRepository pedidoRepository;

    public RelatorioService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Map<String, Integer> getStatusRelatorio() {
        Map<String, Integer> relatorio = new HashMap<>();
        for (Object[] obj : pedidoRepository.countPedidosByStatus()) {
            relatorio.put((String) obj[0], ((Long) obj[1]).intValue());
        }
        return relatorio;
    }
}
