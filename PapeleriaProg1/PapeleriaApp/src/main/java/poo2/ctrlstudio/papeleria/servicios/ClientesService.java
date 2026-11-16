package poo2.ctrlstudio.papeleria.servicios;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ClienteDAO;
import poo2.ctrlstudio.papeleria.modelo.entidades.Clientes;

import java.util.List;

public class ClientesService {
    private final ClienteDAO clienteDAO;

    public ClientesService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void agregarCliente(Clientes cliente) throws DAOException {
        clienteDAO.save(cliente);
    }

    public void editarCliente(Clientes cliente) throws DAOException {
        clienteDAO.update(cliente);
    }

    public void eliminarCliente(Clientes cliente) throws DAOException {
        clienteDAO.delete(cliente);
    }

    public Clientes buscarPorId(Integer id) {
        return clienteDAO.findById(id);
    }

    public List<Clientes> recuperarTodos() {
        return clienteDAO.findAll();
    }
}
