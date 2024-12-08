package StartingWindows;

public class Usuario {
	
	String nombreUsuario;
	String correo;
	String contraseña;
	int saldo;

	public Usuario(String nombreUsuario, String correo, String contraseña) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.contraseña = contraseña;
	}
	

	public int getSaldo() {
		return saldo;
	}


	public void setSaldo(int saldo) {
		this.saldo = saldo;
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
