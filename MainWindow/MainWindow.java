package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainWindow extends JFrame {
    
    public MainWindow() {
        setTitle("WEDO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        
        JPanel panelNorte = new JPanel();
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout());
        
        Calendario calendario = new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        
        panelCentro.add(calendario, BorderLayout.CENTER);
        
        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        
        setVisible(true);
    }
        
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
