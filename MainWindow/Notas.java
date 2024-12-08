package MainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public class Notas extends JFrame {
	String titulo_editado;
	String txt_editado;
	int numero_vistas;
	
	JTextPane apuntePane;
	JTextField tituloL;
    public Notas(String titulo,String txt) {
        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout());
        
        
        
        // Título
        tituloL = new JTextField(titulo);
        tituloL.setFont(new Font("Arial", Font.BOLD, 50));
        
        
        add(tituloL, BorderLayout.NORTH);

        // JTextPane para los apuntes
        apuntePane = new JTextPane();
        apuntePane.setText(txt);
        apuntePane.setFont(new Font("Calibri", Font.PLAIN, 20));
        add(new JScrollPane(apuntePane), BorderLayout.CENTER); // Añadir con scroll
        apuntePane.setPreferredSize(new Dimension(400, 700));

        JButton limpiarB = new JButton("Limpiar");
        limpiarB.addActionListener(e -> {
        	apuntePane.setText(" ");
        	
        });
        
        
        // Botón para subrayar
        JButton underlineButton = new JButton("(Des)Subrayar");
        underlineButton.addActionListener((ActionEvent e) -> {
            // Obtener texto seleccionado
            int start = apuntePane.getSelectionStart();
            int end = apuntePane.getSelectionEnd();

            if (start != end) { // Ver si hay txt subrayado
                StyledDocument doc = apuntePane.getStyledDocument();
                Element element = doc.getCharacterElement(start);
                AttributeSet as = element.getAttributes();

                boolean isUnderlined = StyleConstants.isUnderline(as); // Ver si esta subrayado o no (true o false)

                Style style = apuntePane.addStyle("UnderlineStyle", null);
                StyleConstants.setUnderline(style, !isUnderlined); // Aplica el efecto contrario al que está actualmente

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
        
        ArrayList<String> fontList = new ArrayList<>();
        fontList.add("Arial");
        fontList.add("Calibri");
        fontList.add("Times New Roman");
        fontList.add("Verdana");
        fontList.add("Tahoma");
        fontList.add("Courier New");
        fontList.add("Georgia");

        // Crear JComboBox con las fuentes
        JComboBox<String> fontComboBox = new JComboBox<>(fontList.toArray(new String[0]));
        
        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFont = (String) fontComboBox.getSelectedItem();
                if (selectedFont != null) {
                    apuntePane.setFont(new Font(selectedFont, Font.PLAIN, 20));
                }
            }
        });

        // Panel inferior para el botón
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        underlineButton.setPreferredSize(new Dimension(130,25));
        panelInferior.add(underlineButton);
        panelInferior.add(cambiarVista);
        panelInferior.add(limpiarB);
        panelInferior.add(fontComboBox);
        add(panelInferior, BorderLayout.SOUTH);
        
        titulo_editado=tituloL.getText();
        txt_editado=apuntePane.getText();
        
        titulo= titulo_editado;
        txt=txt_editado;
        
        
    }

    //public static void main(String[] args) {
     //   Notas notas = new Notas("Titulo","");
      //  notas.setVisible(true);
   // }
}

