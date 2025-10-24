package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

   
    
    @GetMapping("/relatorio/status-pedidos")
    public Map<String, Integer> getRelatorioStatusPedidos(
            @RequestParam(name = "data", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        if (data == null) {
            data = LocalDate.now();
        }
        return relatorioService.getStatusRelatorioPorData(data);
    }
}
