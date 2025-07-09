/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author erick
 */

import com.TiendaJIF.domain.ItemCarrito;
import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService {

    private static final String CARRITO_SESSION_KEY = "carrito";

    private Map<Long, ItemCarrito> getCarrito(HttpSession session) {
        Map<Long, ItemCarrito> carrito = (Map<Long, ItemCarrito>) session.getAttribute(CARRITO_SESSION_KEY);
        if (carrito == null) {
            carrito = new HashMap<>();
            session.setAttribute(CARRITO_SESSION_KEY, carrito);
        }
        return carrito;
    }

    @Override
public void agregarProducto(Producto producto, HttpSession session) {
    Map<Long, ItemCarrito> carrito = getCarrito(session);
    Long id = producto.getIdProducto().longValue(); // CORRECTO
    if (carrito.containsKey(id)) {
        ItemCarrito item = carrito.get(id);
        item.setCantidad(item.getCantidad() + 1);
    } else {
        carrito.put(id, new ItemCarrito(producto, 1));
    }
}

    @Override
    public Collection<ItemCarrito> obtenerCarrito(HttpSession session) {
        return getCarrito(session).values();
    }

    @Override
    public double calcularTotal(HttpSession session) {
        return getCarrito(session).values().stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    @Override
    public void eliminarProducto(Long idProducto, HttpSession session) {
        Map<Long, ItemCarrito> carrito = getCarrito(session);
        carrito.remove(idProducto);
    }
}

