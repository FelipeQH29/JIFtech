package com.TiendaJIF.controladores;

import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.CategoriaService;
import com.TiendaJIF.service.ProductoService;
import com.TiendaJIF.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    // ✅ Mostrar productos por categoría
    @GetMapping("/productos/categoria/{idCategoria}")
    public String listarPorCategoria(@PathVariable Long idCategoria, Model model) {
        var categorias = categoriaService.getCategorias();
        var productos = productoService.getProductosPorCategoria(idCategoria);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("productocategoria", idCategoria);
        return "productos";
    }

    // ✅ Detalle del producto
    @GetMapping("/producto/detalle/{id}")
    public String verDetalleProducto(@PathVariable("id") Long idProducto, Model model) {
        var producto = productoService.getProductoPorId(idProducto);
        if (producto == null) {
            return "redirect:/";
        }
        model.addAttribute("producto", producto);
        return "detalleProducto";
    }

    // ✅ Agregar al carrito
    @GetMapping("/producto/agregarCarrito/{id}")
    public String agregarAlCarrito(@PathVariable("id") Long idProducto, HttpSession session) {
        Producto producto = productoService.getProductoPorId(idProducto);
        if (producto != null) {
            carritoService.agregarProducto(producto, session);
        }
        return "redirect:/carrito/ver";
    }

    // ✅ Mostrar formulario nuevo producto
    @GetMapping("/formularioProducto")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "formularioProducto";
    }

    // ✅ Guardar producto (nuevo o actualizado)
    @PostMapping("/producto/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        producto.setActivo(true); // Puedes cambiar esta lógica si necesitas manejar el estado
        productoService.guardarProducto(producto);
        return "redirect:/listaProductos";
    }


    // ✅ Mostrar lista de todos los productos
    @GetMapping("/listaProductos")
    public String listarProductos(Model model) {
        var productos = productoService.getProductos();
        model.addAttribute("productos", productos);
        return "listaProductos"; // sin .html, sin barra, sin espacios
    }


    //  Editar producto existente
    @GetMapping("/producto/editar/{id}")
    public String mostrarFormularioEditarProdsucto(@PathVariable("id") Long idProducto, Model model) {
        var producto = productoService.getProductoPorId(idProducto);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "formularioProducto";
    }

    // ✅ Eliminar producto
    @GetMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long idProducto) {
        productoService.eliminarProducto(idProducto);
        return "redirect:/listaProductos";
    }

    @GetMapping("/productos/buscar/{idCategoria}")
    public String getCustomers (@PathVariable Long idCategoria, @RequestParam("search") Optional<String> searchParam, Model model){
        String finalsearchParam = searchParam.get();
        var categorias = categoriaService.getCategorias();
        var productos = productoService.getProductoPorNombre(idCategoria, finalsearchParam);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("productocategoria", idCategoria);
        model.addAttribute("previoussearch", finalsearchParam);
        return "productos";
    }
}
