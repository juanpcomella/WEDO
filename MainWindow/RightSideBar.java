package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RightSideBar extends JPanel {

    private List<String> habitosDiarios;
    private JPanel habitosPanel;

    public RightSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        // Panel de Objetivos
        JPanel objetivos = new JPanel();
        objetivos.setLayout(new BoxLayout(objetivos, BoxLayout.Y_AXIS));
        objetivos.setBackground(Color.DARK_GRAY);
        objetivos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel objetivosLabel = new JLabel("Objetivos");
        objetivosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        objetivosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        objetivosLabel.setForeground(Color.WHITE);
        objetivos.add(objetivosLabel);

        JTextArea objetivosText = new JTextArea(30, 10);
        JScrollPane objetivosScrollPane = new JScrollPane(objetivosText);
        objetivosText.setEditable(false);
        objetivosText.setFont(new Font("Arial", Font.PLAIN, 18));
        objetivosText.setLineWrap(true);
        objetivosText.setWrapStyleWord(true);

        objetivosText.setText("1. ");
        objetivosText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    String[] lines = objetivosText.getText().split("\n");
                    int lineNumber = lines.length + 1;
                    objetivosText.append("\n" + lineNumber + ". ");
                }
            }
        });

        objetivos.add(objetivosScrollPane);

        JButton objetivosButton = new JButton("Editar Objetivos");
        objetivosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        objetivosButton.setFont(new Font("Arial", Font.PLAIN, 14));
        boolean[] objBtnEditable = {false};

        objetivosButton.addActionListener(e -> {
            if (objBtnEditable[0]) {
                objetivosText.setEditable(true);
                objetivosButton.setText("Guardar");
            } else {
                objetivosText.setEditable(false);
                objetivosButton.setText("Editar Objetivos");
            }
            objBtnEditable[0] = !objBtnEditable[0];
        });

        objetivos.add(objetivosButton);

        add(objetivos);

        // Panel de Hábitos
        JPanel habitos = new JPanel();
        habitos.setLayout(new BoxLayout(habitos, BoxLayout.Y_AXIS));
        habitos.setBackground(Color.DARK_GRAY);
        habitos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel habitosLabel = new JLabel("Hábitos");
        habitosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        habitosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        habitosLabel.setForeground(Color.WHITE);
        habitos.add(habitosLabel);

        habitosDiarios = cargarHabitosDesdeCSV("BaseDeDatos/objetivos_diarios.csv");
        if (habitosDiarios.size() > 4) {
            habitosDiarios = seleccionarHabitosAleatorios(habitosDiarios);
        }

        habitosPanel = new JPanel();
        habitosPanel.setLayout(new GridLayout(4, 1, 5, 5));
        habitosPanel.setBackground(Color.DARK_GRAY);

        actualizarHabitos();

        habitos.add(habitosPanel);
        add(habitos);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> actualizarHabitos());
            }
        }, 0, 86400000);
    }

    private void actualizarHabitos() {
        habitosPanel.removeAll();

        if (habitosDiarios.size() > 4) {
            habitosDiarios = seleccionarHabitosAleatorios(habitosDiarios);
        }

        for (String habito : habitosDiarios) {
            JButton habitoButton = new JButton(habito);
            habitoButton.setFont(new Font("Arial", Font.PLAIN, 12));
            habitoButton.setBackground(Color.RED);
            habitoButton.setForeground(Color.WHITE);

            habitoButton.addActionListener(e -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        this,
                        "¿Has completado el hábito: " + habito + "?",
                        "Completar Hábito",
                        JOptionPane.YES_NO_OPTION
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    habitoButton.setBackground(Color.GREEN); 
                } else {
                    habitoButton.setBackground(Color.RED);
                }
            });

            habitosPanel.add(habitoButton);
        }
        revalidate();
        repaint();
    }

    private List<String> cargarHabitosDesdeCSV(String archivo) {
        List<String> habitos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Asumiendo que el archivo tiene columnas separadas por comas
                String[] partes = linea.split(",");
                if (partes.length > 0) {
                    // Solo usamos la primera columna
                    habitos.add(partes[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar hábitos desde el archivo: " + archivo,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return habitos;
    }

    private List<String> seleccionarHabitosAleatorios(List<String> habitos) {
        Collections.shuffle(habitos);
        return habitos.subList(0, Math.min(4, habitos.size()));
    }
}
