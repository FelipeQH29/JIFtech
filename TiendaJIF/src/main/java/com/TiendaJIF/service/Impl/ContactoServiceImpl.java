/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;
import com.TiendaJIF.dao.ContactoDao;
import com.TiendaJIF.domain.Contacto;
import com.TiendaJIF.service.ContactoService;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author Jose Sequeira
 */
@Service
public class ContactoServiceImpl implements ContactoService {

    private final ContactoDao dao;

    public ContactoServiceImpl(ContactoDao dao) {
        this.dao = dao;
    }

    @Override
    public void guardarContacto(Contacto contacto) {
        dao.save(contacto);
    }

    @Override
    public List<Contacto> listarContactos() {
        return dao.findAll();
    }
}
