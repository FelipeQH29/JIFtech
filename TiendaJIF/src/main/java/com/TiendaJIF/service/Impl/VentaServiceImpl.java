package com.TiendaJIF.service.Impl;


import com.TiendaJIF.dao.VentaDao;
import com.TiendaJIF.domain.Venta;
import com.TiendaJIF.service.VentaService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaDao ventaDao;

    public VentaServiceImpl(VentaDao ventaDao) {
        this.ventaDao = ventaDao;
    }

    @Override
    @Transactional
    public void guardarLinea(Integer idFactura, Integer idProducto, double precioUnitario, int cantidad) {
        Venta v = new Venta();
        v.setIdFactura(idFactura);
        v.setIdProducto(idProducto);
        v.setPrecio(precioUnitario);
        v.setCantidad(cantidad);
        ventaDao.save(v);
    }

    @Override
    public List<Venta> getVentasPorFactura(Integer idFactura) {
        return ventaDao.findByIdFactura(idFactura);
    }

    @Override
    @Transactional
    public void eliminarVentasPorFactura(Integer idFactura) {
        ventaDao.deleteByIdFactura(idFactura);
    }

    @Override
    public Double calcularTotalPorFactura(Integer idFactura) {
        return ventaDao.totalByFactura(idFactura);
    }
}
