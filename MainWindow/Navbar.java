package MainWindow;

import javax.swing.*;
import java.awt.*;

public class Navbar extends JPanel {

    public Navbar() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.LIGHT_GRAY);

        JButton hamburgerMenu = new JButton("≡");
        hamburgerMenu.setFocusable(false);
        hamburgerMenu.setFont(new Font("Arial", Font.BOLD, 30));
        hamburgerMenu.setPreferredSize(new Dimension(65, 65));

        JTextField searchTF = new JTextField(20);

        JButton aboutButton = new JButton("\uD83D\uDD0E");
        JButton contactButton = new JButton("Contact");

        add(hamburgerMenu);
        add(searchTF);
        add(aboutButton);
        add(contactButton);
    }
}
