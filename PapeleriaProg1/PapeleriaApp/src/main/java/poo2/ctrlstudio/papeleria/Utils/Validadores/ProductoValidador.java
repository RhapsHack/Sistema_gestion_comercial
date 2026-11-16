package poo2.ctrlstudio.papeleria.utils.validadores.entidades;

import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;
import poo2.ctrlstudio.papeleria.utils.validadores.ValidadorNumerico;
import poo2.ctrlstudio.papeleria.utils.validadores.ValidadorString;
import poo2.ctrlstudio.papeleria.utils.validadores.interfaces.IValidadorEntidad;

import java.util.ArrayList;
import java.util.List;

public class ProductoValidador implements IValidadorEntidad<Producto> {
    private final List<String> errores = new ArrayList<>();

    private final ValidadorString validadorNombre = new ValidadorString();
    private final ValidadorNumerico<Double> validadorPrecio = new ValidadorNumerico<>();
    private final ValidadorNumerico<Integer> validadorStock = new ValidadorNumerico<>();

    public ProductoValidador() {

        validadorNombre.noVacia().min(2).max(100);
        validadorPrecio.esPositivo();
        validadorStock.esPositivo();
    }

    @Override
    public boolean validar(Producto producto) {
        errores.clear();

        if (producto == null) {
            errores.add("El producto a validar es nulo.");
            return false;
        }

        // Validar nombre
        validadorNombre.setValor(producto.getNombre());
        if (!validadorNombre.validar()) {
            errores.addAll(validadorNombre.getErrores());
        }

        // Validar precio
        validadorPrecio.setValor(producto.getPrecio());
        if (!validadorPrecio.validar()) {
            errores.addAll(validadorPrecio.getErrores());
        }

        // Validar stock
        validadorStock.setValor(producto.getStock());
        if (!validadorStock.validar()) {
            errores.addAll(validadorStock.getErrores());
        }

        return errores.isEmpty();
    }

    @Override
    public <V> void agregarValidador(String nombreCampo, poo2.ctrlstudio.papeleria.utils.validadores.interfaces.IValidador<V, ?> validador, java.util.function.Function<Producto, V> getter) {

    }

    @Override
    public List<String> getErrores() {
        return List.copyOf(errores);
    }
}