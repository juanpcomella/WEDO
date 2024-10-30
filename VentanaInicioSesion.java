import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaBienvenida nuevaVentana = new VentanaBienvenida();
				nuevaVentana.setVisible(true);
				dispose();
			}			
		});
		panelAtras.add(atras,BorderLayout.WEST);

		//Parte superior (logo)
		JPanel panelSuperior = new JPanel(new BorderLayout());
		ImageIcon imagen = new ImageIcon("C:\\Users\\iker.gamboa\\OneDrive - Universidad de Deusto\\Escritorio\\PROYECTO PROGRAMACIÓN RECURSOS\\LOGO WEDO 1.png");
        Image imagenEscalada = imagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		panelSuperior.add(logo,BorderLayout.CENTER);
		panelSuperior.add(panelAtras,BorderLayout.NORTH);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
			//Usuario
		JPanel usuarioPanel = new JPanel(new FlowLayout());
		usuarioPanel.setBackground(new Color(0,0,0,0));//color transparente (el panel)
		JLabel usuario = new JLabel("Usuario: ");
		JTextField usuarioPersonal = new JTextField(20);
		usuarioPanel.add(usuario);
		usuarioPanel.add(usuarioPersonal);
		usuarioPanel.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(usuarioPanel);
		
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
		panelInferior.add(Box.createVerticalStrut(30));
		
			//Log In
		JButton login = new JButton("Entrar");
		login.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(login);
		panelInferior.add(Box.createVerticalStrut(50));
		
		
		panel.add(panelSuperior);
		panel.add(panelInferior);
	}
	
	

	public static void main(String[] args) {
		VentanaInicioSesion ventana = new VentanaInicioSesion();
		ventana.setVisible(true);
	}

}
