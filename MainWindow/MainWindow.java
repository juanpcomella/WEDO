package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("WEDO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BorderLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout());

        JPanel panelEste = new JPanel();
        panelEste.setLayout(new BorderLayout());

        JPanel panelOeste = new JPanel();
        panelOeste.setLayout(new BorderLayout());

        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BorderLayout());

        Navbar navbar = new Navbar();
        int navbarHeight = (int) (getHeight() * 0.1);
        navbar.setPreferredSize(new Dimension(getWidth(), navbarHeight));
        panelNorte.add(navbar, BorderLayout.CENTER);

        Calendario calendario = new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        panelCentro.add(calendario, BorderLayout.CENTER);

        LeftSideBar leftSideBar = new LeftSideBar();
        panelOeste.add(leftSideBar, BorderLayout.CENTER);

        RightSideBar rightSideBar = new RightSideBar();
        int rightSideBarWidth = (int) (getWidth() * 0.1);
        rightSideBar.setPreferredSize(new Dimension(rightSideBarWidth, getHeight()));
        panelEste.add(rightSideBar, BorderLayout.EAST);

        Footer footer = new Footer();
        panelSur.add(footer, BorderLayout.CENTER);


        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(panelEste, BorderLayout.EAST);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelOeste, BorderLayout.WEST);
        panel.add(panelSur, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int dynamicHeight = (int) (getHeight() * 0.1);
                navbar.setPreferredSize(new Dimension(getWidth(), dynamicHeight));
                navbar.revalidate();

                int dynamicWidth = (int) (getWidth() * 0.1);
                rightSideBar.setPreferredSize(new Dimension(dynamicWidth, (getHeight())));
                rightSideBar.revalidate();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
