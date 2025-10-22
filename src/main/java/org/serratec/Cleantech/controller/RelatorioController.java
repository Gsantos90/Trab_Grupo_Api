
package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.service.RelatorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/relatorio/status-pedidos")
    public Map<String, Integer> getRelatorioStatusPedidos() {
        return relatorioService.getStatusRelatorio();
    }
}
