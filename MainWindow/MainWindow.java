package MainWindow;
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
    
    public MainWindow() {
        setTitle("WEDO");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel();
        JPanel panelSur = new JPanel();
        JPanel panelOeste = new JPanel();
        JPanel panelCentro = new JPanel(); 
        JPanel panelEste = new JPanel();
        
        getContentPane().add(panelNorte, BorderLayout.NORTH);
        getContentPane().add(panelSur, BorderLayout.SOUTH);
        getContentPane().add(panelOeste, BorderLayout.WEST);
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        getContentPane().add(panelEste, BorderLayout.EAST);
        
//        ImageIcon imagen = new ImageIcon("src/tresRayas.jpg");
//        Image imageEscalada = imagen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//        ImageIcon imageRedimensionada = new ImageIcon(imageEscalada);
//        JButton tresRayas = new JButton(imageRedimensionada);
//        tresRayas.setOpaque(false);
//        tresRayas.setContentAreaFilled(false);
//        tresRayas.setBorderPainted(false);
//        panelNorte.add(tresRayas);
//        
//        
//        ImageIcon imageTienda = new ImageIcon("src/tiendaLogo.jpg");
//        Image imagenEscalada = imageTienda.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
//        JButton tienda = new JButton(imagenRedimensionada);
//        tienda.setOpaque(false);
//        tienda.setContentAreaFilled(false);
//        tienda.setBorderPainted(false);
//        panelNorte.add(tienda);
        
//        tienda.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tiendaWindow tiendaWindow = new tiendaWindow();
//                tiendaWindow.setLocationRelativeTo(null);
//				tiendaWindow.setVisible(true);
//
//                dispose();
//            }
//        });
        
          
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem opcion1 = new JMenuItem("Añadir Evento Personal");
        JMenuItem opcion2 = new JMenuItem("Añadir Evento Grupal");
      
        popupMenu.add(opcion1);
        popupMenu.add(opcion2);

        JButton botonMas = new JButton("+");
        botonMas.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        panelOeste.add(botonMas);
        botonMas.setPreferredSize(new Dimension(40, 30)); 
        
        botonMas.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(botonMas, botonMas.getWidth() / 2, botonMas.getHeight() / 2);
            }
        });	
          
        calendar = new JCalendar();
        calendar.setWeekOfYearVisible(false);
        calendar.setMaxDayCharacters(3);
        calendar.setPreferredSize(new Dimension(300,300));
        panelCentro.add(calendar);
        
        JComboBox<Opciones> opciones = new JComboBox<>(Opciones.values());
        calendar.getYearChooser().add(opciones, BorderLayout.WEST);
        opciones.setPreferredSize(new Dimension(100, 25));
        
    }

    public static void main(String[] args) {
    	MainWindow window = new MainWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
