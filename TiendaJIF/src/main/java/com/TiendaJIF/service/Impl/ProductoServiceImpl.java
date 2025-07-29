/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.dao.ProductoDao;
import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList; 

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoDao productoDao;

    @Override
    public List<Producto> getProductosPorCategoria(Long idCategoria) {
        return productoDao.findByCategoriaIdCategoria(idCategoria);
    }

    @Override
    public Producto getProductoPorId(Long idProducto) {
        return productoDao.findById(idProducto).orElse(null);
    }

    @Override
    public void guardarProducto(Producto producto) {
        productoDao.save(producto);
    }

    // ✅ Método nuevo implementado
    @Override
    public void eliminarProducto(Long idProducto) {
        productoDao.deleteById(idProducto);
    }

    @Override
    public List<Producto> getProductos() {
        List<Producto> lista = new ArrayList<>();
        productoDao.findAll().forEach(lista::add);
        return lista;
    }
    @Override
    public List<Producto> getProductoPorNombre(Long idCategoria, String nombre) {
        return productoDao.findByCategoriaIdCategoriaAndNombreContainingIgnoreCase(idCategoria, nombre);
    }
 
}




