package ProfileWindow;

import jdk.jfr.Percentage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.event.*;

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

        JPanel profilePicturePanel = new JPanel(new BorderLayout());
        profilePicturePanel.setBackground(Color.WHITE);

        try {
            // Load the image and convert to a circular format
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png")); // Update with your image path
            JLabel profilePictureLabel = new JLabel(new ImageIcon(getCircularImage(profileImage, 200)));
            profilePicturePanel.add(profilePictureLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            profilePicturePanel.add(new JLabel("Image not found"), BorderLayout.CENTER);
        }

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
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Top, Left, Bottom, Right padding

        progressPanel.setBackground(Color.BLUE);

        JLabel progressLabel = new JLabel("Titulo Objetivo", SwingConstants.CENTER);
        progressLabel.setForeground(Color.WHITE);
        progressPanel.add(progressLabel, BorderLayout.NORTH);

        JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        componentPanel.setBackground(Color.LIGHT_GRAY);

        JLabel percentageLabel = new JLabel("0%", JLabel.CENTER);
        percentageLabel.setForeground(Color.WHITE);
        componentPanel.add(percentageLabel, BorderLayout.CENTER);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(60);
        progressBar.setStringPainted(false);

        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int progress = progressBar.getValue(); // Get the current value of the progress bar
                percentageLabel.setText(progress + "%"); // Update the percentage label
            }
        });

        componentPanel.add(progressBar);

        progressPanel.add(componentPanel, BorderLayout.CENTER);
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

    // Method to create a circular image
    private BufferedImage getCircularImage(BufferedImage image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();

        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a circular clip
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(circle);

        // Draw the image scaled to fit within the circle
        g2d.drawImage(image, 0, 0, diameter, diameter, null);

        g2d.dispose();
        return output;
    }

    public static void main(String[] args) {
        ProfileWindow window = new ProfileWindow();
        window.setVisible(true);
    }
}
