package poo2.ctrlstudio.papeleria.vista.interfaces;

public interface IVista {
    void mostrarMensaje(String mensaje);
    void mostrarError(String mensaje);
    void limpiarCampos();
    void actualizarVista();
}