package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.modelo.daos.DAOException;
import poo2.ctrlstudio.papeleria.modelo.entidades.BoletaInversion;
import poo2.ctrlstudio.papeleria.servicios.BoletaInversionService;
import poo2.ctrlstudio.papeleria.servicios.ReglaNegocioException;

import javax.swing.*;
import java.util.List;

public class BoletaInversionController {
    private final BoletaInversionService boletaInversionService;

    public BoletaInversionController(BoletaInversionService boletaInversionService) {
        this.boletaInversionService = boletaInversionService;
    }

    public void procesarInversion(BoletaInversion boleta) {
        try {
            BoletaInversion registrada = boletaInversionService.registrarInversion(boleta);
            JOptionPane.showMessageDialog(
                    null,
                    "¡Inversion registrada con exito!\nID de Boleta: " + registrada.getIdBoleta() + 
                    "\nGasto Total del Lote: $" + registrada.getTotalInversion(),
                    "Reabastecimiento Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (ReglaNegocioException | DAOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo registrar la inversion: " + e.getMessage(),
                    "Error de Inversion",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public List<BoletaInversion> listarInversiones() {
        try {
            return boletaInversionService.recuperarTodosRegistros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar el historial de inversiones: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return List.of();
        }
    }
}