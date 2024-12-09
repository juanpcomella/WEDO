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
        setSize(400, 180);
        setUndecorated(true); // Remove window decorations
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Add MouseListener to detect clicks outside the window
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Do nothing when gaining focus
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                // Close the MiniPerfil when losing focus
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBackground(new Color(173, 216, 230));
        
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

            panel.add(profileLabel);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de perfil: " + e.getMessage());
            JLabel errorLabel = new JLabel("Sin imagen", SwingConstants.CENTER);
            panel.add(errorLabel);
        }

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(173, 216, 230));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel usuarioLabel = new JLabel(usuario.getNombreUsuario());
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton irPerfil = new JButton("Ver tu perfil");
        irPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProfileWindowSelf perfil = new ProfileWindowSelf(usuario);
                perfil.setVisible(true);
                mw.dispose();
                dispose();
            }
        });

        rightPanel.add(usuarioLabel);
        rightPanel.add(irPerfil);

        panel.add(rightPanel);

        add(panel, BorderLayout.CENTER);
        
        JButton botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
				ventanaBienvenida.setVisible(true);
				dispose();
			}
		});
        
        rightPanel.add(botonCerrarSesion);
        
        JButton botonEliminarCuenta = new JButton("Eliminar cuenta");
        botonEliminarCuenta.setBackground(Color.RED);
        botonEliminarCuenta.setForeground(Color.WHITE);
        botonEliminarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        rightPanel.add(botonEliminarCuenta);
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
