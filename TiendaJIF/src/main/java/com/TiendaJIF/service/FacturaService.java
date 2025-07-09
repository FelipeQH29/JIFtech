/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Factura;
import com.TiendaJIF.domain.ItemCarrito;
import java.util.Collection;

public interface FacturaService {
    void guardarFactura(Factura factura, Collection<ItemCarrito> carrito, double total);
}

