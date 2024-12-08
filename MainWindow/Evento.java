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
    private boolean privado;

    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.todoElDia = todoElDia;
    }

    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.todoElDia = false;  
    }
    
    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.todoElDia = todoElDia; 
    }
    
    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, boolean todoElDia, boolean privado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.todoElDia = todoElDia; 
        this.privado = privado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esTodoElDia() {
		return todoElDia;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
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


    public void setTodoElDia(boolean todoElDia) {
        this.todoElDia = todoElDia;
    }

    public int getDuracionEnMinutos() {
        if (todoElDia) {
            return 1440; 
        } else if (horaInicio != null && horaFin != null) {
            return (int) ChronoUnit.MINUTES.between(horaInicio, horaFin);
        }
        return 0;
    }
}
