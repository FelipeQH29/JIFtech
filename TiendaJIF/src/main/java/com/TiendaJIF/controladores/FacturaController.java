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
import com.TiendaJIF.service.ProductoService;     // 👈 NUEVO
import jakarta.servlet.http.HttpSession;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional; // 👈 NUEVO
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ProductoService productoService; // 👈 NUEVO

    @GetMapping("/crear")
    public String mostrarFormularioFactura(HttpSession session, Model model) {
        Collection<ItemCarrito> carrito = carritoService.obtenerCarrito(session);
        double total = carritoService.calcularTotal(session);

        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("factura", new Factura());

        return "factura";
    }

    @PostMapping("/procesar")
    @Transactional // 👈 hacer todo en una sola transacción
    public String procesarPago(@ModelAttribute("factura") Factura factura,
                               HttpSession session,
                               Model model) {

        var carrito = carritoService.obtenerCarrito(session);
        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("error", "El carrito está vacío.");
            return "factura";
        }

        double total = carritoService.calcularTotal(session);

        // Validaciones mínimas del formulario
        if (factura.getNombreTitular() == null || factura.getNombreTitular().isBlank()
                || factura.getNumeroTarjeta() == null || factura.getNumeroTarjeta().isBlank()
                || factura.getVencimiento() == null || factura.getVencimiento().isBlank()
                || factura.getCvv() == null || factura.getCvv().isBlank()) {

            model.addAttribute("error", "Todos los campos son requeridos.");
            model.addAttribute("carrito", carrito);
            model.addAttribute("total", total);
            return "factura";
        }

        // 1) RESTAR STOCK por cada ítem ANTES de guardar la factura
        for (ItemCarrito item : carrito) {
            // getIdProducto puede ser Integer o Long en tu entidad; .longValue() funciona para ambos
            Long idProd = item.getProducto().getIdProducto().longValue();
            int cantidad = item.getCantidad();

            boolean ok = productoService.descontarStock(idProd, cantidad); // debe devolver false si no alcanza
            if (!ok) {
                model.addAttribute("error",
                        "Stock insuficiente para: " + item.getProducto().getNombre());
                model.addAttribute("carrito", carrito);
                model.addAttribute("total", total);
                // @Transactional hace rollback automático al salir por return
                return "factura";
            }
        }

        // 2) Guardar la factura con tu método EXISTENTE
        facturaService.guardarFactura(factura, carrito, total);

        // 3) Limpiar carrito
        session.removeAttribute("carrito");

        return "redirect:/factura/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion() {
        return "confirmacion";
    }
}


