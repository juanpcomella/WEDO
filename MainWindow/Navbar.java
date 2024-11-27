package MainWindow;

import ProfileWindow.ProfileWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Navbar extends JPanel {

    public Navbar() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.LIGHT_GRAY);

        JButton hamburgerMenu = new JButton("≡");
        hamburgerMenu.setFocusable(false);
        hamburgerMenu.setFocusPainted(false);
        hamburgerMenu.setBorderPainted(false);
        hamburgerMenu.setFont(new Font("Arial", Font.BOLD, 30));
        hamburgerMenu.setPreferredSize(new Dimension(65, 65));
        add(hamburgerMenu);

        JTextField searchTF = new JTextField(20);
        add(searchTF);

        JButton searchButton = new JButton("\uD83D\uDD0E");
        add(searchButton);

        JLabel coinLabel = new JLabel("\uD83E\uDE99");
        coinLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(coinLabel);

        int coinAmount = 0;
        JLabel coinAmountLabel = new JLabel(coinAmount+"");
        add(coinAmountLabel);

        ImageIcon shopImage = new ImageIcon(Navbar.class.getResource("/imagenes/shop.png"));
        Image shopImagenEscalada = shopImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(shopImagenEscalada);
        JLabel shopIcon = new JLabel(imagenRedimensionada);
        add(shopIcon);

        ImageIcon notifImage = new ImageIcon(Navbar.class.getResource("/imagenes/notification.png"));
        Image notifImagenEscalada = notifImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon notifImagenRedimensionada = new ImageIcon(notifImagenEscalada);
        JLabel notifIcon = new JLabel(notifImagenRedimensionada);
        add(notifIcon);

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 50));
            JLabel profileLabel = new JLabel(profileIcon);
            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> new ProfileWindow().setVisible(true));
                }
            });
            add(profileLabel);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de perfil: " + e.getMessage());
        }
    }

    private Image getCircularImage(BufferedImage image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2d.drawImage(image, 0, 0, diameter, diameter, null);
        g2d.dispose();
        return output;
    }
}

