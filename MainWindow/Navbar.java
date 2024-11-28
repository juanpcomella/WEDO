package MainWindow;

import ProfileWindow.ProfileWindow;
import VentanaTienda.VentanaTienda;


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
import javax.imageio.ImageIO;

public class Navbar extends JPanel {

    public Navbar() {
        setLayout(new GridBagLayout()); // Cambiar a GridBagLayout
        setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.BOTH;

        // Botón de menú hamburguesa
        JButton hamburgerMenu = new JButton("≡");
        hamburgerMenu.setFocusable(false);
        hamburgerMenu.setFocusPainted(false);
        hamburgerMenu.setBorderPainted(false);
        hamburgerMenu.setFont(new Font("Arial", Font.BOLD, 30));
        hamburgerMenu.setPreferredSize(new Dimension(65, 65));
        gbc.gridx = 0;
        gbc.weightx = 0; // No se expande horizontalmente
        add(hamburgerMenu, gbc);

        // Espacio horizontal (puedes ajustar los tamaños)
        gbc.gridx++;
        gbc.weightx = 0.1; // Espaciador flexible
        add(Box.createHorizontalStrut(20), gbc);

        // Campo de búsqueda
        JTextField searchTF = new JTextField(20);
        searchTF.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx++;
        gbc.weightx = 1; // Se expande para ocupar espacio
        add(searchTF, gbc);

        // Botón de búsqueda
        ImageIcon searchImage = new ImageIcon(Navbar.class.getResource("/imagenes/lupa.png"));
        Image searchImagenEscalada = searchImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon searchImagenRedimensionada = new ImageIcon(searchImagenEscalada);
        JButton searchIcon = new JButton(searchImagenRedimensionada);
        searchIcon.setBorderPainted(false);
        searchIcon.setFocusable(false);
        gbc.gridx++;
        gbc.weightx = 0;
        add(searchIcon, gbc);

        // Espacio horizontal
        gbc.gridx++;
        add(Box.createHorizontalStrut(20), gbc);

        // Icono de moneda
        JLabel coinLabel = new JLabel("\uD83E\uDE99");
        coinLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx++;
        gbc.weightx = 0;
        add(coinLabel, gbc);

        // Cantidad de monedas
        int coinAmount = 0;
        JLabel coinAmountLabel = new JLabel(coinAmount + "");
        gbc.gridx++;
        add(coinAmountLabel, gbc);

        // Icono de tienda
        ImageIcon shopImage = new ImageIcon(Navbar.class.getResource("/imagenes/shop.png"));
        Image shopImagenEscalada = shopImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon shopImagenRedimensionada = new ImageIcon(shopImagenEscalada);
        JButton shopIcon = new JButton(shopImagenRedimensionada);
        shopIcon.setBorderPainted(false);
        shopIcon.setFocusable(false);
        shopIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaTienda ventanaTienda = new VentanaTienda();
                ventanaTienda.setLocationRelativeTo(null);
                ventanaTienda.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(shopIcon)).dispose();
            }
        });

        gbc.gridx++;



        add(shopIcon, gbc);


        // Icono de notificaciones
        ImageIcon notifImage = new ImageIcon(Navbar.class.getResource("/imagenes/notification.png"));
        Image notifImagenEscalada = notifImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon notifImagenRedimensionada = new ImageIcon(notifImagenEscalada);
        JLabel notifIcon = new JLabel(notifImagenRedimensionada);
        gbc.gridx++;
        add(notifIcon, gbc);

        // Icono de perfil (circular)
        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 50));
            JLabel profileLabel = new JLabel(profileIcon);
            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> new ProfileWindow().setVisible(true));
                    ((JFrame) SwingUtilities.getWindowAncestor(profileLabel)).dispose();
                }
            });
            gbc.gridx++;
            add(profileLabel, gbc);
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
