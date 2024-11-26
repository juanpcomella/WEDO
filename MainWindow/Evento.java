package MainWindow;

class Evento {
    private String nombre;
    private String descripcion;
    private Categorias categoria;

    public Evento(String nombre, String descripcion, Categorias categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
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
}
