/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.controladores;



/**
 *
 * @author Felipe
 */

import com.TiendaJIF.service.CategoriaService;
import com.TiendaJIF.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos/categoria/{idCategoria}")
    public String listarPorCategoria(@PathVariable Long idCategoria, Model model) {
        var categorias = categoriaService.getCategorias();
        var productos = productoService.getProductosPorCategoria(idCategoria);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        return "productos";
    }
}
