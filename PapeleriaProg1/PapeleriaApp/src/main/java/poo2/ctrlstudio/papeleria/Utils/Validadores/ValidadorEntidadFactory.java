package poo2.ctrlstudio.papeleria.utils.validadores;

import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;
import poo2.ctrlstudio.papeleria.utils.validadores.entidades.ProductoValidador;
import poo2.ctrlstudio.papeleria.utils.validadores.interfaces.IValidadorEntidad;
import java.util.Optional;
import static poo2.ctrlstudio.papeleria.utils.Constantes.CAT_PRODUCTOS;

public class ValidadorEntidadFactory<T extends IEntidad> {
    
    public static Optional<IValidadorEntidad> crearValidador(String nombreCatalogo) {
        return Optional.ofNullable(
            switch (nombreCatalogo) {
                case CAT_PRODUCTOS -> new ProductoValidador();
                default -> null;
            }
        );
    }
}