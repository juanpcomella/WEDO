package MainWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class LeftSideBar extends JPanel {
    public LeftSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(173, 216, 230));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(50, 70, 90));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBorder(new LineBorder(new Color(173, 216, 230), 10));
        
        JButton button1 = new JButton("+");
        button1.setBackground(new Color(173, 216, 230));
        button1.setForeground(new Color(50, 70, 90));
        button1.setPreferredSize(new Dimension (100,30));
        panel.add(button1); 
        
        Color colorTurquesa = new Color(173, 216, 230);
        Color azulOscuro = new Color(50, 70, 90);
        

        button1.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(null, "Escribe el título de la página:", "Crear Página", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                JButton botonPagina = new JButton(input);
                
                // Diseño del boton
                botonPagina.setPreferredSize(new Dimension (80,30));
                botonPagina.setBackground(azulOscuro);
                botonPagina.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                botonPagina.setBorder(new LineBorder(new Color(173, 216, 230), 5));
                botonPagina.setForeground(colorTurquesa);
                
                // Cuando se crea el boton se añade al panel
                panel.add(botonPagina); 
                panel.revalidate();     
                panel.repaint();
            }
        });

        add(panel); // Añadir el panel a la barra lateral
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Left Sidebar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new LeftSideBar());
        frame.setVisible(true);
    }
}

