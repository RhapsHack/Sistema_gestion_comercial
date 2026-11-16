package poo2.ctrlstudio.papeleria.vista.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelListEntidades extends JPanel {
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;

    public PanelListEntidades() {
        setLayout(new BorderLayout());
        
        // Iniciando el modelo de las tablas y no permitir su edicion
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaDatos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDatos);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTablaDatos() {
        return tablaDatos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}