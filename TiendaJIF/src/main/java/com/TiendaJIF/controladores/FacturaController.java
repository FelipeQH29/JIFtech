/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Factura;
import com.TiendaJIF.domain.ItemCarrito;
import com.TiendaJIF.service.CarritoService;
import com.TiendaJIF.service.FacturaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/crear")
    public String mostrarFormularioFactura(HttpSession session, Model model) {
        Collection<ItemCarrito> carrito = carritoService.obtenerCarrito(session);
        double total = carritoService.calcularTotal(session);

        if (carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("factura", new Factura());

        return "factura";
    }

    @PostMapping("/procesar")
    public String procesarPago(@ModelAttribute("factura") Factura factura,
                                HttpSession session,
                                Model model) {

        var carrito = carritoService.obtenerCarrito(session);
        double total = carritoService.calcularTotal(session);

        if (factura.getNombreTitular().isBlank() || factura.getNumeroTarjeta().isBlank()) {
            model.addAttribute("error", "Todos los campos son requeridos.");
            model.addAttribute("carrito", carrito);
            model.addAttribute("total", total);
            return "factura";
        }

        facturaService.guardarFactura(factura, carrito, total);
        session.removeAttribute("carrito");

        return "redirect:/factura/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion() {
        return "confirmacion";
    }
}


