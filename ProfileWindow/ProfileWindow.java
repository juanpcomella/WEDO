package ProfileWindow;

import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame{

    public ProfileWindow() {
        setTitle("WEDO - Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridBagLayout());

        panelIzquierda.setBackground(Color.BLUE);
        panelIzquierda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(panelIzquierda, gbc);


    }

    public static void main(String[] args) {
        ProfileWindow window = new ProfileWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
