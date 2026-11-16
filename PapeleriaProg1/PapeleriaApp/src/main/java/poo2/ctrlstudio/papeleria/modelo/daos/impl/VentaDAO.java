package poo2.ctrlstudio.papeleria.modelo.daos.impl;

import poo2.ctrlstudio.papeleria.modelo.daos.interf.IVentaDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.Venta;
import jakarta.persistence.EntityManager;

public class VentaDAO extends DAOBase<Venta> implements IVentaDAO {
    private static final long serialVersionUID = 1L;

    public VentaDAO(EntityManager em) {
        super(Venta.class, em);
    }

    public VentaDAO() {
        super(Venta.class);
    }
}