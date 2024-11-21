package MainWindow;

import java.time.LocalDate;

public class Evento {
    private LocalDate fecha;
    private String descripcion;

    public Evento(LocalDate fecha, String descripcion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
