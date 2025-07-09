/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;

/**
 *
 * @author erick
 */

import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.CarritoService;
import com.TiendaJIF.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

    // Ver el carrito
    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        var carrito = carritoService.obtenerCarrito(session);
        double total = carritoService.calcularTotal(session);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito"; // esta es tu plantilla carrito.html
    }

    // Agregar producto al carrito
    @GetMapping("/agregar/{idProducto}")
    public String agregarAlCarrito(@PathVariable("idProducto") Long idProducto, HttpSession session) {
        Producto producto = productoService.getProductoPorId(idProducto);
        if (producto != null) {
            carritoService.agregarProducto(producto, session);
        }
        return "redirect:/carrito/ver";
    }

    // Eliminar producto del carrito
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarDelCarrito(@PathVariable("idProducto") Long idProducto, HttpSession session) {
        carritoService.eliminarProducto(idProducto, session);
        return "redirect:/carrito/ver";
    }
}
