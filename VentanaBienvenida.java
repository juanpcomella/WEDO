import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;


public class VentanaBienvenida extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public VentanaBienvenida() {
		setSize(700,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		panel.setBackground(new Color(173, 216, 230));
		//panel.setBackground(new Color(50, 60, 80)); OPCION MAS OSCURA
		//panel.setBackground(new Color(40, 60, 100)); OPCION MAS AZUL


		add(panel);
		
		//Fondo
		
		//Parte superior (logo y titulo)
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		panelSuperior.setOpaque(false);
		JLabel textoBienvenida = new JLabel("WeDo");
		Font fuente = new Font("Bauhaus 93", Font.BOLD, 30);
		textoBienvenida.setForeground(new Color(50, 70, 90));
		textoBienvenida.setFont(fuente);
		textoBienvenida.setAlignmentX(CENTER_ALIGNMENT);
		
			//Logo
		ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/LOGO WEDO 1.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		logo.setAlignmentX(CENTER_ALIGNMENT);
		
		panelSuperior.add(Box.createVerticalStrut(40));
		panelSuperior.add(textoBienvenida);
		panelSuperior.add(Box.createVerticalStrut(0));
		panelSuperior.add(logo);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		panelInferior.setOpaque(false);
		
			//Inicio sesión
		JButton botonInicioSesion = new JButton("Iniciar sesión");
		botonInicioSesion.setAlignmentX(CENTER_ALIGNMENT);
		botonInicioSesion.setForeground(new Color(255,255,255));
		botonInicioSesion.setBackground(new Color(50,70,90));
		botonInicioSesion.setBorderPainted(false);
		botonInicioSesion.setFocusPainted(false);
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
		pregunta.setForeground(new Color(50,70,90));
		
			//Registro
		JButton registro = new JButton("Registrarse");
		registro.setAlignmentX(CENTER_ALIGNMENT);
		registro.setForeground(new Color(255,255,255));
		registro.setBackground(new Color(50,70,90));
		registro.setBorderPainted(false);
		registro.setFocusPainted(false);
		registro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaRegistroDef nuevaVentana = new VentanaRegistroDef();
				nuevaVentana.setVisible(true);
				dispose();
			}			
		});
		
		panelInferior.add(Box.createVerticalStrut(20));
		panelInferior.add(botonInicioSesion);
		panelInferior.add(Box.createVerticalStrut(20));
		panelInferior.add(pregunta);
		panelInferior.add(Box.createVerticalStrut(5));
		panelInferior.add(registro);
		
		panel.add(panelSuperior);
		panel.add(panelInferior);
	}

	public VentanaBienvenida(String string) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		VentanaBienvenida ventana = new VentanaBienvenida();
		ventana.setVisible(true);

	}

}
