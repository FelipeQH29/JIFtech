package com.TiendaJIF.service.Impl;

import com.TiendaJIF.dao.ProductoDao;
import com.TiendaJIF.domain.Producto;
import com.TiendaJIF.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    @Transactional
    public boolean descontarStock(Long idProducto, int cantidad) {
        var opt = productoDao.findById(idProducto);
        if (opt.isEmpty()) return false;

        Producto p = opt.get();
        if (p.getStock() == null || p.getStock() < cantidad) return false;

        p.setStock(p.getStock() - cantidad);
        if (p.getStock() == 0) {
            p.setActivo(false);  // se oculta cuando ya no hay stock
        }
        productoDao.save(p);
        return true;
    }
}
