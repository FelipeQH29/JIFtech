/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service;

import com.TiendaJIF.domain.Contacto;
import java.util.List;
/**
 *
 * @author Jose Sequeira
 */
public interface ContactoService {
    void guardarContacto(Contacto contacto);
    List<Contacto> listarContactos();
}