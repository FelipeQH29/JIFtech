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

public interface ProductoDao extends CrudRepository<Producto, Long> {
    List<Producto> findByCategoriaIdCategoria(Long idCategoria);
}

