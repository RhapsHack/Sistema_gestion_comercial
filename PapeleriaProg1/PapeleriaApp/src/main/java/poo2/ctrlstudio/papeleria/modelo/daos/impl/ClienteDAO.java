package poo2.ctrlstudio.papeleria.modelo.daos.impl;

import jakarta.persistence.EntityManager;
import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.interf.IClientesDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.Clientes;

import java.util.List;
import java.util.Optional;

// Te tengo el clienteDAO actualizado para el nuevo DAOBase, te encargo el resto.

public class ClienteDAO extends DAOBase<Clientes> implements IClientesDAO {
    private static final long serialVersionUID = 1L;

    public ClienteDAO() {
        super(Clientes.class);
    }

    public ClienteDAO(EntityManager em) {
        super(Clientes.class, em);
    }

    // Metodos especificos para busqueda de clientes
    public Optional<Clientes> buscarPorCorreo(String correo) {
        return Optional.ofNullable(findByCampoUnico("correo", correo));
    }

    public List<Clientes> buscarPorNombre(String nombre) {
        return findByCampo("nombre", nombre);
    }

    public List<Clientes> buscarPorApellido(String apellido) {
        return findByCampo("apellido", apellido);
    }

    public List<Clientes> buscarPorTelefono(int telefono) {
        return findByCampo("telefono", telefono);
    }

    public List<Clientes> consultarClientesConPublicidad() {
        return findByCampo("publicidad", true);
    }

    public List<Clientes> consultarClientesActivos() {
        return findByCampo("activo", true);
    }

    // Actualizaciones de estado puntuales
    public void actualizarPublicidad(Integer idCliente, boolean recibirPublicidad) throws DAOException {
        executeTransaction(() -> {
            Clientes cliente = findById(idCliente);
            if (cliente != null) {
                cliente.setPublicidad(recibirPublicidad);
            }
        });
    }

    public void cambiarEstadoActivo(Integer idCliente, boolean activo) throws DAOException {
        executeTransaction(() -> {
            Clientes cliente = findById(idCliente);
            if (cliente != null) {
                cliente.setActivo(activo);
            }
        });
    }
}