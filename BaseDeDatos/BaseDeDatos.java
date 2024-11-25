package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class BaseDeDatos {
	
	private static final String DB_URL = "jdbc:sqlite:usuarios.db"; // Nombre del archivo SQLite

    // Método para establecer conexión
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
            return null;
        }
    }

    // Crear tabla si no existe
    public static void inicializarBaseDeDatos() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "username TEXT NOT NULL, "
                   + "password TEXT NOT NULL);";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registrar un nuevo usuario
    public static void registrarUsuario(String usuario, String contraseña) {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar usuario.");
        }
    }
    
    private void registrarUsuario(String usuario, String contraseña, String email) {
    	
    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
