package ProfileWindow;

import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame {

    public ProfileWindow() {
        setTitle("WEDO - Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        add(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Left Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridBagLayout());
        GridBagConstraints leftGBC = new GridBagConstraints();

        leftGBC.insets = new Insets(5, 5, 5, 5);
        leftGBC.fill = GridBagConstraints.BOTH;

        panelIzquierda.setBackground(Color.BLUE);
        panelIzquierda.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Temporary JPanel for profile picture
        leftGBC.gridx = 0;
        leftGBC.gridy = 0;
        leftGBC.gridwidth = 2;
        leftGBC.weightx = 0.9;
        leftGBC.weighty = 0.5;
        JPanel profilePicture = new JPanel();
        profilePicture.setBackground(Color.YELLOW);
        panelIzquierda.add(profilePicture, leftGBC);

        // Username JLabel
        leftGBC.gridx = 0;
        leftGBC.gridy = 1;
        leftGBC.gridwidth = 2;
        leftGBC.weightx = 0.9;
        leftGBC.weighty = 0.2;
        JLabel username = new JLabel("Nombre Usuario", SwingConstants.CENTER);
        username.setOpaque(true);
        username.setBackground(Color.YELLOW);
        panelIzquierda.add(username, leftGBC);

        panel.add(panelIzquierda, gbc);

        // Right Panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Equal weight for right panel
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;

        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new GridBagLayout());
        GridBagConstraints rightGBC = new GridBagConstraints();

        rightGBC.insets = new Insets(5, 5, 5, 5);
        rightGBC.fill = GridBagConstraints.BOTH;

        panelDerecha.setBackground(Color.RED);
        panelDerecha.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        rightGBC.gridx = 0;
        rightGBC.gridy = 0;
        rightGBC.gridwidth = 2;
        rightGBC.weightx = 0.9;
        rightGBC.weighty = 0.5;

        JLabel rachaLabel = new JLabel("Racha de objetivos diarios");
        rachaLabel.setOpaque(true);
        rachaLabel.setBackground(Color.YELLOW);
        panelDerecha.add(rachaLabel, rightGBC);

        panel.add(panelDerecha, gbc);
    }

    public static void main(String[] args) {
        ProfileWindow window = new ProfileWindow();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
