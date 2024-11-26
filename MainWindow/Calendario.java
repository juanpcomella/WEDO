package MainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Calendario extends JPanel {
    private int año;
    private int mes;
    private LocalDate seleccionado;
    private JPanel diasPanel;
    JLabel tituloLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);

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

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        prevButton.addActionListener(e -> actualizarMes(-1));
        nextButton.addActionListener(e -> actualizarMes(1));

        JPanel monthPanel = new JPanel();
        monthPanel.add(prevButton);
        monthPanel.add(nextButton);
        panelArriba.add(monthPanel, BorderLayout.WEST);

        add(panelArriba, BorderLayout.NORTH);

        diasPanel.setLayout(new GridLayout(0, 7));
        add(diasPanel, BorderLayout.CENTER);

        actualizarVista();
    }

    private String getMonthYearString() {
        return String.format("%d - %02d", año, mes);
    }

    private void actualizarVista() {
        diasPanel.removeAll();
        mostrarVistaMes();
        diasPanel.revalidate();
        diasPanel.repaint();
    }
    
    private void actualizarMes(int offset) {
		seleccionado = seleccionado.plusMonths(offset);
        año = seleccionado.getYear();
        mes = seleccionado.getMonthValue();
        tituloLabel.setText(getMonthYearString());
        actualizarVista();

    }

    private void mostrarVistaMes() {
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
            JLabel diaLabel = new JLabel(String.valueOf(dia), SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            diaLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            diaLabel.setOpaque(true);
            
            if (date.equals(hoy)) {
                diaLabel.setBackground(Color.LIGHT_GRAY);
            } else {
                diaLabel.setBackground(Color.WHITE);
            }
            
            diasPanel.add(diaLabel);
            
            diaLabel.addMouseListener(new MouseAdapter() {
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
    }

    private void mostrarDialogo(LocalDate date) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Evento para el " + date.toString());
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
        JComboBox<String> minutos =  new JComboBox<>(minutosArray);
        
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
                if (nombreEvento.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un nombre para el evento.");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Evento guardado para el " + date.toString() + ".");
                    dialog.dispose();
                }
            }
        });

        panel.add(new JLabel());
        panel.add(botonGuardar);

        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    public static void interfaz() {
        JFrame frame = new JFrame("Calendario de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendario::interfaz);
    }
}
