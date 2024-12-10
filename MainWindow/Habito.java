package MainWindow;

public class Habito {
	public String fecha;
	public String nombre;
	public String descripcion;
	public boolean completado;
	
	public Habito(String nombre, String descripcion, boolean completado) {
		this.nombre =nombre;
		this.descripcion = descripcion;
		this.completado = completado;
	}
	public Habito(String fecha, String nombre) {
		this.fecha = fecha;
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescipcion() {
		return descripcion;
	}

	public void setDescipcion(String descipcion) {
		this.descripcion = descipcion;
	}

	public boolean isCompletado() {
		return completado;
	}
	public void setCompletado(boolean completado) {
		this.completado = completado;
	}


	
	
}
