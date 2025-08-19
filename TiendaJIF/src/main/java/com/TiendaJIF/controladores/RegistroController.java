/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;

/**
 *
 * @author brene
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.TiendaJIF.domain.Usuario;
import com.TiendaJIF.service.RegistroService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j; // Para logging con @Slf4j
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller // Marca esta clase como un controlador de Spring MVC
@Slf4j // Habilita el uso de logs con log.info, log.error, etc.
@RequestMapping("/registro") // Ruta base para este controlador
public class RegistroController {

    @Autowired // Inyección del servicio de registro
    private RegistroService registroService;

    // Muestra el formulario para nuevo registro
    @GetMapping("/nuevo")
    public String nuevo(Model model, Usuario usuario) {
        return "/registro/nuevo";
    }

    // Muestra el formulario para recordar cuenta
    @GetMapping("/recordar")
    public String recordar(Model model, Usuario usuario) {
        return "/registro/recordar";
    }
    // Procesa el formulario de creación de usuario

    @PostMapping("/crearUsuario")
    public String crearUsuario(Model model, Usuario usuario)
            throws MessagingException {
        // Invoca al servicio para crear el usuario y agrega datos al modelo
        model = registroService.crearUsuario(model, usuario);
        // Redirige a la vista de salida con el mensaje correspondiente
        return "/registro/salida";
    }

    // Muestra pantalla de activación con los datos del enlace
    @GetMapping("/activar/{usuario}/{id}")
    public String activar(
            Model model,
            @PathVariable(value = "usuario") String usuario,
            @PathVariable(value = "id") String id) {

        // Intenta activar el usuario con el enlace recibido
        model = registroService.activar(model, usuario, id);

        // Si el modelo contiene datos del usuario, va a la salida
        if (model.containsAttribute("usuario")) {
            return "/registro/salida";
        } else {
            return "/registro/activar";
        }
    }

    // Procesa la activación del usuario con la imagen cargada
    @PostMapping("/activar")
    public String activar(Usuario usuario)
            throws MessagingException {
        // Activa el usuario 
        registroService.activar(usuario,null);
        // Redirige al login
        return "redirect:/";
    }

    // Procesa el formulario para recordar usuario (recuperar cuenta)
    @PostMapping("/recordarUsuario")
    public String recordarUsuario(Model model, Usuario usuario)
            throws MessagingException {

        // Llama al servicio para enviar el correo de recuperación
        model = registroService.recordarUsuario(model, usuario);
        // Muestra salida con mensaje
        return "/registro/salida";
    }

}

