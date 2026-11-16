package poo2.ctrlstudio.papeleria.modelo.daos.impl;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IBaseDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DAOBase<T extends IEntidad> implements IBaseDAO<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private EntityManager em = null;
    private final Class<T> claseEntidad;

    public static final String LOGGER_NAME = "DAOBase";
    static final Logger log = Logger.getLogger(LOGGER_NAME);

    public DAOBase(Class<T> claseEntidad, EntityManager em) {
        this.claseEntidad = claseEntidad;
        setEm(em);
    }

    public DAOBase(Class<T> claseEntidad) {
        this(claseEntidad, null);
    }

    @Override
    public T findById(Integer id) {
        if (id == null) return null;
        return em.find(claseEntidad, id);
    }

    public Optional<T> findByIdOptional(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        T entidad = em.find(claseEntidad, id);
        return Optional.ofNullable(entidad);
    }

    @Override
    public List<T> findAll() {
        return ejecutarConsulta(null, null);
    }

    @Override
    public List<T> findAll(int pagina, int tamano) {
        return ejecutarConsulta(pagina, tamano);
    }

    @Override
    public List<T> findByCampo(String campo, Object valor) {
        return em.createQuery(consultaPorCampo(campo, valor)).getResultList();
    }

    @Override
    public T findByCampoUnico(String campo, Object valor) {
        try {
            return em.createQuery(consultaPorCampo(campo, valor)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private CriteriaQuery<T> consultaPorCampo(String campo, Object valor) {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
        Root<T> raiz = criteriaQuery.from(claseEntidad);
        criteriaQuery.select(raiz)
                .where(em.getCriteriaBuilder().equal(raiz.get(campo), valor));
        return criteriaQuery;
    }

    private List<T> ejecutarConsulta(Integer pagina, Integer tamano) {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
        criteriaQuery.select(criteriaQuery.from(claseEntidad));
        var consulta = em.createQuery(criteriaQuery);
        if (pagina != null && tamano != null) {
            consulta.setFirstResult(pagina * tamano).setMaxResults(tamano);
        }
        return consulta.getResultList();
    }

    @Override
    public T save(T entidad) throws DAOException {
        return executeTransactionWithResult(() -> {
            em.persist(entidad);
            return entidad;
        });
    }

    @Override
    public List<T> saveAll(List<T> listaEntidades) throws DAOException {
        return executeTransactionWithResult(() -> {
            List<T> guardados = new ArrayList<>();
            for (T entidad : listaEntidades) {
                em.persist(entidad);
                guardados.add(entidad);
            }
            return guardados;
        });
    }

    @Override
    public T update(T entidad) throws DAOException {
        return executeTransactionWithResult(() -> em.merge(entidad));
    }

    @Override
    public List<T> updateAll(List<T> registros) throws DAOException {
        return executeTransactionWithResult(() -> {
            List<T> actualizados = new ArrayList<>();
            for (T entidad : registros) {
                actualizados.add(em.merge(entidad));
            }
            return actualizados;
        });
    }

    @Override
    public void delete(T entidad) throws DAOException {
        executeTransaction(() -> {
// Borrado seguro
            T managedEntity = em.contains(entidad) ? entidad : em.merge(entidad);
            em.remove(managedEntity);
        });
    }

    @Override
    public void deleteById(Integer id) throws DAOException {
        executeTransaction(() -> {
            T entidad = em.find(claseEntidad, id);
            if (entidad != null) {
                em.remove(entidad);
            }
        });
    }

    @Override
    public void deleteAll(List<T> listaRegistros) throws DAOException {
        executeTransaction(() -> {
            for (T entidad : listaRegistros) {
                T managedEntity = em.contains(entidad) ? entidad : em.merge(entidad);
                em.remove(managedEntity);
            }
        });
    }

    private T executeTransactionWithResult(DBExecute<T> comando) throws DAOException {
        try {
            em.getTransaction().begin();
            T salida = comando.executeDBCommand();
            em.getTransaction().commit();
            return salida;
        } catch (Exception e) {
            revertirTransaccion(e);
            throw new DAOException("Error de persistencia: " + e.getMessage(), e);
        }
    }

    private void executeTransaction(DBJustExecute comando) throws DAOException {
        try {
            em.getTransaction().begin();
            comando.executeDBCommand();
            em.getTransaction().commit();
        } catch (Exception e) {
            revertirTransaccion(e);
            throw new DAOException("Error de persistencia: " + e.getMessage(), e);
        }
    }

    private void revertirTransaccion(Exception causa) {
        log.severe(causa.getMessage());
        try {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
// Rollback, deshacer cambios
        } catch (Exception falloRollback) {
            log.severe("El rollback tambien fallo: " + falloRollback.getMessage());
        }
    }

    public void setEm(EntityManager em) {
        if (em != null) {
            this.em = em;
        }
    }

    protected EntityManager getEm() {
        return em;
    }

    @FunctionalInterface
    public interface DBExecute<T> {
        T executeDBCommand();
    }

    @FunctionalInterface
    public interface DBJustExecute {
        void executeDBCommand();
    }
}