package StartingWindows;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
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
		Font fuente = new Font("Bauhaus 93", Font.BOLD, 80);
		textoBienvenida.setForeground(new Color(50, 70, 90));
		textoBienvenida.setFont(fuente);
		textoBienvenida.setAlignmentX(CENTER_ALIGNMENT);
		
			//Logo
		ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/LOGO WEDO 1.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);
		logo.setAlignmentX(CENTER_ALIGNMENT);
		
		panelSuperior.add(Box.createVerticalStrut(60));
		panelSuperior.add(textoBienvenida);
		panelSuperior.add(Box.createVerticalStrut(0));
		panelSuperior.add(logo);
		
		//Parte inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		panelInferior.setOpaque(false);
		
		//Inicio sesión
		JButton botonInicioSesion = new JButton("Iniciar sesión");
		botonInicioSesion.setFocusPainted(false);
		botonInicioSesion.setAlignmentX(CENTER_ALIGNMENT);
		botonInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 30));
		botonInicioSesion.setForeground(Color.WHITE);
		botonInicioSesion.setBackground(new Color(50,70,90));
		botonInicioSesion.setOpaque(true);
		botonInicioSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaLoginDef ventanaLoginDef = new VentanaLoginDef();
//				ventanaLoginDef.setLocationRelativeTo(null);
				ventanaLoginDef.setVisible(true);
				dispose();
			}			
		});
			
			//¿No tienes cuenta?
		JLabel pregunta = new JLabel("¿Aún no tienes cuenta?");
		pregunta.setAlignmentX(CENTER_ALIGNMENT);
		pregunta.setForeground(new Color(50,70,90));
		pregunta.setFont(new Font("Tahoma", Font.BOLD, 18));
		
			//Registro
		JButton registro = new JButton("Registrarse");
		registro.setAlignmentX(CENTER_ALIGNMENT);
		registro.setFont(new Font("Tahoma", Font.BOLD, 30));
		registro.setBackground(new Color(50,70,90));
		registro.setForeground(Color.WHITE);
		registro.setOpaque(true);
		registro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				VentanaRegistroDef ventanaRegistroDef = new VentanaRegistroDef();
				ventanaRegistroDef.setLocationRelativeTo(null);
				ventanaRegistroDef.setVisible(true);
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

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
			ventanaBienvenida.setVisible(true);
			ventanaBienvenida.setLocationRelativeTo(null);
			ventanaBienvenida.setExtendedState(JFrame.MAXIMIZED_BOTH);

		});

	}

}
