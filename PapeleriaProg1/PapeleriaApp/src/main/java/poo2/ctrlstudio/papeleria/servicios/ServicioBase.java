package poo2.ctrlstudio.papeleria.servicios;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IBaseDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;
import poo2.ctrlstudio.papeleria.servicios.interfaces.IServicioBase;

import java.util.List;
import java.util.Optional;

public class ServicioBase<T extends IEntidad> implements IServicioBase<T> {
    protected final IBaseDAO<T> dao;

    public ServicioBase(IBaseDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public T guardar(T entidad) throws DAOException {
        return dao.save(entidad);
    }

    @Override
    public T actualizar(T entidad) throws DAOException {
        return dao.update(entidad);
    }

    @Override
    public void borrarRegistro(T entidad) throws DAOException {
        dao.delete(entidad);
    }

    @Override
    public T buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<T> buscarPorIdOptional(Integer id) {
        return Optional.ofNullable(dao.findById(id));
    }

    @Override
    public List<T> recuperarTodosRegistros() {
        return dao.findAll();
    }

    @Override
    public List<T> recuperarPagina(int pagina, int tamano) {
        return dao.findAll(pagina, tamano);
    }

    @Override
    public List<T> buscarPorCampo(String campo, Object valor) {
        return dao.findByCampo(campo, valor);
    }
}