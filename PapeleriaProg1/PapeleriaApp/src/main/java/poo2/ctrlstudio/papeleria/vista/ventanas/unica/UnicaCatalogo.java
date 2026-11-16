package poo2.ctrlstudio.papeleria.vista.ventanas.unica;

import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UnicaCatalogo extends JFrame {
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnBorrar;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private PanelListEntidades panelListado;
    private PanelShowEntity panelFormulario;

    public UnicaCatalogo(String titulo) {
        setTitle(titulo);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Paneles principales
        panelListado = new PanelListEntidades();
        panelFormulario = new PanelShowEntity();

        // Dividir la ventana en dos secciones
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelListado, panelFormulario);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // Panel inferior para los botones de accion CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnBorrar = new JButton("Borrar");
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
        
        // Estado inicial de los botones
        configurarBotonesAlta();
    }

    // Metodos para asignar los listener
    public void setAccionNuevo(ActionListener listener) { btnNuevo.addActionListener(listener); }
    public void setAccionEditar(ActionListener listener) { btnEditar.addActionListener(listener); }
    public void setAccionBorrar(ActionListener listener) { btnBorrar.addActionListener(listener); }
    public void setAccionAceptar(ActionListener listener) { btnAceptar.addActionListener(listener); }
    public void setAccionCancelar(ActionListener listener) { btnCancelar.addActionListener(listener); }

    // Configuracion de botones
    public void configurarBotonesAlta() {
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnAceptar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    public void configurarBotonesDetalle() {
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnAceptar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    public void configurarBotonesEdicion() {
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnAceptar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    public PanelListEntidades getPanelListado() {
        return panelListado;
    }

    public PanelShowEntity getPanelFormulario() {
        return panelFormulario;
    }
}