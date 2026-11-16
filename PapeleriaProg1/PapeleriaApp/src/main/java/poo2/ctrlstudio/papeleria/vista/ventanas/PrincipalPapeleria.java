package poo2.ctrlstudio.papeleria.vista.ventanas;

import poo2.ctrlstudio.papeleria.controlador.ClienteController;
import poo2.ctrlstudio.papeleria.controlador.ProductoController;
import poo2.ctrlstudio.papeleria.controlador.VentaController;
import poo2.ctrlstudio.papeleria.controlador.BoletaInversionController;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ClienteDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.VentaDAO;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.BoletaInversionDAO;
import poo2.ctrlstudio.papeleria.servicios.ClientesService;
import poo2.ctrlstudio.papeleria.servicios.ProductoService;
import poo2.ctrlstudio.papeleria.servicios.VentaService;
import poo2.ctrlstudio.papeleria.servicios.BoletaInversionService;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;
import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;
import poo2.ctrlstudio.papeleria.modelo.entidades.Clientes;

import jakarta.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class PrincipalPapeleria extends JFrame {

    private final EntityManager em;

    public PrincipalPapeleria(EntityManager em) {
        this.em = em;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Control y Gestion de Papeleria");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de productos
        JPanel panelProductosContainer = new JPanel(new BorderLayout());
        PanelShowEntity<Producto> formProducto = new PanelShowEntity<>();
        PanelListEntidades<Producto> listProducto = new PanelListEntidades<>();
        JSplitPane splitProducto = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formProducto, listProducto);
        splitProducto.setDividerLocation(350);
        panelProductosContainer.add(splitProducto, BorderLayout.CENTER);

        ProductoDAO productoDAO = new ProductoDAO(em);
        ProductoService productoService = new ProductoService(productoDAO);

        new ProductoController(productoService, formProducto, listProducto, null, ResourceBundle.getBundle("messages.messages"));

        tabbedPane.addTab("Productos", panelProductosContainer);

        // Pestaña de clientes
        JPanel panelClientesContainer = new JPanel(new BorderLayout());
        PanelShowEntity<Clientes> formCliente = new PanelShowEntity<>();
        PanelListEntidades<Clientes> listCliente = new PanelListEntidades<>();
        JSplitPane splitCliente = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formCliente, listCliente);
        splitCliente.setDividerLocation(350);
        panelClientesContainer.add(splitCliente, BorderLayout.CENTER);

        ClienteDAO clienteDAO = new ClienteDAO(em);
        ClientesService clienteService = new ClientesService(clienteDAO);
        new ClienteController(clienteService, formCliente, listCliente, null, ResourceBundle.getBundle("messages.messages"));

        tabbedPane.addTab("Clientes", panelClientesContainer);

        // Pestaña de ventas y tickets
        JPanel panelVentas = new JPanel();
        panelVentas.add(new JLabel("Modulo de Ventas"));
        tabbedPane.addTab("Ventas (Tickets)", panelVentas);

        // Pestañas de boletas de inversion
        JPanel panelInversiones = new JPanel();
        panelInversiones.add(new JLabel("Modulo de Boletas de Inversion / Lotes"));
        tabbedPane.addTab("Boletas de Inversion", panelInversiones);

        add(tabbedPane, BorderLayout.CENTER);
    }
}