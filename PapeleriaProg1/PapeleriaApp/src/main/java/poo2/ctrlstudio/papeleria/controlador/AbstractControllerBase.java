package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.controlador.interfaces.IControllerBase;
import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.entidades.IEntidad;
import poo2.ctrlstudio.papeleria.servicios.interfaces.IServicioBase;
import poo2.ctrlstudio.papeleria.utils.validadores.interfaces.IValidadorEntidad;
import poo2.ctrlstudio.papeleria.vista.ventanas.actualizacion.AbstractVentanaUpdateEntidad;
import poo2.ctrlstudio.papeleria.vista.ventanas.borrado.AbstractVentanaBorradoEntidad;
import poo2.ctrlstudio.papeleria.vista.ventanas.captura.AbstractVentanaCapturaEntidad;
import poo2.ctrlstudio.papeleria.vista.ventanas.listado.AbstractVentanaListadoEntidades;

import javax.swing.*;
import java.util.List;

import static poo2.ctrlstudio.papeleria.utils.Constantes.MSG_ERROR_VALIDACION;
import static poo2.ctrlstudio.papeleria.utils.FunctionUtils.crearInstancia;

public abstract class AbstractControllerBase<T extends IEntidad, U extends IServicioBase<T>> implements IControllerBase<T> {
    protected final U servicio;
    protected final AbstractVentanaCapturaEntidad<T> ventanaCaptura;
    protected final AbstractVentanaListadoEntidades<T> ventanaListado;
    protected final AbstractVentanaBorradoEntidad<T> ventanaBorrado;
    protected final AbstractVentanaUpdateEntidad<T> ventanaUpdate;
    protected final IValidadorEntidad<T> validadorEntidad;

    protected List<T> registros;
    protected T registroActual;
    protected Class<T> clazz;

    public AbstractControllerBase(Class<T> clazz,
                                  U servicio,
                                  AbstractVentanaCapturaEntidad<T> ventanaCaptura,
                                  AbstractVentanaListadoEntidades<T> ventanaListado,
                                  AbstractVentanaBorradoEntidad<T> ventanaBorrado,
                                  AbstractVentanaUpdateEntidad<T> ventanaUpdateEntidad,
                                  IValidadorEntidad<T> validadorEntidad) {
        this.clazz = clazz;
        this.validadorEntidad = validadorEntidad;
        this.servicio = servicio;
        
        registros = servicio.recuperarTodosRegistros();
        registroActual = null;

        this.ventanaCaptura = ventanaCaptura;
        if (this.ventanaCaptura != null) {
            this.ventanaCaptura.setAccionAceptar(e -> aceptarCaptura());
            this.ventanaCaptura.setAccionCancelar(e -> cancelarCaptura(ventanaCaptura));
        }

        this.ventanaListado = ventanaListado;
        if (this.ventanaListado != null) {
            this.ventanaListado.setAccionAceptar(e -> aceptarListado());
            this.ventanaListado.setAccionRegresar(e -> ventanaListado.setVisible(false));
        }

        this.ventanaBorrado = ventanaBorrado;
        if (this.ventanaBorrado != null) {
            this.ventanaBorrado.setAccionRegresar(e -> ventanaBorrado.setVisible(false));
            this.ventanaBorrado.setAccionAceptar(e -> borrarRegistro());
        }

        this.ventanaUpdate = ventanaUpdateEntidad;
        if (this.ventanaUpdate != null) {
            this.ventanaUpdate.setAccionAceptar(e -> aceptarUpdate());
            this.ventanaUpdate.setAccionCancelar(e -> cancelarCaptura(ventanaUpdate));
        }
    }

    @Override
    public void agregarRegistro() {
        try {
            servicio.guardar(registroActual);
        } catch (DAOException e) {
            mostrarErrorPersistencia(e);
            return;
        }
        registros = servicio.recuperarTodosRegistros();
    }

    @Override
    public void actualizarRegistro() {
        try {
            servicio.actualizar(registroActual);
        } catch (DAOException e) {
            mostrarErrorPersistencia(e);
            return;
        }
        registros = servicio.recuperarTodosRegistros();
    }

    public void borrarRegistro() {
        try {
            if (registroActual != null) {
                servicio.borrarRegistro(registroActual);
            }
        } catch (DAOException e) {
            mostrarErrorPersistencia(e);
        }
        registros = servicio.recuperarTodosRegistros();
    }

    public void aceptarCaptura() {
        if (validadorEntidad.validar(registroActual)) {
            agregarRegistro();
            if (ventanaCaptura != null) {
                ventanaCaptura.limpiarFormulario();
                ventanaCaptura.setVisible(false);
            }
        } else {
            mostrarErroresValidacion();
        }
    }

    public void aceptarUpdate() {
        if (validadorEntidad.validar(registroActual)) {
            actualizarRegistro();
            if (ventanaUpdate != null) {
                ventanaUpdate.limpiarFormulario();
                ventanaUpdate.setVisible(false);
            }
        } else {
            mostrarErroresValidacion();
        }
    }

    public void aceptarListado() {
        if (ventanaListado != null) {
            ventanaListado.setVisible(false);
        }
    }

    public void cancelarCaptura(JFrame ventana) {
        if (ventana != null) {
            ventana.setVisible(false);
        }
    }

    protected void mostrarErroresValidacion() {
        StringBuilder sb = new StringBuilder(MSG_ERROR_VALIDACION).append("\n");
        for (String error : validadorEntidad.getErrores()) {
            sb.append("- ").append(error).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarErrorPersistencia(DAOException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
    }

    public T crearNuevoRegistro() throws ExceptionInInitializerError {
        var instanciaCreada = crearInstancia(clazz);
        if (instanciaCreada.isPresent()) {
            return instanciaCreada.get();
        } else {
            return null;
        }
    }
}