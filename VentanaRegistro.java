import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaRegistro extends JFrame{
	
	public VentanaRegistro() {
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
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
		panel.add(panelAtras,BorderLayout.NORTH);
		
		JPanel datos = new JPanel();
		datos.setLayout(new BoxLayout(datos,BoxLayout.Y_AXIS));
		
		//Elementos
		JLabel introducirDatos = new JLabel("Introduce tus datos",SwingConstants.CENTER);
		datos.add(Box.createVerticalStrut(10));
		datos.add(introducirDatos);
		datos.add(Box.createVerticalStrut(20));
		
		JPanel filaNombre = new JPanel(new FlowLayout());
		filaNombre.setBackground(new Color(0,0,0,0));
		JLabel nombre = new JLabel("Nombre: ");
		JTextField nombrePersonal = new JTextField(20);
		filaNombre.add(nombre);
		filaNombre.add(nombrePersonal);
		datos.add(filaNombre);
				
		JPanel filaUsuario = new JPanel(new FlowLayout());
		filaUsuario.setBackground(new Color(0,0,0,0));
		JLabel usuario = new JLabel("Usuario: ");
		JTextField usuarioPersonal = new JTextField(20);
		filaUsuario.add(usuario);
		filaUsuario.add(usuarioPersonal);
		datos.add(filaUsuario);

		JPanel filaCorreo = new JPanel(new FlowLayout());
		filaCorreo.setBackground(new Color(0,0,0,0));
		JLabel correo = new JLabel("Correo electrónico: ");
		JTextField correoPersonal = new JTextField(20);
		filaCorreo.add(correo);
		filaCorreo.add(correoPersonal);
		datos.add(filaCorreo);

		JPanel filaContraseña = new JPanel(new FlowLayout());
		filaContraseña.setBackground(new Color(0,0,0,0));
		JLabel contraseña = new JLabel("Contraseña: ");
		JTextField contraseñaPersonal = new JTextField(20);
		filaContraseña.add(contraseña);
		filaContraseña.add(contraseñaPersonal);
		datos.add(filaContraseña);

		JPanel filaConfirmarContraseña = new JPanel(new FlowLayout());
		filaConfirmarContraseña.setBackground(new Color(0,0,0,0));
		JLabel confirmarContraseña = new JLabel("Confirmar contraseña: ");
		JTextField confirmarContraseñaPersonal = new JTextField(20);
		filaConfirmarContraseña.add(confirmarContraseña);
		filaConfirmarContraseña.add(confirmarContraseñaPersonal);
		datos.add(filaConfirmarContraseña);
		datos.add(Box.createVerticalStrut(20));
		
		JButton crearCuenta = new JButton("Crear cuenta");
		datos.add(crearCuenta);
		datos.add(Box.createVerticalStrut(50));
		
		panel.add(datos,BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		
		VentanaRegistro ventana = new VentanaRegistro();
		ventana.setVisible(true);
	}

}
