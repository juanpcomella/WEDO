import java.awt.*;
import javax.swing.*;


public class VentanaBienvenida extends JFrame {
	
	public VentanaBienvenida() {
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		add(panel);
		
		//Parte superior (logo y titulo)
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		JLabel textoBienvenida = new JLabel("BIENVENIDO A WEDO");
		textoBienvenida.setAlignmentX(CENTER_ALIGNMENT);
		JLabel logo = new JLabel("LOGO");
		logo.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(textoBienvenida);
		panelSuperior.add(logo);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		JButton botonInicioSesion = new JButton("Iniciar sesión");
		botonInicioSesion.setAlignmentX(CENTER_ALIGNMENT);
		JLabel pregunta = new JLabel("¿Aún no tienes cuenta?");
		pregunta.setAlignmentX(CENTER_ALIGNMENT);
		JButton registro = new JButton("Registrarse");
		registro.setAlignmentX(CENTER_ALIGNMENT);
		panelInferior.add(botonInicioSesion);
		panelInferior.add(Box.createVerticalStrut(20));
		panelInferior.add(pregunta);
		panelInferior.add(Box.createVerticalStrut(5));
		panelInferior.add(registro);
		
		panel.add(panelSuperior);
		panel.add(panelInferior);
	}

	public static void main(String[] args) {
		
		VentanaBienvenida ventana = new VentanaBienvenida();
		ventana.setVisible(true);

	}

}
