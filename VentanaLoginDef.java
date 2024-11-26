import MainWindow.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;

import BaseDeDatos.BDs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class VentanaLoginDef extends JFrame {

    private static final long serialVersionUID = 1L;

	public VentanaLoginDef() {
        setTitle("WEDO - Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				VentanaBienvenida nuevaVentana = new VentanaBienvenida();
				nuevaVentana.setVisible(true);
				dispose();		
			}
        	
        });
        
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
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/LOGO WEDO 1.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		logo.setAlignmentX(CENTER_ALIGNMENT);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 5, 10);
        datos.add(logo, gbc);

//        JLabel introducirDatos = new JLabel("Introduce tus datos", JLabel.CENTER);
//		introducirDatos.setForeground(new Color(50, 70, 90));
//        gbc.gridy = 1;
//        gbc.insets = new Insets(5, 10, 20, 10);
//        datos.add(introducirDatos, gbc);

        // Espacio entre texto e inputs, aquí va el logo.
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridheight = 1;
        datos.add(Box.createVerticalStrut(0), gbc);

        gbc.gridwidth = 1;

        JLabel username = new JLabel("Usuario o Email");
		username.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 0, 10);
        datos.add(username, gbc);

        JTextField usernameTF = new JTextField(20);
        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 1);
		usernameTF.setBorder(whiteBorder);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(usernameTF, gbc);

        JLabel password = new JLabel("Contraseña");
		password.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(password, gbc);

        JPasswordField passwordTF = new JPasswordField(20);
		passwordTF.setBorder(whiteBorder);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordTF, gbc);

        JCheckBox recuerdame = new JCheckBox("Recordar");
		recuerdame.setForeground(new Color(50, 70, 90));
		recuerdame.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(recuerdame, gbc);

        JButton olvidado = new JButton("¿Has olvidado tu usuario o contraseña?");
		olvidado.setForeground(new Color(50, 70, 90));
        olvidado.setContentAreaFilled(false);
        olvidado.setBorderPainted(false);
        olvidado.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(olvidado, gbc);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setForeground(new Color(255,255,255));
        loginButton.setBackground(new Color(50,70,90));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.setMaximumSize(new Dimension(150, 50));
        loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameUser = usernameTF.getText();
                String passwordUser = new String(passwordTF.getPassword());
                if(!BDs.usuarioExistente().contains(usernameUser)) {
                    	JOptionPane.showMessageDialog(null,"Nombre de usuario o contraseña incorrectos.");
                }else if(!BDs.contraseñaExistente().contains(passwordUser)) {
                	JOptionPane.showMessageDialog(null,"Nombre de usuario o ontraseña incorrectos.");
                }else {
                	JOptionPane.showMessageDialog(null,"Bienvenido "+usernameUser+"!");
                	MainWindow mw = new MainWindow();
                    mw.setVisible(true);
                    dispose();
                }
                //FALTA AÑADIR LA POSIBILIDAD DE INICIAR SESION CON EL EMAIL
			}
		});
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        datos.add(loginButton, gbc);

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
        VentanaLoginDef window = new VentanaLoginDef();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
