package StartingWindows;

import javax.swing.*;

import BaseDeDatos.BDs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VentanaRegistroDef extends JFrame {
	
    private static final long serialVersionUID = 1L;

	public VentanaRegistroDef() {
        setTitle("WEDO - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(700, 500);

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
		volverButton.setForeground(new Color(50, 70, 90));
        volverButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverButton.setContentAreaFilled(false);
        volverButton.setBorderPainted(false);
        volverButton.setFocusable(false);
        panelVolver.add(volverButton);
        panel.add(panelVolver, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridBagLayout());
        datos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

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
		introducirDatos.setForeground(new Color(50, 70, 90));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10);
        datos.add(introducirDatos, gbc);

        gbc.gridwidth = 1;

        JLabel username = new JLabel("Usuario");
        username.setFont(new Font("Tahoma", Font.BOLD, 20));
		username.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 0, 10);
        datos.add(username, gbc);

        JTextField usernameTF = new JTextField(20);
        usernameTF.setFont(new Font("Arial", Font.PLAIN, 19));
		usernameTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(usernameTF, gbc);

        JLabel email = new JLabel("Email");
        email.setFont(new Font("Tahoma", Font.BOLD, 20));
		email.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(email, gbc);

        JTextField emailTF = new JTextField(20);
        emailTF.setFont(new Font("Arial", Font.PLAIN, 19));
		emailTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(emailTF, gbc);

        JLabel password = new JLabel("Contraseña");
        password.setFont(new Font("Tahoma", Font.BOLD, 20));
		password.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(password, gbc);

        JPasswordField passwordTF = new JPasswordField(20);
        passwordTF.setFont(new Font("Arial", Font.PLAIN, 19));
		passwordTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordTF, gbc);

        JLabel passwordConf = new JLabel("Confirmar Contraseña");
        passwordConf.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordConf.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(passwordConf, gbc);

        JPasswordField passwordConfTF = new JPasswordField(20);
        passwordConfTF.setFont(new Font("Arial", Font.PLAIN, 19));
		passwordConfTF.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 10, 25, 10);
        datos.add(passwordConfTF, gbc);

        JButton registerButton = new JButton("Registrarse");
        //registerButton.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(50,70,90));
        registerButton.setFont(new Font("Tahoma", Font.BOLD, 30));
        registerButton.setOpaque(true);
        
        registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String passwordUser = new String(passwordTF.getPassword());
				String passwordConfUser = new String(passwordConfTF.getPassword());
				String emailUser = emailTF.getText();
				String usernameUser = usernameTF.getText();
				//rellenar todos los campos
				if (usernameUser.isEmpty() || emailUser.isEmpty() || passwordUser.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor rellena todos los datos.");
                    //confirmar que la contraseña y su confirmación sean iguales
                }else if (!passwordUser.equals(passwordConfUser)) {
                    JOptionPane.showMessageDialog(null,"Las contraseñas no son iguales.");
                }
				//comprobar que el nombre de usuario no existe
                else if(BDs.usuarioExistente(usernameUser)){
                	JOptionPane.showMessageDialog(null,"Nombre de usuario existente.");
                }else if(BDs.emailExistente(emailUser)){
                	JOptionPane.showMessageDialog(null,"Correo electrónico en uso.");
                }
                else {                	
                	BDs.crearTabla();
                	BDs.insertarElementos(usernameUser, passwordUser, emailUser);
//                	for (int i = 0; i < BDs.usuarioExistente().size(); i++) {
//    		            System.out.println(BDs.usuarioExistente().get(i));
//    				}
//                	for (int i = 0; i < BDs.contraseñaExistente().size(); i++) {
//    		            System.out.println(BDs.contraseñaExistente().get(i));
//    				}
//                	for (int i = 0; i < BDs.emailExistente().size(); i++) {
//    		            System.out.println(BDs.emailExistente().get(i));
//    				}
//    				System.out.println(BDs.usuarioExistente().size());
                    JOptionPane.showMessageDialog(null, "Cuenta Creada! \nBienvenido a WEDO!");
                    VentanaEmpezarCuestionario nuevaVentana = new VentanaEmpezarCuestionario(usernameUser);
    				nuevaVentana.setVisible(true);
    				dispose();

                }

			}       	
        });
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        datos.add(registerButton, gbc);

        // Lógica de Validación
        // Estos usuarios y contraseñas son temporales, luego se implementarán en la base de datos.
//        HashMap<String, String> users = new HashMap<>();
//        users.put("juanpcomella", "password");
//        users.put("adrianbaz", "12345");
//        users.put("anderorma", "contraseña");
//        users.put("ikergamboa", "98765");

 

        panel.add(datos, BorderLayout.CENTER);

    }
	
	public static void main(String[] args) {
//		// Carga el sqlite-JDBC driver usando el cargador de clases

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            VentanaRegistroDef window = new VentanaRegistroDef();
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setVisible(true);

        });

    }
}
