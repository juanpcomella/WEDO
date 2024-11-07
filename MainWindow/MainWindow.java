package MainWindow;
import javax.swing.*;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JDateChooser dateChooser;
	
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
        
        opcion1.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                crearVentanaEvento();
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
    
    private void crearVentanaEvento() {
		
		JDialog dialog = new JDialog(this, "Añadir Evento Personal", true);
		dialog.setSize(400, 300);
		dialog.setLayout(new BorderLayout());
		dialog.setLocationRelativeTo(this);

		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridLayout(4, 2, 10, 10));

		JLabel etiquetaNombre = new JLabel("Nombre del evento:");
		JTextField campoNombre = new JTextField();

		JLabel etiquetaFecha = new JLabel("Fecha:");
		dateChooser = new JDateChooser();
		
		JLabel etiquetaInvitar = new JLabel("Invitar a");
		JTextField campoInvitar = new JTextField();
		//Aqui faltaria añadir el codigo para que comprobase que el usuario al que quiere invitar este en nuestra BBDD
		
		JLabel etiquetaCategorias = new JLabel("Categoria");
		JComboBox<Categorias> categorias = new JComboBox<>(Categorias.values());

		panelContenido.add(etiquetaNombre);
		panelContenido.add(campoNombre);
		panelContenido.add(etiquetaFecha);
		panelContenido.add(dateChooser);
		panelContenido.add(etiquetaInvitar);
		panelContenido.add(campoInvitar);
		panelContenido.add(etiquetaCategorias);
		panelContenido.add(categorias);

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreEvento = campoNombre.getText();
				Date fechaEvento = dateChooser.getDate();
				if (nombreEvento.isEmpty() || fechaEvento == null) {
					JOptionPane.showMessageDialog(dialog, "Evento vacio, te falta por añadir el nombre del evento o la fecha");
					dialog.dispose();
				} else {
					JOptionPane.showMessageDialog(dialog, "Evento '" + nombreEvento + "' añadido con fecha: " + fechaEvento);
					dialog.dispose();
				}
			}
		});

		dialog.add(panelContenido, BorderLayout.CENTER);
		dialog.add(botonGuardar, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

    public static void main(String[] args) {
    	MainWindow window = new MainWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
