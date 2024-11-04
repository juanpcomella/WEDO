import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class VentanaBienvenida extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		
			//Logo
		ImageIcon imagen = new ImageIcon("C:\\Users\\iker.gamboa\\OneDrive - Universidad de Deusto\\Escritorio\\PROYECTO PROGRAMACIÓN RECURSOS\\LOGO WEDO 1.png");
        Image imagenEscalada = imagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		logo.setAlignmentX(CENTER_ALIGNMENT);
		
		panelSuperior.add(Box.createVerticalStrut(20));
		panelSuperior.add(textoBienvenida);
		panelSuperior.add(Box.createVerticalStrut(20));
		panelSuperior.add(logo);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
			//Inicio sesión
		JButton botonInicioSesion = new JButton("Iniciar sesión");
		botonInicioSesion.setAlignmentX(CENTER_ALIGNMENT);
		botonInicioSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaLoginDef nuevaVentana = new VentanaLoginDef();
				nuevaVentana.setVisible(true);
				dispose();
			}			
		});
			
			//¿No tienes cuenta?
		JLabel pregunta = new JLabel("¿Aún no tienes cuenta?");
		pregunta.setAlignmentX(CENTER_ALIGNMENT);
		
			//Registro
		JButton registro = new JButton("Registrarse");
		registro.setAlignmentX(CENTER_ALIGNMENT);
		registro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaRegistroDef nuevaVentana = new VentanaRegistroDef();
				nuevaVentana.setVisible(true);
				dispose();
			}			
		});
		
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
