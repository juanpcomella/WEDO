package ProfileWindow;

import BaseDeDatos.BDs;
import MainWindow.MainWindow;
import StartingWindows.Usuario;
import MainWindow.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.io.*;



import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class ProfileWindowSelf extends JFrame {

    public ProfileWindowSelf(Usuario usuario) {
        setTitle("WEDO - " + usuario.getNombreUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        GridBagConstraints leftGBC = new GridBagConstraints();
        leftGBC.insets = new Insets(5, 5, 5, 5);
        leftGBC.fill = GridBagConstraints.NONE;
        leftGBC.anchor = GridBagConstraints.NORTHWEST;


        // Left Panel - Back Button
        leftGBC.gridx = 0;
        leftGBC.gridy = 0;
        leftGBC.weightx = 0;
        leftGBC.weighty = 0;
        JButton backButton = new JButton("<<");
        backButton.setBackground(new Color(0,0,0,0));
        backButton.setFocusPainted(false);
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow mainWindow = new MainWindow(usuario);
                mainWindow.setVisible(true);
                dispose();
            }
        });

        leftPanel.add(backButton, leftGBC);

        // Left Panel - Edit Profile Button
        leftGBC.gridx = 0;
        leftGBC.gridy = 1;
        leftGBC.anchor = GridBagConstraints.NORTHEAST;
        leftGBC.insets = new Insets(0, 0, 0, 15);
        JButton editButton = new JButton("Editar Perfil");
        editButton.setFont(new Font("Arial", Font.PLAIN, 16));
        editButton.setBackground(new Color(0,0,0,0));
        editButton.setFocusPainted(false);
        editButton.setFocusable(false);
        editButton.setContentAreaFilled(false);

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditarPerfil editarPerfil = new EditarPerfil(usuario);
                editarPerfil.setSize(450, 600);
                editarPerfil.setLocationRelativeTo(null);
                editarPerfil.setVisible(true);
            }
        });

        leftPanel.add(editButton, leftGBC);

        // Left Panel - Profile Picture
        leftGBC.gridy = 2;
        leftGBC.weightx = 1.0;
        leftGBC.weighty = 0.40;
        leftGBC.fill = GridBagConstraints.BOTH;
        leftGBC.anchor = GridBagConstraints.CENTER;

        JPanel profilePicturePanel = new JPanel(new BorderLayout());
        profilePicturePanel.setBackground(Color.WHITE);

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            JLabel profilePictureLabel = new JLabel(new ImageIcon(getCircularImage(profileImage, 200)));
            profilePicturePanel.add(profilePictureLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            profilePicturePanel.add(new JLabel("Image not found"), BorderLayout.CENTER);
        }

        leftPanel.add(profilePicturePanel, leftGBC);

        // Left Panel - Username and Button Row
        leftGBC.gridy = 3;
        leftGBC.weighty = 0.2;

        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(Color.WHITE);
        JLabel usernameLabel = new JLabel(""+usuario.getNombreUsuario(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 48));
        usernamePanel.add(usernameLabel);
        leftPanel.add(usernamePanel, leftGBC);

        // Left Panel - Description Row
        leftGBC.gridy = 4;
        leftGBC.weighty = 0.4;

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Alguna descripción o algún dato noc");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        rightGBC.weighty = 0.333;
        JPanel dailyStreakPanel = new JPanel(new BorderLayout());
        dailyStreakPanel.setBackground(new Color(58, 92, 181));

        JLabel streakTitleLabel = new JLabel("Racha de Objetivos Diarios", SwingConstants.CENTER);
        streakTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        streakTitleLabel.setForeground(Color.WHITE);
        streakTitleLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        dailyStreakPanel.add(streakTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(58, 92, 181));
        GridBagConstraints streakGBC = new GridBagConstraints();
        streakGBC.insets = new Insets(5, 5, 5, 5);

        streakGBC.gridx = 0;
        streakGBC.gridy = 0;
        streakGBC.anchor = GridBagConstraints.CENTER;
        JLabel flameIcon = new JLabel("\uD83D\uDD25");
        flameIcon.setFont(new Font("Arial", Font.PLAIN, 48));
        contentPanel.add(flameIcon, streakGBC);

        streakGBC.gridx = 1;
        streakGBC.gridy = 0;
        streakGBC.anchor = GridBagConstraints.WEST;
        final int[] streakCount = {0};
        JLabel streakCountLabel = new JLabel(streakCount[0] + "");
        streakCountLabel.setFont(new Font("Arial", Font.BOLD, 48));
        streakCountLabel.setForeground(new Color(255, 165, 0));
        contentPanel.add(streakCountLabel, streakGBC);

        streakGBC.gridx = 2;
        streakGBC.gridy = 0;

        contentPanel.add(Box.createHorizontalStrut(20), streakGBC);

        streakGBC.gridx = 3;
        streakGBC.gridy = 0;

        streakGBC.anchor = GridBagConstraints.CENTER;
        JPanel gridPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        gridPanel.setBackground(new Color(58, 92, 181));

        int cantObjetivosDiarios = 4;
        final int[] completedObjectives = {0};

        for (int i = 0; i < cantObjetivosDiarios; i++) {
            JPanel box = new JPanel();
            box.setPreferredSize(new Dimension(60, 60));
            box.setBackground(new Color(244, 100, 93)); // Inicialmente rojo

            // Configurar el tooltip nativo de Swing
            box.setToolTipText("<html><b>Información del objetivo</b><br>Este panel representa un objetivo diario.</html>");

            // Añadir el listener para manejar clics en el panel
            box.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int response = JOptionPane.showConfirmDialog(
                            null,
                            "¿Quieres marcar este objetivo como completado?",
                            "Confirmar Acción",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        if (box.getBackground().equals(new Color(244, 100, 93))) {
                            box.setBackground(new Color(197, 229, 191)); // Cambiar a verde
                            completedObjectives[0]++;

                            // Verificar si todas las tareas están completas
                            if (completedObjectives[0] == cantObjetivosDiarios) {
                                streakCount[0]++; // Incrementar contador de racha
                                streakCountLabel.setText(streakCount[0] + ""); // Actualizar etiqueta de racha

                                JOptionPane.showMessageDialog(
                                        null,
                                        "¡Felicidades! Has completado tus tareas de hoy.\nRacha incrementada a " + streakCount[0],
                                        "Actualización Racha",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }
                    }
                }
            });

            // Agregar el panel a tu contenedor (por ejemplo, un JPanel principal)
            gridPanel.add(box);
        }


        contentPanel.add(gridPanel, streakGBC);

        dailyStreakPanel.add(contentPanel, BorderLayout.CENTER);

        rightPanel.add(dailyStreakPanel, rightGBC);

        // Right Panel - Progress Panel
        rightGBC.gridy = 1;
        rightGBC.weighty = 0.333;

        JPanel progressPanel = new JPanel();
        progressPanel.setBackground(new Color(58, 92, 181));
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));

        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel progressLabel = new JLabel("Titulo del objetivo", SwingConstants.CENTER);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 24));
        progressPanel.add(progressLabel);

        progressPanel.add(Box.createVerticalGlue());

        JLabel percentageLabel = new JLabel("0%", SwingConstants.CENTER);
        percentageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        percentageLabel.setForeground(Color.WHITE);
        percentageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        progressPanel.add(percentageLabel);

        progressPanel.add(Box.createVerticalStrut(5));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(15);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(150, 10));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressPanel.add(progressBar);

        percentageLabel.setText(progressBar.getValue() + "%");

        progressPanel.add(Box.createVerticalGlue());

        rightPanel.add(progressPanel, rightGBC);

        // Right Panel - Calendar Panel
        rightGBC.gridy = 2;
        rightGBC.weighty = 0.333;
        JPanel activityPanel = new JPanel();
        activityPanel.setBackground(new Color(58, 92, 181));
        activityPanel.setLayout(new BorderLayout());

        JLabel activityLabel = new JLabel("Próximas actividades de " + usuario.getNombreUsuario(), SwingConstants.CENTER);
        activityLabel.setForeground(Color.WHITE);
        activityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        activityLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        activityPanel.add(activityLabel, BorderLayout.NORTH);

        // Configuración de la tabla
        String[] activityTableParams = {"Activity", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(activityTableParams, 0);
        JTable activityJTable = new JTable(tableModel);
        activityJTable.setOpaque(false);
        activityJTable.setBackground(new Color(0, 0, 0, 0));

        JScrollPane jtableScroll = new JScrollPane(activityJTable);
        jtableScroll.getViewport().setOpaque(false);
        jtableScroll.setOpaque(false);

        activityPanel.add(jtableScroll, BorderLayout.CENTER);

// Cargar eventos del usuario
        cargarEventosEnTabla(usuario.getNombreUsuario(), activityJTable, tableModel);

        rightPanel.add(activityPanel, rightGBC);

        mainPanel.add(rightPanel, gbc);

        // Add main panel to frame
        add(mainPanel);
    }

    private BufferedImage getCircularImage(BufferedImage image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(circle);
        g2d.drawImage(image, 0, 0, diameter, diameter, null);

        g2d.dispose();
        return output;
    }

    public void cargarEventosEnTabla(String usuario, JTable tabla, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        ArrayList<Evento> eventos = BDs.crearListaEventosPorUsuario(usuario);

        for (Evento evento : eventos) {
            String actividad = evento.getNombre();
            String fecha = evento.getFecha().toString();
            tableModel.addRow(new Object[]{actividad, fecha});
        }

        tabla.revalidate();
        tabla.repaint();
    }


    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear la ventana principal
        SwingUtilities.invokeLater(() -> {
            Usuario user = new Usuario(null, null, null);
            ProfileWindowSelf window = new ProfileWindowSelf(user);
            window.setVisible(true);

        });

    }

}
