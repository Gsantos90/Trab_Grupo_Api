package org.serratec.Cleantech.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
  
    public void enviarEmailNovoCliente(String email, String nome) {
        System.out.println("--- EMAIL SERVICE SIMULADO ---");
        System.out.println("Atenção: A lógica real de envio de e-mail deve ser implementada aqui.");
        System.out.println("Simulando envio para: " + nome + " (" + email + ")");
    }
}