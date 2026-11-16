package poo2.ctrlstudio.papeleria.servicios;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IVentaDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.DetalleVenta;
import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;
import poo2.ctrlstudio.papeleria.modelo.entidades.Venta;

import java.util.List;

public class VentaService extends ServicioBase<Venta> {
    private final IVentaDAO ventaDAO;
    private final ProductoDAO productoDAO;

    public VentaService(IVentaDAO ventaDAO, ProductoDAO productoDAO) {
        super(ventaDAO);
        this.ventaDAO = ventaDAO;
        this.productoDAO = productoDAO;
    }

    public Venta registrarVenta(Venta venta) throws DAOException, ReglaNegocioException {
// Valida que la venta tenga detalles
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("El ticket no contiene ningun producto registrado.");
        }

// Verifica el stock y totales
        double totalCalculado = 0.0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoDAO.findById(detalle.getProducto().getIdproducto());
            
            if (producto == null) {
                throw new ReglaNegocioException("El producto con ID " + detalle.getProducto().getIdproducto() + " no existe.");
            }

            if (producto.getStock() < detalle.getCantidad()) {
                throw new ReglaNegocioException("Stock insuficiente para el producto: " + producto.getNombre() 
                    + " (Disponible: " + producto.getStock() + ", Solicitado: " + detalle.getCantidad() + ")");
            }

// Actualiza el stock del producto con una resta
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoDAO.update(producto);

// Calcula subtotal
            detalle.setPrecioUnitario(producto.getPrecio());
            double subtotal = producto.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);
            totalCalculado += subtotal;
        }

        venta.setTotal(totalCalculado);

// Validacion de pago y cambio
        if (venta.getMontoPagado() < venta.getTotal()) {
            throw new ReglaNegocioException("El monto pagado es menor al total de la venta.");
        }
        venta.setMontoCambio(venta.getMontoPagado() - venta.getTotal());

// Guardado de la venta
        return ventaDAO.save(venta);
    }
}