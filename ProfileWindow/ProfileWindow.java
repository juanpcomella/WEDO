package ProfileWindow;

import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame {

    public ProfileWindow() {
        setTitle("WEDO - Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Left Panel Configuration
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);

        // Left Panel - Profile Picture
        GridBagConstraints leftGBC = new GridBagConstraints();
        leftGBC.insets = new Insets(5, 5, 5, 5);
        leftGBC.fill = GridBagConstraints.BOTH;
        leftGBC.gridx = 0;
        leftGBC.gridy = 0;
        leftGBC.weightx = 1.0;
        leftGBC.weighty = 0.4;

        JPanel profilePicturePanel = new JPanel();
        profilePicturePanel.setBackground(Color.BLUE);
        profilePicturePanel.setPreferredSize(new Dimension(200, 200));
        profilePicturePanel.setLayout(new BorderLayout());

        JLabel profilePictureLabel = new JLabel("Foto de Perfil", SwingConstants.CENTER);
        profilePicturePanel.add(profilePictureLabel, BorderLayout.CENTER);
        leftPanel.add(profilePicturePanel, leftGBC);

        // Left Panel - Username and Button Row
        leftGBC.gridy = 1;
        leftGBC.weighty = 0.2;

        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Nombre Usuario", SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        usernamePanel.add(usernameLabel);
        leftPanel.add(usernamePanel, leftGBC);

        // Left Panel - Description Row
        leftGBC.gridy = 2;
        leftGBC.weighty = 0.4;

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Alguna descripción o algún dato noc");
        descriptionPanel.add(descriptionLabel);
        leftPanel.add(descriptionPanel, leftGBC);

        mainPanel.add(leftPanel, gbc);

        // Right Panel Configuration
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints rightGBC = new GridBagConstraints();
        rightGBC.insets = new Insets(5, 5, 5, 5);
        rightGBC.fill = GridBagConstraints.BOTH;
        rightGBC.gridx = 0;
        rightGBC.weightx = 1.0;

        // Right Panel - Daily Streaks Panel
        rightGBC.gridy = 0;
        rightGBC.weighty = 0.3;
        JPanel dailyStreakPanel = new JPanel();
        dailyStreakPanel.setBackground(Color.BLUE);
        dailyStreakPanel.add(new JLabel("Racha de objetivos diarios"));
        rightPanel.add(dailyStreakPanel, rightGBC);

        // Right Panel - Progress Panel
        rightGBC.gridy = 1;
        rightGBC.weighty = 0.3;
        JPanel progressPanel = new JPanel();
        progressPanel.setBackground(Color.BLUE);
        progressPanel.add(new JLabel("Progreso de algún objetivo"));
        rightPanel.add(progressPanel, rightGBC);

        // Right Panel - Calendar Panel
        rightGBC.gridy = 2;
        rightGBC.weighty = 0.4;
        JPanel calendarPanel = new JPanel();
        calendarPanel.setBackground(Color.BLUE);
        calendarPanel.add(new JLabel("Calendario con eventos públicos"));
        rightPanel.add(calendarPanel, rightGBC);

        mainPanel.add(rightPanel, gbc);

        // Add main panel to frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        ProfileWindow window = new ProfileWindow();
        window.setVisible(true);
    }
}
