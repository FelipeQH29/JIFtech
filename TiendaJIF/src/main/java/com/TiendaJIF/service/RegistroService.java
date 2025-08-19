/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.TiendaJIF.service;

/**
 *
 * @author brene
 */
import com.TiendaJIF.domain.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servicio para gestionar el registro de usuarios.
 */
public interface RegistroService {

    /**
     * Activa un usuario por enlace.
     */
    public Model activar(Model model, String usuario, String clave);

    /**
     * Crea un nuevo usuario y envía correo de activación.
     */
    public Model crearUsuario(Model model, Usuario usuario) throws MessagingException;

    /**
     * Activa usuario con imagen de perfil.
     */
    public void activar(Usuario usuario, MultipartFile imagenFile);

    /**
     * Envía correo para recuperar cuenta.
     */
    public Model recordarUsuario(Model model, Usuario usuario) throws MessagingException;
}
