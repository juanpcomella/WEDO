package VentanaTienda;

import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
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
import java.security.KeyStore.TrustedCertificateEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class VentanaTienda2 extends JFrame {

    private int hoveredColumn = -1;
    private int hoveredRow = -1;
    private Map<Point, Boolean> estadoCeldasIcono = new HashMap<>();
    private Map<Point, Boolean> estadoCeldasMoneda = new HashMap<>();
    private DefaultTableModel modeloIcono;
    private DefaultTableModel modeloDinero;
    private int money;
    private Map<Integer, Item> itemMapIconos = new HashMap<>();
    private Map<Integer, Item> itemMapMonedas = new HashMap<>();

    public VentanaTienda2(Usuario usuario) {
        BDs.crearTablaCompras();
        //Configuración de la ventana principal
        setTitle("Tienda2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Solo la columna de precio es editable
            }
        };
        llenarTablaIconos(modeloIcono, usuario);
        money = BDs.getSaldo(usuario.getNombreUsuario());

        // TabbedPane para las secciones
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 255, 180));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JPanel para los iconos
        JPanel panelIconos = new JPanel(new BorderLayout());
        JPanel headerPanelIconos = crearHeaderPanel(String.valueOf(money), usuario);
        panelIconos.add(headerPanelIconos, BorderLayout.NORTH);

        // Crear la tabla donde estarán los iconos.
        JTable iconoT = new JTable(modeloIcono);
        iconoT.setBackground(Color.BLUE);
        iconoT.getTableHeader().setVisible(false);
        iconoT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        iconoT.setRowHeight(100);

        JScrollPane scrollIconos = new JScrollPane(iconoT);
        JPanel panelIconoConMargen = agregarMargen(scrollIconos, 20, 250, 90, 250);
        panelIconos.setBackground(Color.CYAN);
        panelIconos.add(panelIconoConMargen, BorderLayout.CENTER);

        tabbedPane.addTab("Iconos", panelIconos);

        // Llenar la tabla de iconos pasando el usuario
        llenarTablaIconos(modeloIcono, usuario);

        // Listener para detectar la celda bajo el mouse
        iconoT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Detectar fila y columna donde está el mouse
                int row = iconoT.rowAtPoint(e.getPoint());
                int column = iconoT.columnAtPoint(e.getPoint());

                // Actualizar las variables hoveredRow y hoveredColumn si estamos en la columna de precios
                if (column == 1) { // Asegurarse de que es la columna de precios
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1; // Resetear si el mouse no está sobre la columna
                }

                // Forzar la actualización de la tabla
                iconoT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // No se utiliza
            }
        });

        iconoT.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new PrecioCellRenderer(estadoCeldasIcono));
        iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(iconoT, estadoCeldasIcono, itemMapIconos, usuario));

        // Insertar ítems en la base de datos (solo si no existen)
        insertarIconosBaseDeDatos(usuario);

        // Inicializar estado de celdas como no compradas
        for (int i = 0; i < iconoT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1); // Crear un Point para la celda en la fila i y columna 1
            estadoCeldasIcono.put(celda_comprada, false); // Inicializar como no comprada
        }

        // Panel para las monedas
        JPanel panelMonedas = new JPanel(new BorderLayout());
        tabbedPane.addTab("Monedas", panelMonedas);

        JPanel headerPanelMonedas = crearHeaderPanel(String.valueOf(money), usuario);
        panelMonedas.add(headerPanelMonedas, BorderLayout.NORTH);

        modeloDinero = new DefaultTableModel(new Object[]{"Moneda", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Solo la columna de precio es editable
            }
        };

        JTable monedasT = new JTable();
        monedasT.setModel(modeloDinero);
        monedasT.setRowHeight(100);
        monedasT.getTableHeader().setVisible(false);
        monedasT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        monedasT.setBackground(new Color(0, 100, 0));
        monedasT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);



        JScrollPane scrollMonedas = new JScrollPane(monedasT);
        JPanel panelMonedaConMargen = agregarMargen(scrollMonedas, 20, 250, 90, 250);
        panelMonedas.setBackground(Color.CYAN);
        panelMonedas.add(panelMonedaConMargen, BorderLayout.CENTER);

        llenarTablaMonedas(modeloDinero);

        monedasT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Detectar fila y columna donde está el mouse
                int row = monedasT.rowAtPoint(e.getPoint());
                int column = monedasT.columnAtPoint(e.getPoint());

                // Actualizar las variables hoveredRow y hoveredColumn si estamos en la columna de precios
                if (column == 1) { // Asegurarse de que es la columna de precios
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1; // Resetear si el mouse no está sobre la columna
                }

                // Forzar la actualización de la tabla
                monedasT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // No se utiliza
            }
        });


        monedasT.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());

        monedasT.getColumnModel().getColumn(1).setCellRenderer(new PrecioCellRenderer(estadoCeldasMoneda));

        monedasT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(monedasT, estadoCeldasMoneda, itemMapMonedas, usuario));


        //add(panelMonedas, BorderLayout.CENTER);
        insertarMonedasBaseDeDatos();

        for (int i = 0; i < monedasT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1);
            estadoCeldasMoneda.put(celda_comprada, false);
        }

        add(tabbedPane,BorderLayout.CENTER);
        setVisible(true);

    }
    public void insertarIconosBaseDeDatos(Usuario usuario) {
        // Ejemplo de ítems a insertar
        Item[] iconos = {
                new Item("LogoHomer", 85, "foto", "imagenes/logoHomer.png"),
                new Item("LogoOso", 120, "foto", "imagenes/logoOso.png"),
                new Item("LogoPerro", 200, "foto", "imagenes/logoPerro.png"),
                new Item("LogoUnicornio", 70, "foto", "imagenes/logoUnicornio.png"),
                new Item("LogoPlayaPalmeras", 140, "foto", "imagenes/logoPlayaPalmeras.png"),
                new Item("PerfilMonje", 300, "foto", "imagenes/perfilMonje.png"),
                new Item("LogoPanda", 200, "foto", "imagenes/logoPanda.png")
        };

        // Insertar ítems en la base de datos
        for (Item item : iconos) {
            if (!BDs.itemExiste(item.getNombreItem())) {
                BDs.insertarItem("defaultUser", item.getNombreItem(), item.getPrecioItem(), item.getTipoItem(), item.getContenido());
                System.out.println("Item " + item.getNombreItem() + " insertado en la base de datos.");
            } else {
                System.out.println("Item " + item.getNombreItem() + " ya existe en la base de datos. No se insertará.");
            }
        }
        System.out.println("Iconos insertados en la base de datos.");

        // Forzar la recarga de datos en el modelo de la tabla
        modeloIcono.setRowCount(0); // Limpiar el modelo
        llenarTablaIconos(modeloIcono, usuario); // Volver a cargar los ítems desde la base de datos
    }

    public void insertarMonedasBaseDeDatos() {
        Item[] monedas = {
                new Item("ZorroEnLuna", 50, "moneda", "imagenes/zorroenluna.png"),
                new Item("PizzaAzul", 100, "moneda", "imagenes/pizzaazul.png"),
                new Item("FutCoin", 50, "moneda", "imagenes/futcoin.png"),
                new Item("FlorEnLuna", 100, "moneda", "imagenes/florenluna.png"),
                new Item("CrabCoin", 50, "moneda", "imagenes/crabcoin.png"),
                new Item("DragonCoin", 100, "moneda", "imagenes/dragoncoin.png"),
                new Item("GhostCoin", 50, "moneda", "imagenes/ghostcoin.png"),
                new Item("NubeCoin", 100, "moneda", "imagenes/nubecoin.png"),
                new Item("OnePiece", 50, "moneda", "imagenes/onepiece.png"),
                new Item("IndiaCoin", 100, "moneda", "imagenes/indiacoin.png")
        };

        for (Item moneda : monedas) {
            if (!BDs.itemExiste(moneda.getNombreItem())) {
                BDs.insertarItem("defaultUser", moneda.getNombreItem(), moneda.getPrecioItem(), moneda.getTipoItem(), moneda.getContenido());
                System.out.println("Moneda " + moneda.getNombreItem() + " insertada en la base de datos.");
            } else {
                System.out.println("Moneda " + moneda.getNombreItem() + " ya existe en la base de datos. No se insertará.");
            }
        }
        System.out.println("Monedas insertadas en la base de datos.");

        // Forzar la recarga de datos en el modelo de la tabla
        modeloDinero.setRowCount(0); // Limpiar el modelo
        llenarTablaMonedas(modeloDinero); // Volver a cargar las monedas desde la base de datos
    }

    private JPanel crearHeaderPanel(String dinero, Usuario usuario) {
        // Color principal para el header
        Color colorPrincipal = new Color(173, 216, 230);

        // Icono de monedas
        JLabel dineroL = new JLabel(cargarImagen("imagenes/coin_sin_fondo.png", 30, 30));
        JLabel stringDinero = new JLabel(dinero);
        stringDinero.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));


        // Panel central que contiene el icono y la cantidad de monedas
        JPanel panelNorteIcono = new JPanel();
        panelNorteIcono.setBackground(colorPrincipal);
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);

        // Panel general con diseño BorderLayout
        JPanel panelNorteIcono2 = new JPanel(new BorderLayout());
        panelNorteIcono2.setBackground(colorPrincipal);
        panelNorteIcono2.add(panelNorteIcono, BorderLayout.CENTER);

        // Botón "Volver"
        JButton volverB = new JButton("<< Volver");
        volverB.setForeground(new Color(50, 70, 90));
        volverB.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverB.setContentAreaFilled(false);
        volverB.setBorderPainted(false);
        volverB.setFocusable(false);

        // Acción para el botón "Volver"
        volverB.addActionListener(e -> {
            MainWindow mainWindow = new MainWindow(usuario);
            mainWindow.setVisible(true);
            dispose();
        });

        panelNorteIcono2.add(volverB, BorderLayout.WEST);

        // Label vacío para centrar
        JLabel labelVacio = new JLabel(" ");
        labelVacio.setForeground(colorPrincipal);
        panelNorteIcono2.add(labelVacio, BorderLayout.EAST);

        return panelNorteIcono2;
    }

    private void llenarTablaIconos(DefaultTableModel modeloIcono, Usuario usuario) {
        // Obtener la lista de todos los ítems de tipo "foto" desde la base de datos
        ArrayList<Item> listaItems = BDs.obtenerItemsPorTipo("foto");

        if (listaItems.isEmpty()) {
            System.out.println("No hay ítems disponibles para mostrar.");
            return;
        }

        // Limpiar el modelo y el itemMap para evitar duplicados si se vuelve a llenar
        modeloIcono.setRowCount(0);
        itemMapIconos.clear();

        for (int i = 0; i < listaItems.size(); i++) {
            Item item = listaItems.get(i);

            // Agregar el ítem al mapa con la fila como clave
            itemMapIconos.put(i, item);

            // Procesar el ítem para la tabla
            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80);
            modeloIcono.addRow(new Object[]{
                    icono, // Icono procesado como ImageIcon
                    new Object[]{item.getPrecioItem(), cargarImagen("imagenes/coin_sin_fondo.png", 30, 30)} // Precio y moneda
            });
        }
    }

    private void llenarTablaMonedas(DefaultTableModel modeloDinero) {
        // Obtener la lista de monedas desde la base de datos
        ArrayList<Item> listaMonedas = BDs.obtenerItemsPorTipo("moneda");

        // Limpiar el modelo y el itemMapMonedas para evitar duplicados
        modeloDinero.setRowCount(0);
        itemMapMonedas.clear();

        for (int i = 0; i < listaMonedas.size(); i++) {
            Item item = listaMonedas.get(i);

            // Agregar el ítem al mapa con la fila como clave
            itemMapMonedas.put(i, item);

            // Procesar el ítem para la tabla
            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80);
            modeloDinero.addRow(new Object[]{
                    icono, // Imagen del ítem procesada como ImageIcon
                    new Object[]{item.getPrecioItem(), cargarImagen("imagenes/coin_sin_fondo.png", 30, 30)} // Precio y moneda
            });
        }
    }

    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
        try {
            ImageIcon icono = new ImageIcon(ruta);
            Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + ruta);
            return new ImageIcon(new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB));
        }
    }

    private class ImageCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                // Si el valor es un ImageIcon, lo mostramos en un JLabel
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
            // Si no es un ImageIcon, usamos el renderizado predeterminado
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    private class PrecioCellRenderer extends DefaultTableCellRenderer {
        private final Map<Point, Boolean> estadoCeldas;

        public PrecioCellRenderer(Map<Point, Boolean> estadoCeldas) {
            this.estadoCeldas = estadoCeldas;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel();
            Point celda_Render = new Point(row, column);

            // Verificar si la celda ya está comprada
            if (estadoCeldas.getOrDefault(celda_Render, false)) {
                JLabel compradoL = new JLabel("Comprado");
                compradoL.setFont(new Font("Arial", Font.BOLD, 24));
                compradoL.setForeground(Color.green);
                compradoL.setHorizontalAlignment(SwingConstants.CENTER);
                compradoL.setVerticalAlignment(SwingConstants.CENTER);
                return compradoL;
            } else {
                // Mostrar botón "Comprar" cuando la celda está resaltada
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
    }

    private class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton button;
        private Object valorOriginal;
        private JLabel comprado;
        private Point celda;
        private JTable table; // Tabla actual
        private Map<Integer, Item> itemMap; // Mapa específico de la tabla actual
        private Map<Point, Boolean> estadoCeldas; // Parámetro para el estado de las celdas
        private Usuario usuario; // Usuario actual

        public ButtonCellEditor(JTable table, Map<Point, Boolean> estadoCeldas, Map<Integer, Item> itemMap, Usuario usuario) {
            this.table = table;
            this.estadoCeldas = estadoCeldas;
            this.itemMap = itemMap; // Asigna el mapa específico de la tabla actual
            this.usuario = usuario;

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

                        // Restar dinero y actualizar el saldo del usuario
                        money -= precio;
                        usuario.setSaldo(money);
                        BDs.updateSaldo(usuario.getNombreUsuario(), usuario.getSaldo());
                        estadoCeldas.put(celda, true);

                        // Agregar item comprado a la base de datos de items comprados.
                        Item itemComprado = itemMap.get(selectedRow); // Obtener el ítem correspondiente
                        if (itemComprado != null) {
                            BDs.insertarCompra(
                                    usuario.getNombreUsuario(),
                                    itemComprado.getNombreItem(),
                                    itemComprado.getPrecioItem(),
                                    itemComprado.getTipoItem(),
                                    itemComprado.getContenido()
                            );
                            System.out.println("Item registrado: " + itemComprado.getNombreItem());
                        } else {
                            System.err.println("Error: No se encontró el ítem correspondiente para la fila " + selectedRow);
                        }


                        // Actualizar la celda con "Comprado"
                        table.setValueAt(comprado, selectedRow, selectedColumn);
                        table.repaint();

                        JOptionPane.showMessageDialog(null, "¡Compra realizada!");
                    } else {
                        System.out.println("Compra cancelada.");
                    }
                } else {
                    int dinero_faltante = (money - precio) * (-1);
                    JOptionPane.showMessageDialog(
                            null,
                            "Dinero insuficiente, te faltan " + dinero_faltante + " monedas",
                            "Error, compra no realizada",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                fireEditingStopped();
            });

            panel = new JPanel(new BorderLayout());
            panel.add(button, BorderLayout.CENTER);
        }

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

    private JPanel agregarMargen(JComponent componente, int top, int left, int bottom, int right) {
        JPanel panelConMargen = new JPanel();
        panelConMargen.setLayout(new BoxLayout(panelConMargen, BoxLayout.Y_AXIS));
        panelConMargen.setBorder(new EmptyBorder(top, left, bottom, right)); // Márgenes
        panelConMargen.add(componente); // Añadir el componente (por ejemplo, JScrollPane o JTable)
        return panelConMargen;
    }

    public static void main(String[] args) {
        BDs.crearTablaItems();
        String user = "juanpcomella";
        Usuario usuario = BDs.obtenerUsuario(user);
        VentanaTienda2 ventana = new VentanaTienda2(usuario);
        ventana.setVisible(true);
    }
}