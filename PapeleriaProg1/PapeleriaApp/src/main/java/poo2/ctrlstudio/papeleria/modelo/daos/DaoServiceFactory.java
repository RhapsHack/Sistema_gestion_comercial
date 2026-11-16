package poo2.ctrlstudio.papeleria.modelo.daos;

import jakarta.persistence.EntityManager;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.DAOBase;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.utils.JPAUtil;
import poo2.ctrlstudio.papeleria.servicios.ProductoService;
import poo2.ctrlstudio.papeleria.servicios.interfaces.IServicioBase;

import java.util.Optional;

import static poo2.ctrlstudio.papeleria.utils.Constantes.CAT_PRODUCTOS;

public abstract class DaoServiceFactory {

    protected static final EntityManager EM = JPAUtil.crearEntityManager();

    private DaoServiceFactory() {
    }

    public static Optional<DAOBase<?>> getDAO(String nombre) {
        return Optional.ofNullable(
                switch (nombre) {
                    case CAT_PRODUCTOS -> new ProductoDAO(EM);
                    default -> null;
                }
        );
    }

    public static Optional<IServicioBase<?>> getServicio(String nombre) {
        var daoOptional = getDAO(nombre);
        if (daoOptional.isPresent()) {
            return Optional.ofNullable(
                    switch (nombre) {
                        case CAT_PRODUCTOS -> new ProductoService((IProductoDAO) daoOptional.get());
                        default -> null;
                    }
            );
        }
        return Optional.empty();
    }
}