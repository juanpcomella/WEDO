package StartingWindows;

public class Usuario {
	
	String nombreUsuario;
	String correo;
	String contraseña;
	public int dinero;
	int saldo;

	public Usuario(String nombreUsuario, String correo, String contraseña, int dinero) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.contraseña = contraseña;
		this.dinero = dinero;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public int getDinero() {
		return dinero;
	}
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true; // Mismo objeto en memoria
		if (obj == null || getClass() != obj.getClass()) return false; // Tipos diferentes
		Usuario usuario = (Usuario) obj;
		return this.nombreUsuario.equals(usuario.nombreUsuario); // Compara por un identificador único
	}

	public static void main(String[] args) {

	}

}
