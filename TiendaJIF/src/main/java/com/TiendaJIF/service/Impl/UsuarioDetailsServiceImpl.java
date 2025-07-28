/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.service.Impl;

/**
 *
 * @author Jose Sequeira
 */
import com.TiendaJIF.dao.UsuarioDao;
import com.TiendaJIF.domain.Rol;
import com.TiendaJIF.domain.Usuario;
import com.TiendaJIF.service.UsuarioDetailsService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private HttpSession session;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Se busca el usuario que tiene el username pasado por parámetro...
        Usuario usuario = usuarioDao.findByUsername(username);
        
        //Se valida si se recuperó un usuario / sino lanza un error
        if (usuario==null) {
            throw new UsernameNotFoundException(username);
        }
        
        //Si estamos acá es porque si se recuperó un usuario...
        session.removeAttribute("usuarioImagen");
        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        
        //Se van a recuperar los roles del usuario y se crean los roles ya como seguridad de Spring
        var roles = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {
           roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        //Se retorna un User (de tipo UserDetails)
        return new User(usuario.getUsername(),usuario.getPassword(),roles);
    }

}