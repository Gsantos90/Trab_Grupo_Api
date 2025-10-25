package org.serratec.Cleantech.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }

    public void enviarEmailBoasVindas(String email, String nome) {
        try {
            String assunto = "🎉 Bem-vindo à Cleantech!";
            String htmlBody = criarTemplateBoasVindas(nome);
            sendHtmlMessage(email, assunto, htmlBody);
        } catch (Exception e) {
         
        	
            String texto = String.format(
                "Olá, %s!\\n\\nSeja muito bem-vindo à Cleantech!\\n\\nAtenciosamente,\\nEquipe Cleantech", nome);
            sendSimpleMessage(email, "Bem-vindo à Cleantech", texto);
        }
    }

   
    public void enviarEmailAlteracaoDados(String email, String nome, List<String> alteracoes, String ipCliente) {
        try {
            String assunto = "🔒 Seus dados foram atualizados - Cleantech";
            String htmlBody = criarTemplateAlteracaoDados(nome, alteracoes, ipCliente);
            sendHtmlMessage(email, assunto, htmlBody);
        } catch (Exception e) {
   
            String texto = String.format(
                "Olá, %s!\\n\\nSeus dados foram atualizados.\\n\\nAlterações:\\n%s\\n\\nIP: %s\\n\\nSe não foi você, entre em contato.\\n\\nAtenciosamente,\\nEquipe Cleantech", 
                nome, String.join("\\n", alteracoes), ipCliente);
            sendSimpleMessage(email, "Dados Atualizados - Cleantech", texto);
        }
    }


    private String criarTemplateBoasVindas(String nome) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                    .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 10px; }
                    .header { background: #4CAF50; color: white; padding: 10px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { padding: 20px; }
                    .footer { margin-top: 20px; font-size: 12px; text-align: center; color: #666; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🌱 Cleantech</h1>
                    </div>
                    <div class="content">
                        <h2>Olá, %s!</h2>
                        <p>Seja muito bem-vindo à <strong>Cleantech</strong>!</p>
                        <p>Estamos felizes em tê-lo conosco. Aqui você encontrará soluções sustentáveis para um futuro melhor.</p>
                        <p>Explore nossos serviços e junte-se à nossa missão de preservar o meio ambiente.</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Cleantech. Todos os direitos reservados.</p>
                    </div>
                </div>
            </body>
            </html>
            """, nome);
    }

   
    private String criarTemplateAlteracaoDados(String nome, List<String> alteracoes, String ipCliente) {
        StringBuilder alteracoesHtml = new StringBuilder();
        for (String alteracao : alteracoes) {
            alteracoesHtml.append("<li>").append(alteracao).append("</li>");
        }

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                    .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 10px; }
                    .header { background: #2196F3; color: white; padding: 10px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { padding: 20px; }
                    .alteracoes { background: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }
                    .footer { margin-top: 20px; font-size: 12px; text-align: center; color: #666; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔒 Cleantech</h1>
                    </div>
                    <div class="content">
                        <h2>Olá, %s!</h2>
                        <p>Seus dados foram atualizados em nossa plataforma.</p>
                        <div class="alteracoes">
                            <h3>Alterações realizadas:</h3>
                            <ul>%s</ul>
                        </div>
                        <p><strong>IP:</strong> %s</p>
                        <p><strong>Data:</strong> %s</p>
                        <p>Se não foi você, entre em contato conosco imediatamente.</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Cleantech. Todos os direitos reservados.</p>
                    </div>
                </div>
            </body>
            </html>
            """, nome, alteracoesHtml.toString(), ipCliente, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}