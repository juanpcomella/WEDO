import java.awt.*;

import javax.swing.*;

public class VentanaPerfil extends JFrame{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	VentanaPerfil(){
		setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Panel principal
        JPanel panel = new JPanel(new GridLayout(1,2));
		panel.setBackground(new Color(173, 216, 230));
		add(panel);
		
			//Parte izquierda		                                           		
		ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/PERFIL.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(175, 150, Image.SCALE_SMOOTH);// MANTENER PROPORCIÓN ESCALA PARA QUE SEA REDONDO
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel logo = new JLabel(imagenRedimensionada);                                 
//		logo.setHorizontalAlignment(JLabel.CENTER);
//		logo.setVerticalAlignment(JLabel.CENTER);                                                                                                                                                                     
//		logo.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(logo);                                                  
   
        //Menu
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Margen pequeño
//        menuBar.setOpaque(false);
//        
//        JMenu menuArchivo = new JMenu("Archivo");
//        menuArchivo.setOpaque(false);
//        
//        JMenuItem menuItemNuevo = new JMenuItem("Nuevo");
//        menuArchivo.setOpaque(false);
//
//        JMenuItem menuItemAbrir = new JMenuItem("Abrir");
//        menuArchivo.setOpaque(false);
//
//        JMenuItem menuItemGuardar = new JMenuItem("Guardar");
//        menuArchivo.setOpaque(false);
//
//        JMenuItem menuItemSalir = new JMenuItem("Salir");
//        menuArchivo.setOpaque(false);
//
//        
//        menuArchivo.add(menuItemNuevo);
//        menuArchivo.add(menuItemAbrir);
//        menuArchivo.add(menuItemGuardar);
//        menuArchivo.addSeparator(); // Línea separadora
//        menuArchivo.add(menuItemSalir);
//        
//        menuBar.add(menuArchivo);
//        panelIzquierdo.add(menuBar);
        
	}

	public static void main(String[] args) {
		VentanaPerfil ventana = new VentanaPerfil();
		ventana.setVisible(true);
	}

}
