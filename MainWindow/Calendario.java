package MainWindow;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import BaseDeDatos.BDs;
import StartingWindows.Usuario;

public class Calendario extends JPanel {

    private int año;
    private int mes;
    private LocalDate seleccionado;
    private JPanel diasPanel;
    private boolean esVistaSemanal = false;
    JLabel tituloLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);

    public Calendario(int año, int mes, Usuario usuario) {
        this.año = año;
        this.mes = mes;
        this.seleccionado = LocalDate.of(año, mes, 1);
        this.diasPanel = new JPanel();
        Calendar(usuario);
    }
    
    String nombreEv;
    String descripcionEv;
    Categorias categoria;
    LocalTime horaInicioEv;
    LocalTime horaFinEv;
    boolean todoElDiaEv;
    boolean esPrivado;

    private void Calendar(Usuario usuario) {
        setLayout(new BorderLayout());
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setBackground(new Color(173, 216, 230));
        tituloLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        tituloLabel.setBackground(new Color(173, 216, 230));
        panelArriba.add(tituloLabel, BorderLayout.CENTER);

        JButton botonPrevio = new JButton("<");
        JButton botonSiguiente = new JButton(">");
        botonSiguiente.setBackground(new Color(50,70,90));
        botonSiguiente.setForeground(Color.WHITE);
        botonPrevio.setBackground(new Color(50,70,90));
        botonPrevio.setForeground(Color.WHITE);

        botonPrevio.addActionListener(e -> {
            if (esVistaSemanal) {
                actualizarSemana(-1, usuario);
            } else {
                actualizarMes(-1, usuario);
            }
            actualizarTitulo();
        });

        botonSiguiente.addActionListener(e -> {
            if (esVistaSemanal) {
                actualizarSemana(1, usuario);
            } else {
                actualizarMes(1, usuario);
            }
            actualizarTitulo();
        });

        JPanel mesPanel = new JPanel();
        mesPanel.setBackground(new Color(173, 216, 230));
        mesPanel.add(botonPrevio);
        mesPanel.add(botonSiguiente);
        panelArriba.add(mesPanel, BorderLayout.WEST);

        add(panelArriba, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBotones.setBackground(new Color(173, 216, 230));
        JButton botonVista = new JButton("Cambiar vista");
        botonVista.setBackground(new Color(50,70,90));
        botonVista.setForeground(Color.WHITE);
        botonVista.setPreferredSize(new Dimension(120, 30));
        botonVista.addActionListener(e -> toggleView(usuario));
        panelBotones.add(botonVista);

        JButton botonMesActual = new JButton("Hoy");
        botonMesActual.setBackground(new Color(50,70,90));
        botonMesActual.setForeground(Color.WHITE);
        botonMesActual.addActionListener(e -> irMesActual(usuario));
        panelBotones.add(botonMesActual);

        panelArriba.add(panelBotones, BorderLayout.EAST);

        diasPanel.setLayout(new GridLayout(0, 7));
        add(diasPanel, BorderLayout.CENTER);
        diasPanel.setBackground(new Color(173, 216, 230));

        actualizarVista(usuario);
    }

    private String getMonthYearString() {
        return String.format("%d - %02d", año, mes);
    }

    private void irMesActual(Usuario usuario) {
        LocalDate hoy = LocalDate.now();
        this.año = hoy.getYear();
        this.mes = hoy.getMonthValue();
        this.seleccionado = hoy;
        actualizarTitulo();
        actualizarVista(usuario);
    }

    void toggleView(Usuario usuario) {
        esVistaSemanal = !esVistaSemanal;
        actualizarTitulo();
        actualizarVista(usuario);
    }

    private void actualizarTitulo() {
        if (esVistaSemanal) {
            actualizarTituloSemanal();
        } else {
            tituloLabel.setText(getMonthYearString());
        }
    }

    private void actualizarVista(Usuario usuario) {
        diasPanel.removeAll();
        if (esVistaSemanal) {
            mostrarVistaSemanal(usuario);
        } else {
            mostrarVistaMes(usuario);
        }
        diasPanel.revalidate();
        diasPanel.repaint();
    }

    private void actualizarMes(int offset, Usuario usuario) {
        seleccionado = seleccionado.plusMonths(offset);
        año = seleccionado.getYear();
        mes = seleccionado.getMonthValue();
        actualizarVista(usuario);
    }

    private void actualizarSemana(int offset, Usuario usuario) {
        seleccionado = seleccionado.plusWeeks(offset);
        actualizarVista(usuario);
        actualizarTitulo();
    }

    private void actualizarTituloSemanal() {
        LocalDate startOfWeek = seleccionado.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        tituloLabel.setText("Semana: " + startOfWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + endOfWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void mostrarVistaMes(Usuario usuario) {
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
            diaLabel.setFont(new Font("Arial", Font.BOLD, 14));
            diaLabel.setOpaque(true);
            if (date.equals(hoy)) {
            	diaLabel.setBackground(Color.LIGHT_GRAY);
            }else {
				diaLabel.setBackground(Color.WHITE);
			}
            
            diaPanel.add(diaLabel, BorderLayout.NORTH); 
            JPanel eventosContainer = new JPanel(new BorderLayout());
            eventosContainer.setOpaque(false);

            JPanel todoElDiaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            todoElDiaPanel.setOpaque(false);

            JPanel eventosPanel = new JPanel(new GridLayout(5, 0));
            eventosPanel.setOpaque(false);

            BDs.crearTablaEventos();
            for (Evento evento : BDs.crearListaEventosPorUsuario(usuario.getNombreUsuario())) {
                if (evento.getFecha().equals(date)) {
                    if (evento.esEventoTodoElDia()) {
                        JLabel eventoLabelTodoElDia = new JLabel();
                        eventoLabelTodoElDia.setOpaque(true);
                        eventoLabelTodoElDia.setPreferredSize(new Dimension(12, 12));
                        eventoLabelTodoElDia.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                        eventoLabelTodoElDia.setFont(new Font("Arial", Font.BOLD, 8));
                        eventoLabelTodoElDia.setBackground(new Color(233,145,183));

                        todoElDiaPanel.add(eventoLabelTodoElDia);

                        eventoLabelTodoElDia.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                mostrarEventoTodoElDia(evento, date, usuario);
                            }
                        });
                    } else {
                        JLabel eventoLabel = new JLabel(evento.getNombre());
                        eventoLabel.setOpaque(true);
                        eventoLabel.setPreferredSize(new Dimension(8, 8));
                        eventoLabel.setBorder(BorderFactory.createLineBorder(Color.white));
                        eventoLabel.setFont(new Font("Arial", Font.BOLD, 8));
                        eventoLabel.setForeground(Color.BLACK);

                        if (evento.getCategoria().equals(Categorias.Estudios)) {
                            eventoLabel.setBackground(new Color(255,245,150));
                        } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                            eventoLabel.setBackground(new Color(255,194,145));
                        } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                            eventoLabel.setBackground(new Color(200,160,210));
                        } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                            eventoLabel.setBackground(new Color(190, 230,180));
                        }

                        eventoLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                mostrarEvento(evento, date, usuario);
                            }
                        });

                        eventosPanel.add(eventoLabel);
                    }
                }
            }

            eventosContainer.add(todoElDiaPanel, BorderLayout.NORTH); 
            eventosContainer.add(eventosPanel, BorderLayout.CENTER); 

            diaPanel.add(eventosContainer, BorderLayout.CENTER);
            diasPanel.add(diaPanel);

            diaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDialogo(date, usuario);
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

    private void mostrarVistaSemanal(Usuario usuario) {
        diasPanel.removeAll();

        JPanel horasPanelIzquierda = new JPanel();
        horasPanelIzquierda.setLayout(new GridLayout(25, 1));
        horasPanelIzquierda.setPreferredSize(new Dimension(60, 750));
        horasPanelIzquierda.setBackground(new Color(173, 216, 230));

        for (int hora = 0; hora < 25; hora++) {
            JLabel horaLabel = new JLabel(String.format("%02d:00", hora), SwingConstants.CENTER);
            horaLabel.setFont(new Font("Arial", Font.BOLD, 12));
            horaLabel.setOpaque(true);
            horaLabel.setBackground(new Color(173, 216, 230));
            horasPanelIzquierda.add(horaLabel);
        }

        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        LocalDate startOfWeek = seleccionado.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate hoy = LocalDate.now();

        diasPanel.setLayout(new GridLayout(1, 8)); 
        diasPanel.setBackground(new Color(173, 216, 230));
        diasPanel.add(horasPanelIzquierda);

        for (int i = 0; i < 7; i++) {
            LocalDate diaActual = startOfWeek.plusDays(i);

            JPanel diaPanel = new JPanel();
            diaPanel.setLayout(new BorderLayout());
            diaPanel.setBackground(Color.WHITE);
            diaPanel.setPreferredSize(new Dimension(150, 750));

            if (diaActual.equals(hoy)) {
                diaPanel.setBackground(Color.LIGHT_GRAY);
            }

            JLabel diaSemanaLabel = new JLabel(diasSemana[i] + " " + diaActual.getDayOfMonth(), SwingConstants.CENTER);
            diaSemanaLabel.setFont(new Font("Arial", Font.BOLD, 16));
            diaPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            diaPanel.add(diaSemanaLabel, BorderLayout.NORTH);

            JPanel horasPanel = new JPanel(null) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.LIGHT_GRAY);

                    for (int y = 30; y <= getHeight(); y += 30) {
                        g.drawLine(0, y, getWidth(), y);
                    }
                }
            };
            horasPanel.setPreferredSize(new Dimension(150, 750)); 

            horasPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDialogo(diaActual, usuario);
                }
            });

            Map<Integer, ArrayList<Evento>> eventosPorHora = new HashMap<>();
            for (Evento evento : BDs.crearListaEventosPorUsuario(usuario.getNombreUsuario())) {
                if (evento.getFecha().equals(diaActual)) {
                    int inicioEvento = evento.getHoraInicio().getHour();
                    eventosPorHora.computeIfAbsent(inicioEvento, k -> new ArrayList<>()).add(evento);
                }
            }

            JPanel panelEventosTodoElDia = new JPanel();
            panelEventosTodoElDia.setLayout(new BoxLayout(panelEventosTodoElDia, BoxLayout.Y_AXIS));

            for (Map.Entry<Integer, ArrayList<Evento>> entry : eventosPorHora.entrySet()) {
                int horaInicio = entry.getKey();
                ArrayList<Evento> eventos = entry.getValue();

                int anchoTotal = 150;
                int anchoPorEvento = anchoTotal / eventos.size();

                for (int index = 0; index < eventos.size(); index++) {
                    Evento evento = eventos.get(index);
                    int posicionX = index * anchoPorEvento;

                    int duracionEnMinutos = evento.getHoraFin().getHour() * 60 + evento.getHoraFin().getMinute()
                                            - (evento.getHoraInicio().getHour() * 60 + evento.getHoraInicio().getMinute());
                    if (evento.esEventoTodoElDia()) {
                        JPanel eventoLabelTodoElDia = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setColor(new Color(233,145,183));
                                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                                g2d.setColor(Color.WHITE);
                                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                            }
                        };
                        eventoLabelTodoElDia.setOpaque(false);
                        eventoLabelTodoElDia.setPreferredSize(new Dimension(10, 30)); 


                        panelEventosTodoElDia.add(eventoLabelTodoElDia);

                        eventoLabelTodoElDia.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                mostrarEventoTodoElDia(evento, diaActual, usuario);
                            }
                        });
                    } else {
                        if (duracionEnMinutos >= 60) {
                            int altoEvento = (duracionEnMinutos * 30) / 60; 
                            int posicionY = (evento.getHoraInicio().getHour() * 60 + evento.getHoraInicio().getMinute()) * 30 / 60;

                            JLabel eventoLabel = new JLabel(
                                    "<html>" + evento.getNombre() + "<br>" +
                                    evento.getHoraInicio() + " - " + evento.getHoraFin() + "</html>",
                                    SwingConstants.CENTER);
                            eventoLabel.setOpaque(true);
                            eventoLabel.setFont(new Font("Arial", Font.BOLD, 10));
                            eventoLabel.setForeground(Color.BLACK);
                            eventoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                            if (evento.getCategoria().equals(Categorias.Estudios)) {
                                eventoLabel.setBackground(new Color(255,245,150));
                            } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                                eventoLabel.setBackground(new Color(255,194,145));
                            } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                                eventoLabel.setBackground(new Color(200,160,210));
                            } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                                eventoLabel.setBackground(new Color(190, 230,180));//VERDE
                            }

                            eventoLabel.setBounds(posicionX, posicionY, anchoPorEvento, altoEvento);
                            horasPanel.add(eventoLabel);
                            
                            eventoLabel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    mostrarEvento(evento, diaActual, usuario);
                                }
                            });
                        }else {
                        	int altoEvento = (duracionEnMinutos * 30) / 60; 
                            int posicionY = (evento.getHoraInicio().getHour() * 60 + evento.getHoraInicio().getMinute()) * 30 / 60;

                            JLabel eventoLabel = new JLabel();
                            eventoLabel.setOpaque(true);
                            eventoLabel.setFont(new Font("Arial", Font.BOLD, 10));
                            eventoLabel.setForeground(Color.WHITE);
                            eventoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            
                            if (evento.getCategoria().equals(Categorias.Estudios)) {
                                eventoLabel.setBackground(new Color(255,245,150));
                            } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                                eventoLabel.setBackground(new Color(255,194,145));
                            } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                                eventoLabel.setBackground(new Color(200,160,210));
                            } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                                eventoLabel.setBackground(new Color(190, 230,180));
                            }

                            eventoLabel.setBounds(posicionX, posicionY, anchoPorEvento, altoEvento);
                            horasPanel.add(eventoLabel);
                            
                            eventoLabel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    mostrarEvento(evento, diaActual, usuario);
                                }
                            });
						}
                    }
                }
            }

            diaPanel.add(panelEventosTodoElDia, BorderLayout.WEST);

            diaPanel.add(horasPanel, BorderLayout.CENTER);
            diasPanel.add(diaPanel);
        }
        diasPanel.revalidate();
        diasPanel.repaint();
    }

    private void mostrarDialogo(LocalDate date, Usuario usuario) { 
        JDialog dialog = new JDialog();
        dialog.setTitle("Añadir evento para " + date.toString());
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230));
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel etiquetaNombre = new JLabel("Nombre del evento:");
        etiquetaNombre.setOpaque(true);
        etiquetaNombre.setBackground(new Color(173, 216, 230));
        JTextField campoNombre = new JTextField();
        panel.add(etiquetaNombre);
        panel.add(campoNombre);

        panel.add(new JLabel());
        JCheckBox todoElDiaCheckBox = new JCheckBox("Todo el día");
        todoElDiaCheckBox.setBackground(new Color(173, 216, 230));
        panel.add(todoElDiaCheckBox);

        JLabel fechaInicio = new JLabel("Hora de inicio:");
        fechaInicio.setOpaque(true);
        fechaInicio.setBackground(new Color(173, 216, 230));
        Integer[] horasArray = new Integer[25];
        for (int i = 0; i < 25; i++) {
            horasArray[i] = i;
        }
        String[] minutosArray = {"00", "15", "30", "45"};
        JComboBox<Integer> horas = new JComboBox<>(horasArray);
        JComboBox<String> minutos = new JComboBox<>(minutosArray);

        JPanel panelHoraInicio = new JPanel();
        panelHoraInicio.setBackground(new Color(173, 216, 230));
        panelHoraInicio.add(horas);
        panelHoraInicio.add(minutos);

        panel.add(fechaInicio);
        panel.add(panelHoraInicio);

        JLabel fechaFinal = new JLabel("Hora de finalización:");
        fechaFinal.setOpaque(true);
        fechaFinal.setBackground(new Color(173, 216, 230));
        JComboBox<Integer> horasFinal = new JComboBox<>(horasArray);
        JComboBox<String> minutosFinal = new JComboBox<>(minutosArray);

        JPanel panelHoraFinal = new JPanel();
        panelHoraFinal.setBackground(new Color(173, 216, 230));
        panelHoraFinal.add(horasFinal);
        panelHoraFinal.add(minutosFinal);

        panel.add(fechaFinal);
        panel.add(panelHoraFinal);

        JLabel descripcion = new JLabel("Breve descripción:");
        descripcion.setOpaque(true);
        descripcion.setBackground(new Color(173, 216, 230));
        JTextArea texto = new JTextArea(4, 20);
        texto.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(texto);

        panel.add(descripcion);
        panel.add(scroll);

        JRadioButton publicoButton = new JRadioButton("Público");
        JRadioButton privadoButton = new JRadioButton("Privado");

        publicoButton.setBackground(new Color(173, 216, 230));
        privadoButton.setBackground(new Color(173, 216, 230));

        ButtonGroup group = new ButtonGroup();
        group.add(publicoButton);
        group.add(privadoButton);

        privadoButton.setSelected(true);

        JPanel panelPrivacidad = new JPanel();
        panelPrivacidad.setBackground(new Color(173, 216, 230));
        panelPrivacidad.add(publicoButton);
        panelPrivacidad.add(privadoButton);

        JLabel privacidadLabel = new JLabel("Privacidad:");
        privacidadLabel.setOpaque(true);
        privacidadLabel.setBackground(new Color(173, 216, 230));
        panel.add(privacidadLabel);
        panel.add(panelPrivacidad);

        JLabel etiquetaCategorias = new JLabel("Categoría:");
        etiquetaCategorias.setOpaque(true);
        etiquetaCategorias.setBackground(new Color(173, 216, 230));
        JComboBox<Categorias> categorias = new JComboBox<>(Categorias.values());
        panel.add(etiquetaCategorias);
        panel.add(categorias);

        todoElDiaCheckBox.addActionListener(e -> {
            boolean seleccionado = todoElDiaCheckBox.isSelected();
            horas.setEnabled(!seleccionado);
            minutos.setEnabled(!seleccionado);
            horasFinal.setEnabled(!seleccionado);
            minutosFinal.setEnabled(!seleccionado);
        });

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombreEv = campoNombre.getText();
                descripcionEv = texto.getText();
                categoria = (Categorias) categorias.getSelectedItem();

                if (todoElDiaCheckBox.isSelected()) {
                    todoElDiaEv = true;
                    horaInicioEv = LocalTime.of(0, 0); 
                    horaFinEv = LocalTime.of(23, 59);
                } else {
                    int horaInicio = (int) horas.getSelectedItem();
                    int minutoInicio = Integer.parseInt((String) minutos.getSelectedItem());
                    int horaFin = (int) horasFinal.getSelectedItem();
                    int minutoFin = Integer.parseInt((String) minutosFinal.getSelectedItem());

                    horaInicioEv = LocalTime.of(horaInicio, minutoInicio);
                    horaFinEv = LocalTime.of(horaFin, minutoFin);
                    todoElDiaEv = false;
                }
                boolean esPublico = false;
                if(publicoButton.isSelected()) {
                	esPublico = true;
                }else {
                	esPublico = false;
                }

                if (nombreEv.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un nombre para el evento.");
                } else if (!todoElDiaEv && (horaFinEv.isBefore(horaInicioEv) || horaFinEv.equals(horaInicioEv))) {
                    JOptionPane.showMessageDialog(dialog, "La hora de fin debe ser posterior a la hora de inicio.");
                } else {
                    BDs.insertarEventos(
                        usuario.getNombreUsuario(),
                        nombreEv,
                        descripcionEv,
                        categoria.toString(),
                        date.toString(),
                        horaInicioEv.toString(),
                        horaFinEv.toString(),
                        todoElDiaEv,
                        esPublico
                    );
                    JOptionPane.showMessageDialog(dialog, "Evento guardado.");
                    actualizarVista(usuario);
                    dialog.dispose();
                }

            }
        });

        panel.add(new JLabel());
        panel.add(botonGuardar);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }


    private void mostrarEvento(Evento evento, LocalDate date, Usuario usuario) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Evento para el " + date.toString());
        dialog.setSize(500, 400); 
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel();
        if (evento.getCategoria().equals(Categorias.Estudios)) {
            panel.setBackground(new Color(255,245,150));
        } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
        	panel.setBackground(new Color(255,194,145));
        } else if (evento.getCategoria().equals(Categorias.Deporte)) {
        	panel.setBackground(new Color(200,160,210));
        } else if (evento.getCategoria().equals(Categorias.Ocio)) {
        	panel.setBackground(new Color(190, 230,180));
        }
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLabel labelNombre = new JLabel("               Nombre evento:");
        JLabel labelNombreEvento = new JLabel(evento.getNombre());
        panel.add(labelNombre);
        panel.add(labelNombreEvento);

        JLabel labelDescripcion = new JLabel("               Descripción:");
        JLabel labelDescripcionEvento = new JLabel(evento.getDescripcion());
        panel.add(labelDescripcion);
        panel.add(labelDescripcionEvento);

        JLabel labelFecha = new JLabel("               Fecha del evento:");
        JLabel labelFechaEvento = new JLabel(evento.getFecha().format(formatter));
        panel.add(labelFecha);
        panel.add(labelFechaEvento);
        
        JLabel labelHoraInicio = new JLabel("               Hora de Inicio");
        LocalTime horaInicioLocalTime = evento.getHoraInicio();
        DateTimeFormatter formatterFechaInicio = DateTimeFormatter.ofPattern("HH:mm");
        String horaInicioFormatted = horaInicioLocalTime.format(formatterFechaInicio);
        JLabel horaInicio = new JLabel(horaInicioFormatted);
        panel.add(labelHoraInicio);
        panel.add(horaInicio);
        
        JLabel labelHoraFinal = new JLabel("               Hora de Fin");
        LocalTime horaFinalLocalTime = evento.getHoraFin();
        DateTimeFormatter formatterFechaFinal = DateTimeFormatter.ofPattern("HH:mm");
        String horaFinalFormatted = horaFinalLocalTime.format(formatterFechaFinal);
        JLabel horaFinal = new JLabel(horaFinalFormatted);
        panel.add(labelHoraFinal);
        panel.add(horaFinal);

        JButton botonEliminar = new JButton("Eliminar evento");
        botonEliminar.setBackground(new Color(200,80,80)); 
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false); 
        botonEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        botonEliminar.setBorder(BorderFactory.createLineBorder(new Color(200,80,80), 3));

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	    nombreEv = evento.getNombre();
            	    descripcionEv = evento.getDescripcion();
            	    categoria = evento.getCategoria();
            	    horaInicioEv = evento.getHoraInicio();
            	    horaFinEv = evento.getHoraFin();
            	    todoElDiaEv = evento.esEventoTodoElDia();

                int confirmacion = JOptionPane.showConfirmDialog(dialog, "¿Estás seguro de eliminar este evento?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                	
                	//aqui va lo de borrar el evento de la bbdd
                    BDs.eliminarEventos(usuario.getNombreUsuario(), nombreEv, descripcionEv, categoria.toString(), date.toString(), horaInicioEv.toString(), horaFinEv.toString(), todoElDiaEv);
                    
                	JOptionPane.showMessageDialog(dialog, "Evento eliminado.");
                    actualizarVista(usuario);
                    dialog.dispose(); 
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.X_AXIS)); 
        panelBoton.add(Box.createHorizontalGlue()); 
        if (evento.getCategoria().equals(Categorias.Estudios)) {
        	panelBoton.setBackground(new Color(255,245,150));
        } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
        	panelBoton.setBackground(new Color(255,194,145));
        } else if (evento.getCategoria().equals(Categorias.Deporte)) {
        	panelBoton.setBackground(new Color(200,160,210));
        } else if (evento.getCategoria().equals(Categorias.Ocio)) {
        	panelBoton.setBackground(new Color(190, 230,180));
        }
        panelBoton.add(botonEliminar);
        panelBoton.add(Box.createHorizontalGlue()); 
 
        panel.add(panelBoton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }
    
    private void mostrarEventoTodoElDia(Evento evento, LocalDate date, Usuario usuario) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Evento para el " + date.toString());
        dialog.setSize(350, 250); 
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(233,145,183));
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLabel labelNombre = new JLabel("               Nombre evento:");
        JLabel labelNombreEvento = new JLabel(evento.getNombre());
        panel.add(labelNombre);
        panel.add(labelNombreEvento);

        JLabel labelDescripcion = new JLabel("               Descripción:");
        JLabel labelDescripcionEvento = new JLabel(evento.getDescripcion());
        panel.add(labelDescripcion);
        panel.add(labelDescripcionEvento);

        JLabel labelFecha = new JLabel("               Fecha del evento:");
        JLabel labelFechaEvento = new JLabel(evento.getFecha().format(formatter));
        panel.add(labelFecha);
        panel.add(labelFechaEvento);
        
        JButton botonEliminar = new JButton("Eliminar evento");
        botonEliminar.setBackground(new Color(200,80,80)); 
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false); 
        botonEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        botonEliminar.setBorder(BorderFactory.createLineBorder(new Color(200,80,80),3));

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	    nombreEv = evento.getNombre();
            	    descripcionEv = evento.getDescripcion();
            	    categoria = evento.getCategoria();
            	    horaInicioEv = evento.getHoraInicio();
            	    horaFinEv = evento.getHoraFin();
            	    todoElDiaEv = evento.esEventoTodoElDia();

                int confirmacion = JOptionPane.showConfirmDialog(dialog, "¿Estás seguro de eliminar este evento?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                	
                	//aqui va lo de borrar el evento de la bbdd
                    BDs.eliminarEventos(usuario.getNombreUsuario(), nombreEv, descripcionEv, categoria.toString(), date.toString(), horaInicioEv.toString(), horaFinEv.toString(), todoElDiaEv);
                    
                	JOptionPane.showMessageDialog(dialog, "Evento eliminado.");
                    actualizarVista(usuario);
                    dialog.dispose(); 
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.X_AXIS)); 
        panelBoton.add(Box.createHorizontalGlue()); 
        panelBoton.setBackground(new Color(233,145,183));
        panelBoton.add(botonEliminar);
        panelBoton.add(Box.createHorizontalGlue()); 
 
        panel.add(panelBoton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    public static void interfaz(Usuario usuario) {
        JFrame frame = new JFrame("Calendario de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), usuario));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> Calendario.interfaz(null));
//    }
}