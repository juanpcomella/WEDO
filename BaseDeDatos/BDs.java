package BaseDeDatos;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import StartingWindows.Usuario;
import org.w3c.dom.Text;

import MainWindow.Categorias;
import MainWindow.Evento;

public class BDs {
	public static void crearTablaUsuarios() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
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
	public static String pasarDeEmailAUsername(String correo) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		String nombreUsuario = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT username FROM usuarios WHERE email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, correo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nombreUsuario = resultSet.getString("username");
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
		return nombreUsuario;
	}

//	public static String getUsername(String usuario) {
//		try {
//		} catch (ClassNotFoundException e) {
//			Class.forName("org.sqlite.JDBC");
//			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
//		}
//		Connection connection = null;
//		String nombreUsuario = null;
//		try {
//			// Crear una conexión de BD
//			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuario2");//a partir de los ultimo : es donde quieres que se guarden
//			// Crear gestores de sentencias
//			Statement statement = connection.createStatement();//crear consultas
//			statement.setQueryTimeout(30);  // poner timeout 30 msg
//
//			String sql = "SELECT username FROM usuarios WHERE username = ?";
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//	        preparedStatement.setString(1, usuario);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                nombreUsuario = resultSet.getString("username");
//            }
//		} catch(SQLException e) {
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if(connection != null)
//					connection.close();
//			} catch(SQLException e) {
//				// Cierre de conexión fallido
//				System.err.println(e);
//			}
//	}
//		return nombreUsuario;
//	}
	public static String getPassword(String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		String contraseña = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT password FROM usuarios WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                contraseña = resultSet.getString("password");
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
		return contraseña;
		
	
	}
	public static String getEmail(String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		String correoE = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT email FROM usuarios WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                correoE = resultSet.getString("email");
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
		return correoE;
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//empezamos a trabajar con los eventos para cada usuario
	public static void crearTablaEventos() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			// Ejecutar sentencias SQL (Delete)
//			statement.executeUpdate("drop table if exists person");
			
			// Ejecutar sentencias SQL (Update)
			statement.executeUpdate("create table if not exists eventos (username string, nombreEvento string, descripcionEv string, categoriaEv string, fechaEv string, "
					+ "horaInicioEv string, horaFinEv string, todoElDiaEv boolean)");

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
	
	public static void insertarEventos(String usuario, String nombre, String descripcion, String categoria, String fecha, String horaInicio, String horaFin, boolean todoElDia) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "insert into eventos (username, nombreEvento, descripcionEv, categoriaEv, fechaEv, horaInicioEv, horaFinEv, todoElDiaEv) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Asignar los valores de las variables a los parámetros
            preparedStatement.setString(1, usuario);     
            preparedStatement.setString(2, nombre);  
            preparedStatement.setString(3, descripcion);
            preparedStatement.setString(4, categoria);
            preparedStatement.setString(5, fecha);
            preparedStatement.setString(6, horaInicio);
            preparedStatement.setString(7, horaFin);
            preparedStatement.setBoolean(8, todoElDia);
          
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
	
	public static ArrayList<Evento> crearListaEventosPorUsuario(String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		ArrayList<Evento> eventos = new ArrayList<>();
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuariosYeventos");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT nombreEvento, descripcionEv, categoriaEv, fechaEv, horaInicioEv, horaFinEv, todoElDiaEv  FROM eventos WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
        		Evento evento = new Evento(usuario, usuario, null, null, null, null, false);
                evento.setNombre(resultSet.getString("nombreEvento"));
                evento.setDescripcion(resultSet.getString("descripcionEv"));
                evento.setCategoria(Categorias.valueOf(resultSet.getString("categoriaEv")));
                evento.setFecha(LocalDate.parse(resultSet.getString("fechaEv")));
                evento.setHoraInicio(LocalTime.parse(resultSet.getString("horaInicioEv")));
                evento.setHoraFin(LocalTime.parse(resultSet.getString("horaFinEv")));
                evento.setTodoElDia(Boolean.parseBoolean(resultSet.getString("todoElDiaEv")));
                eventos.add(evento);

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
		return eventos;
	}
/*
	public Usuario obtenerUsuario(String username){
		Usuario usuario = null;
		try{

		}
	}

 */
}

