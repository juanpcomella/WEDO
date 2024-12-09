package MainWindow;

import ProfileWindow.ProfileWindowSelf;
import StartingWindows.Usuario;
import StartingWindows.VentanaBienvenida;

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
        setSize(400, 220);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        leftPanel.setBackground(new Color(173, 216, 230));

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 80));
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

        mainPanel.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(173, 216, 230));
        rightPanel.setLayout(new BorderLayout());
        mainPanel.add(rightPanel);

        JLabel usuarioLabel = new JLabel(usuario.getNombreUsuario(), SwingConstants.CENTER);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usuarioLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rightPanel.add(usuarioLabel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        middlePanel.setBackground(new Color(173, 216, 230));

        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.addActionListener(e -> {
            VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
            ventanaBienvenida.setVisible(true);
            dispose();
        });

        JButton botonEliminarCuenta = new JButton("Eliminar cuenta");
        botonEliminarCuenta.setBackground(Color.RED);
        botonEliminarCuenta.setForeground(Color.WHITE);
        botonEliminarCuenta.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Cuenta eliminada correctamente.");
                VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
                ventanaBienvenida.setVisible(true);
                dispose();
            }
        });

        middlePanel.add(botonCerrarSesion);
        middlePanel.add(botonEliminarCuenta);
        rightPanel.add(middlePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(173, 216, 230));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton irPerfil = new JButton("Ver tu perfil");
        irPerfil.addActionListener(e -> {
            ProfileWindowSelf perfil = new ProfileWindowSelf(usuario);
            perfil.setVisible(true);
            mw.dispose();
            dispose();
        });
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
