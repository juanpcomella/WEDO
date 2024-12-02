package pruebaMainWindow;

import StartingWindows.Usuario;

import javax.swing.*;

import MainWindow.Calendario;
import MainWindow.LeftSideBar;
import MainWindow.Navbar;
import MainWindow.RightSideBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class pruebaMainWindow1 extends JFrame {

    private static final Usuario Usuario = null;
    private Calendario calendario;

    public pruebaMainWindow1(Usuario usuario) {
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

        LeftSideBar leftSideBar = new LeftSideBar();
        leftSideBar.setPreferredSize(new Dimension(0, getHeight()));
        panelOeste.add(leftSideBar, BorderLayout.CENTER);

        Navbar navbar = new Navbar(leftSideBar, usuario);
        int navbarHeight = (int) (getHeight() * 0.1);
        navbar.setPreferredSize(new Dimension(getWidth(), navbarHeight));
        panelNorte.add(navbar, BorderLayout.CENTER);

        calendario = new Calendario(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        panelCentro.add(calendario, BorderLayout.CENTER);


        RightSideBar rightSideBar = new RightSideBar();
        int rsbWidth = (int) (getWidth() * 0.1);
        rightSideBar.setPreferredSize(new Dimension(rsbWidth, getHeight()));
        panelEste.add(rightSideBar, BorderLayout.EAST);

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
        Usuario usuario = new Usuario(null, null, null);
        pruebaMainWindow1 window = new pruebaMainWindow1(usuario);
        window.setVisible(true);
    }
}
