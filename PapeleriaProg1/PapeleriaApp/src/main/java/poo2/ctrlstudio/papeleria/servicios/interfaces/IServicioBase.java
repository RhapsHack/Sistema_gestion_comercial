package poo2.ctrlstudio.papeleria.servicios.interfaces;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;

import java.util.List;
import java.util.Optional;

public interface IServicioBase<T extends IEntidad> {
    T guardar(T entidad) throws DAOException;
    T actualizar(T entidad) throws DAOException;
    void borrarRegistro(T entidad) throws DAOException;
    
    T buscarPorId(Integer id);
    Optional<T> buscarPorIdOptional(Integer id);
    
    List<T> recuperarTodosRegistros();
    List<T> recuperarPagina(int pagina, int tamano);
    List<T> buscarPorCampo(String campo, Object valor);
}