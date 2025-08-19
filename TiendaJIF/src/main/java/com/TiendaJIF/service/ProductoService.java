/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Producto;
import java.util.List;



public interface ProductoService {
    
    public List<Producto> getProductosPorCategoria(Long idCategoria);

    public Producto getProductoPorId(Long idProducto);
    
    public void guardarProducto(Producto producto);

    
    public List<Producto> getProductos(); // Para listar todos
    
    public void eliminarProducto(Long idProducto); // Para eliminar por ID
    
    public List<Producto> getProductoPorNombre(Long idCategoria, String nombre);
    
    /** Resta stock en la compra. Debe retornar false si no alcanza. */
    boolean descontarStock(Long idProducto, int cantidad);
}


