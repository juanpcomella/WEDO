package MainWindow;

import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Calendario {
    private int year;
    private int month;
    private LocalDate selectedDate;
    private HashMap<LocalDate, ArrayList<Evento>> eventos;
    private JPanel diasPanel;
    private JLabel titleLabel;
    private JComboBox<String> viewSelector;

    public Calendario(int year, int month) {
        this.year = year;
        this.month = month;
        this.selectedDate = LocalDate.of(year, month, 1);
        this.eventos = new HashMap<>();
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
        String view = (String) viewSelector.getSelectedItem();
        if (view.equals("Mes")) {
            mostrarVistaMes();
            diasPanel.revalidate();
            diasPanel.repaint();
        }
    }

    private void mostrarVistaMes() {
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel diaLabel = new JLabel(dia, SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.BOLD, 12));
            diasPanel.add(diaLabel);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
    }
}
