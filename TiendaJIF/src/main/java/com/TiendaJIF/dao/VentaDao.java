/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.TiendaJIF.dao;

/**
 *
 * @author Jose Sequeira
 */
import com.TiendaJIF.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VentaDao extends JpaRepository<Venta, Integer> {

    List<Venta> findByIdFactura(Integer idFactura);

    @Modifying
    @Query("DELETE FROM Venta v WHERE v.idFactura = :idFactura")
    void deleteByIdFactura(@Param("idFactura") Integer idFactura);

    @Query("SELECT COALESCE(SUM(v.precio * v.cantidad), 0) FROM Venta v WHERE v.idFactura = :idFactura")
    Double totalByFactura(@Param("idFactura") Integer idFactura);
}
