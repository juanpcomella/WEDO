package MainWindow;

import BaseDeDatos.BDs;
import ProfileWindow.ProfileWindowOther;
import ProfileWindow.ProfileWindowSelf;
import StartingWindows.Usuario;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;


public class LeftSideBar extends JPanel {
    public LeftSideBar(Usuario usuario) {
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
        
        add(new JScrollPane(panel)); // Añadir el panel a la barra lateral

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setBackground(new Color(50, 70, 90));
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setBorder(new LineBorder(new Color(173, 216, 230), 5));

        String usuarioActual = usuario.getNombreUsuario();
        cargarSeguimientosEnPanel(usuarioActual, panel2);

        add(new JScrollPane(panel2));
    }

    public void cargarSeguimientosEnPanel(String usuarioActual, JPanel panel2) {
        panel2.removeAll();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        Usuario user = BDs.obtenerUsuario(usuarioActual);

        // Añadir un título "Siguiendo"
        JLabel titulo = new JLabel("Siguiendo", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.add(Box.createVerticalStrut(10));
        panel2.add(titulo);
        panel2.add(Box.createVerticalStrut(20));

        ArrayList<Usuario> listaSeguimientos = BDs.crearListaSeguimientosPorUsuario(usuarioActual);

        for (Usuario seguido : listaSeguimientos) {
            JButton botonSeguido = new JButton(seguido.getNombreUsuario());
            botonSeguido.setFont(new Font("Arial", Font.PLAIN, 18));
            botonSeguido.setPreferredSize(new Dimension(175, 40));
            botonSeguido.setMaximumSize(new Dimension(175, 40));
            botonSeguido.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonSeguido.addActionListener(e -> {

                SwingUtilities.invokeLater(() -> new ProfileWindowOther(user, seguido).setVisible(true));
                SwingUtilities.getWindowAncestor(LeftSideBar.this).dispose();
            });

            panel2.add(botonSeguido);
            panel2.add(Box.createVerticalStrut(7));
        }

        panel2.revalidate();
        panel2.repaint();
    }
    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("Left Sidebar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new LeftSideBar());
        frame.setVisible(true);
    }
     */
}

