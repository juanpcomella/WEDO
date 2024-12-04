package pruebaMainWindow;

import ProfileWindow.*;
import StartingWindows.Usuario;
import VentanaTienda.VentanaTienda;


import javax.swing.*;

import MainWindow.LeftSideBar;

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

import static java.lang.Thread.sleep;

public class Navbar1 extends JPanel {

    private boolean isLeftSidebarVisible = false;

    public Navbar1(LeftSideBar leftSideBar, Usuario usuario) {
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

        // left sidebar thread logic
        int lsbWidth = (int) ((getWidth()) * 0.1); // 15% of the width for the sidebar

        hamburgerMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    int targetWidth = isLeftSidebarVisible ? 0 : lsbWidth;
                    int currentWidth = leftSideBar.getWidth();
                    int step = isLeftSidebarVisible ? -10 : 10; // Correct the step direction

                    System.out.println("Starting animation. Target width: " + targetWidth + ", Current width: " + currentWidth);

                    while ((step > 0 && currentWidth < targetWidth) || (step < 0 && currentWidth > targetWidth)) {
                        currentWidth += step;

                        // Avoid overshooting the target width
                        if ((step > 0 && currentWidth > targetWidth) || (step < 0 && currentWidth < targetWidth)) {
                            currentWidth = targetWidth;
                        }

                        int finalCurrentWidth = currentWidth;
                        SwingUtilities.invokeLater(() -> {
                            leftSideBar.setPreferredSize(new Dimension(finalCurrentWidth, leftSideBar.getHeight()));
                            SwingUtilities.getWindowAncestor(leftSideBar).revalidate();
                            SwingUtilities.getWindowAncestor(leftSideBar).repaint();
                        });

                        try {
                            Thread.sleep(10); // Control animation speed
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    // Toggle sidebar visibility state
                    isLeftSidebarVisible = !isLeftSidebarVisible;
                }).start();
            }
        });



        // Espacio horizontal (puedes ajustar los tamaños)
        gbc.gridx++;
        gbc.weightx = 0.1; // Espaciador flexible
        add(Box.createHorizontalStrut(20), gbc);

        // Campo de búsqueda
        JTextField searchTF = new JTextField(20);
        searchTF.setFont(new Font("Arial", Font.PLAIN, 20));
        //--------------------------
        gbc.gridx++;
        gbc.weightx = 1; // Se expande para ocupar espacio
        add(searchTF, gbc);

        // Botón de búsqueda
        ImageIcon searchImage = new ImageIcon(Navbar1.class.getResource("/imagenes/lupa.png"));
        Image searchImagenEscalada = searchImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon searchImagenRedimensionada = new ImageIcon(searchImagenEscalada);
        JButton searchIcon = new JButton(searchImagenRedimensionada);
        searchIcon.setBorderPainted(false);
        searchIcon.setFocusable(false);
        searchIcon.setFocusPainted(false);
        searchIcon.setContentAreaFilled(false);
        searchIcon.setBackground(new Color(0,0,0,0));
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
        ImageIcon shopImage = new ImageIcon(Navbar1.class.getResource("/imagenes/shop.png"));
        Image shopImagenEscalada = shopImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon shopImagenRedimensionada = new ImageIcon(shopImagenEscalada);
        JButton shopIcon = new JButton(shopImagenRedimensionada);
        shopIcon.setBackground(new Color(0,0,0,0));
        shopIcon.setFocusPainted(false);
        shopIcon.setBorderPainted(false);
        shopIcon.setFocusable(false);
        shopIcon.setContentAreaFilled(false);
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
        ImageIcon notifImage = new ImageIcon(Navbar1.class.getResource("/imagenes/notification.png"));
        Image notifImagenEscalada = notifImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon notifImagenRedimensionada = new ImageIcon(notifImagenEscalada);
        JLabel notifIcon = new JLabel(notifImagenRedimensionada);
        gbc.gridx++;
        add(notifIcon, gbc);

        // Icono de perfil (circular)
//        try {
//            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
//            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 50));
//            JLabel profileLabel = new JLabel(profileIcon);
//            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            profileLabel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    SwingUtilities.invokeLater(() -> new ProfileWindow(usuario).setVisible(true));
//                    ((JFrame) SwingUtilities.getWindowAncestor(profileLabel)).dispose();
//                }
//            });
//            gbc.gridx++;
//            add(profileLabel, gbc);
//        } catch (IOException e) {
//            System.out.println("Error al cargar la imagen de perfil: " + e.getMessage());
//        }
//
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
