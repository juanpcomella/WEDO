package MainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.*;

public class Notas extends JFrame {
	int numero_vistas;
    public Notas() {
        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout());

        // Título
        JLabel tituloL = new JLabel("Título");
        tituloL.setFont(new Font("Arial", Font.BOLD, 50));
        add(tituloL, BorderLayout.NORTH);

        // JTextPane para los apuntes
        JTextPane apuntePane = new JTextPane();
        apuntePane.setFont(new Font("Calibri", Font.PLAIN, 20));
        add(new JScrollPane(apuntePane), BorderLayout.CENTER); // Añadir con scroll
        apuntePane.setPreferredSize(new Dimension(400, 700));

        // Botón para subrayar
        JButton underlineButton = new JButton("Subrayar");
        underlineButton.addActionListener((ActionEvent e) -> {
            // Obtener texto seleccionado
            int start = apuntePane.getSelectionStart();
            int end = apuntePane.getSelectionEnd();

            if (start != end) { // Asegurarse de que haya texto seleccionado
                StyledDocument doc = apuntePane.getStyledDocument();
                Style style = apuntePane.addStyle("UnderlineStyle", null);

                // Aplicar subrayado
                StyleConstants.setUnderline(style, true);
                doc.setCharacterAttributes(start, end - start, style, false);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona texto para subrayar.", "Texto no seleccionado", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        //Colores principales: 
        Color colorTurquesa = new Color(173, 216, 230);
        Color azulOscuro = new Color(50, 70, 90);
        
        
        numero_vistas=0;
        // Boton para cambiar vista:
        JButton cambiarVista = new JButton("Cambiar vista");
        
        cambiarVista.addActionListener(e -> {
        	
        if (numero_vistas==0) {
        	apuntePane.setBackground(colorTurquesa);
        	apuntePane.setForeground(azulOscuro);
        	numero_vistas = numero_vistas+1;
        } else if (numero_vistas==1) {
        	apuntePane.setBackground(azulOscuro);
        	apuntePane.setForeground(colorTurquesa);
        	numero_vistas++;
        } else if (numero_vistas==2) {
        	apuntePane.setBackground(new Color(255, 192, 203));
        	apuntePane.setForeground(new Color(147, 112, 219));
        	numero_vistas++;
        } else if (numero_vistas == 3) {
            apuntePane.setBackground(new Color(152, 255, 152)); // Verde Menta
            apuntePane.setForeground(new Color(47, 79, 79)); // Gris Oscuro
            numero_vistas++;
        } else if (numero_vistas == 4) {
            apuntePane.setBackground(new Color(255, 255, 153)); // Amarillo Pastel
            apuntePane.setForeground(new Color(169, 169, 169)); // Gris Claro
            numero_vistas++;
        } else if (numero_vistas == 5) {
            apuntePane.setBackground(new Color(216, 191, 216)); // Lila Claro
            apuntePane.setForeground(new Color(128, 0, 128)); // Morado Oscuro
            numero_vistas++;
        } else if (numero_vistas == 6) {
            apuntePane.setBackground(new Color(255, 127, 80)); // Coral Claro
            apuntePane.setForeground(new Color(0, 0, 128)); // Azul Marino
            numero_vistas++;
        } else if (numero_vistas == 7) {
            apuntePane.setBackground(new Color(245, 245, 220)); // Beige Claro
            apuntePane.setForeground(new Color(139, 69, 19)); // Marrón
            numero_vistas++;
        } else if (numero_vistas == 8) {
            // Dark Mode: Fondo oscuro con texto claro
            apuntePane.setBackground(new Color(51, 51, 51)); // Gris muy oscuro / Negro
            apuntePane.setForeground(new Color(255, 255, 255)); // Gris Claro / Blanco
            apuntePane.setCaretColor(new Color(255, 255, 255)); // Blanco (cursor)
            numero_vistas++; // Reiniciar el contador para volver al inicio
        } else if (numero_vistas==9) {
        	apuntePane.setBackground(Color.WHITE);
        	apuntePane.setForeground(Color.BLACK);
            apuntePane.setCaretColor(Color.BLACK);
        	numero_vistas=0;
        }
        
        });

        // Panel inferior para el botón
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(underlineButton);
        panelInferior.add(cambiarVista);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Notas notas = new Notas();
        notas.setVisible(true);
    }
}

