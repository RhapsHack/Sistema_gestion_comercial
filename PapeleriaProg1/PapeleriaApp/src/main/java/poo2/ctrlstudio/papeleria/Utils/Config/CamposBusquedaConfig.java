package poo2.ctrlstudio.papeleria.utils.config;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static poo2.ctrlstudio.papeleria.utils.Constantes.CAT_PRODUCTOS;

public abstract class CamposBusquedaConfig {

    // Mapeo de los campos buscables
    private static final Map<String, CampoBusqueda> CAMPOS_PRODUCTOS = campos(
            entry("id", "id", TipoCampoBusqueda.INTEGER),
            entry("nombre", "nombre", TipoCampoBusqueda.STRING),
            entry("precio", "precio", TipoCampoBusqueda.STRING), // planteandome si hacerlo double
            entry("stock", "stock", TipoCampoBusqueda.INTEGER),
            entry("activo", "activo", TipoCampoBusqueda.BOOLEAN)
    );

    private CamposBusquedaConfig() {
    }

    private static Map<String, CampoBusqueda> campos(CampoBusqueda... registros) {
        var salida = new LinkedHashMap<String, CampoBusqueda>();
        for (CampoBusqueda registro : registros) {
            salida.put(registro.key(), registro);
        }
        return salida;
    }

    private static CampoBusqueda entry(String key, String atributoJpa, TipoCampoBusqueda tipo) {
        return new CampoBusqueda(key, atributoJpa, tipo);
    }

    public static Map<String, CampoBusqueda> campos(String nombreCatalogo) {
        return switch (nombreCatalogo) {
            case CAT_PRODUCTOS -> CAMPOS_PRODUCTOS;
            default -> Map.of();
        };
    }

    public static Optional<CampoBusqueda> campoPorEtiqueta(String nombreCatalogo, String etiquetaBundle) {
        return Optional.ofNullable(campos(nombreCatalogo).get(etiquetaBundle));
    }

    /**
     * Convierte el texto escrito a un valor del tipo del campo buscado.
     */
    public static Optional<Object> convertirValor(CampoBusqueda campo, String texto) {
        if (campo == null || texto == null) {
            return Optional.empty();
        }
        var valor = texto.trim();
        return switch (campo.tipo()) {
            case STRING -> Optional.of(valor);
            case INTEGER -> convertirEntero(valor);
            case BOOLEAN -> convertirBooleano(valor);
        };
    }

    public static Optional<Object> convertirValor(String nombreCatalogo, String etiquetaBundle, String texto) {
        return campoPorEtiqueta(nombreCatalogo, etiquetaBundle).flatMap(campo -> convertirValor(campo, texto));
    }

    private static Optional<Object> convertirEntero(String valor) {
        try {
            return Optional.of(Integer.parseInt(valor));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Object> convertirBooleano(String valor) {
        var texto = valor.toLowerCase(Locale.ROOT);
        if (texto.equals("true") || texto.equals("verdadero")) {
            return Optional.of(Boolean.TRUE);
        }
        if (texto.equals("false") || texto.equals("falso")) {
            return Optional.of(Boolean.FALSE);
        }
        return Optional.empty();
    }
}