package src.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Objetivo {
	public String nombre;
	public LocalDate fechaFin;
	public String descripcion;
	public int cuantoQueda;
	public boolean completado;
	
	public Objetivo(String nombre, String descripcion, LocalDate fechaFin,boolean completado) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaFin = fechaFin;
		this.completado = completado;
	}
	
	public int calcularCuantoQueda() {
        LocalDate hoy = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(hoy, fechaFin);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCuantoQueda() {
		return cuantoQueda;
	}

	public void setCuantoQueda(int cuantoQueda) {
		this.cuantoQueda = cuantoQueda;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}
	
	
}
