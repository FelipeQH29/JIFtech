/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author brene
 */
import com.TiendaJIF.domain.Usuario;
import com.TiendaJIF.service.CorreoService;
import com.TiendaJIF.service.RegistroService;
import com.TiendaJIF.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource; // Para mensajes internacionalizados
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Para cifrado de contraseñas
import org.springframework.stereotype.Service; // Marca la clase como un servicio de Spring
import org.springframework.ui.Model; // Para pasar atributos a la vista
import org.springframework.web.multipart.MultipartFile; // Para manejar archivos subidos

@Service // Indica que esta clase es un servicio y puede ser inyectada
public class RegistroServiceImpl implements RegistroService {

    @Autowired // Inyecta el servicio de correos
    private CorreoService correoService;

    @Autowired // Inyecta el servicio de usuarios
    private UsuarioService usuarioService;

    @Autowired // Inyecta la fuente de mensajes (mensajes.properties)
    private MessageSource messageSource; // creado en semana 4...


    @Override
    public Model activar(Model model, String username, String clave) {
        // Buscar usuario por nombre y clave
        Usuario usuario = usuarioService.getUsuarioPorUsernameYPassword(username, clave);

        if (usuario != null) {
            // Si existe, agregar el usuario al modelo
            model.addAttribute("usuario", usuario);
        } else {
            // Si no existe, agregar mensajes de error al modelo
            model.addAttribute("titulo",
                    messageSource.getMessage("registro.activar", null, Locale.getDefault()));
            model.addAttribute("mensaje",
                    messageSource.getMessage("registro.activar.error", null, Locale.getDefault()));
        }

        return model;
    }

    @Override
    public void activar(Usuario usuario, MultipartFile imagenFile) {
        // Encriptar la contraseña del usuario
        var codigo = new BCryptPasswordEncoder();
        usuario.setPassword(codigo.encode(usuario.getPassword()));

        // Guardar el usuario final con roles
        usuarioService.save(usuario, true);
    }

    @Override
    public Model crearUsuario(Model model, Usuario usuario)
            throws MessagingException {

        String mensaje;

        // Verifica si el usuario (por nombre o correo) ya existe
        if (!usuarioService.existeUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo())) {

            // Genera una clave temporal
            String clave = demeClave();

            // Asigna la clave encriptada al usuario
            usuario.setPassword(clave);
            usuario.setActivo(true); // U

            // Guarda el usuario con roles
            usuarioService.save(usuario, true);

            // Envía correo con enlace de activación
            enviaCorreoActivar(usuario, clave);

            // Mensaje de éxito
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.activacion.ok", null, Locale.getDefault()),
                    usuario.getCorreo());

        } else {
            // Mensaje si el usuario ya existe
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.usuario.o.correo", null, Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }

        // Agrega título y mensaje al modelo para mostrar en la vista
        model.addAttribute("titulo",
                messageSource.getMessage("registro.activar", null, Locale.getDefault()));
        model.addAttribute("mensaje", mensaje);

        return model;
    }

    @Override
    public Model recordarUsuario(Model model, Usuario usuario)
            throws MessagingException {

        String mensaje;

        // Buscar usuario por nombre de usuario y correo
        Usuario usuario2 = usuarioService.getUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo());

        if (usuario2 != null) {
            // Generar nueva clave
            String clave = demeClave();

            // Asignar nueva clave encriptada y desactivar usuario temporalmente
            usuario2.setPassword(clave);
            usuario2.setActivo(false);

            // Guardar sin asignar roles
            usuarioService.save(usuario2, false);

            // Enviar correo con nueva clave
            enviaCorreoRecordar(usuario2, clave);

            // Mensaje de éxito
            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.recordar.ok", null, Locale.getDefault()),
                    usuario.getCorreo());

        } else {
            // Mensaje si el usuario no existe
            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.usuario.o.correo", null, Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }

        // Agrega información al modelo para la vista
        model.addAttribute("titulo",
                messageSource.getMessage("registro.activar", null, Locale.getDefault()));
        model.addAttribute("mensaje", mensaje);

        return model;
    }

    // Método privado para generar una clave aleatoria de 40 caracteres
    private String demeClave() {
        // Conjunto de caracteres permitidos para la clave
        String tira = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_*+";

        String clave = "";

        // Genera una clave de 40 caracteres aleatorios
        for (int i = 0; i < 40; i++) {
            // Selecciona un carácter aleatorio de 'tira' y lo añade a la clave
            clave += tira.charAt((int) (Math.random() * tira.length()));
        }

        return clave;
    }

    // Ojo: cómo se lee una propiedad del application.properties
    @Value("${servidor.http}")
    private String servidor;

    // Envía un correo de activación al usuario
    private void enviaCorreoActivar(Usuario usuario, String clave) throws MessagingException {
        // Obtener mensaje base desde messages.properties
        String mensaje = messageSource.getMessage(
                "registro.correo.activar", null, Locale.getDefault());

        // Formatear el mensaje con los datos del usuario y el servidor
        mensaje = String.format(mensaje,
                usuario.getNombre(),
                usuario.getApellidos(),
                servidor,
                usuario.getUsername(),
                clave);

        // Obtener el asunto del correo desde messages.properties
        String asunto = messageSource.getMessage(
                "registro.mensaje.activacion", null, Locale.getDefault());

        // Enviar correo HTML
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, mensaje);
    }

    // Envía un correo para recordar (recuperar) la cuenta del usuario
    private void enviaCorreoRecordar(Usuario usuario, String clave) throws MessagingException {
        // Obtener mensaje base desde messages.properties
        String mensaje = messageSource.getMessage(
                "registro.correo.recordar", null, Locale.getDefault());

        // Formatear mensaje con los datos del usuario y la nueva clave
        mensaje = String.format(mensaje,
                usuario.getNombre(),
                usuario.getApellidos(),
                servidor,
                usuario.getUsername(),
                clave);

        // Obtener asunto del correo
        String asunto = messageSource.getMessage(
                "registro.mensaje.recordar", null, Locale.getDefault());

        // Enviar correo HTML
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, mensaje);
    }

}