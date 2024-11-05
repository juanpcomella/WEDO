

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class VentanaEmpezarCuestionario extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaEmpezarCuestionario() {
        setTitle("WEDO");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal con GridBagLayout para centrar
        JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Texto de Bienvenido a WEDO
        JLabel bienvenidoL = new JLabel(" Bienvenido (usuario)!");
        bienvenidoL.setFont(new Font("Bauhaus 93", Font.PLAIN, 35));
		bienvenidoL.setForeground(new Color(50, 70, 90));
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(0, 0, 20, 0); // Espacio debajo del Label
        panel.add(bienvenidoL,gbc);

        
        // Texto de descripción en la parte superior
        JLabel empezarL = new JLabel("Para disfrutar de una experiencia más personalizada, se recomienda completar el siguiente formulario.");
		empezarL.setForeground(new Color(50, 70, 90));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER; //ajustamos el texto para que esté en el centro
        gbc.insets = new java.awt.Insets(0, 0, 20, 0); // Espacio debajo del texto
        panel.add(empezarL, gbc);
        

        // Botón "Empezar Cuestionario" centrado
        JButton botonEmpezar = new JButton("Empezar cuestionario");
        botonEmpezar.setForeground(new Color(255,255,255));
        botonEmpezar.setBackground(new Color(50,70,90));
        botonEmpezar.setBorderPainted(false);
        botonEmpezar.setFocusPainted(false);
        botonEmpezar.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 2; // Colocación en la segunda fila
        gbc.insets = new java.awt.Insets(0, 0, 20, 0); // Espacio debajo del botón
        panel.add(botonEmpezar, gbc);

        // Botón "Omitir" debajo del botón "Empezar Cuestionario"
        JButton botonOmitir = new JButton("Omitir");
        botonOmitir.setForeground(new Color(255,255,255));
        botonOmitir.setBackground(new Color(50,70,90));
        botonOmitir.setBorderPainted(false);
        botonOmitir.setFocusPainted(false);
        gbc.gridy = 3; // Colocación en la tercera fila
        panel.add(botonOmitir, gbc);
        
        //Botones izquierda y derecha para la parte Izquierda del panel General
//        JButton izquierdaB = new JButton ("<--");
//		JButton derechaB = new JButton("-->");
		
		//creacion de un borderLayout, y, dentro del mismo un gridLayout
//		JPanel panelIzq = new JPanel(new BorderLayout());
//		JPanel flechasP = new JPanel(new GridLayout(1,2));
		
		//añadimos las flechas al panel flechasP
//		flechasP.add(izquierdaB);
//		panelIzq.add(flechasP, BorderLayout.SOUTH);
		
		//Botones izquierda y derecha para la parte derecha del panel General
//        JButton izquierdaA = new JButton ("<--");
//		JButton derechaA = new JButton("-->");
		
		//creacion de un borderLayout, y, dentro del mismo un gridLayout
//		JPanel panelDch = new JPanel(new BorderLayout());
//		JPanel flechasF = new JPanel(new GridLayout(1,2));
		
		//añadimos las flechas al panel flechasP
//		flechasF.add(derechaA);
//		panelDch.add(flechasF, BorderLayout.SOUTH);
		
		
        //creamos un panelGeneral (un BorderLayout), para añadir el panel dentro y que quede todo bien centralizado
        JPanel panelGeneral = new JPanel(new BorderLayout());
        panelGeneral.add(panel, BorderLayout.CENTER);
        
		//añadimos el panel izquierdo a la parte izquierda del panelGeneral
//        panelGeneral.add(panelIzq,BorderLayout.WEST);
        
		//añadimos el panel derecho a la parte izquierda del panelGeneral
//        panelGeneral.add(panelDch, BorderLayout.EAST);
        
        //estos dos paneles son por si queremos poner abajo unas pequeñas imagenes de presentacion de la app y que el 
        //usuario pueda ir pasandolas a traves de las flechas que hay en dichos paneles
                
        
        add(panelGeneral); // Añadir panel a la ventana


        // Mostrar ventana
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new VentanaEmpezarCuestionario();
    }
}

