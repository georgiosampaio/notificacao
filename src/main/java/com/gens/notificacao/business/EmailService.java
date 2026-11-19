package com.gens.notificacao.business;

import com.gens.notificacao.business.exceptions.EmailException;
import com.gens.notificacao.infraestructure.dto.TarefasDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    @Value("${envio.mail.from}")
    private String from;

    @Value("${envio.mail.nameSender}")
    private String nameSender;


    public void enviarEmail(TarefasDTO tarefasDTO) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(from, nameSender));
            mimeMessageHelper.setTo(InternetAddress.parse(tarefasDTO.getEmailUsuario()));
            mimeMessageHelper.setSubject("Notificação de Tarefa: " + tarefasDTO.getNomeDaTarefa());

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefasDTO.getNomeDaTarefa());
            context.setVariable("descricao", tarefasDTO.getDescricaoDaTarefa());
            context.setVariable("dataEvento", tarefasDTO.getDataEvento());

            String htmlTemplate = templateEngine.process("notificacaoTemplate", context);
            mimeMessageHelper.setText(htmlTemplate, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar email", e);
        }

    }
}
