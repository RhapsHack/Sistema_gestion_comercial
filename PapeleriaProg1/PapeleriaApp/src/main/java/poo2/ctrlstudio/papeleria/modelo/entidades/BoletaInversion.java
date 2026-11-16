package poo2.ctrlstudio.papeleria.modelo.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boletas_inversion")
public class BoletaInversion implements IEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoleta;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private String proveedor;

    @Column(nullable = false)
    private double totalInversion;

    private String observaciones;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleInversion> detalles = new ArrayList<>();

    public BoletaInversion() {
        this.fecha = LocalDateTime.now();
    }

    public void agregarDetalle(DetalleInversion detalle) {
        detalles.add(detalle);
        detalle.setBoleta(this);
    }

    // Getters y Setters
    public Integer getIdBoleta() { return idBoleta; }
    public void setIdBoleta(Integer idBoleta) { this.idBoleta = idBoleta; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public double getTotalInversion() { return totalInversion; }
    public void setTotalInversion(double totalInversion) { this.totalInversion = totalInversion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public List<DetalleInversion> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleInversion> detalles) { this.detalles = detalles; }
}