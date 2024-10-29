import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ventanaEmpezarCuestionario extends JFrame {
    public ventanaEmpezarCuestionario() {
        setTitle("WEDO");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal con GridBagLayout para centrar
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Texto de descripción en la parte superior
        JLabel empezarL = new JLabel("Para personalizar la aplicación acorde a ti, te recomendamos hacer el cuestionario inicial");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(0, 0, 20, 0); // Espacio debajo del texto
        panel.add(empezarL, gbc);
        

        // Botón "Empezar Cuestionario" centrado
        JButton botonEmpezar = new JButton("EMPEZAR CUESTIONARIO");
        botonEmpezar.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1; // Colocación en la segunda fila
        gbc.insets = new java.awt.Insets(0, 0, 20, 0); // Espacio debajo del botón
        panel.add(botonEmpezar, gbc);

        // Botón "Omitir" debajo del botón "Empezar Cuestionario"
        JButton botonOmitir = new JButton("Omitir");
        gbc.gridy = 2; // Colocación en la tercera fila
        panel.add(botonOmitir, gbc);
        
        //Botones izquierda y derecha para la parte Izquierda del panel General
        JButton izquierdaB = new JButton ("<--");
		JButton derechaB = new JButton("-->");
		
		//creacion de un borderLayout, y, dentro del mismo un gridLayout
		JPanel panelIzq = new JPanel(new BorderLayout());
		JPanel flechasP = new JPanel(new GridLayout(1,2));
		
		//añadimos las flechas al panel flechasP
		flechasP.add(izquierdaB);
		flechasP.add(derechaB);
		panelIzq.add(flechasP, BorderLayout.SOUTH);
		

		
        
        JPanel panelGeneral = new JPanel(new BorderLayout());
        panelGeneral.add(panel, BorderLayout.CENTER);
        
		//añadimos el panel izquierdo a la parte izquierda del panelGeneral
        panelGeneral.add(panelIzq,BorderLayout.WEST);
        
        
        add(panelGeneral); // Añadir panel a la ventana


        // Mostrar ventana
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new ventanaEmpezarCuestionario();
    }
}
