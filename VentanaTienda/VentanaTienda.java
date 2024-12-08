package VentanaTienda;

import javax.swing.*;

import java.io.File;
import java.io.IOException;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import BaseDeDatos.BDs;
import MainWindow.MainWindow;
import MainWindow.Navbar;
import StartingWindows.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.KeyStore.TrustedCertificateEntry;
import java.util.HashMap;
import java.util.Map;

public class VentanaTienda extends JFrame {

    private int hoveredColumn = -1;
    private int hoveredRow = -1;
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private Map<Point, Boolean> estadoCeldasIcono = new HashMap<>();
    private Map<Point, Boolean> estadoCeldasMoneda = new HashMap<>();
    private Map<Point, Boolean> estadoCeldasApodo = new HashMap<>(); //new

    private int money;
    public VentanaTienda(Usuario usuario) {
        // Configuración de la ventana principal
        setTitle("Tienda");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        
        Color colorPrincipal = new Color(173, 216, 230);

        // Marcar el estado de las celdas
        Map<Point, Boolean> estadoCeldasIcono = new HashMap<>();

        // TabbedPane para las secciones
        JTabbedPane tabbedPane = new JTabbedPane();


        // Panel para los iconos
        JPanel panelIcono = new JPanel(new BorderLayout());
        tabbedPane.addTab("Iconos de perfil", panelIcono);

        // Modelo de la tabla de iconos
        DefaultTableModel modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        JTable iconoT = new JTable(modeloIcono);
        
     // Crear el JTable
        JScrollPane scrollPane = new JScrollPane(iconoT);

        // Crear un panel para envolver el JScrollPane
        panelIcono.add(scrollPane, BorderLayout.CENTER);


        // Fondo del tabbedPane:
        tabbedPane.setBackground(Color.yellow);
        
        iconoT.setBackground(Color.blue);
        
        panelIcono.setBackground(Color.cyan);
        
        // MouseListener para capturar las celdas seleccionadas
        iconoT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = iconoT.rowAtPoint(e.getPoint());
                int column = iconoT.columnAtPoint(e.getPoint());

                if (column == 1) {
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1;
                }

                iconoT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Método generado automáticamente
            }
        });

        Point celda = new Point(hoveredRow, hoveredColumn);

        // Ocultamos los tableheaders
        iconoT.getTableHeader().setVisible(false);
        iconoT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        //iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        iconoT.setRowHeight(100);


        // Dinero almacenado aquí, luego se hará un parseInt para convertir
        String dinero = Integer.toString(BDs.getSaldo(usuario.getNombreUsuario()));
        
        // Parseo de dinero
        money = Integer.parseInt(dinero);

        // Cargar imágenes y redimensionarlas
        ImageIcon icon1 = new ImageIcon("imagenes/Imagen1.png");
        Image img = icon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(img);

        ImageIcon logoOso = cargarImagen("imagenes/logoOso.png", 80, 80);
        ImageIcon logoPerro = cargarImagen("imagenes/logoPerro.png", 80, 80);
        ImageIcon logoPanda = cargarImagen("imagenes/logoPanda.png", 80, 80);
        ImageIcon logoUnicornio = cargarImagen("imagenes/logoUnicornio.png", 80, 80);
        ImageIcon perfilMonje = cargarImagen("imagenes/perfilMonje.png", 80, 80);
        ImageIcon logoPlayaPalmeras = cargarImagen("imagenes/logoPlayaPalmeras.png", 80, 80);
        ImageIcon logoHomer = cargarImagen("imagenes/logoHomer.png", 80, 80);

        ImageIcon icon2 = cargarImagen("imagenes/coin_sin_fondo.png", 50, 50);

        // Agregar ejemplos de filas con un icono y precio
        modeloIcono.addRow(new Object[]{logoHomer, new Object[]{85, icon2}});
        modeloIcono.addRow(new Object[]{logoOso, new Object[]{120, icon2}});
        modeloIcono.addRow(new Object[]{logoPerro, new Object[]{200, icon2}});
        modeloIcono.addRow(new Object[]{logoUnicornio, new Object[]{70, icon2}});
        modeloIcono.addRow(new Object[]{logoPlayaPalmeras, new Object[]{140, icon2}});
        modeloIcono.addRow(new Object[]{perfilMonje, new Object[]{300, icon2}});
        modeloIcono.addRow(new Object[]{logoPanda, new Object[]{200, icon2}});


        for (int i = 0; i < iconoT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1); // Crear un Point para la celda en la fila i y columna 1
            estadoCeldasIcono.put(celda_comprada, false); // Añadir al HashMap con valor por defecto false
        }

        
        // Renderizador para la columna de "Precio" (JPanel con número e imagen)
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel();
                Point celda_Render = new Point(row, column);
                if (estadoCeldasIcono.getOrDefault(celda_Render, false)) {
                    // Si la celda ya está comprada, muestra el JLabel "Comprado"
                    JLabel compradoL = new JLabel("Comprado");
                    compradoL.setFont(new Font("Arial", Font.BOLD, 24));
                    compradoL.setForeground(Color.green);
                    compradoL.setHorizontalAlignment(SwingConstants.CENTER);
                    compradoL.setVerticalAlignment(SwingConstants.CENTER);
                    return compradoL;

                } else {

                if (column == hoveredColumn && row == hoveredRow) {
                	JButton boton = new JButton("Comprar");
                	boton.setBackground(new Color(255, 215, 0));
                	boton.setFont(new Font("Arial", Font.BOLD, 24));
                	return boton;
                } else {
                	if (value instanceof Object[]) {
                	    // Crear un panel con GridBagLayout
                	    JPanel panelElements = new JPanel(new GridBagLayout());
                	    GridBagConstraints gbc = new GridBagConstraints();
                	    gbc.insets = new Insets(0, 1, 0, 1); // Espaciado entre componentes
                	    gbc.gridy = 0; // Misma fila para ambos elementos
                	    gbc.weightx = .0; // Permitir expansión horizontal
                	    gbc.anchor = GridBagConstraints.CENTER; // Centrado vertical y horizontal

                	    Object[] cellData = (Object[]) value;

                	    // Crear el JLabel para el número
                	    JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                	    numberLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
                	    gbc.gridx = 0; // Columna izquierda
                	    gbc.anchor = GridBagConstraints.CENTER; // Alineado hacia la izquierda en su celda
                	    panelElements.add(numberLabel, gbc);

                	    // Crear el JLabel para el ícono
                	    if (cellData[1] instanceof Icon) {
                	        JLabel iconLabel = new JLabel((Icon) cellData[1]);
                	        gbc.gridx = 1; // Columna derecha
                	        gbc.anchor = GridBagConstraints.CENTER; // Alineado hacia la derecha en su celda
                	        panelElements.add(iconLabel, gbc);
                	    }

                	    return panelElements;
                	}


                    }
                }
                
                return panel;
            }
        });

        JLabel dineroL = new JLabel(icon2);
        JPanel panelNorteIcono = new JPanel();
        panelNorteIcono.setBackground(colorPrincipal);
        JLabel stringDinero = new JLabel(dinero);
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);
        
        JPanel panelNorteIcono2 = new JPanel(new BorderLayout());
        
        panelNorteIcono2.setBackground(colorPrincipal);
        
        panelNorteIcono2.add(panelNorteIcono,BorderLayout.CENTER);
        
        
        
        JLabel StringmonyL = new JLabel(dinero);
        JLabel StrApodomoney = new JLabel(dinero);

        panelIcono.add(panelNorteIcono2, BorderLayout.NORTH);
        
        JButton volverB = new JButton("<< Volver");
        panelNorteIcono2.add(volverB, BorderLayout.WEST);
        
        JLabel labelVacio = new JLabel ("esto es jlabel para centrar");//solucion para centrar 
        labelVacio.setForeground(colorPrincipal);
        
        panelNorteIcono2.add(labelVacio, BorderLayout.EAST);
 
        
        
        
        volverB.setForeground(new Color(50, 70, 90));
        volverB.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverB.setContentAreaFilled(false);
        volverB.setBorderPainted(false);
        volverB.setFocusable(false);
        
        // Asi se puede poner el boton de volver a la izquierda del todo

        // Renderizador para la columna de "Icono" (solo imagen)
        iconoT.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Icon) {
                    return new JLabel((Icon) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

     // Funcionalidad del botón
        class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
            private JPanel panel;
            private JButton button;
            private Object valorOriginal;
            private JLabel comprado;
            private Point celda;
            private JTable table; // Parámetro para la tabla
            private Map<Point, Boolean> estadoCeldas; // Parámetro para el estado de las celdas

            public ButtonCellEditor(JTable table, Map<Point, Boolean> estadoCeldas) {
                this.table = table;
                this.estadoCeldas = estadoCeldas;

                button = new JButton("Comprar");
                button.setBackground(new Color(220, 100, 80));
            	button.setFont(new Font("Arial", Font.BOLD, 24));
                button.addActionListener(e -> {
                    // Obtener la celda seleccionada
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();
                    celda = new Point(selectedRow, selectedColumn);

                    // Obtener el precio de la celda seleccionada
                    Object cellValue = table.getValueAt(selectedRow, 1);
                    int precio = 0;
                    if (cellValue instanceof Object[]) {
                        Object[] cellData = (Object[]) cellValue;
                        if (cellData[0] instanceof Integer) {
                            precio = (Integer) cellData[0];
                        }
                    }
                    
//                    String dinero = Integer.toString(BDs.getSaldo(usuario.getNombreUsuario()));                  
//                    // Parseo de dinero
//                    money = Integer.parseInt(dinero);

                    // Verificar si hay suficiente dinero
                    if (money >= precio) {
                        int respuesta = JOptionPane.showConfirmDialog(
                                null,
                                "¿Desea comprar por " + precio + " monedas?",
                                "Comprar",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        if (respuesta == JOptionPane.YES_OPTION) {
                            comprado = new JLabel("Comprado");
                            comprado.setFont(new Font("Arial", Font.BOLD, 24));
                            comprado.setForeground(Color.green);
                            comprado.setHorizontalAlignment(SwingConstants.CENTER);
                            comprado.setVerticalAlignment(SwingConstants.CENTER);
                            System.out.println("Compra realizada.");
                            money -= precio; // Restar el precio al dinero del usuario
                            usuario.setSaldo(money);
                            BDs.updateSaldo(usuario.getNombreUsuario(),usuario.getSaldo());
                            Navbar.coinAmountLabel.setText(String.valueOf(usuario.getSaldo()));
                            stringDinero.setText(String.valueOf(money));
                            StringmonyL.setText(String.valueOf(money));
                            StrApodomoney.setText(String.valueOf(money));
                            
                            
                            estadoCeldas.put(celda, true);
                            table.setValueAt(comprado, selectedRow, selectedColumn);
                            panel.repaint();
                        } else {
                            System.out.println("Compra cancelada.");
                            estadoCeldas.put(celda, false);
                        }
                    } else {
                        // Mostrar un mensaje de error si no hay suficiente dinero
                    	int dinero_faltante = (money-precio) * (-1) ;
                        JOptionPane.showMessageDialog(
                                null,
                                "Dinero insuficiente, te faltan " + dinero_faltante + " monedas" ,
                                "Error, compra no realizada",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    fireEditingStopped();
                });

                panel = new JPanel(new BorderLayout());
                panel.add(button, BorderLayout.CENTER);
            }//

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                celda = new Point(row, column);
                valorOriginal = value;
                if (estadoCeldas.getOrDefault(celda, true)) {
                	JLabel compradoL = new JLabel("Comprado");
                    compradoL.setFont(new Font("Arial", Font.BOLD, 24));
                    compradoL.setForeground(Color.green);
                    compradoL.setHorizontalAlignment(SwingConstants.CENTER);
                    compradoL.setVerticalAlignment(SwingConstants.CENTER);
                    return compradoL;
                } else {
                    return panel;
                }
            }

            @Override
            public Object getCellEditorValue() {
                return valorOriginal;
            }
        }



        iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(iconoT, estadoCeldasIcono));


        // Panel para los apodos
        JPanel panelApodos = new JPanel();
        tabbedPane.addTab("Apodos", panelApodos);
        
        


        // Panel para la temática
        JPanel panelTematica = new JPanel(new BorderLayout());
        tabbedPane.addTab("Monedas", panelTematica);
        
        
        JTable monedasT = new JTable();
        
        
  
        
        DefaultTableModel modeloDinero = new DefaultTableModel(new Object[]{"Moneda", "Precio"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Solo la columna de precio es editable
            }
        };
        monedasT.setModel(modeloDinero);
        monedasT.setRowHeight(100);
        monedasT.getTableHeader().setVisible(false);
        monedasT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        monedasT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        
        
        ImageIcon zorroenluna = cargarImagen("imagenes/zorroenluna.png", 80, 80);
        ImageIcon pizzaazul = cargarImagen("imagenes/pizzaazul.png", 80, 80);
        

        modeloDinero.addRow(new Object[]{zorroenluna, new Object[]{50, icon2}});
        modeloDinero.addRow(new Object[]{pizzaazul, new Object[]{100, icon2}});

        ImageIcon futCoin = cargarImagen("imagenes/futcoin.png", 80, 80);
        ImageIcon florenluna = cargarImagen("imagenes/florenluna.png", 80, 80);

        modeloDinero.addRow(new Object[]{futCoin, new Object[]{50, icon2}});
        modeloDinero.addRow(new Object[]{florenluna, new Object[]{100, icon2}});

        ImageIcon crabcoin = cargarImagen("imagenes/crabcoin.png", 80, 80);
        ImageIcon dragoncoin = cargarImagen("imagenes/dragoncoin.png", 80, 80);

        modeloDinero.addRow(new Object[]{crabcoin, new Object[]{50, icon2}});
        modeloDinero.addRow(new Object[]{dragoncoin, new Object[]{100, icon2}});

        ImageIcon ghostCoin = cargarImagen("imagenes/ghostcoin.png", 80, 80);
        ImageIcon nubeCoin = cargarImagen("imagenes/nubecoin.png", 80, 80);

        modeloDinero.addRow(new Object[]{ghostCoin, new Object[]{50, icon2}});
        modeloDinero.addRow(new Object[]{nubeCoin, new Object[]{100, icon2}});

        ImageIcon onepiece = cargarImagen("imagenes/onepiece.png", 80, 80);
        ImageIcon indiacoin = cargarImagen("imagenes/indiacoin.png", 80, 80);

        modeloDinero.addRow(new Object[]{onepiece, new Object[]{50, icon2}});
        modeloDinero.addRow(new Object[]{indiacoin, new Object[]{100, icon2}});

        
        
     // Renderizador para la columna Moneda (solo imagen)
        monedasT.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Icon) {
                    return new JLabel((Icon) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        


        // Renderizador para la columna "Precio" (con número e icono)
        monedasT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel();
                Point celda_Render = new Point(row, column);
                
                if (estadoCeldasMoneda.getOrDefault(celda_Render, false)) {
                    // Si la celda ya está comprada, muestra el JLabel "Comprado"
                    JLabel compradoL = new JLabel("Comprado");
                    compradoL.setFont(new Font("Arial", Font.BOLD, 24));
                    compradoL.setForeground(Color.green);
                    compradoL.setHorizontalAlignment(SwingConstants.CENTER);
                    compradoL.setVerticalAlignment(SwingConstants.CENTER);
                    return compradoL;
                } else {
                    if (column == hoveredColumn && row == hoveredRow) {
                        // Mostrar el botón "Comprar" cuando la celda está resaltada
                        JButton boton = new JButton("Comprar");
                        boton.setBackground(new Color(255, 215, 0));
                        boton.setFont(new Font("Arial", Font.BOLD, 24));
                        return boton;
                    } else {
                        if (value instanceof Object[]) {//
                            // Crear un panel con GridBagLayout
                            JPanel panelElements = new JPanel(new GridBagLayout());
                            GridBagConstraints gbc = new GridBagConstraints();
                            gbc.insets = new Insets(0, 1, 0, 1); // Espaciado entre componentes
                            gbc.gridy = 0; // Misma fila para ambos elementos
                            gbc.weightx = 0.0; // Evitar expansión horizontal
                            gbc.anchor = GridBagConstraints.CENTER; // Centrado vertical y horizontal

                            Object[] cellData = (Object[]) value;

                            // Crear el JLabel para el número
                            JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                            numberLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
                            gbc.gridx = 0; // Columna izquierda
                            panelElements.add(numberLabel, gbc);

                            // Crear el JLabel para el ícono
                            if (cellData[1] instanceof Icon) {
                                JLabel iconLabel = new JLabel((Icon) cellData[1]);
                                gbc.gridx = 1; // Columna derecha
                                panelElements.add(iconLabel, gbc);
                            }

                            return panelElements;
                        }
                    }
                }
                return panel; 
            }
        });
        
        

        // Color tabla monedasT
        monedasT.setBackground(new Color(0, 100, 0));
        

        monedasT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = monedasT.rowAtPoint(e.getPoint());
                int column = monedasT.columnAtPoint(e.getPoint());

                if (column == 1) {
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1;
                }

                monedasT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Método generado automáticamente
            }
        });
        
       

        for (int i = 0; i < monedasT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1);
            estadoCeldasMoneda.put(celda_comprada, false);
        }

        JPanel panelNorteMonedas = new JPanel();
        JLabel icoinmony = new JLabel(icon2);
        panelNorteMonedas.add(icoinmony);
        panelNorteMonedas.add(StringmonyL);
        
        panelNorteMonedas.setBackground(colorPrincipal);
    
        JPanel panelNorteMonedas2 = new JPanel(new BorderLayout());
        
        panelNorteMonedas2.setBackground(colorPrincipal);
        
        panelNorteMonedas2.add(panelNorteMonedas, BorderLayout.CENTER);
        
        JLabel labelVacio3 = new JLabel("esto es jlabel para centrar");
        
        labelVacio3.setForeground(colorPrincipal);
        
        panelNorteMonedas2.add(labelVacio3,BorderLayout.EAST);
        
        JButton volverB3 = new JButton("<< Volver");
        
        panelNorteMonedas2.add(volverB3,BorderLayout.WEST);
        volverB3.setForeground(new Color(50, 70, 90));
        volverB3.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverB3.setContentAreaFilled(false);
        volverB3.setBorderPainted(false);
        volverB3.setFocusable(false);
        
        
        
        
        panelTematica.add(panelNorteMonedas2, BorderLayout.NORTH);
        
 
        
        monedasT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(monedasT, estadoCeldasMoneda));


        
        

        // Agregar el tabbedPane al frame
        add(tabbedPane,BorderLayout.CENTER);
        setVisible(true);
        
        
        
     // Panel con márgenes para el JTable de Iconos
        JPanel panelIconoConMargen = new JPanel();
        panelIconoConMargen.setLayout(new BoxLayout(panelIconoConMargen, BoxLayout.Y_AXIS));
        panelIconoConMargen.setBorder(new EmptyBorder(20, 250, 90, 250)); // Márgenes: arriba, izquierda, abajo, derecha
        panelIconoConMargen.add(scrollPane); // Añadir el JScrollPane que contiene el JTable
        panelIcono.add(panelIconoConMargen, BorderLayout.CENTER); // Añadir el panel con márgenes al panel principal
        
        // Panel con márgenes para el JTable de Monedas
        JPanel panelMonedasConMargen = new JPanel();
        panelMonedasConMargen.setLayout(new BoxLayout(panelMonedasConMargen, BoxLayout.Y_AXIS));
        panelMonedasConMargen.setBorder(new EmptyBorder(20, 250, 90, 250)); // Márgenes
        panelMonedasConMargen.add(new JScrollPane(monedasT)); // Añadir el JScrollPane con el JTable
        panelTematica.add(panelMonedasConMargen, BorderLayout.CENTER); // Añadir al panel principal
        

        
     // Configurar la tabla de apodos
        DefaultTableModel modeloApodos = new DefaultTableModel(new Object[]{"Apodo", "Acción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Solo la columna "Acción" es editable
            }
        };
        
        JTable apodosT = new JTable();


        apodosT.setModel(modeloApodos);

        // Crear renderizador para la tabla
        apodosT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel();
                Point celda_Render = new Point(row, column);

                if (estadoCeldasApodo.getOrDefault(celda_Render, false)) {
                    // Mostrar "Comprado" si la celda ya está comprada
                    JLabel compradoL = new JLabel("Comprado");
                    compradoL.setFont(new Font("Arial", Font.BOLD, 24));
                    compradoL.setForeground(Color.green);
                    compradoL.setHorizontalAlignment(SwingConstants.CENTER);
                    return compradoL;
                } else if (column == hoveredColumn && row == hoveredRow) {
                    // Mostrar botón "Comprar" si la celda está resaltada
                    JButton boton = new JButton("Comprar");
                    boton.setBackground(new Color(255, 215, 0));
                    boton.setFont(new Font("Arial", Font.BOLD, 24));
                    return boton;
                } else if (value instanceof Object[]) {
                    // Renderizar celda con ícono y texto
                    JPanel panelElements = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(0, 1, 0, 1);
                    gbc.gridy = 0;
                    gbc.weightx = 0.0;
                    gbc.anchor = GridBagConstraints.CENTER;

                    Object[] cellData = (Object[]) value;

                    JLabel textLabel = new JLabel(String.valueOf(cellData[0]));
                    textLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
                    gbc.gridx = 0;
                    panelElements.add(textLabel, gbc);

                    if (cellData[1] instanceof Icon) {
                        JLabel iconLabel = new JLabel((Icon) cellData[1]);
                        gbc.gridx = 1;
                        panelElements.add(iconLabel, gbc);
                    }

                    return panelElements;
                }
                return panel;
            }
        });
        

        // Editor de celdas
        apodosT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(apodosT, estadoCeldasApodo));

        // Listener para resaltar celdas
        apodosT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = apodosT.rowAtPoint(e.getPoint());
                int column = apodosT.columnAtPoint(e.getPoint());

                if (column == 1) {
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1;
                }
                apodosT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Método generado automáticamente
            }
        });
        
        

        // Configuración del panel superior
        JPanel panelNorteApodo = new JPanel();
        panelNorteApodo.setBackground(colorPrincipal);
        panelNorteApodo.add(new JLabel(icon2)); //Icono
        panelNorteApodo.add(StrApodomoney); // Etiqueta del dinero
        StrApodomoney.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        
        stringDinero.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        
        StringmonyL.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        
        JPanel panelNorteApodo2 = new JPanel(new BorderLayout());        
        
        panelNorteApodo2.add(panelNorteApodo, BorderLayout.CENTER);
        
        JButton volverB2 = new JButton("<< Volver");
        
        
        volverB2.setForeground(new Color(50, 70, 90));
        volverB2.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverB2.setContentAreaFilled(false);
        volverB2.setBorderPainted(false);
        volverB2.setFocusable(false);
        
        
        panelNorteApodo2.add(volverB2,BorderLayout.WEST);
        
        
        JLabel labelVacio2 = new JLabel("esto es jlabel para centrar");
        labelVacio2.setForeground(colorPrincipal);
        
        panelNorteApodo2.add(labelVacio2,BorderLayout.EAST);

        
        panelNorteApodo2.setBackground(colorPrincipal);



        // Configuración del panel con márgenes para la tabla
        JPanel panelApodosConMargen = new JPanel();
        panelApodosConMargen.setLayout(new BoxLayout(panelApodosConMargen, BoxLayout.Y_AXIS));
        panelApodosConMargen.setBorder(new EmptyBorder(20, 250, 90, 250));
        panelApodosConMargen.add(new JScrollPane(apodosT));

        // Agregar componentes al panel principal de apodos
        panelApodos.setLayout(new BorderLayout());
        panelApodos.add(panelNorteApodo2, BorderLayout.NORTH);
        panelApodos.add(panelApodosConMargen, BorderLayout.CENTER);

        

        
        apodosT.setRowHeight(50);//new

        
        Font customFontBatman = null;
		try {
			customFontBatman = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Gotham.ttf")).deriveFont(48f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFontBatman);
        
      ;
        

        
        
        JLabel batmanL = new JLabel("Batman");
        batmanL.setFont(customFontBatman);
        JPanel panelBatman = new JPanel();
        panelBatman.add(batmanL);
        
        
        modeloApodos.addRow(new Object[]{panelBatman, new Object[]{200, icon2}});


        apodosT.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof JPanel) {
                    return (Component) value;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        
        apodosT.getTableHeader().setVisible(false);
        apodosT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        //iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        
        
        Font customFontSs = null;
		try {
			customFontSs = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Saiyan-Sans.ttf")).deriveFont(48f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge2.registerFont(customFontBatman);
        
        JLabel ssLabel = new JLabel("Super Saiyan");
        ssLabel.setFont(customFontSs);
        
        JPanel ssPanel = new JPanel();
        ssPanel.add(ssLabel);
        
        modeloApodos.addRow(new Object[]{ssPanel, new Object[]{100, icon2}});
        
        Font customFontAve = null;
		try {
			customFontAve = Font.createFont(Font.TRUETYPE_FONT, new File("textos/AVENGEANCE HEROIC AVENGER.ttf")).deriveFont(48f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        GraphicsEnvironment ge3 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge3.registerFont(customFontBatman);
        
        JLabel aveLabel = new JLabel("Vengador ");
        aveLabel.setFont(customFontAve);
        
        JPanel avePanel = new JPanel();
        avePanel.add(aveLabel);
        
        modeloApodos.addRow(new Object[]{avePanel, new Object[]{100, icon2}});
        
        
        
        Font customFontTer = null;
        
        try {
			customFontTer = Font.createFont(Font.TRUETYPE_FONT, new File("textos/terminator real nfi.ttf")).deriveFont(30f);
	        

		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("no se encontro");
		}
        GraphicsEnvironment ge4 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge4.registerFont(customFontTer);
        
        JLabel terLabel = new JLabel("Terminator");
        terLabel.setFont(customFontTer);
        
        JPanel terPanel = new JPanel();
        terPanel.add(terLabel);
        
        modeloApodos.addRow(new Object[]{terPanel, new Object[]{100, icon2}});
     
        
        Font customFontGlad = null;
        
        try {
			customFontGlad = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Marav2.ttf")).deriveFont(40f);
	        

		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("no se encontro");
		}
        GraphicsEnvironment ge5 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge5.registerFont(customFontGlad);
        
        JLabel gladLabel = new JLabel("{Gladiador#");
        gladLabel.setFont(customFontGlad);
        
        JPanel gladPanel = new JPanel();
        gladPanel.add(gladLabel);
        
        modeloApodos.addRow(new Object[]{gladPanel, new Object[]{100, icon2}});
        
        
        Font maestroFont = null;
        
        try {
			maestroFont = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Karate.ttf")).deriveFont(40f);
	        

		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("no se encontro");
		}
        GraphicsEnvironment ge6 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge6.registerFont(maestroFont);
        
        JLabel maestroL = new JLabel("maestro");
        maestroL.setFont(maestroFont);
        
        JPanel maestroPanel = new JPanel();
        maestroPanel.add(maestroL);
        
        //maestroPanel.setBackground(Color.red); // Asi se colorean aunque sea por separado
        
        modeloApodos.addRow(new Object[]{maestroPanel, new Object[]{100, icon2}});
        
        
        
        
        
        for (int i = 0; i < apodosT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1); 
            estadoCeldasApodo.put(celda_comprada, false); 
        }
        
        volverB.addActionListener(e ->{
        	MainWindow mainWindow = new MainWindow(usuario);
            mainWindow.setVisible(true);
            dispose();
        	
        });
        
        volverB2.addActionListener(e ->{
        	MainWindow mainWindow = new MainWindow(usuario);
            mainWindow.setVisible(true);
            dispose();
        	
        });
        
        volverB3.addActionListener(e ->{
        	MainWindow mainWindow = new MainWindow(usuario);
            mainWindow.setVisible(true);
            dispose();
        	
        });
        	
        

    }
    
    

        


    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(ruta);
        Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
    	Usuario usuario = new Usuario(null, null, null);
        VentanaTienda ventana = new VentanaTienda(usuario);
        ventana.setVisible(true);
    }
}
