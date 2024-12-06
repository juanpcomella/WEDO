package MainWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Timer;

public class RightSideBar extends JPanel {

    private ArrayList<String> habitosTotales;
    private ArrayList<String> habitosDiarios; 
    private JPanel objetivosPanel;
    private JPanel habitosPanel;
    private static final String ARCHIVO_HABITOS = "BaseDeDatos/habitos_guardados.txt";
    private ArrayList<Objetivo> listaObjetivos = new ArrayList<>();

    public RightSideBar() {
        setLayout(new GridLayout(3, 1, 5, 5));
        setBackground(new Color(50,70,90));
        setBorder(new LineBorder(new Color(173, 216, 230),10));

        // Panel de Objetivos
        JPanel objetivos = new JPanel();
        objetivos.setLayout(new BoxLayout(objetivos, BoxLayout.Y_AXIS));
//        objetivos.setBackground(Color.WHITE);
        objetivos.setBackground(new Color(50,70,90));
        objetivos.setBorder(new LineBorder(new Color(50,70,90),5));

        JLabel objetivosLabel = new JLabel("Objetivos");
        objetivosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        objetivosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        objetivosLabel.setForeground(Color.WHITE);
        objetivos.add(objetivosLabel);

        objetivosPanel = new JPanel();
        objetivosPanel.setLayout(new BoxLayout(objetivosPanel, BoxLayout.Y_AXIS));
        objetivosPanel.setBackground(Color.WHITE);

        JScrollPane objetivosScrollPane = new JScrollPane(objetivosPanel);
        objetivosScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        objetivosScrollPane.setPreferredSize(new Dimension(300, 200));
        objetivos.add(objetivosScrollPane);

        JButton añadirObjetivoButton = new JButton("Añadir Objetivo");
        añadirObjetivoButton.setForeground(new Color(50,70,90));
        añadirObjetivoButton.setBackground(Color.WHITE);
        añadirObjetivoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        añadirObjetivoButton.setFont(new Font("Arial", Font.PLAIN, 14));

        añadirObjetivoButton.addActionListener(e -> {
            mostrarDialogoAñadirObjetivo();
        });
        objetivos.add(Box.createVerticalStrut(20));
        objetivos.add(añadirObjetivoButton);
        
        JPanel espacio = new JPanel();
        espacio.setPreferredSize(new Dimension(10, 30)); 
        
        add(objetivos);
        JPanel habitos = new JPanel();
        habitos.setBackground(new Color(50,70,90));
        habitos.setLayout(new BoxLayout(habitos, BoxLayout.Y_AXIS));
        habitos.setBackground(new Color(50,70,90));
        habitos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel habitosLabel = new JLabel("Hábitos");
        habitosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        habitosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        habitosLabel.setForeground(Color.WHITE);
        habitos.add(habitosLabel);

        habitosTotales = cargarHabitosDesdeCSV("BaseDeDatos/objetivos_diarios.csv");
        habitosDiarios = cargarHabitosDiarios(); 
        habitosPanel = new JPanel();
        habitosPanel.setLayout(new GridLayout(4, 1, 5, 5));
        habitosPanel.setBackground(new Color(50,70,90));
//        habitosPanel.setBorder(new LineBorder(new Color(50,70,90),5));

        if (habitosDiarios.isEmpty()) {
            generarHabitosDiarios();
        }

        actualizarHabitosPanel();

        habitos.add(habitosPanel);
        add(habitos);

        JPanel vacio = new JPanel();
//        vacio.setBackground(Color.LIGHT_GRAY); 
        vacio.setBackground(new Color(50,70,90));
        add(vacio);
    }

    private void mostrarDialogoAñadirObjetivo() {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        JTextField nombreField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField fechaField = new JTextField();

        dialogPanel.add(new JLabel("Nombre del objetivo:"));
        dialogPanel.add(nombreField);
        dialogPanel.add(new JLabel("Descripción del objetivo:"));
        dialogPanel.add(descripcionField);
        dialogPanel.add(new JLabel("Fecha de cumplimiento (YYYY-MM-DD):"));
        dialogPanel.add(fechaField);

        int option = JOptionPane.showConfirmDialog(this, dialogPanel, "Añadir Objetivo", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String fecha = fechaField.getText().trim();

            if (!nombre.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty()) {
                añadirObjetivo(nombre, fecha);
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void añadirObjetivo(String nombre, String fechaCumplimiento) {
        Objetivo objetivo = new Objetivo(nombre, "Descripción del objetivo", LocalDate.parse(fechaCumplimiento), false);
        objetivo.setCuantoQueda(obtenerCuantoQueda(fechaCumplimiento)); 

        listaObjetivos.add(objetivo);

        ordenarObjetivosPorFecha();

        actualizarPanelObjetivos();
    }

    private void ordenarObjetivosPorFecha() {
        listaObjetivos.sort(Comparator.comparingInt(Objetivo::getCuantoQueda));
    }

    private void actualizarPanelObjetivos() {
        objetivosPanel.removeAll();

        for (Objetivo objetivo : listaObjetivos) {
            JLabel objetivoLabel = new JLabel(objetivo.getNombre());

            objetivoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            objetivoLabel.setBackground(new Color(179, 229, 252));
            objetivoLabel.setForeground(new Color(30, 136, 229)); 
            objetivoLabel.setOpaque(true); 
            objetivoLabel.setHorizontalAlignment(SwingConstants.CENTER); 
            objetivoLabel.setPreferredSize(new Dimension(getWidth(), 40));
            objetivoLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
            objetivoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

            String mensajeConTiempoRestante = "Quedan " + objetivo.getCuantoQueda() + " días";
            objetivoLabel.setText(objetivo.getNombre() + " - " + mensajeConTiempoRestante);

            objetivosPanel.add(objetivoLabel);
        }

        revalidate();
        repaint();
    }

    private int obtenerCuantoQueda(String fechaCumplimiento) {
        LocalDate fechaObjetivo = LocalDate.parse(fechaCumplimiento);
        LocalDate fechaHoy = LocalDate.now();
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaHoy, fechaObjetivo);
    }

    private String calcularTiempoRestante(String fechaCumplimiento) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaObjetivo = formatoFecha.parse(fechaCumplimiento);
            Date fechaHoy = new Date();
            long diferenciaMillis = fechaObjetivo.getTime() - fechaHoy.getTime();
            long diasRestantes = diferenciaMillis / (1000 * 60 * 60 * 24);

            if (diasRestantes < 0) {
                return "¡El objetivo ya pasó!";
            } else if (diasRestantes == 0) {
                return "¡Hoy es el día!";
            } else {
                return diasRestantes + " días restantes";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Fecha inválida";
        }
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
            writer.println(fechaHoy);
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
