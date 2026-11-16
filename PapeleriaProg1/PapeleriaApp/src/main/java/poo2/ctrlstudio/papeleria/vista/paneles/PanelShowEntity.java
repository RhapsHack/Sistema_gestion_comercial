package poo2.ctrlstudio.papeleria.vista.paneles;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PanelShowEntity extends JPanel {
    private final Map<String, JTextField> camposTexto;

    public PanelShowEntity() {
        setLayout(new GridLayout(0, 2, 10, 10));
        camposTexto = new HashMap<>();
        
        // Inicializa los campos
        inicializarCampo("ID (Auto)", false);
        inicializarCampo("Nombre", true);
        inicializarCampo("Precio", true);
        inicializarCampo("Stock", true);
    }

    private void inicializarCampo(String etiqueta, boolean editable) {
        JLabel label = new JLabel(etiqueta + ":");
        JTextField textField = new JTextField();
        textField.setEditable(editable);
        
        camposTexto.put(etiqueta.toLowerCase(), textField);
        add(label);
        add(textField);
    }

    public JTextField getCampo(String nombreCampo) {
        return camposTexto.get(nombreCampo.toLowerCase());
    }

    public void limpiarFormulario() {
        for (JTextField field : camposTexto.values()) {
            field.setText("");
        }
    }
}