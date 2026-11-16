package poo2.ctrlstudio.papeleria.utils.validadores.entidades;

import poo2.ctrlstudio.papeleria.modelo.entidades.Clientes;
import poo2.ctrlstudio.papeleria.utils.validadores.interfaces.IValidadorEntidad;

import java.util.ArrayList;
import java.util.List;

public class ClienteValidador implements IValidadorEntidad<Clientes> {
    private final List<String> errores = new ArrayList<>();

    @Override
    public boolean validar(Clientes cliente) {
        errores.clear();


        // Validador cliente
        if (cliente == null) {
            errores.add("El cliente no puede ser nulo.");
            return false;
        }


        // Validador nombre cliente
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            errores.add("El nombre del cliente no puede estar vacio.");
        }

        // Validador telefono cliente
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            errores.add("El telefono del cliente no puede estar vacio.");
        }

        // No se como quieran las validaciones, te lo dejo a elección mantener estos o agregar.

        return errores.isEmpty();
    }

    @Override
    public List<String> getErrores() {
        return errores;
    }
}