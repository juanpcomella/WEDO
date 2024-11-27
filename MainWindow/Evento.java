package MainWindow;

import java.time.LocalDate;



public class Evento {

    private String nombre;

    private String descripcion;

    private Categorias categoria;

    private LocalDate fecha;



    public Evento(String nombre, String descripcion, Categorias categoria, LocalDate fecha) {

        this.nombre = nombre;

        this.descripcion = descripcion;

        this.categoria = categoria;

        this.fecha = fecha;

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

}

