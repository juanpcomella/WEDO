package VentanaTienda;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

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
    private int money;
    public VentanaTienda() {
        // Configuración de la ventana principal
        setTitle("Tienda");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());

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

        //panelIcono.add(new JScrollPane(iconoT), BorderLayout.CENTER);

        // Dinero almacenado aquí, luego se hará un parseInt para convertir
        String dinero = "200";
        

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
        panelNorteIcono.setBackground(new Color(255, 215, 0));
        JLabel stringDinero = new JLabel(dinero);
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);
        JLabel StringmonyL = new JLabel(dinero);

        panelIcono.add(panelNorteIcono, BorderLayout.NORTH);

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

                    // Verificar si hay suficiente dinero
                    if (money >= precio) {
                        int respuesta = JOptionPane.showConfirmDialog(
                                null,
                                "¿Desea comprar?",
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
                            stringDinero.setText(String.valueOf(money));
                            StringmonyL.setText(String.valueOf(money));
                            
                            estadoCeldas.put(celda, true);
                            table.setValueAt(comprado, selectedRow, selectedColumn);
                            panel.repaint();
                        } else {
                            System.out.println("Compra cancelada.");
                            estadoCeldas.put(celda, false);
                        }
                    } else {
                        // Mostrar un mensaje de error si no hay suficiente dinero
                        JOptionPane.showMessageDialog(
                                null,
                                "Error, no se puede comprar",
                                "Dinero insuficiente",
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
        //panelTematica.add(new JScrollPane(monedasT), BorderLayout.CENTER);
        
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
                        if (value instanceof Object[]) {
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
                return panel; // Retornar un panel vacío si no hay datos
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
        panelTematica.add(panelNorteMonedas, BorderLayout.NORTH);
        panelNorteMonedas.setBackground(new Color(255, 215, 0));
        
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

        // Design of the text money:
        stringDinero.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        StringmonyL.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
    }

        


    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(ruta);
        Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        VentanaTienda ventana = new VentanaTienda();
        ventana.setVisible(true);
    }
}
