package poo2.ctrlstudio.papeleria.servicios;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IBoletaInversionDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.BoletaInversion;
import poo2.ctrlstudio.papeleria.modelo.entidades.DetalleInversion;
import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;

public class BoletaInversionService extends ServicioBase<BoletaInversion> {
    private final IBoletaInversionDAO boletaInversionDAO;
    private final ProductoDAO productoDAO;

    public BoletaInversionService(IBoletaInversionDAO boletaInversionDAO, ProductoDAO productoDAO) {
        super(boletaInversionDAO);
        this.boletaInversionDAO = boletaInversionDAO;
        this.productoDAO = productoDAO;
    }

    public BoletaInversion registrarInversion(BoletaInversion boleta) throws DAOException, ReglaNegocioException {
// Validacion de los detalles
        if (boleta.getDetalles() == null || boleta.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("La boleta de inversión no contiene productos agregados.");
        }

        double inversionTotal = 0.0;

// Procesar cada producto adquirido
        for (DetalleInversion detalle : boleta.getDetalles()) {
            Producto producto = productoDAO.findById(detalle.getProducto().getIdproducto());

            if (producto == null) {
                throw new ReglaNegocioException("El producto ingresado no existe en el catálogo.");
            }

// Sumar la cantidad agregada de stock al alamcen
            producto.setStock(producto.getStock() + detalle.getCantidadAgregada());
            
            productoDAO.update(producto);

// Calcular subtotal de la inversion
            double subtotal = detalle.getCostoUnitario() * detalle.getCantidadAgregada();
            detalle.setSubtotal(subtotal);
            inversionTotal += subtotal;
        }

        boleta.setTotalInversion(inversionTotal);

  // Guardar
        return boletaInversionDAO.save(boleta);
    }
}