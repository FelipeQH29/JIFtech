/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service;

/**
 *
 * @author Jose Sequeira
 */
import com.TiendaJIF.domain.Venta;
import java.util.List;

public interface VentaService {

    void guardarLinea(Integer idFactura, Integer idProducto, double precioUnitario, int cantidad);

    List<Venta> getVentasPorFactura(Integer idFactura);

    void eliminarVentasPorFactura(Integer idFactura);

    Double calcularTotalPorFactura(Integer idFactura);
}
