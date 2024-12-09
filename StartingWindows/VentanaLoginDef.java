package StartingWindows;

import MainWindow.Habito;
import MainWindow.MainWindow;

import javax.swing.*;

import BaseDeDatos.BDs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VentanaLoginDef extends JFrame {

    private static final long serialVersionUID = 1L;

	public VentanaLoginDef() {
        setTitle("WEDO - Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(173, 216, 230));
        add(panel);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.setOpaque(false);
        JButton volverButton = new JButton("<< Volver");
        volverButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
                ventanaBienvenida.setLocationRelativeTo(null);
				ventanaBienvenida.setVisible(true);
				dispose();
			}
        });
        volverButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		volverButton.setForeground(new Color(50, 70, 90));
        volverButton.setContentAreaFilled(false);
        volverButton.setBorderPainted(false);
        volverButton.setFocusable(false);
        panelVolver.add(volverButton);
        panel.add(panelVolver, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridBagLayout());
        datos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/LOGO WEDO 1.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		logo.setAlignmentX(CENTER_ALIGNMENT);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 5, 10);
        datos.add(logo, gbc);

        JLabel introducirDatos = new JLabel("Introduce tus datos", JLabel.CENTER);
        introducirDatos.setFont(new Font("Tahoma", Font.BOLD, 30));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10);
        datos.add(introducirDatos, gbc);

        gbc.gridwidth = 1;

        JLabel username = new JLabel("Usuario / Email");
		username.setForeground(new Color(50, 70, 90));
        username.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 0, 10);
        datos.add(username, gbc);

        JTextField usernameTF = new JTextField(20);
		usernameTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        usernameTF.setFont(new Font("Arial", Font.PLAIN, 19));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(usernameTF, gbc);

        JLabel password = new JLabel("Contraseña");
		password.setForeground(new Color(50, 70, 90));
        password.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(password, gbc);

        JPasswordField passwordTF = new JPasswordField(20);
		passwordTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        passwordTF.setFont(new Font("Arial", Font.PLAIN, 19));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordTF, gbc);

        JCheckBox recuerdame = new JCheckBox("Recuérdame");
		recuerdame.setForeground(new Color(50, 70, 90));
		recuerdame.setOpaque(false);
        recuerdame.setFont(new Font("Arial", Font.ITALIC, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 0, 10);
        datos.add(recuerdame, gbc);

        JButton olvidado = new JButton("¿Has olvidado tu usuario o contraseña?");
		olvidado.setForeground(new Color(50, 70, 90));
        olvidado.setFont(new Font("Arial", Font.ITALIC, 16));
        olvidado.setContentAreaFilled(false);
        olvidado.setBorderPainted(false);
        olvidado.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				olvidado.setFont(olvidado.getFont().deriveFont(
					Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				olvidado.setFont(new Font("Arial", Font.ITALIC, 16));
			}

        });
        olvidado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 // Crear un JTextField
				String usuarioParaContraseña = null;
                JTextField textField = new JTextField(15); // Ancho de 15 columnas
                JTextField textField2 = new JTextField(15); // Ancho de 15 columnas
                JTextField textField3 = new JTextField(15); // Ancho de 15 columnas


                // Mostrar el JOptionPane con el JTextField
                int option = JOptionPane.showOptionDialog(
                        panel,
                        new Object[]{"Introduce tu correo o usuario:", textField},
                        "",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new Object[]{"Aceptar"}, // Botón personalizado
                        "Aceptar"
                );
                if(option == 0) {
                	String correoRep = null;
                	String correoOuserDeRecuperacion = textField.getText();
                	if(BDs.usuarioExistente(correoOuserDeRecuperacion)) {
                		usuarioParaContraseña = correoOuserDeRecuperacion;
                		correoRep = BDs.getEmail(correoOuserDeRecuperacion); 
                		BDs.crearTablaCodigosDeVerificacionTemporales();
                		EnviarCorreoRecuperacion.enviarCorreo(correoRep);
                		boolean codigoCorrecto = false;
                        while (!codigoCorrecto) {
                            int option2 = JOptionPane.showOptionDialog(
                                    panel,
                                    new Object[]{"Introduce el código de verificación:", textField2},
                                    "",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    new Object[]{"Aceptar"}, // Botón personalizado
                                    "Aceptar"
                            );

                            if (option2 == 0) {
                                String codigoIngresado = textField2.getText();
                                if (codigoIngresado.equals(BDs.obtenerCodigoDeVerificacion(correoRep))) {
                                    codigoCorrecto = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Código de verificación incorrecto. Inténtalo de nuevo.");
                                }
                            } else {
                                return; // Cancelar
                            }
                        }
                        boolean contraseñaValida = false;
                        while (!contraseñaValida) {
                            int option3 = JOptionPane.showOptionDialog(
                                    panel,
                                    new Object[]{"Introduce tu nueva contraseña:", textField3},
                                    "",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    new Object[]{"Aceptar"}, // Botón personalizado
                                    "Aceptar"
                            );

                            if (option3 == 0) {
                                String nuevaContraseña = textField3.getText().trim();
                                if (!nuevaContraseña.isEmpty()) {
                                    BDs.updatePassword(usuarioParaContraseña, nuevaContraseña);
                                    JOptionPane.showMessageDialog(null, "¡Contraseña actualizada exitosamente!");
                                    contraseñaValida = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Introduce una contraseña válida.");
                                }
                            } else {
                                return; // Cancelar
                            }
                        }
                	}else if(BDs.emailExistente(correoOuserDeRecuperacion)) {
                		usuarioParaContraseña = BDs.getUsuarioMedianteCorreo(correoOuserDeRecuperacion);
                		correoRep = correoOuserDeRecuperacion;
                		BDs.crearTablaCodigosDeVerificacionTemporales();
                		EnviarCorreoRecuperacion.enviarCorreo(correoRep);
                		boolean codigoCorrecto = false;
                        while (!codigoCorrecto) {
                            int option2 = JOptionPane.showOptionDialog(
                                    panel,
                                    new Object[]{"Introduce el código de verificación:", textField2},
                                    "",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    new Object[]{"Aceptar"}, // Botón personalizado
                                    "Aceptar"
                            );

                            if (option2 == 0) {
                                String codigoIngresado = textField2.getText();
                                if (codigoIngresado.equals(BDs.obtenerCodigoDeVerificacion(correoRep))) {
                                    codigoCorrecto = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Código de verificación incorrecto. Inténtalo de nuevo.");
                                }
                            } else {
                                return; // Cancelar
                            }
                        }
                    	//HAY QUE HACER CAMBIOS
                        boolean contraseñaValida = false;
                        while (!contraseñaValida) {
                            int option3 = JOptionPane.showOptionDialog(
                                    panel,
                                    new Object[]{"Introduce tu nueva contraseña:", textField3},
                                    "",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    new Object[]{"Aceptar"}, // Botón personalizado
                                    "Aceptar"
                            );

                            if (option3 == 0) {
                                String nuevaContraseña = textField3.getText().trim();
                                if (!nuevaContraseña.isEmpty()) {
                                    BDs.updatePassword(usuarioParaContraseña, nuevaContraseña);
                                    JOptionPane.showMessageDialog(null, "¡Contraseña actualizada exitosamente!");
                                    contraseñaValida = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Introduce una contraseña válida.");
                                }
                            } else {
                                return; // Cancelar
                            }
                        }
                	}else {
                        JOptionPane.showMessageDialog(null,"Usuario o correo no encontrados.");
                	}
                	
                }
                
			}
        });
        

        gbc.gridy = 7;
        datos.add(olvidado, gbc);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(new Color(50,70,90));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 30));
        loginButton.setOpaque(true);
        loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameUser = usernameTF.getText();
                String passwordUser = new String(passwordTF.getPassword());
                if(!BDs.usuarioExistente(usernameUser) & !BDs.emailExistente(usernameUser)) {
                    	JOptionPane.showMessageDialog(null,"Nombre de usuario o contraseña incorrectos.");
                }else if(!BDs.contraseñaExistente(passwordUser)) {
                	JOptionPane.showMessageDialog(null,"Nombre de usuario o contraseña incorrectos.");
                }else {
                	String nombreUsuario = null;
                	if(usernameUser.contains("@") & usernameUser.contains(".")) {
                		nombreUsuario = BDs.pasarDeEmailAUsername(usernameUser);
                    	JOptionPane.showMessageDialog(null,"Bienvenido "+nombreUsuario+"!");
                	}else {
                    	JOptionPane.showMessageDialog(null,"Bienvenido "+usernameUser+"!");

                	}
                	Usuario usuario = new Usuario(usernameUser, usernameUser, nombreUsuario);
                	if(usernameUser.contains("@") & usernameUser.contains(".")) {
                		usuario.setNombreUsuario(BDs.pasarDeEmailAUsername(usernameUser));
                	}else {
                		usuario.setCorreo(BDs.getEmail(usernameUser));
                	}
                    usuario.setSaldo(BDs.getSaldo(usuario.getNombreUsuario()));
                  	MainWindow mw = new MainWindow(usuario);
                    mw.setVisible(true);
                    BDs.crearTablaSeguimientos();
                    dispose();
                }
			}
		});

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        datos.add(loginButton, gbc);

        panel.add(datos, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            VentanaLoginDef window = new VentanaLoginDef();
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
        

}
}
