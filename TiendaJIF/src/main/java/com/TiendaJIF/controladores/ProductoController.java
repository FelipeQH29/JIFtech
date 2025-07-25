/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.CategoriaService;
import com.TiendaJIF.service.ProductoService;
import com.TiendaJIF.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    // Mostrar productos por categoría
    @GetMapping("/productos/categoria/{idCategoria}")
    public String listarPorCategoria(@PathVariable Long idCategoria, Model model) {
        var categorias = categoriaService.getCategorias();
        var productos = productoService.getProductosPorCategoria(idCategoria);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/producto/detalle/{id}")
    public String verDetalleProducto(@PathVariable("id") Long idProducto, Model model) {
        var producto = productoService.getProductoPorId(idProducto);
        if (producto == null) {
            return "redirect:/"; // O podrías mostrar una página de error
        }
        model.addAttribute("producto", producto);
        return "detalleProducto"; // apunta al archivo detalleProducto.html
    }

    // ✅ Nuevo: Agregar producto al carrito
    @GetMapping("/producto/agregarCarrito/{id}")
    public String agregarAlCarrito(@PathVariable("id") Long idProducto, HttpSession session) {
        Producto producto = productoService.getProductoPorId(idProducto);
        if (producto != null) {
            carritoService.agregarProducto(producto, session);
        }
        return "redirect:/carrito/ver";
    }
@GetMapping("/formularioProducto")
public String mostrarFormularioNuevoProducto(Model model) {
    System.out.println("CARGANDO FORMULARIO NUEVO");
    model.addAttribute("producto", new Producto());
    model.addAttribute("categorias", categoriaService.getCategorias());
    return "/formularioProducto";
}


    @PostMapping("/producto/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        producto.setActivo(true); // Puedes modificar esto según lógica
        productoService.guardarProducto(producto);
        return "redirect:/"; // Redirige al inicio o a donde quieras
    }
}
