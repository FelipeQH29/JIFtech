/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Factura;
import com.TiendaJIF.domain.ItemCarrito;
import com.TiendaJIF.service.FacturaService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Override
    public void guardarFactura(Factura factura, Collection<ItemCarrito> carrito, double total) {
        // Aquí se simula guardar en la base de datos
        System.out.println("Factura creada:");
        System.out.println("Titular: " + factura.getNombreTitular());
        System.out.println("Total: ₡" + total);
    }
}

