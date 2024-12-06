package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

public class RightSideBar extends JPanel {

    private ArrayList<String> habitosTotales; // Lista completa de hábitos desde el CSV
    private ArrayList<String> habitosDiarios; // Hábitos diarios seleccionados
    private JPanel habitosPanel;
    private static final String ARCHIVO_HABITOS = "BaseDeDatos/habitos_diarios_actuales.csv";

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

        habitosTotales = cargarHabitosDesdeCSV("BaseDeDatos/objetivos_diarios.csv");
        habitosDiarios = cargarHabitosDiarios(); // Cargar hábitos si ya existen para el día actual
        habitosPanel = new JPanel();
        habitosPanel.setLayout(new GridLayout(4, 1, 5, 5));
        habitosPanel.setBackground(Color.DARK_GRAY);

        if (habitosDiarios.isEmpty()) {
            generarHabitosDiarios(); // Generar solo si no hay hábitos guardados para hoy
        }

        actualizarHabitosPanel(); // Actualizar la interfaz con los hábitos actuales

        habitos.add(habitosPanel);
        add(habitos);

        programarActualizacionDiaria();
    }

    private void generarHabitosDiarios() {
        if (habitosTotales.size() < 4) {
            habitosDiarios = new ArrayList<>(habitosTotales);
        } else {
            Collections.shuffle(habitosTotales);
            habitosDiarios = new ArrayList<>(habitosTotales.subList(0, 4));
        }
        guardarHabitosDiarios();
    }

    private void actualizarHabitosPanel() {
        habitosPanel.removeAll();

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

    private void programarActualizacionDiaria() {
        Timer timer = new Timer();
        TimerTask tareaDiaria = new TimerTask() {
            @Override
            public void run() {
                generarHabitosDiarios();
                SwingUtilities.invokeLater(() -> actualizarHabitosPanel());
            }
        };

        long tiempoRestanteHoy = calcularMilisegundosHastaMedianoche();
        timer.schedule(tareaDiaria, tiempoRestanteHoy, 86400000);
    }

    private long calcularMilisegundosHastaMedianoche() {
        Calendar ahora = Calendar.getInstance();
        Calendar medianoche = (Calendar) ahora.clone();
        medianoche.set(Calendar.HOUR_OF_DAY, 0);
        medianoche.set(Calendar.MINUTE, 0);
        medianoche.set(Calendar.SECOND, 0);
        medianoche.set(Calendar.MILLISECOND, 0);
        medianoche.add(Calendar.DAY_OF_YEAR, 1);

        return medianoche.getTimeInMillis() - ahora.getTimeInMillis();
    }

    private void guardarHabitosDiarios() {
        try (PrintWriter writer = new PrintWriter(ARCHIVO_HABITOS)) {
            String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            writer.println(fechaHoy); // Guardar la fecha actual
            for (String habito : habitosDiarios) {
                writer.println(habito);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los hábitos diarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<String> cargarHabitosDiarios() {
    	ArrayList<String> habitos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HABITOS))) {
            String fechaGuardada = reader.readLine();
            String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if (fechaGuardada != null && fechaGuardada.equals(fechaHoy)) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    habitos.add(linea.trim());
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe o hay un error, devolvemos una lista vacía
        }
        return habitos;
    }

    private ArrayList<String> cargarHabitosDesdeCSV(String archivo) {
    	ArrayList<String> habitos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length > 0) {
                    habitos.add(partes[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar hábitos desde el archivo: " + archivo,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return habitos;
    }
}
