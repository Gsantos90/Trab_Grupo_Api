package org.serratec.Cleantech.controller;

import org.serratec.Cleantech.dto.EmailDTO;
import org.serratec.Cleantech.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Cleantech/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.sendSimpleMessage(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
            return ResponseEntity.ok("E-mail enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    @PostMapping("/send-html")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.sendHtmlMessage(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
            return ResponseEntity.ok("E-mail HTML enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    @PostMapping("/send-welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.enviarEmailBoasVindas(emailDTO.getTo(), emailDTO.getNome());
            return ResponseEntity.ok("E-mail de boas-vindas enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}