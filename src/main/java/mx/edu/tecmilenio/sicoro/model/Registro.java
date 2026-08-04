package mx.edu.tecmilenio.sicoro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Registro {
    private final String id;
    private final LocalDate fecha;
    private final String area;
    private final String responsable;
    private final String tipo;
    private final String descripcion;
    private String estatus;
    private LocalDateTime fechaActualizacion;

    public Registro(String id, LocalDate fecha, String area, String responsable,
                    String tipo, String descripcion, String estatus,
                    LocalDateTime fechaActualizacion) {
        this.id = Objects.requireNonNull(id);
        this.fecha = Objects.requireNonNull(fecha);
        this.area = Objects.requireNonNull(area);
        this.responsable = Objects.requireNonNull(responsable);
        this.tipo = Objects.requireNonNull(tipo);
        this.descripcion = Objects.requireNonNull(descripcion);
        this.estatus = Objects.requireNonNull(estatus);
        this.fechaActualizacion = Objects.requireNonNull(fechaActualizacion);
    }

    public String getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getArea() { return area; }
    public String getResponsable() { return responsable; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public String getEstatus() { return estatus; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    public void actualizarEstatus(String nuevoEstatus) {
        this.estatus = Objects.requireNonNull(nuevoEstatus);
        this.fechaActualizacion = LocalDateTime.now();
    }
}
