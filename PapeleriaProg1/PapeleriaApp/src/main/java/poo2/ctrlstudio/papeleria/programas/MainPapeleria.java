package poo2.ctrlstudio.papeleria.programas;

import poo2.ctrlstudio.papeleria.controlador.ProductoController;
import poo2.ctrlstudio.papeleria.modelo.daos.impl.ProductoDAO;
import poo2.ctrlstudio.papeleria.modelo.utils.JPAUtil;
import poo2.ctrlstudio.papeleria.servicios.ProductoService;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;
import poo2.ctrlstudio.papeleria.vista.ventanas.unica.UnicaCatalogo;

import jakarta.persistence.EntityManager;
import java.util.ResourceBundle;
import javax.swing.*;

public class MainPapeleria {

    public static void main(String[] args) {
        System.out.println("Iiniciando el sistema de papeleria");

        SwingUtilities.invokeLater(() -> {
            EntityManager em = null;
            try {
                em = JPAUtil.crearEntityManager();

                ProductoDAO productoDAO = new ProductoDAO(em);
                ProductoService productoService = new ProductoService(productoDAO);
                ProductoController controlador = new ProductoController(productoService);

                UnicaCatalogo ventana = new UnicaCatalogo("Gestion de Inventario");

                ventana.setVisible(true);

                System.out.println("Interfaz grafica iniciada exitosamente.");

            } catch (Exception e) {
                System.err.println("Error critico al iniciar la aplicacion grafica: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Error critico al iniciar el sistema: " + e.getMessage(),
                        "Error de Inicio",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}