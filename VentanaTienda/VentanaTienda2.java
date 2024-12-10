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
    private JLabel saldoLabel;
    private Map<Integer, Item> itemMapIconos = new HashMap<>();
    private Map<Integer, Item> itemMapMonedas = new HashMap<>();

    public VentanaTienda2(Usuario usuario) {

        BDs.crearTablaItems();
        BDs.crearTablaCompras();

        setTitle("Tienda2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        llenarTablaIconos(modeloIcono, usuario);
        money = BDs.getSaldo(usuario.getNombreUsuario());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 255, 180));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelIconos = new JPanel(new BorderLayout());
        JPanel headerPanelIconos = crearHeaderPanel(usuario);
        panelIconos.add(headerPanelIconos, BorderLayout.NORTH);

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

        llenarTablaIconos(modeloIcono, usuario);

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

            }
        });

        iconoT.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new PrecioCellRenderer(estadoCeldasIcono));
        iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(iconoT, estadoCeldasIcono, itemMapIconos, usuario));

        insertarIconosBaseDeDatos(usuario);

        for (int i = 0; i < iconoT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1);
            estadoCeldasIcono.put(celda_comprada, false);
        }

        // Panel para las monedas
        JPanel panelMonedas = new JPanel(new BorderLayout());
        tabbedPane.addTab("Monedas", panelMonedas);

        JPanel headerPanelMonedas = crearHeaderPanel(usuario);
        panelMonedas.add(headerPanelMonedas, BorderLayout.NORTH);

        modeloDinero = new DefaultTableModel(new Object[]{"Moneda", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
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
            }
        });


        monedasT.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());

        monedasT.getColumnModel().getColumn(1).setCellRenderer(new PrecioCellRenderer(estadoCeldasMoneda));

        monedasT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(monedasT, estadoCeldasMoneda, itemMapMonedas, usuario));


        insertarMonedasBaseDeDatos();

        for (int i = 0; i < monedasT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1);
            estadoCeldasMoneda.put(celda_comprada, false);
        }

        add(tabbedPane,BorderLayout.CENTER);
        setVisible(true);

    }
    public void insertarIconosBaseDeDatos(Usuario usuario) {

        Item[] iconos = {
                new Item("LogoHomer", 85, "foto", "imagenes/logoHomer.png"),
                new Item("LogoOso", 120, "foto", "imagenes/logoOso.png"),
                new Item("LogoPerro", 200, "foto", "imagenes/logoPerro.png"),
                new Item("LogoUnicornio", 70, "foto", "imagenes/logoUnicornio.png"),
                new Item("LogoPlayaPalmeras", 140, "foto", "imagenes/logoPlayaPalmeras.png"),
                new Item("PerfilMonje", 300, "foto", "imagenes/perfilMonje.png"),
                new Item("LogoPanda", 200, "foto", "imagenes/logoPanda.png")
        };

        for (Item item : iconos) {
            if (!BDs.itemExiste(item.getNombreItem())) {
                BDs.insertarItem("defaultUser", item.getNombreItem(), item.getPrecioItem(), item.getTipoItem(), item.getContenido());
                System.out.println("Item " + item.getNombreItem() + " insertado en la base de datos.");
            } else {
                System.out.println("Item " + item.getNombreItem() + " ya existe en la base de datos. No se insertará.");
            }
        }
        System.out.println("Iconos insertados en la base de datos.");

        modeloIcono.setRowCount(0);
        llenarTablaIconos(modeloIcono, usuario);
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

        modeloDinero.setRowCount(0);
        llenarTablaMonedas(modeloDinero);
    }

    private JPanel crearHeaderPanel(Usuario usuario) {
        Color colorPrincipal = new Color(173, 216, 230);

        JLabel dineroL = new JLabel(cargarImagen("imagenes/coin_sin_fondo.png", 30, 30));
        JLabel stringDinero = new JLabel(String.valueOf(money)); // Guardar referencia al saldo
        stringDinero.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));

        JPanel panelNorteIcono = new JPanel();
        panelNorteIcono.setBackground(colorPrincipal);
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);

        JPanel panelNorteIcono2 = new JPanel(new BorderLayout());
        panelNorteIcono2.setBackground(colorPrincipal);
        panelNorteIcono2.add(panelNorteIcono, BorderLayout.CENTER);

        JButton volverB = new JButton("<< Volver");
        volverB.setForeground(new Color(50, 70, 90));
        volverB.setFont(new Font("Tahoma", Font.BOLD, 15));
        volverB.setContentAreaFilled(false);
        volverB.setBorderPainted(false);
        volverB.setFocusable(false);

        volverB.addActionListener(e -> {
            MainWindow mainWindow = new MainWindow(usuario);
            mainWindow.setVisible(true);
            dispose();
        });

        panelNorteIcono2.add(volverB, BorderLayout.WEST);

        JLabel labelVacio = new JLabel(" ");
        labelVacio.setForeground(colorPrincipal);
        panelNorteIcono2.add(labelVacio, BorderLayout.EAST);

        // Almacenar el JLabel en un atributo para poder actualizarlo
        this.saldoLabel = stringDinero;

        return panelNorteIcono2;
    }


    private void llenarTablaIconos(DefaultTableModel modeloIcono, Usuario usuario) {
        ArrayList<Item> listaItems = BDs.obtenerItemsPorTipo("foto");

        if (listaItems.isEmpty()) {
            System.out.println("No hay ítems disponibles para mostrar.");
            return;
        }

        modeloIcono.setRowCount(0);
        itemMapIconos.clear();

        for (int i = 0; i < listaItems.size(); i++) {
            Item item = listaItems.get(i);

            itemMapIconos.put(i, item);

            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80);
            modeloIcono.addRow(new Object[]{
                    icono,
                    new Object[]{item.getPrecioItem(), cargarImagen("imagenes/coin_sin_fondo.png", 30, 30)} // Precio y moneda
            });
        }
    }

    private void llenarTablaMonedas(DefaultTableModel modeloDinero) {
        ArrayList<Item> listaMonedas = BDs.obtenerItemsPorTipo("moneda");

        modeloDinero.setRowCount(0);
        itemMapMonedas.clear();

        for (int i = 0; i < listaMonedas.size(); i++) {
            Item item = listaMonedas.get(i);

            itemMapMonedas.put(i, item);

            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80);
            modeloDinero.addRow(new Object[]{
                    icono,
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
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
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

            if (estadoCeldas.getOrDefault(celda_Render, false)) {
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
                        JPanel panelElements = new JPanel(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(0, 1, 0, 1);
                        gbc.gridy = 0;
                        gbc.weightx = 0.0;
                        gbc.anchor = GridBagConstraints.CENTER;

                        Object[] cellData = (Object[]) value;

                        JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                        numberLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
                        gbc.gridx = 0;
                        panelElements.add(numberLabel, gbc);

                        if (cellData[1] instanceof Icon) {
                            JLabel iconLabel = new JLabel((Icon) cellData[1]);
                            gbc.gridx = 1;
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
        private JTable table;
        private Map<Integer, Item> itemMap;
        private Map<Point, Boolean> estadoCeldas;
        private Usuario usuario;

        public ButtonCellEditor(JTable table, Map<Point, Boolean> estadoCeldas, Map<Integer, Item> itemMap, Usuario usuario) {
            this.table = table;
            this.estadoCeldas = estadoCeldas;
            this.itemMap = itemMap;
            this.usuario = usuario;

            button = new JButton("Comprar");
            button.setBackground(new Color(220, 100, 80));
            button.setFont(new Font("Arial", Font.BOLD, 24));

            button.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                celda = new Point(selectedRow, selectedColumn);

                Object cellValue = table.getValueAt(selectedRow, 1);
                int precio = 0;
                if (cellValue instanceof Object[]) {
                    Object[] cellData = (Object[]) cellValue;
                    if (cellData[0] instanceof Integer) {
                        precio = (Integer) cellData[0];
                    }
                }

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

                        // Restar dinero y actualizar el saldo
                        money -= precio;
                        usuario.setSaldo(money);
                        BDs.updateSaldo(usuario.getNombreUsuario(), usuario.getSaldo());
                        estadoCeldas.put(celda, true);

                        // Actualizar visualmente el saldo en el header
                        saldoLabel.setText(String.valueOf(money));

                        // Registrar la compra en la base de datos
                        Item itemComprado = itemMap.get(selectedRow);
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

                        // Actualizar la celda visualmente
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
        panelConMargen.setBorder(new EmptyBorder(top, left, bottom, right));
        panelConMargen.add(componente);
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