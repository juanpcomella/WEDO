package MainWindow;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Calendario extends JPanel {
    private int year;
    private int month;
    private LocalDate selectedDate;
    private JPanel diasPanel;
    private JLabel titleLabel;
    private JComboBox<String> viewSelector;

    public Calendario(int year, int month) {
        this.year = year;
        this.month = month;
        this.selectedDate = LocalDate.of(year, month, 1);
        this.diasPanel = new JPanel();
        this.viewSelector = new JComboBox<>(new String[] {"Mes"});
        Calendar();
    }

    private void Calendar() {
        setLayout(null);

        titleLabel = new JLabel(getMonthYearString(), SwingConstants.CENTER);
        titleLabel.setBounds(100, 10, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        viewSelector.setBounds(450, 10, 100, 30);
        add(viewSelector);

        diasPanel.setLayout(new java.awt.GridLayout(0, 7));
        diasPanel.setBounds(50, 50, 500, 300);
        add(diasPanel);

        actualizarVista();
    }

    private String getMonthYearString() {
        return String.format("%d - %02d", year, month);
    }

    private void changeMonth(int offset) {
        selectedDate = selectedDate.plusMonths(offset);
        year = selectedDate.getYear();
        month = selectedDate.getMonthValue();
        titleLabel.setText(getMonthYearString());
        actualizarVista();
    }

    private void actualizarVista() {
        diasPanel.removeAll();
        mostrarVistaMes();
        diasPanel.revalidate();
        diasPanel.repaint();
    }

    private void mostrarVistaMes() {
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel diaLabel = new JLabel(dia, SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.BOLD, 12));
            diasPanel.add(diaLabel);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate primeroMes = yearMonth.atDay(1);
        int diasMes = yearMonth.lengthOfMonth();
        int primeroSemana = (primeroMes.getDayOfWeek().getValue() % 7);

        
        for(int i = 0; i < primeroSemana; i++) {
            diasPanel.add(new JLabel(""));
        }
        
        for (int dia = 1; dia <= diasMes; dia++) {
            LocalDate date = LocalDate.of(year, month, dia);
            JLabel diaLabel = new JLabel(String.valueOf(dia), SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            diaLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            diaLabel.setOpaque(true);
            diasPanel.add(diaLabel);
        }
        
        int sitios = 42;
        int utilizados = primeroSemana + diasMes;
        for (int i = utilizados; i < sitios; i++) {
            diasPanel.add(new JLabel(""));
        }
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
