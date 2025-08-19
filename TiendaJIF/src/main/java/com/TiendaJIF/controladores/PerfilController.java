/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;

/**
 *
 * @author Jose Sequeira
 */
import com.TiendaJIF.domain.Usuario;
import com.TiendaJIF.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/ver")
    public String perfil(Authentication authentication, Model model) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);

        model.addAttribute("usuario", usuario);
        return "perfil/ver"; // perfil.html en templates
    }
}
