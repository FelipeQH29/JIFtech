/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author Felipe
 */

import com.TiendaJIF.dao.CategoriaDao;
import com.TiendaJIF.domain.Categoria;
import com.TiendaJIF.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public List<Categoria> getCategorias() {
        return (List<Categoria>) categoriaDao.findAll();
    }
}


