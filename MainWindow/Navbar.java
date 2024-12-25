package MainWindow;

import BaseDeDatos.BDs;
import ProfileWindow.ProfileWindowOther;
import ProfileWindow.ProfileWindowSelf;
import StartingWindows.Usuario;
import VentanaTienda.VentanaTienda;
import VentanaTienda.VentanaTienda2;

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

import static java.lang.Thread.sleep;

public class Navbar extends JPanel {
    public static JLabel coinAmountLabel;

    public Navbar(LeftSideBar leftSideBar, Usuario usuario, JFrame mw) {
        Usuario CURRENT_USER = usuario;
        setLayout(new GridBagLayout());
        setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Botón de menú hamburguesa
        JButton hamburgerMenu = new JButton("≡");
        hamburgerMenu.setBackground(new Color(50, 70, 90));
        hamburgerMenu.setForeground(Color.WHITE);
        hamburgerMenu.setFocusable(false);
        hamburgerMenu.setFocusPainted(false);
        hamburgerMenu.setBorderPainted(false);
        hamburgerMenu.setFont(new Font("Arial", Font.BOLD, 30));
        hamburgerMenu.setPreferredSize(new Dimension(55, 55));
        gbc.gridx = 0;
        gbc.weightx = 0; // No se expande horizontalmente
        add(hamburgerMenu, gbc);

        // Left sidebar logic
        hamburgerMenu.addActionListener(new ActionListener() {
            private boolean isLeftSidebarVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    int targetWidth = isLeftSidebarVisible ? 0 : 200;
                    int currentWidth = leftSideBar.getPreferredSize().width;
                    int step = isLeftSidebarVisible ? -10 : 10;

                    while ((step > 0 && currentWidth < targetWidth) || (step < 0 && currentWidth > targetWidth)) {
                        currentWidth += step;
                        int finalCurrentWidth = currentWidth;
                        SwingUtilities.invokeLater(() -> {
                            leftSideBar.setPreferredSize(new Dimension(finalCurrentWidth, leftSideBar.getHeight()));
                            leftSideBar.revalidate();
                            leftSideBar.repaint();
                        });
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    isLeftSidebarVisible = !isLeftSidebarVisible;
                }).start();
            }
        });

        gbc.gridx++;
        gbc.weightx = 0.1;
        add(Box.createHorizontalStrut(20), gbc);

        // Search Bar
        JTextField searchTF = new JTextField(20);
        searchTF.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx++;
        gbc.weightx = 1;
        add(searchTF, gbc);

        ImageIcon searchImage = new ImageIcon(Navbar.class.getResource("/imagenes/lupaDEF-removebg-preview.png"));
        Image searchImageScaled = searchImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton searchIcon = new JButton(new ImageIcon(searchImageScaled));
        searchIcon.setBorderPainted(false);
        searchIcon.setFocusable(false);
        searchIcon.setFocusPainted(false);
        searchIcon.setContentAreaFilled(false);
        gbc.gridx++;
        gbc.weightx = 0;

        searchIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String usuarioInput = searchTF.getText();
                if (BDs.usuarioExistente(usuarioInput)) {
                    Usuario usuarioObtenido = BDs.obtenerUsuario(usuarioInput);
                    if (usuarioObtenido != null) {
                        if (usuarioObtenido.equals(CURRENT_USER)) {
                            ProfileWindowSelf profile = new ProfileWindowSelf(usuario);
                            profile.setVisible(true);
                        } else {
                            ProfileWindowOther profile = new ProfileWindowOther(usuario, usuarioObtenido);
                            profile.setVisible(true);
                        }
                        ((JFrame) SwingUtilities.getWindowAncestor(searchIcon)).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado en la base de datos.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario no existe.");
                }
            }
        });
        add(searchIcon, gbc);

        gbc.gridx++;
        add(Box.createHorizontalStrut(20), gbc);

        ImageIcon coinIconDefault = new ImageIcon("imagenes/coin_sin_fondo.png");
        Image coinImg = coinIconDefault.getImage();
        Image resizedCoinImg = coinImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel coinIcon = new JLabel(new ImageIcon(resizedCoinImg));
        gbc.gridx++;
        gbc.weightx = 0;
        add(coinIcon, gbc);

        int saldo = BDs.getSaldo(usuario.getNombreUsuario()); 
//        coinAmountLabel = new JLabel(Integer.toString(saldo));
        coinAmountLabel = new JLabel(Integer.toString(saldo));
        coinAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        coinAmountLabel.setForeground(new Color(50, 70, 90));
        gbc.gridx++;
        add(coinAmountLabel, gbc);

        // Shop Icon
        ImageIcon shopImage = new ImageIcon(Navbar.class.getResource("/imagenes/shop.png"));
        Image shopImageScaled = shopImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton shopIcon = new JButton(new ImageIcon(shopImageScaled));
        shopIcon.setBackground(new Color(0, 0, 0, 0));
        shopIcon.setFocusPainted(false);
        shopIcon.setBorderPainted(false);
        shopIcon.setFocusable(false);
        shopIcon.setContentAreaFilled(false);
        shopIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaTienda2 ventanaTienda = new VentanaTienda2(usuario);
                ventanaTienda.setLocationRelativeTo(null);
                ventanaTienda.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(shopIcon)).dispose();
            }
        });
        gbc.gridx++;
        add(shopIcon, gbc);

        // Profile Icon
        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 60));
            JLabel profileLabel = new JLabel(profileIcon);
            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        MiniPerfil miniPerfil = new MiniPerfil(usuario, mw);
                        int x = profileLabel.getX() - 330;
                        int y = profileLabel.getY();
                        miniPerfil.setLocation(x, y);
                        miniPerfil.setVisible(true);
                    });
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