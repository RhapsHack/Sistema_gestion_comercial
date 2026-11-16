package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;
import poo2.ctrlstudio.papeleria.servicios.interfaces.IServicioBase;
import poo2.ctrlstudio.papeleria.Utils.Config.CampoBusqueda;
import poo2.ctrlstudio.papeleria.Utils.Config.CamposBusquedaConfig;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;
import poo2.ctrlstudio.papeleria.vista.ventanas.unica.UnicaCatalogo;
import mx.edu.uaz.ingsoft.poo2.utils.validadores.interfaces.IValidadorEntidad;

import javax.swing.*;
import java.util.List;
import java.util.ResourceBundle;

import static mx.edu.uaz.ingsoft.poo2.utils.Constantes.MSG_ERROR_VALIDACION;

public class CatalogoUnicoController<T extends IEntidad> {
    public enum Modo {
        ALTA,
        DETALLE,
        EDICION
    }

    private final String nombreCatalogo;
    private final IServicioBase<T> servicio;
    private final IValidadorEntidad<T> validador;
    private final PanelShowEntity<T> panelFormulario;
    private final PanelListEntidades<T> panelListado;
    private final UnicaCatalogo ventana;
    private final ResourceBundle resBundle;

    private Modo modo = Modo.ALTA;
    private T registroActual;
    private int pagina = 0;
    private int tamanoPagina = 5;
    private boolean paginaCompleta = true;
    private boolean busquedaActiva = false;
    private CampoBusqueda campoBusquedaActivo;
    private String textoBusquedaActivo;

    public CatalogoUnicoController(String nombreCatalogo,
                                   IServicioBase<T> servicio,
                                   IValidadorEntidad<T> validador,
                                   PanelShowEntity<T> panelFormulario,
                                   PanelListEntidades<T> panelListado,
                                   UnicaCatalogo ventana,
                                   ResourceBundle resBundle) {
        this.nombreCatalogo = nombreCatalogo;
        this.servicio = servicio;
        this.validador = validador;
        this.panelFormulario = panelFormulario;
        this.panelListado = panelListado;
        this.ventana = ventana;
        this.resBundle = resBundle;

        inicializarEventos();
        cargarPagina();
    }

    private void inicializarEventos() {
        // Eventos
        panelListado.setAccionSeleccion(e -> {
            var seleccionado = panelListado.obtenerSeleccionado();
            if (seleccionado != null) {
                registroActual = seleccionado;
                cambiarModo(Modo.DETALLE);
                panelFormulario.mostrar(registroActual);
            }
        });

        // Boton de Nuevo (Alta)
        ventana.setAccionNuevo(e -> {
            cambiarModo(Modo.ALTA);
            panelFormulario.limpiar();
        });

        // Boton Edita
        ventana.setAccionEditar(e -> {
            if (registroActual != null) {
                cambiarModo(Modo.EDICION);
            }
        });

        // Boton de borrar
        ventana.setAccionBorrar(e -> {
            if (registroActual != null) {
                try {
                    servicio.borrarRegistro(registroActual);
                    cargarPagina();
                    cambiarModo(Modo.ALTA);
                    panelFormulario.limpiar();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Boton de aceptar
        ventana.setAccionAceptar(e -> {
            panelFormulario.leer(registroActual);
            if (validador.validar(registroActual)) {
                try {
                    if (modo == Modo.ALTA) {
                        servicio.guardar(registroActual);
                    } else if (modo == Modo.EDICION) {
                        servicio.actualizar(registroActual);
                    }
                    cargarPagina();
                    cambiarModo(Modo.DETALLE);
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(ventana, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                StringBuilder sb = new StringBuilder(MSG_ERROR_VALIDACION).append("\n");
                for (String err : validador.getErrores()) {
                    sb.append("- ").append(err).append("\n");
                }
                JOptionPane.showMessageDialog(ventana, sb.toString(), "Validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Boton de cancelar
        ventana.setAccionCancelar(e -> {
            if (modo == Modo.EDICION && registroActual != null) {
                cambiarModo(Modo.DETALLE);
                panelFormulario.mostrar(registroActual);
            } else {
                cambiarModo(Modo.ALTA);
                panelFormulario.limpiar();
            }
        });
    }

    private void cambiarModo(Modo nuevoModo) {
        this.modo = nuevoModo;
        switch (nuevoModo) {
            case ALTA -> {
                panelFormulario.habilitarCampos(true);
                ventana.configurarBotonesAlta();
            }
            case DETALLE -> {
                panelFormulario.habilitarCampos(false);
                ventana.configurarBotonesDetalle();
            }
            case EDICION -> {
                panelFormulario.habilitarCampos(true);
                ventana.configurarBotonesEdicion();
            }
        }
    }

    public void cargarPagina() {
        List<T> datos;
        if (busquedaActiva && campoBusquedaActivo != null) {
            var valorTipado = CamposBusquedaConfig.convertirValor(campoBusquedaActivo, textoBusquedaActivo);
            if (valorTipado.isPresent()) {
                datos = servicio.buscarPorCampo(campoBusquedaActivo.atributoJpa(), valorTipado.get());
            } else {
                datos = List.of();
            }
        } else {
            datos = servicio.recuperarPagina(pagina, tamanoPagina);
        }
        panelListado.actualizarTabla(datos);
    }
}