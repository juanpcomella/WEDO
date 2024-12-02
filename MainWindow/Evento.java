package MainWindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Evento {
    private String nombre;
    private String descripcion;
    private Categorias categoria;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean todoElDia;

    // Constructor para evento todo el día
    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.todoElDia = todoElDia;
    }

    // Constructor para evento con hora de inicio y fin
    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.todoElDia = false;  // No es todo el día si tiene horas específicas
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public boolean esTodoElDia() {
        return todoElDia;
    }

    public void setTodoElDia(boolean todoElDia) {
        this.todoElDia = todoElDia;
    }

    // Método para calcular la duración en minutos
    public int getDuracionEnMinutos() {
        if (todoElDia) {
            return 1440;  // Un día tiene 1440 minutos
        } else if (horaInicio != null && horaFin != null) {
            return (int) ChronoUnit.MINUTES.between(horaInicio, horaFin);
        }
        return 0;  // Si no hay duración definida
    }
}
