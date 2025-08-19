/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.TiendaJIF.dao;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.domain.Producto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoDao extends CrudRepository<Producto, Long> {
    List<Producto> findByCategoriaIdCategoria(Long idCategoria);
    List<Producto> findByCategoriaIdCategoriaAndNombreContainingIgnoreCase(Long categoria, String nombre);
    
        // ProductoDao
    @Modifying
    @Query("""
           UPDATE Producto p
              SET p.stock = p.stock - :cantidad
            WHERE p.idProducto = :id
              AND p.stock >= :cantidad
           """)
    int descontarStock(@Param("id") Long idProducto, @Param("cantidad") int cantidad);

}

