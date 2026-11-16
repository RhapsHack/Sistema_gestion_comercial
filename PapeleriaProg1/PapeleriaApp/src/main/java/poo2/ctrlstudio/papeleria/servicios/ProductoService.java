package poo2.ctrlstudio.papeleria.servicios;

import poo2.ctrlstudio.papeleria.modelo.daos.interf.IProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;

public class ProductoService extends ServicioBase<Producto> {
    private final IProductoDAO productoDAO;

    public ProductoService(IProductoDAO productoDAO) {
        super(productoDAO);
        this.productoDAO = productoDAO;
    }

    // Modificable
}