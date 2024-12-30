package ProfileWindow;

import BaseDeDatos.BDs;
import MainWindow.MainWindow;
import MainWindow.Evento;
import StartingWindows.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.io.*;


import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import static BaseDeDatos.BDs.*;

public class ProfileWindowOther extends JFrame {

    public ProfileWindowOther(Usuario usuarioActual, Usuario usuarioBusqueda) {
        setTitle("WEDO - " + usuarioBusqueda.getNombreUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 70, 90));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Left Panel Configuration
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(173, 216, 230)); // Updated color

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
        backButton.setBackground(new Color(0, 0, 0, 0));
        backButton.setFocusPainted(false);
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow mainWindow = new MainWindow(usuarioActual);
                mainWindow.setVisible(true);
                dispose();
            }
        });

        leftPanel.add(backButton, leftGBC);

// Left Panel - Profile Picture
        leftGBC.gridy = 1;
        leftGBC.weightx = 1.0;
        leftGBC.weighty = 0.40;
        leftGBC.fill = GridBagConstraints.BOTH;
        leftGBC.anchor = GridBagConstraints.CENTER;

        JPanel profilePicturePanel = new JPanel(new BorderLayout());
        profilePicturePanel.setBackground(new Color(173, 216, 230)); // Updated color

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            JLabel profilePictureLabel = new JLabel(new ImageIcon(getCircularImage(profileImage, 400)));
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
        usernamePanel.setBackground(new Color(173, 216, 230)); // Updated color
        JLabel usernameLabel = new JLabel("" + usuarioBusqueda.getNombreUsuario(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 48));
        usernamePanel.add(usernameLabel);
        leftPanel.add(usernamePanel, leftGBC);

// Left Panel - Description Row
        leftGBC.gridy = 4;
        leftGBC.weighty = 0.4;

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(new Color(173, 216, 230)); // Updated color
        JLabel descriptionLabel = new JLabel("");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        descriptionPanel.add(descriptionLabel);
        leftPanel.add(descriptionPanel, leftGBC);

