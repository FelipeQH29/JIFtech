/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author brene
 */
import com.TiendaJIF.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un servicio de Spring
public class CorreoServiceImpl implements CorreoService {

    @Autowired // Inyección automática del componente JavaMailSender
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoHtml(
            String para,
            String asunto,
            String contenidoHtml)
            throws MessagingException {

        // Crear un mensaje MIME para correo electrónico
        MimeMessage message = mailSender.createMimeMessage();

        // Utilidad para configurar el mensaje (texto HTML, destinatario, asunto, etc.)
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Establecer destinatario
        helper.setTo(para);

        // Establecer asunto
        helper.setSubject(asunto);

        // Establecer contenido como HTML
        helper.setText(contenidoHtml, true);

        // Enviar el correo
        mailSender.send(message);
    }
}
