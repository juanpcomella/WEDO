package MainWindow;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class Calendario extends JPanel {

    private int año;
    private int mes;
    private LocalDate seleccionado;
    private JPanel diasPanel;
    private boolean esVistaSemanal = false;
    JLabel tituloLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);
    ArrayList<Evento> listaEventos = new ArrayList<>();

    public Calendario(int año, int mes) {
        this.año = año;
        this.mes = mes;
        this.seleccionado = LocalDate.of(año, mes, 1);
        this.diasPanel = new JPanel();
        Calendar();
    }

    private void Calendar() {
        setLayout(new BorderLayout());
        JPanel panelArriba = new JPanel(new BorderLayout());
        tituloLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panelArriba.add(tituloLabel, BorderLayout.CENTER);

        JButton botonPrevio = new JButton("<");
        JButton botonSiguiente = new JButton(">");

        botonPrevio.addActionListener(e -> {
            if (esVistaSemanal) {
                actualizarSemana(-1);
            } else {
                actualizarMes(-1);
            }
            actualizarTitulo();
        });

        botonSiguiente.addActionListener(e -> {
            if (esVistaSemanal) {
                actualizarSemana(1);
            } else {
                actualizarMes(1);
            }
            actualizarTitulo();
        });

        JPanel mesPanel = new JPanel();
        mesPanel.add(botonPrevio);
        mesPanel.add(botonSiguiente);
        panelArriba.add(mesPanel, BorderLayout.WEST);

        add(panelArriba, BorderLayout.NORTH);

        JButton botonMesActual = new JButton("Hoy");
        panelArriba.add(botonMesActual, BorderLayout.EAST);
        botonMesActual.addActionListener(e -> irMesActual());
        botonMesActual.setBackground(Color.LIGHT_GRAY);

        JButton botonVista = new JButton("Cambiar vista");
        botonVista.addActionListener(e -> toggleView());
        panelArriba.add(botonVista, BorderLayout.SOUTH);

        diasPanel.setLayout(new GridLayout(0, 7));
        add(diasPanel, BorderLayout.CENTER);

        actualizarVista();
    }

    private String getMonthYearString() {
        return String.format("%d - %02d", año, mes);
    }

    private void irMesActual() {
        LocalDate hoy = LocalDate.now();
        this.año = hoy.getYear();
        this.mes = hoy.getMonthValue();
        this.seleccionado = hoy;
        actualizarTitulo();
        actualizarVista();
    }

    void toggleView() {
    	esVistaSemanal = !esVistaSemanal;
        actualizarTitulo();
        actualizarVista();
    }

    private void actualizarTitulo() {
        if (esVistaSemanal) {
            actualizarTituloSemanal();
        } else {
            tituloLabel.setText(getMonthYearString());
        }
    }

    private void actualizarVista() {
        diasPanel.removeAll();
        if (esVistaSemanal) {
            mostrarVistaSemanal();
        } else {
            mostrarVistaMes();
        }
        diasPanel.revalidate();
        diasPanel.repaint();
    }

    private void actualizarMes(int offset) {
        seleccionado = seleccionado.plusMonths(offset);
        año = seleccionado.getYear();
        mes = seleccionado.getMonthValue();
        actualizarVista();
    }

    private void actualizarSemana(int offset) {
        seleccionado = seleccionado.plusWeeks(offset);
        actualizarVista();
        actualizarTitulo();
    }

    private void actualizarTituloSemanal() {
        LocalDate startOfWeek = seleccionado.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        tituloLabel.setText("Semana: " + startOfWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + endOfWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void mostrarVistaMes() {
        diasPanel.removeAll();
        diasPanel.setLayout(new GridLayout(0, 7)); 
        
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel diaLabel = new JLabel(dia, SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.BOLD, 12));
            diasPanel.add(diaLabel);
        }

        YearMonth yearMonth = YearMonth.of(año, mes);
        LocalDate primeroMes = yearMonth.atDay(1);
        int diasMes = yearMonth.lengthOfMonth();
        int primeroSemana = primeroMes.getDayOfWeek().getValue();
        primeroSemana = (primeroSemana == 7) ? 6 : primeroSemana - 1;
        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < primeroSemana; i++) {
            diasPanel.add(new JLabel(""));
        }

        for (int dia = 1; dia <= diasMes; dia++) {
            LocalDate date = LocalDate.of(año, mes, dia);
            JPanel diaPanel = new JPanel(new BorderLayout());
            diaPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JLabel diaLabel = new JLabel(String.valueOf(dia), SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.PLAIN, 20));

            if (date.equals(hoy)) {
                diaPanel.setBackground(Color.LIGHT_GRAY);
            } else {
                diaPanel.setBackground(Color.WHITE);
            }

            diaPanel.add(diaLabel, BorderLayout.NORTH);

            JPanel eventosPanel = new JPanel();
            eventosPanel.setLayout(new GridLayout(5, 0));
            eventosPanel.setOpaque(false);

            for (Evento evento : listaEventos) {
                if (evento.getFecha().equals(date)) {
                    JLabel eventoLabel = new JLabel(evento.getNombre());
                    eventoLabel.setOpaque(true);
                    eventoLabel.setPreferredSize(new Dimension(8, 8));
                    eventoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    eventoLabel.setFont(new Font("Arial", Font.BOLD, 8));

                    if (evento.getCategoria().equals(Categorias.Estudios)) {
                        eventoLabel.setBackground(Color.MAGENTA);
                    } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                        eventoLabel.setBackground(Color.GREEN);
                    } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                        eventoLabel.setBackground(Color.CYAN);
                    } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                        eventoLabel.setBackground(Color.ORANGE);
                    }

                    eventoLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            mostrarEvento(evento, date);
                        }
                    });

                    eventosPanel.add(eventoLabel);
                }
            }

            diaPanel.add(eventosPanel, BorderLayout.CENTER);
            diasPanel.add(diaPanel);

            diaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDialogo(date);
                }
            });
        }

        int sitios = 42;
        int utilizados = primeroSemana + diasMes;
        for (int i = utilizados; i < sitios; i++) {
            diasPanel.add(new JLabel(""));
        }

        diasPanel.revalidate();
        diasPanel.repaint();
    }


    private void mostrarVistaSemanal() {
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        LocalDate startOfWeek = seleccionado.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate hoy = LocalDate.now();
        
        diasPanel.setLayout(new GridLayout(1, 7));

        for (int i = 0; i < 7; i++) {
            LocalDate diaActual = startOfWeek.plusDays(i);

            JPanel diaPanel = new JPanel();
            diaPanel.setLayout(new BorderLayout());
            diaPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            diaPanel.setBackground(Color.WHITE);
            diaPanel.setPreferredSize(new Dimension(150, 200));

            if (diaActual.equals(hoy)) {
                diaPanel.setBackground(Color.LIGHT_GRAY);
            }

            JLabel diaSemanaLabel = new JLabel(diasSemana[i], SwingConstants.CENTER);
            diaSemanaLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel numeroDiaLabel = new JLabel(String.valueOf(diaActual.getDayOfMonth()), SwingConstants.CENTER);
            numeroDiaLabel.setFont(new Font("Arial", Font.PLAIN, 20));

            JPanel headerPanel = new JPanel(new GridLayout(2, 1));
            headerPanel.setBackground(Color.LIGHT_GRAY);
            headerPanel.add(diaSemanaLabel);
            headerPanel.add(numeroDiaLabel);
            diaPanel.add(headerPanel, BorderLayout.NORTH);

            JPanel eventosPanel = new JPanel();
            eventosPanel.setLayout(new BoxLayout(eventosPanel, BoxLayout.Y_AXIS));
            eventosPanel.setOpaque(false);

            for (Evento evento : listaEventos) {
                if (evento.getFecha().equals(diaActual)) {
                    JLabel eventoLabel = new JLabel(evento.getNombre(), SwingConstants.CENTER);
                    eventoLabel.setOpaque(true);
                    eventoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    eventoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    if (evento.getCategoria().equals(Categorias.Estudios)) {
                        eventoLabel.setBackground(Color.MAGENTA);
                    } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                        eventoLabel.setBackground(Color.GREEN);
                    } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                        eventoLabel.setBackground(Color.CYAN);
                    } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                        eventoLabel.setBackground(Color.ORANGE);
                    }

                    eventoLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    eventoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    eventoLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            mostrarEvento(evento, diaActual);
                        }
                    });

                    eventosPanel.add(eventoLabel);
                }
            }

            diaPanel.add(eventosPanel, BorderLayout.CENTER);

            diaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDialogo(diaActual);
                }
            });

            diasPanel.add(diaPanel);
        }
    }

    private void mostrarDialogo(LocalDate date) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Añadir evento para " + date.toString());
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel etiquetaNombre = new JLabel("Nombre del evento:");
        JTextField campoNombre = new JTextField();
        panel.add(etiquetaNombre);
        panel.add(campoNombre);

        panel.add(new JLabel());
        JCheckBox todoElDiaCheckBox = new JCheckBox("Todo el día");
        panel.add(todoElDiaCheckBox);

        JLabel fechaInicio = new JLabel("Hora de inicio:");
        Integer[] horasArray = new Integer[25];
        for (int i = 0; i < 25; i++) {
            horasArray[i] = i;
        }
        String[] minutosArray = {"00", "15", "30", "45"};
        JComboBox<Integer> horas = new JComboBox<>(horasArray);
        JComboBox<String> minutos = new JComboBox<>(minutosArray);

        JPanel panelHoraInicio = new JPanel();
        panelHoraInicio.add(horas);
        panelHoraInicio.add(minutos);

        panel.add(fechaInicio);
        panel.add(panelHoraInicio);

        JLabel fechaFinal = new JLabel("Hora de finalización:");
        JComboBox<Integer> horasFinal = new JComboBox<>(horasArray);
        JComboBox<String> minutosFinal = new JComboBox<>(minutosArray);

        JPanel panelHoraFinal = new JPanel();
        panelHoraFinal.add(horasFinal);
        panelHoraFinal.add(minutosFinal);

        panel.add(fechaFinal);
        panel.add(panelHoraFinal);

        JLabel descripcion = new JLabel("Breve descripción:");
        JTextArea texto = new JTextArea(4, 20);
        texto.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(texto);

        panel.add(descripcion);
        panel.add(scroll);

        JLabel etiquetaCategorias = new JLabel("Categoría:");
        JComboBox<Categorias> categorias = new JComboBox<>(Categorias.values());
        panel.add(etiquetaCategorias);
        panel.add(categorias);

        todoElDiaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccionado = todoElDiaCheckBox.isSelected();
                horas.setEnabled(!seleccionado);
                minutos.setEnabled(!seleccionado);
                horasFinal.setEnabled(!seleccionado);
                minutosFinal.setEnabled(!seleccionado);
            }
        });

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEvento = campoNombre.getText();
                String descripcionEvento = texto.getText();
                Categorias categoriaSeleccionada = (Categorias) categorias.getSelectedItem();
                if (nombreEvento.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un nombre para el evento.");
                } else {
                    Evento evento = new Evento(nombreEvento, descripcionEvento, categoriaSeleccionada, date);
                    listaEventos.add(evento);
                    JOptionPane.showMessageDialog(dialog, "Evento guardado.");
                    actualizarVista();
                    dialog.dispose();
                }
            }
        });

        panel.add(new JLabel());
        panel.add(botonGuardar);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void mostrarEvento(Evento evento, LocalDate date) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Evento para el " + date.toString());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLabel labelNombre = new JLabel("Nombre evento:");
        JLabel labelNombreEvento = new JLabel(evento.getNombre());
        panel.add(labelNombre);
        panel.add(labelNombreEvento);
        JLabel labelDescripcion = new JLabel("Descripción del evento:");
        JLabel labelDescripcionEvento = new JLabel(evento.getDescripcion());
        panel.add(labelDescripcion);
        panel.add(labelDescripcionEvento);
        JLabel labelFecha = new JLabel("Fecha del evento:");
        JLabel labelFechaEvento = new JLabel(evento.getFecha().format(formatter));
        panel.add(labelFecha);
        panel.add(labelFechaEvento);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    public static void interfaz() {
        JFrame frame = new JFrame("Calendario de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendario::interfaz);
    }
}