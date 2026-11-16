package poo2.ctrlstudio.papeleria.modelo.daos.impl;

import jakarta.persistence.EntityManager;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;

public class ProductoDAO extends DAOBase<Producto> implements IProductoDAO {
    
    public ProductoDAO(EntityManager em) {
        super(Producto.class, em);
    }
    
    // Agregar consultas personalizadas
}