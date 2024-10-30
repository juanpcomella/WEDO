import java.awt.FlowLayout;
import java.awt.*;

import javax.swing.*;

public class VentanaInicioSesion extends JFrame{
	
	public VentanaInicioSesion() {
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		add(panel);
		
		//Volver a la anterior ventana
		JPanel panelAtras = new JPanel(new BorderLayout());
		JButton atras = new JButton("<-");
		panelAtras.add(atras,BorderLayout.WEST);

		//Parte superior (logo)
		JPanel panelSuperior = new JPanel(new BorderLayout());
		JLabel logo = new JLabel("LOGO", SwingConstants.CENTER);
		panelSuperior.add(logo,BorderLayout.CENTER);
		panelSuperior.add(panelAtras,BorderLayout.NORTH);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
			//Usuario
		JPanel usuarioPanel = new JPanel(new FlowLayout());
				//Color transparente
		usuarioPanel.setBackground(new Color(0,0,0,0));
		JLabel usuario = new JLabel("Usuario: ");
		JTextField usuarioPersonal = new JTextField(20);
		usuarioPanel.add(usuario);
		usuarioPanel.add(usuarioPersonal);
		usuarioPanel.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(usuarioPanel);
		panelInferior.add(Box.createVerticalStrut(-60));
		
			//Contraseña
		JPanel contraseñaPanel = new JPanel(new FlowLayout());
		contraseñaPanel.setBackground(new Color(0,0,0,0));
		JLabel contraseña = new JLabel("Contraseña: ");
		JTextField contraseñaPersonal = new JTextField(18);
		contraseñaPanel.add(contraseña);
		contraseñaPanel.add(contraseñaPersonal);
		contraseñaPanel.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(contraseñaPanel);
		
			//¿Olvidado?
		JButton olvidado = new JButton("¿Has olvidado tu usuario o contraseña?");
		olvidado.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(olvidado);
		panelInferior.add(Box.createVerticalStrut(20));
		
			//Log In
		JButton login = new JButton("Entrar");
		login.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(login);
		
		
		panel.add(panelSuperior);
		panel.add(panelInferior);
	}
	
	

	public static void main(String[] args) {
		VentanaInicioSesion ventana = new VentanaInicioSesion();
		ventana.setVisible(true);
	}

}
