package MainWindow;

import ProfileWindow.ProfileWindowSelf;
import StartingWindows.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MiniPerfil extends JFrame {

    public MiniPerfil(Usuario usuario, JFrame mw) {
        setTitle("Mini Perfil");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

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
