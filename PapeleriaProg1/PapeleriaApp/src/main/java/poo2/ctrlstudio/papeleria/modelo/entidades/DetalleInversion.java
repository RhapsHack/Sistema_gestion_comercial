package poo2.ctrlstudio.papeleria.modelo.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_inversiones")
public class DetalleInversion implements IEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleInversion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_boleta")
    private BoletaInversion boleta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(nullable = false)
    private int cantidadAgregada;

    @Column(nullable = false)
    private double costoUnitario;

    @Column(nullable = false)
    private double subtotal;

    public DetalleInversion() {}

    public DetalleInversion(Producto producto, int cantidadAgregada, double costoUnitario) {
        this.producto = producto;
        this.cantidadAgregada = cantidadAgregada;
        this.costoUnitario = costoUnitario;
        this.subtotal = costoUnitario * cantidadAgregada;
    }

    public Integer getIdDetalleInversion() { return idDetalleInversion; }
    public void setIdDetalleInversion(Integer idDetalleInversion) { this.idDetalleInversion = idDetalleInversion; }
    public BoletaInversion getBoleta() { return boleta; }
    public void setBoleta(BoletaInversion boleta) { this.boleta = boleta; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidadAgregada() { return cantidadAgregada; }
    public void setCantidadAgregada(int cantidadAgregada) { this.cantidadAgregada = cantidadAgregada; }
    public double getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(double costoUnitario) { this.costoUnitario = costoUnitario; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}