// Follow Button
        leftGBC.fill = GridBagConstraints.NONE;
        leftGBC.gridy = 5;
        leftGBC.insets = new Insets(5, 0, 0, 15);
        JButton followButton = new JButton();
        if (yaSigue(usuarioActual, usuarioBusqueda)) {
            followButton.setText("Siguiendo");
        } else {
            followButton.setText("Seguir");
        }

        followButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        followButton.setBackground(new Color(0, 0, 0, 0));
        followButton.setFocusPainted(false);
        followButton.setFocusable(false);
        followButton.setContentAreaFilled(false);

        followButton.addActionListener(e -> {
            if (usuarioActual != null && usuarioBusqueda != null) {
                try {
                    if (followButton.getText().equals("Seguir")) {
                        crearTablaSeguimientos();
                        insertarElementosSeguimientos(usuarioActual, usuarioBusqueda);
                        followButton.setText("Siguiendo");
                        JOptionPane.showMessageDialog(null,
                                "¡Ahora sigues a " + usuarioBusqueda.getNombreUsuario() + "!",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        eliminarSeguimiento(usuarioActual, usuarioBusqueda);
                        followButton.setText("Seguir");
                        JOptionPane.showMessageDialog(null,
                                "Has dejado de seguir a " + usuarioBusqueda.getNombreUsuario() + ".",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al actualizar el estado de seguimiento: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se pueden procesar los usuarios.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        leftPanel.add(followButton, leftGBC);

        mainPanel.add(leftPanel, gbc);


        // Right Panel Configuration
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(50, 70, 90));

        GridBagConstraints rightGBC = new GridBagConstraints();
        rightGBC.insets = new Insets(5, 5, 5, 5);
        rightGBC.fill = GridBagConstraints.BOTH;
        rightGBC.gridx = 0;
        rightGBC.weightx = 1.0;

        // Right Panel Follower Count
        rightGBC.gridy = 0;
        rightGBC.weighty = 0.333;
        JPanel countPanel = new JPanel(new GridLayout(1,2));
        countPanel.setBackground(new Color(173, 216, 230));

        int cuentaSeguidores = BDs.obtenerCuentaSeguidores(usuarioBusqueda.getNombreUsuario());
        int cuentaSeguidos = BDs.obtenerCuentaSiguiendo(usuarioBusqueda.getNombreUsuario());

        JLabel seguidoresLabel = new JLabel(cuentaSeguidores+" seguidores", SwingConstants.CENTER);
        seguidoresLabel.setFont(new Font("Arial", Font.BOLD, 24));
        countPanel.add(seguidoresLabel, rightGBC);

        JLabel seguidosLabel = new JLabel(cuentaSeguidos+" seguidos", SwingConstants.CENTER);
        seguidosLabel.setFont(new Font("Arial", Font.BOLD, 24));
        countPanel.add(seguidosLabel, rightGBC);

        rightPanel.add(countPanel, rightGBC);


        // Right Panel - Daily Streaks Panel
        rightGBC.gridy = 1;
        rightGBC.weighty = 0.333;
        JPanel dailyStreakPanel = new JPanel(new BorderLayout());
        dailyStreakPanel.setBackground(new Color(173, 216, 230));

        JLabel streakTitleLabel = new JLabel("Racha de Objetivos Diarios", SwingConstants.CENTER);
        streakTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        streakTitleLabel.setForeground(new Color(50, 70, 90));
        streakTitleLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        streakTitleLabel.setBackground(new Color(173, 216, 230));
        dailyStreakPanel.add(streakTitleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints streakGBC = new GridBagConstraints();
        streakGBC.insets = new Insets(5, 5, 5, 5);

        streakGBC.gridx = 0;
        streakGBC.gridy = 0;
        streakGBC.anchor = GridBagConstraints.CENTER;
        JLabel flameIcon = new JLabel("");
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
        gridPanel.setBackground(new Color(173, 216, 230));

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

        // Right Panel - Calendar Panel
        rightGBC.gridy = 2;
        rightGBC.weighty = 0.333;
        JPanel activityPanel = new JPanel();
        activityPanel.setBackground(new Color(173, 216, 230));
        activityPanel.setLayout(new BorderLayout());

        // Table Label
        JLabel activityLabel = new JLabel("Próximas actividades de "+ usuarioBusqueda.getNombreUsuario(), SwingConstants.CENTER);
        activityLabel.setForeground(new Color(50, 70, 90));
        activityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        activityLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        activityPanel.add(activityLabel, BorderLayout.NORTH);

        String[] activityTableParams = {"Activity", "Date"};

        DefaultTableModel tableModel = new DefaultTableModel(activityTableParams, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable activityJTable = new JTable(tableModel);
        activityJTable.setFont(new Font("Arial", Font.PLAIN, 16));
        activityJTable.setRowHeight(30);
        activityJTable.setShowGrid(true);
        activityJTable.setGridColor(new Color(200, 200, 200));
        activityJTable.setBackground(Color.WHITE);
        activityJTable.setForeground(Color.BLACK);

        // CENTRADO DEL CHAT
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < activityJTable.getColumnCount(); i++) {
            activityJTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = activityJTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBackground(new Color(58, 92, 181));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        JScrollPane jtableScroll = new JScrollPane(activityJTable);
        jtableScroll.getViewport().setOpaque(false);
        jtableScroll.setOpaque(false);
        jtableScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        activityPanel.add(jtableScroll, BorderLayout.CENTER);

        cargarEventosEnTabla(usuarioBusqueda.getNombreUsuario(), activityJTable, tableModel);

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

        ArrayList<Evento> eventos = BDs.crearListaEventosPublicosPorUsuario(usuario);

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
            ProfileWindowOther window = new ProfileWindowOther(user, user);
            window.setVisible(true);

        });

    }

}
