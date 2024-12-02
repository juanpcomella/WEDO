package MainWindow;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento {
    private String nombre;
    private String descripcion;
    private Categorias categoria;
    private LocalDate fecha;
    private boolean todoElDia;

    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.todoElDia = todoElDia;
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
    
    public boolean esTodoElDia() {
        return todoElDia;
    }

    public void setTodoElDia(boolean todoElDia) {
        this.todoElDia = todoElDia;
    }
}
