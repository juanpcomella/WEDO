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
        	BDs.crearTablaEventos();

            for (Evento evento : BDs.crearListaEventosPorUsuario(usuario.getNombreUsuario())) {
                if (evento.getFecha().equals(date)) {
                	if (!evento.esTodoElDia()) {
                	    JLabel eventoLabelTodoElDia = new JLabel();
                	    eventoLabelTodoElDia.setOpaque(true);
                	    eventoLabelTodoElDia.setPreferredSize(new Dimension(10, 10));
                	    eventoLabelTodoElDia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                	    eventoLabelTodoElDia.setFont(new Font("Arial", Font.BOLD, 8));
                	    eventoLabelTodoElDia.setBackground(Color.RED);

                	    diaPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 2)); 
                	    diaPanel.add(eventoLabelTodoElDia);

                	    // Listener para manejar clics en el evento
                	    eventoLabelTodoElDia.addMouseListener(new MouseAdapter() {
                	        @Override
                	        public void mouseClicked(MouseEvent e) {
                	            mostrarEvento(evento, date, usuario);
                	        }
                	    });
                                     
						
					}else {
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
	                            mostrarEvento(evento, date, usuario);
	                        }
	                    });

	                    eventosPanel.add(eventoLabel);
					}
                }
            }

            diaPanel.add(eventosPanel, BorderLayout.CENTER);
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
        horasPanelIzquierda.setLayout(new GridLayout(24, 1));
        horasPanelIzquierda.setPreferredSize(new Dimension(60, 600));
        horasPanelIzquierda.setBackground(new Color(173, 216, 230));

        for (int hora = 0; hora < 24; hora++) {
            JPanel horaContainer = new JPanel(new GridBagLayout());
            JLabel horaLabel = new JLabel(String.format("%02d:00", hora));
            horaLabel.setFont(new Font("Arial", Font.BOLD, 12));
            horaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            horaLabel.setBackground(new Color(173, 216, 230));
            horaContainer.add(horaLabel);
            horaContainer.setBackground(new Color(173, 216, 230));
            horasPanelIzquierda.add(horaContainer);
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
            diaPanel.setPreferredSize(new Dimension(150, 600));

            if (diaActual.equals(hoy)) {
                diaPanel.setBackground(Color.LIGHT_GRAY);
            }

            JLabel diaSemanaLabel = new JLabel(diasSemana[i] + " " + diaActual.getDayOfMonth(), SwingConstants.CENTER);
            diaSemanaLabel.setFont(new Font("Arial", Font.BOLD, 16));
            diaPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            diaPanel.add(diaSemanaLabel, BorderLayout.NORTH);

            // Crear un panel personalizado para las horas y las líneas grises
            JPanel horasPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    // Dibujar líneas grises horizontales cada 25 píxeles
                    g.setColor(Color.LIGHT_GRAY);
                    for (int y = 25; y < getHeight(); y += 25) {
                        g.drawLine(0, y, getWidth(), y);
                    }
                }
            };
            horasPanel.setLayout(null); // Usamos 'null' para gestionar la disposición manual de los eventos

            // Agregar los eventos al panel de horas
            Map<Integer, ArrayList<Evento>> eventosPorHora = new HashMap<>();
            for (Evento evento : BDs.crearListaEventosPorUsuario(usuario.getNombreUsuario())) {
                if (evento.getFecha().equals(diaActual)) {
                    int inicioEvento = evento.getHoraInicio().getHour();
                    eventosPorHora.computeIfAbsent(inicioEvento, k -> new ArrayList<>()).add(evento);
                }
            }

            for (Map.Entry<Integer, ArrayList<Evento>> entry : eventosPorHora.entrySet()) {
                int horaInicio = entry.getKey();
                ArrayList<Evento> eventos = entry.getValue();

                int anchoTotal = 150; // Ancho total del panel
                int anchoPorEvento = anchoTotal / eventos.size(); // Ancho de cada evento

                for (int index = 0; index < eventos.size(); index++) {
                    Evento evento = eventos.get(index);
                    int posicionX = index * anchoPorEvento;

                    int duracionEvento = evento.getHoraFin().getHour() - evento.getHoraInicio().getHour();
                    int altoEvento = duracionEvento * 25;

                    JLabel eventoLabel = new JLabel(
                            evento.getNombre() + " " +
                            evento.getHoraInicio() + " - " +
                            evento.getHoraFin()
                    );
                    eventoLabel.setOpaque(true);
                    eventoLabel.setFont(new Font("Arial", Font.BOLD, 10));
                    eventoLabel.setForeground(Color.WHITE);
                    eventoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    eventoLabel.setVerticalAlignment(SwingConstants.NORTH);

                    // Colorear según categoría
                    if (evento.getCategoria().equals(Categorias.Estudios)) {
                        eventoLabel.setBackground(Color.MAGENTA);
                    } else if (evento.getCategoria().equals(Categorias.Trabajo)) {
                        eventoLabel.setBackground(Color.GREEN);
                    } else if (evento.getCategoria().equals(Categorias.Deporte)) {
                        eventoLabel.setBackground(Color.CYAN);
                    } else if (evento.getCategoria().equals(Categorias.Ocio)) {
                        eventoLabel.setBackground(Color.ORANGE);
                    }

                    int posicionY = horaInicio * 25;
                    eventoLabel.setBounds(posicionX, posicionY, anchoPorEvento, altoEvento);
                    horasPanel.add(eventoLabel);
                }
            }

            diaPanel.add(horasPanel, BorderLayout.CENTER);

            diaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDialogo(diaActual, usuario);
                }
            });

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
        
        JRadioButton publicoButton = new JRadioButton("Público");
        JRadioButton privadoButton = new JRadioButton("Privado");

        ButtonGroup group = new ButtonGroup();
        group.add(publicoButton);
        group.add(privadoButton);

        privadoButton.setSelected(true);
        
        JPanel panelPrivacidad = new JPanel();
        panelPrivacidad.add(publicoButton);
        panelPrivacidad.add(privadoButton);
        panel.add(new JLabel("Privacidad:"));
        panel.add(panelPrivacidad);
        

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
                nombreEv = campoNombre.getText();
                descripcionEv = texto.getText();
                categoria = (Categorias) categorias.getSelectedItem();
                int horaInicio = (int) horas.getSelectedItem();
                int minutoInicio = Integer.parseInt((String) minutos.getSelectedItem());
                int horaFin = (int) horasFinal.getSelectedItem();
                int minutoFin = Integer.parseInt((String) minutosFinal.getSelectedItem());

                horaInicioEv = LocalTime.of(horaInicio, minutoInicio);
                horaFinEv  = LocalTime.of(horaFin, minutoFin);

                todoElDiaEv = todoElDiaCheckBox.isSelected();

                if (nombreEv.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un nombre para el evento.");
                } else {
                    BDs.insertarEventos(usuario.getNombreUsuario(), nombreEv, descripcionEv, categoria.toString(), date.toString(), horaInicioEv.toString(), horaFinEv.toString(), todoElDiaEv);
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
        panel.setLayout(new GridLayout(6, 1, 10, 10));

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
        
        JLabel labelHoraInicio = new JLabel("Hora de Inicio");
        LocalTime horaInicioLocalTime = evento.getHoraInicio();
        DateTimeFormatter formatterFechaInicio = DateTimeFormatter.ofPattern("HH:mm");
        String horaInicioFormatted = horaInicioLocalTime.format(formatterFechaInicio);
        JLabel horaInicio = new JLabel(horaInicioFormatted);
        panel.add(labelHoraInicio);
        panel.add(horaInicio);
        
        JLabel labelHoraFinal = new JLabel("Hora de Fin");
        LocalTime horaFinalLocalTime = evento.getHoraFin();
        DateTimeFormatter formatterFechaFinal = DateTimeFormatter.ofPattern("HH:mm");
        String horaFinalFormatted = horaFinalLocalTime.format(formatterFechaFinal);
        JLabel horaFinal = new JLabel(horaFinalFormatted);
        panel.add(labelHoraFinal);
        panel.add(horaFinal);

        JButton botonEliminar = new JButton("Eliminar evento");
        botonEliminar.setBackground(Color.RED); 
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false); 
        botonEliminar.setFont(new Font("Arial", Font.BOLD, 12));

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	    nombreEv = evento.getNombre();
            	    descripcionEv = evento.getDescripcion();
            	    categoria = evento.getCategoria();
            	    horaInicioEv = evento.getHoraInicio();
            	    horaFinEv = evento.getHoraFin();
            	    todoElDiaEv = evento.esTodoElDia();

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