package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.modelo.entidades.Clientes;
import poo2.ctrlstudio.papeleria.servicios.ClientesService;
import poo2.ctrlstudio.papeleria.Utils.Validadores.ClienteValidador;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;
import poo2.ctrlstudio.papeleria.vista.ventanas.unica.UnicaCatalogo;

// Te dejaria terminar clientes pero ya deje validador, por favor dale un vistazo y acomodalo a tu gusto

import java.util.ResourceBundle;

public class ClienteController extends CatalogoUnicoController<Clientes> {

    public ClienteController(ClientesService servicio,
                             PanelShowEntity<Clientes> panelFormulario,
                             PanelListEntidades<Clientes> panelListado,
                             UnicaCatalogo ventana,
                             ResourceBundle resBundle) 
                             {
                super(
                "Catalogo de Clientes",
                servicio,
                new ClienteValidador(),
                panelFormulario,
                panelListado,
                ventana,
                resBundle
        );
    }
}