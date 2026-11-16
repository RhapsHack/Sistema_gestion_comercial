package poo2.ctrlstudio.papeleria.controlador;

import poo2.ctrlstudio.papeleria.modelo.entidades.Producto;
import poo2.ctrlstudio.papeleria.servicios.ProductoService;
import poo2.ctrlstudio.papeleria.Utils.Validadores.ProductoValidador;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelListEntidades;
import poo2.ctrlstudio.papeleria.vista.paneles.PanelShowEntity;
import poo2.ctrlstudio.papeleria.vista.ventanas.unica.UnicaCatalogo;

import java.util.ResourceBundle;

public class ProductoController extends CatalogoUnicoController<Producto> {

    public ProductoController(ProductoService servicio, PanelShowEntity<Producto> panelFormulario, PanelListEntidades<Producto> panelListado,
                              UnicaCatalogo ventana, ResourceBundle resBundle) {
                super(
                "Catalogo de Productos",
                servicio,
                new ProductoValidador(),
                panelFormulario,
                panelListado,
                ventana,
                resBundle
        );
    }
}