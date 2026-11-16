package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.entidades.Venta;
import poo2.ctrlstudio.papeleria.servicios.ReglaNegocioException;
import poo2.ctrlstudio.papeleria.servicios.VentaService;

import javax.swing.*;
import java.util.List;

public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void procesarVenta(Venta venta) {
        try {
            Venta registrada = ventaService.registrarVenta(venta);
            JOptionPane.showMessageDialog(
                    null,
                    "¡Venta registrada con éxito!\nID del Ticket: " + registrada.getIdVenta() + 
                    "\nTotal: $" + registrada.getTotal() + 
                    "\nCambio: $" + registrada.getMontoCambio(),
                    "Ticket Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (ReglaNegocioException | DAOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo completar la venta: " + e.getMessage(),
                    "Error en Venta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public List<Venta> listarVentas() {
        try {
            return ventaService.recuperarTodosRegistros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar el historial de ventas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return List.of();
        }
    }
}