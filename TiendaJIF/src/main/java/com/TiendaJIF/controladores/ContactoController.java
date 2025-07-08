/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;
import com.TiendaJIF.domain.Contacto;
import com.TiendaJIF.service.ContactoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author Jose Sequeira
 */
@Controller
@RequestMapping("/contact")
public class ContactoController {

    private final ContactoService service;

    public ContactoController(ContactoService service) {
        this.service = service;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        model.addAttribute("title", "Contáctanos");
        return "contact";
    }

    @PostMapping
    public String enviarFormulario(@ModelAttribute("contacto") Contacto contacto,
                                   RedirectAttributes ra) {
        service.guardarContacto(contacto);
        ra.addFlashAttribute("success", "¡Mensaje enviado correctamente!");
        return "redirect:/contact";
    }
}

