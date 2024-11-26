package BaseDeDatos;

import java.sql.*;
import java.util.ArrayList;

public class BDs {
	public static void crearTabla() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			// Ejecutar sentencias SQL (Delete)
//			statement.executeUpdate("drop table if exists person");
			
			// Ejecutar sentencias SQL (Update)
			statement.executeUpdate("create table if not exists usuarios (username string, password string, email string)");

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch(SQLException e) {
				// Cierre de conexión fallido
				System.err.println(e);
			}
	}
	}
	public static void insertarElementos(String usuario, String contraseña, String email) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "insert into usuarios (username, password, email) VALUES (?, ?, ?)";

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Asignar los valores de las variables a los parámetros
            preparedStatement.setString(1, usuario);     
            preparedStatement.setString(2, contraseña);  
            preparedStatement.setString(3, email);
            
            preparedStatement.executeUpdate();

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch(SQLException e) {
				// Cierre de conexión fallido
				System.err.println(e);
			}
	}
	}
	public static ArrayList<String> usuarioExistente() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		ArrayList<String> nombresUsuario = new ArrayList<>();

		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT username FROM usuarios";
            ResultSet resultSet = statement.executeQuery(sql);

            // Recorrer los resultados y añadirlos al ArrayList
            while (resultSet.next()) {
                nombresUsuario.add(resultSet.getString("username"));
            }

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch(SQLException e) {
				// Cierre de conexión fallido
				System.err.println(e);
			}
	}
	return nombresUsuario;
	}
	public static ArrayList<String> contraseñaExistente() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		ArrayList<String> contraseñaExistente = new ArrayList<>();

		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			ResultSet rs = statement.executeQuery("select * from usuarios");
			while(rs.next()) {
				contraseñaExistente.add(rs.getString("password"));
			}

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if(connection != null)
					connection.close();
			} catch(SQLException e) {
				// Cierre de conexión fallido
				System.err.println(e);
			}
	}
	return contraseñaExistente;
	}
	

}