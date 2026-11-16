package poo2.ctrlstudio.papeleria.modelo.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("papeleriaPU");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Error al inicializar la EntityManagerFactory: " + ex.getMessage());
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static EntityManager crearEntityManager() {
        return getEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static void cerrar() {
        close();
    }
}