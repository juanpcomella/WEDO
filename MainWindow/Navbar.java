package MainWindow;

import BaseDeDatos.BDs;
import ProfileWindow.ProfileWindowOther;
import ProfileWindow.ProfileWindowSelf;
import StartingWindows.Usuario;
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

import static java.lang.Thread.sleep;

public class Navbar extends JPanel {
	public static JLabel coinAmountLabel;

	public Navbar(LeftSideBar leftSideBar, Usuario usuario) {
        Usuario CURRENT_USER = usuario;
        setLayout(new GridBagLayout()); // Cambiar a GridBagLayout
        setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.BOTH;

        // Botón de menú hamburguesa
        JButton hamburgerMenu = new JButton("≡");
        hamburgerMenu.setBackground(new Color(50,70,90));
        hamburgerMenu.setForeground(Color.WHITE);
        hamburgerMenu.setFocusable(false);
        hamburgerMenu.setFocusPainted(false);
        hamburgerMenu.setBorderPainted(false);
        hamburgerMenu.setFont(new Font("Arial", Font.BOLD, 30));
        hamburgerMenu.setPreferredSize(new Dimension(55, 55));
        gbc.gridx = 0;
        gbc.weightx = 0; // No se expande horizontalmente
        add(hamburgerMenu, gbc);

        // left sidebar thread logic
        int lsbWidth = (int) ((Toolkit.getDefaultToolkit().getScreenSize().width) * 0.1);

        hamburgerMenu.addActionListener(new ActionListener() {
            private boolean isLeftSidebarVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    int targetWidth = isLeftSidebarVisible ? 0 : lsbWidth;
                    int currentWidth = leftSideBar.getPreferredSize().width;
                    int step = isLeftSidebarVisible ? -10 : 10;

                    while ((step > 0 && currentWidth < targetWidth) || (step < 0 && currentWidth > targetWidth)) {
                        currentWidth += step;

                        if ((step > 0 && currentWidth > targetWidth) || (step < 0 && currentWidth < targetWidth)) {
                            currentWidth = targetWidth;
                        }

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

        JTextField searchTF = new JTextField(20);
        searchTF.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx++;
        gbc.weightx = 1;
        add(searchTF, gbc);

        ImageIcon searchImage = new ImageIcon(Navbar.class.getResource("/imagenes/lupaDEF-removebg-preview.png"));
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

        searchIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String usuarioInput = searchTF.getText();

                // Verificar si el usuario existe
                if (BDs.usuarioExistente(usuarioInput)) {
                    Usuario usuarioObtenido = BDs.obtenerUsuario(usuarioInput);

                    if (usuarioObtenido != null) {
                        // Comparar con el usuario actual
                        if (usuarioObtenido.equals(CURRENT_USER)) {
                            // Abrir ProfileWindowSelf
                            ProfileWindowSelf profile = new ProfileWindowSelf(usuario);
                            profile.setVisible(true);
                        } else {
                            // Abrir ProfileWindowOther
                            ProfileWindowOther profile = new ProfileWindowOther(usuario, usuarioObtenido);
                            profile.setVisible(true);
                        }
                        // Cerrar ventana actual
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
        
        ImageIcon iconoMonedaDefault = new ImageIcon("imagenes/coin_sin_fondo.png");
        Image img = iconoMonedaDefault.getImage(); // Obtener la imagen del ícono
        Image resizedImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Escalar la imagen
        ImageIcon resizedIcon = new ImageIcon(resizedImg); // Crear un nuevo ImageIcon

        JLabel coinLabel = new JLabel(resizedIcon);
        coinLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx++;
        gbc.weightx = 0;
        add(coinLabel, gbc);
        coinAmountLabel = new JLabel(Integer.toString(usuario.getSaldo()));
        coinAmountLabel.setFont(new Font("Arial",Font.BOLD,20));
        coinAmountLabel.setForeground(new Color(50, 70, 90));
        gbc.gridx++;
        add(coinAmountLabel, gbc);

        ImageIcon shopImage = new ImageIcon(Navbar.class.getResource("/imagenes/shop.png"));
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
                VentanaTienda ventanaTienda = new VentanaTienda(usuario);
                ventanaTienda.setLocationRelativeTo(null);
                ventanaTienda.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(shopIcon)).dispose();
            }
        });

        gbc.gridx++;



        add(shopIcon, gbc);

        ImageIcon notifImage = new ImageIcon(Navbar.class.getResource("/imagenes/notification.png"));
        Image notifImagenEscalada = notifImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon notifImagenRedimensionada = new ImageIcon(notifImagenEscalada);
        JLabel notifIcon = new JLabel(notifImagenRedimensionada);
        gbc.gridx++;
        add(notifIcon, gbc);

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            ImageIcon profileIcon = new ImageIcon(getCircularImage(profileImage, 50));
            JLabel profileLabel = new JLabel(profileIcon);
            profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        MiniPerfil miniPerfil = new MiniPerfil(usuario);
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
