package BaseDeDatos;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import StartingWindows.Usuario;
import org.w3c.dom.Text;

import MainWindow.Categorias;
import MainWindow.Evento;
import MainWindow.Habito;
import MainWindow.Objetivo;

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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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

	public static String getUsername(String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		String nombreUsuario = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg

			String sql = "SELECT username FROM usuarios WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, usuario);
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

	public static String getUsuarioMedianteCorreo(String correo) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		String correoE = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg

			String sql = "SELECT username FROM usuarios WHERE email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, correo);
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
	
	public static boolean updatePassword(String usuario, String nuevaContraseña) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		boolean actualizado = false;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			
	        String sql = "UPDATE usuarios SET password = ? WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, nuevaContraseña);
	        preparedStatement.setString(2, usuario);
	        
	        int filasActualizadas = preparedStatement.executeUpdate(); // Ejecutar la actualización

	        actualizado = filasActualizadas > 0;

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
		System.out.println(getPassword(usuario));
		return actualizado;
		
	
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
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
	
	public static void eliminarEventos(String usuario, String nombre, String descripcion, String categoria, String fecha, String horaInicio, String horaFin, boolean todoElDia) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "delete from eventos where username = ? and nombreEvento = ? and descripcionEv = ? and categoriaEv = ? and fechaEv = ? and horaInicioEv = ? and horaFinEv = ? and todoElDiaEv = ?";

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

	public static Usuario obtenerUsuario(String username) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return null;
		}
		Connection connection = null;
		Usuario usuario = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
			String sql = "SELECT username, email, password FROM usuarios WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				usuario = new Usuario(
						resultSet.getString("username"),
						resultSet.getString("email"),
						resultSet.getString("password")
				);
			} else {
				System.err.println("No se encontraron resultados para el usuario: " + username);
			}

		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return usuario;
	}

	public static boolean setUsuario(Usuario usuario) {
		try {
			// Cargar el driver JDBC
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return false;
		}

		Connection connection = null;
		try {
			// Conectar a la base de datos
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");

			// Comando SQL para insertar o actualizar un usuario
			String sql = "INSERT INTO usuarios (username, email, password) " +
					"VALUES (?, ?, ?) " +
					"ON CONFLICT(username) DO UPDATE SET email = excluded.email, password = excluded.password";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getNombreUsuario());
			preparedStatement.setString(2, usuario.getCorreo());
			preparedStatement.setString(3, usuario.getContraseña());

			// Ejecutar la consulta
			int rowsAffected = preparedStatement.executeUpdate();

			// Verificar si se realizó la operación
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			return false;
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}


	//aqui empiezo a crear metodos para trabajar con la recuperacion de la contraseña (una tabla donde se guarde el codigo de recuperacion...)
	public static void crearTablaCodigosDeVerificacionTemporales() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			// Ejecutar sentencias SQL (Delete)
