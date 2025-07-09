/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service;

/**
 *
 * @author erick
 */

import com.TiendaJIF.domain.ItemCarrito;
import com.TiendaJIF.domain.Producto;
import jakarta.servlet.http.HttpSession;
import java.util.Collection;

public interface CarritoService {
    void agregarProducto(Producto producto, HttpSession session);
    Collection<ItemCarrito> obtenerCarrito(HttpSession session);
    double calcularTotal(HttpSession session);
    void eliminarProducto(Long idProducto, HttpSession session);
}


