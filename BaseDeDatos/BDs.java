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
	public static Boolean usuarioExistente(String nombreBuscado) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		Boolean existe = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT username FROM usuarios WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, nombreBuscado);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            } else {
                existe = false;
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
		return existe;
	}
	public static Boolean contraseñaExistente(String contraseñaBuscada) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		Boolean existe = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT password FROM usuarios WHERE password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, contraseñaBuscada);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            } else {
                existe = false;
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
		return existe;
	}
	
	public static Boolean emailExistente(String emailBuscado) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		Boolean existe = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT email FROM usuarios WHERE email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, emailBuscado);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            } else {
                existe = false;
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
		return existe;
	}

}