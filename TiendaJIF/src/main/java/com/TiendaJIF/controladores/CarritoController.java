package com.TiendaJIF.controladores;

import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.domain.ItemCarrito;
import com.TiendaJIF.domain.Factura;              // ← TU clase (titular, tarjeta, etc.)
import com.TiendaJIF.service.CarritoService;
import com.TiendaJIF.service.ProductoService;
import com.TiendaJIF.service.FacturaService;

import jakarta.servlet.http.HttpSession;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired 
    private CarritoService carritoService;
    @Autowired 
    private ProductoService productoService;
    @Autowired 
    private FacturaService  facturaService;

    // Ver el carrito
    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        var carrito = carritoService.obtenerCarrito(session);
        double total = carritoService.calcularTotal(session);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        return "carrito";
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

    // Mostrar formulario de pago
    @GetMapping("/crear")
    public String mostrarPago(HttpSession session, Model model) {
        var carrito = carritoService.obtenerCarrito(session);
        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("msgErr", "El carrito está vacío.");
            return "carrito";
        }
        double total = carritoService.calcularTotal(session);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        if (!model.containsAttribute("factura")) {
            model.addAttribute("factura", new Factura());
        }
        return "carrito"; // muestra el carrito
    }

    // Confirmar compra
    @PostMapping("/confirmar")
    public String confirmarCompra(@ModelAttribute("factura") Factura facturaPago,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        Collection<ItemCarrito> carrito = carritoService.obtenerCarrito(session);
        if (carrito == null || carrito.isEmpty()) {
            ra.addFlashAttribute("msgErr", "El carrito está vacío.");
            return "redirect:/carrito/ver";
        }

        double total = carritoService.calcularTotal(session);

        try {
            // guarda la factura
            facturaService.guardarFactura(facturaPago, carrito, total);

            // Limpia carrito tras compra exitosa
            session.removeAttribute("carrito");
            ra.addFlashAttribute("msgOk", "Compra realizada correctamente.");
            return "redirect:/carrito/ver";
        } catch (Exception e) {
            ra.addFlashAttribute("msgErr", "No se pudo procesar el pago: " + e.getMessage());
            ra.addFlashAttribute("factura", facturaPago);
            return "redirect:/carrito/pagar";
        }
    }
}
