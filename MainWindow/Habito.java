package MainWindow;

public class Habito {
	public String nombre;
	public String descipcion;
	public boolean completado;
	
	public Habito(String nombre, String descripcion, boolean completado) {
		this.nombre =nombre;
		this.descipcion = descripcion;
		this.completado = completado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescipcion() {
		return descipcion;
	}

	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}
	
	
}