//			statement.executeUpdate("drop table if exists person");
			
			// Ejecutar sentencias SQL (Update)
			statement.executeUpdate("create table if not exists codigos (email string, codigo string, fecha_expiracion long)");

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
	
	public static void insertarCodigosDeVerificacion(String correo, String codigo, Long fecha_expiracion) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "insert into codigos (email, codigo, fecha_expiracion) VALUES (?, ?, ?)";

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Asignar los valores de las variables a los parámetros
            preparedStatement.setString(1, correo);     
            preparedStatement.setString(2, codigo);  
            preparedStatement.setLong(3, fecha_expiracion);  
  
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
	public static void eliminarPorEmail(String correo) {
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
	        return;
	    }
	    Connection connection = null;
	    try {
	        // Crear una conexión de BD
	        connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
	        
	        // Instrucción SQL para eliminar
	        String sql = "DELETE FROM codigos WHERE email = ?";
	        
	        // Preparar la consulta
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        // Asignar el valor del correo al parámetro
	        preparedStatement.setString(1, correo);
	        
	        // Ejecutar la consulta
	        preparedStatement.executeUpdate();
//	        System.out.println("Número de filas eliminadas: " + filasAfectadas);
	        
	    } catch (SQLException e) {
	        System.err.println("Error en la operación DELETE: " + e.getMessage());
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            // Cierre de conexión fallido
	            System.err.println("Error cerrando la conexión: " + e.getMessage());
	        }
	    }
	}

	public static String obtenerCodigoDeVerificacion(String correo) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return null;
		}
		Connection connection = null;
		String codigo = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
			String sql = "SELECT codigo FROM codigos WHERE email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, correo);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				codigo = resultSet.getString("codigo");
			} 

		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return codigo;
	}
	
	public static Long obtenerFechaExpiracion(String correo) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return null;
		}
		Connection connection = null;
		Long fecha = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
			String sql = "SELECT fecha_expiracion FROM codigos WHERE email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, correo);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				fecha = resultSet.getLong("fecha_expiracion");
			} 

		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return fecha;
	}

	public static void crearTablaSeguimientos() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}

		Connection connection = null;
		try {
			// Conexión a la base de datos
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // Timeout de 30 ms

			// Crear la tabla con tipos de datos correctos
			String sql = "CREATE TABLE IF NOT EXISTS seguimientos (" +
					"seguidor TEXT NOT NULL, " +
					"seguido TEXT NOT NULL, " +
					"PRIMARY KEY (seguidor, seguido))";
			statement.executeUpdate(sql);

			System.out.println("Tabla 'seguimientos' creada o ya existente.");
		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	public static void insertarElementosSeguimientos(Usuario seguidor, Usuario seguido) {
		try {
			// Cargar el driver JDBC
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}

		Connection connection = null;
		try {
			// Conectar a la base de datos
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");

			// Consulta SQL para insertar relación de seguimiento
			String sql = "INSERT INTO seguimientos (seguidor, seguido) VALUES (?, ?)";

			// Preparar la consulta
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			// Asignar valores a los parámetros
			preparedStatement.setString(1, seguidor.getNombreUsuario());
			preparedStatement.setString(2, seguido.getNombreUsuario());

			// Ejecutar la consulta
			preparedStatement.executeUpdate();

			System.out.println("Relación de seguimiento insertada con éxito.");
		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	public static boolean yaSigue(Usuario seguidor, Usuario seguido) {
		boolean sigue = false;
		try {
			// Cargar el driver JDBC
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return false;
		}

		Connection connection = null;
		try {
			// Conectar a la base de datos
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");

			// Comando SQL para insertar o actualizar un usuario
			String sql = "SELECT 1 FROM seguimientos WHERE seguidor = ? AND seguido = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, seguidor.getNombreUsuario());
			preparedStatement.setString(2, seguido.getNombreUsuario());

			ResultSet resultSet = preparedStatement.executeQuery();

			// Si hay resultados, significa que ya lo sigue
			if (resultSet.next()) {
				sigue = true;
			}
		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}

		return sigue;
	}
	//AQUI EMPIEZAN LOS METODOS DE LOS HABITOS
	public static void crearTablaHabitosTemporales() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			// Ejecutar sentencias SQL (Delete)
//			statement.executeUpdate("drop table if exists person");
			
			// Ejecutar sentencias SQL (Update)
			statement.executeUpdate("create table if not exists habitos (username string, fecha_hoy string, habito string)");

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
	
	public static void insertarHabitosTemporales(String usuario, String fecha, String habito) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "insert into habitos (username, fecha_hoy, habito) VALUES (?, ?, ?)";

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Asignar los valores de las variables a los parámetros
            preparedStatement.setString(1, usuario);     
            preparedStatement.setString(2, fecha); 
            preparedStatement.setString(3, habito);  
            
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
	
	public static ArrayList<Habito> crearListaHabitos(String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		ArrayList<Habito> habitos = new ArrayList<>();
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT fecha_hoy, habito FROM habitos WHERE username = ? and fecha_hoy = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
        		Habito habito = new Habito(null, null, false);
        		habito.setFecha(resultSet.getString("fecha_hoy"));
        		habito.setNombre(resultSet.getString("habito"));     
                habitos.add(habito);
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
		return habitos;
	}
	
	public static void eliminarTodosLosHabitos() {
	    try {
	        // Cargar el driver de SQLite
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
	        return;
	    }

	    Connection connection = null;
	    try {
	        // Crear una conexión a la base de datos
	        connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");

	        // Crear gestor de sentencias
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  // Poner un timeout de 30 segundos

	        // Consulta SQL para eliminar todas las tuplas de la tabla "habitos"
	        String sql = "DELETE FROM habitos";

	        // Ejecutar la consulta
	        statement.executeUpdate(sql);

	    } catch (SQLException e) {
	        System.err.println("Error al eliminar hábitos: " + e.getMessage());
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();  // Cerrar la conexión
	            }
	        } catch (SQLException e) {
	            System.err.println("Error al cerrar la conexión: " + e.getMessage());
	        }
	    }
	}
	
	//AQUI EMPIEZAN LOS METODOS DE LOS OBJETIVOS
	public static void crearTablaObjetivos() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			// Ejecutar sentencias SQL (Delete)
//			statement.executeUpdate("drop table if exists person");
			
			// Ejecutar sentencias SQL (Update)
			statement.executeUpdate("create table if not exists objetivos (username string, nombre_obj string, descripcion_obj string, fecha_obj string, completado_obj boolean)");

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
	
	public static void insertarObjetivos(String nombreUsuario, String nombreObj, String descripcionObj, String fechaObj, boolean completadoObj) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
			return;
		}
		Connection connection = null;
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "insert into objetivos (username, nombre_obj, descripcion_obj, fecha_obj, completado_obj) VALUES (?, ?, ?, ?, ?)";

            // Preparar la consulta
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Asignar los valores de las variables a los parámetros
            preparedStatement.setString(1, nombreUsuario);     
            preparedStatement.setString(2, nombreObj);
            preparedStatement.setString(3, descripcionObj);  
            preparedStatement.setString(4, fechaObj);
            preparedStatement.setBoolean(5, completadoObj);  
            
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
	
	public static ArrayList<Objetivo> crearListaObjetivos(String nombreUsuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
		}
		Connection connection = null;
		ArrayList<Objetivo> objetivos = new ArrayList<>();
		try {
			// Crear una conexión de BD
			connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");//a partir de los ultimo : es donde quieres que se guarden
			// Crear gestores de sentencias
			Statement statement = connection.createStatement();//crear consultas
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			
			String sql = "SELECT nombre_obj, descripcion_obj, fecha_obj, completado_obj FROM objetivos WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, nombreUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
        		Objetivo objetivo = new Objetivo(null, null, null, false);
        		objetivo.setNombre(resultSet.getString("nombre_obj"));
        		objetivo.setDescripcion(resultSet.getString("descripcion_obj"));
        		objetivo.setFechaFin(LocalDate.parse((resultSet.getString("fecha_obj"))));
        		objetivo.setCompletado(resultSet.getBoolean("completado_obj"));

                objetivos.add(objetivo);
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
		return objetivos;
	}
	
	public static void eliminarObjetivos(String usuario, String nombreObjetivo) {
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.err.println("ERROR: Driver sqlite para JDBC no encontrado");
	        return;
	    }
	    Connection connection = null;
	    try {
	        // Crear una conexión de BD
	        connection = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/usuarioEventosYDemas");
	        
	        // Instrucción SQL para eliminar
	        String sql = "DELETE FROM objetivos WHERE username = ? and nombre_obj = ?";
	        
	        // Preparar la consulta
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        // Asignar el valor del correo al parámetro
	        preparedStatement.setString(1, usuario);
	        preparedStatement.setString(2, nombreObjetivo);
	        
	        // Ejecutar la consulta
	        preparedStatement.executeUpdate();
//	        System.out.println("Número de filas eliminadas: " + filasAfectadas);
	        
	    } catch (SQLException e) {
	        System.err.println("Error en la operación DELETE: " + e.getMessage());
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            // Cierre de conexión fallido
	            System.err.println("Error cerrando la conexión: " + e.getMessage());
	        }
	    }
	}

//	public static void main(String[] args) {
//		crearTablaCodigosDeVerificacionTemporales();
//	}

}

