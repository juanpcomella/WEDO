package MainWindow;

import javax.swing.*;
import java.awt.*;

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


    }
}
