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
        panelNorte.setLayout(new BorderLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout());

        Navbar navbar = new Navbar();
        int navbarHeight = (int) (getHeight() * 0.1);
        navbar.setPreferredSize(new Dimension(getWidth(), navbarHeight));
        panelNorte.add(navbar, BorderLayout.NORTH);

        Calendario calendario = new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        panelCentro.add(calendario, BorderLayout.CENTER);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int dynamicHeight = (int) (getHeight() * 0.1);
                navbar.setPreferredSize(new Dimension(getWidth(), dynamicHeight));
                navbar.revalidate();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
