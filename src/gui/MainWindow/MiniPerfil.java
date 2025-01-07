package src.gui.MainWindow;

import src.gui.ProfileWindow.ProfileWindowSelf;
import src.domain.Usuario;
import src.gui.StartingWindows.VentanaBienvenida;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MiniPerfil extends JFrame {

    public MiniPerfil(Usuario usuario, JFrame mw) {
    	setTitle("Mini Perfil");
        setSize(350, 250); 
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 60, 80), 2));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); 
        leftPanel.setBackground(new Color(173, 216, 230));

        try {
            BufferedImage profileImage = ImageIO.read(new File("resources/resources.resources/imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 70)); 
            JLabel profileLabel = new JLabel(profileIcon);
            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> new ProfileWindowSelf(usuario).setVisible(true));
                    dispose();
                }
            });
            leftPanel.add(profileLabel);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de perfil: " + e.getMessage());
            JLabel errorLabel = new JLabel("Sin imagen", SwingConstants.CENTER);
            leftPanel.add(errorLabel);
        }

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(173, 216, 230));
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        JLabel usuarioLabel = new JLabel(usuario.getNombreUsuario(), SwingConstants.CENTER);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
        usuarioLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rightPanel.add(usuarioLabel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(new Color(173, 216, 230));

        JPanel countPanel = new JPanel(new GridLayout(1, 2));
        countPanel.setBackground(new Color(173, 216, 230));

        /*
        int cuentaSeguidores = BDs.obtenerCuentaSeguidores(usuario.getNombreUsuario());
        int cuentaSeguidos = BDs.obtenerCuentaSiguiendo(usuario.getNombreUsuario());

        JLabel seguidoresLabel = new JLabel(cuentaSeguidores + " seguidores", SwingConstants.CENTER);
        seguidoresLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Texto más pequeño
        countPanel.add(seguidoresLabel);

        JLabel seguidosLabel = new JLabel(cuentaSeguidos + " seguidos", SwingConstants.CENTER);
        seguidosLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Texto más pequeño
        countPanel.add(seguidosLabel);
        */

        middlePanel.add(countPanel);

//        middlePanel.add(Box.createVerticalStrut(10));  

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.setBackground(new Color(173, 216, 230));

        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        botonCerrarSesion.setPreferredSize(new Dimension(200, 40)); 
        botonCerrarSesion.setBackground(Color.GRAY);  
        botonCerrarSesion.setForeground(Color.WHITE);  
        botonCerrarSesion.addActionListener(e -> {
            VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
            ventanaBienvenida.setVisible(true);
            dispose();
            mw.dispose();
        });

//        JButton botonEliminarCuenta = new JButton("Eliminar cuenta");
//        botonEliminarCuenta.setPreferredSize(new Dimension(200, 40));  
//        botonEliminarCuenta.setBackground(new Color(200,80,80));  
//        botonEliminarCuenta.setForeground(Color.WHITE); 
//        botonEliminarCuenta.addActionListener(e -> {
//            int confirmacion = JOptionPane.showConfirmDialog(
//                null,
//                "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.",
//                "Confirmar Eliminación",
//                JOptionPane.YES_NO_OPTION
//            );
//
//            if (confirmacion == JOptionPane.YES_OPTION) {
//                JOptionPane.showMessageDialog(null, "Cuenta eliminada correctamente.");
//                VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
//                ventanaBienvenida.setVisible(true);
//                dispose();
//            }
//        });

        botonesPanel.add(botonCerrarSesion);
//        botonesPanel.add(botonEliminarCuenta);
        middlePanel.add(botonesPanel);

        rightPanel.add(middlePanel, BorderLayout.CENTER);

        JButton irPerfil = new JButton("Ver tu perfil");
        irPerfil.setBackground(new Color(50,70,90));
        irPerfil.setForeground(Color.WHITE);
        irPerfil.setPreferredSize(new Dimension(200, 40));
        irPerfil.addActionListener(e -> {
            ProfileWindowSelf perfil = new ProfileWindowSelf(usuario);
            perfil.setVisible(true);
            mw.dispose();
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setBackground(new Color(173, 216, 230));
        bottomPanel.add(irPerfil);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private BufferedImage getCircularImage(BufferedImage image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(circle);
        g2d.drawImage(image, 0, 0, diameter, diameter, null);

        g2d.dispose();
        return output;
    }
}
