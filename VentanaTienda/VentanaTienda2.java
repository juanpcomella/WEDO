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
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private Map<Point, Boolean> estadoCeldasIcono = new HashMap<>();
    private Map<Point, Boolean> estadoCeldasMoneda = new HashMap<>();
    private Map<Point, Boolean> estadoCeldasApodo = new HashMap<>(); //new
    private DefaultTableModel modeloIcono;
    private DefaultTableModel modeloDinero;
    private DefaultTableModel modeloApodos;

    private int money;

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
        String dinero = Integer.toString(BDs.getSaldo(usuario.getNombreUsuario()));
        money = Integer.parseInt(dinero);


        // Marcar el estado de las celdas
        Map<Point, Boolean> estadoCeldasIcono = new HashMap<>();

        // TabbedPane para las secciones
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 255, 180));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JPanel para los iconos
        JPanel panelIconos = new JPanel(new BorderLayout());

        JPanel headerPanelIconos = crearHeaderPanel(String.valueOf(money), usuario);

        panelIconos.add(headerPanelIconos, BorderLayout.NORTH);

        tabbedPane.addTab("Iconos", panelIconos);

        // Crear la tabla donde estarán los iconos.
        JTable iconoT = new JTable(modeloIcono);
        iconoT.setBackground(Color.BLUE);
        iconoT.getTableHeader().setVisible(false);
        iconoT.getTableHeader().setPreferredSize(new Dimension(0, 0));
        iconoT.setRowHeight(100);

// Llenar la tabla de iconos pasando el usuario
        llenarTablaIconos(modeloIcono, usuario);

        JScrollPane scrollIconos = new JScrollPane(iconoT);
        JPanel panelIconoConMargen = agregarMargen(scrollIconos, 20, 250, 90, 250);
        panelIconos.setBackground(Color.CYAN);
        panelIconos.add(panelIconoConMargen, BorderLayout.CENTER);

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
        iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(iconoT, estadoCeldasIcono, usuario));

// Insertar ítems en la base de datos (solo si no existen)
        insertarIconosBaseDeDatos(usuario);

// Inicializar estado de celdas como no compradas
        for (int i = 0; i < iconoT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1); // Crear un Point para la celda en la fila i y columna 1
            estadoCeldasIcono.put(celda_comprada, false); // Inicializar como no comprada
        }



        // Panel para los apodos
        JPanel panelApodos = new JPanel(new BorderLayout());
        tabbedPane.addTab("Apodos", panelApodos);

        // Crear el header panel para los apodos
        JPanel headerPanelApodos = crearHeaderPanel(String.valueOf(money), usuario);
        panelApodos.add(headerPanelApodos, BorderLayout.NORTH);

        // Modelo de la tabla de apodos
        modeloApodos = new DefaultTableModel(new Object[]{"Apodo", "Acción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Solo la columna de acción es editable
            }
        };

        // Crear la tabla de apodos
        JTable apodosT = new JTable(modeloApodos);
        apodosT.setRowHeight(50);
        apodosT.getTableHeader().setVisible(false);
        apodosT.getTableHeader().setPreferredSize(new Dimension(0, 0));

        // Llenar la tabla de apodos
        llenarTablaApodos(modeloApodos);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollApodos = new JScrollPane(apodosT);
        JPanel panelApodoConMargen = agregarMargen(scrollApodos, 20, 250, 90, 250);
        panelApodos.setBackground(Color.CYAN);
        panelApodos.add(panelApodoConMargen, BorderLayout.CENTER);


        // Listener para detectar celdas resaltadas
        apodosT.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = apodosT.rowAtPoint(e.getPoint());
                int column = apodosT.columnAtPoint(e.getPoint());

                if (column == 1) { // Asegurar que estamos en la columna de acciones
                    hoveredRow = row;
                    hoveredColumn = column;
                } else {
                    hoveredRow = -1;
                    hoveredColumn = -1; // Restablecer si el mouse no está sobre la columna
                }
                apodosT.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Método generado automáticamente
            }
        });

        apodosT.getColumnModel().getColumn(0).setCellRenderer(new ApodoCellRenderer());

        // Renderizador para la columna de precios y acciones
        apodosT.getColumnModel().getColumn(1).setCellRenderer(new PrecioCellRenderer(estadoCeldasApodo));

        // Editor para los botones de compra
        apodosT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(apodosT, estadoCeldasApodo, usuario));

        insertarApodosBaseDeDatos();

        // Llenar el estado de las celdas como no compradas inicialmente
        for (int i = 0; i < apodosT.getRowCount(); i++) {
            Point celda_comprada = new Point(i, 1); // Fila i, columna de acciones (1)
            estadoCeldasApodo.put(celda_comprada, false); // Inicializar como no comprada
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

        llenarTablaMonedas(modeloDinero);

        JScrollPane scrollMonedas = new JScrollPane(monedasT);
        JPanel panelMonedaConMargen = agregarMargen(scrollMonedas, 20, 250, 90, 250);
        panelMonedas.setBackground(Color.CYAN);
        panelMonedas.add(panelMonedaConMargen, BorderLayout.CENTER);

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

        monedasT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor(monedasT, estadoCeldasMoneda, usuario));


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

    public void insertarApodosBaseDeDatos() {
        Item[] apodos = {
                new Item("Batman", 200, "label", "textos/Batman.ttf"),
                new Item("Super Saiyan", 100, "label", "textos/Saiyan-Sans.ttf"),
                new Item("Vengador", 100, "label", "textos/AVENGEANCE HEROIC AVENGER.ttf"),
                new Item("Terminator", 100, "label", "textos/terminator real nfi.ttf"),
                new Item("Gladiador", 100, "label", "textos/Marav2.ttf"),
                new Item("Maestro", 100, "label", "textos/Karate.ttf")
        };

        for (Item apodo : apodos) {
            if (!BDs.itemExiste(apodo.getNombreItem())) {
                BDs.insertarItem("defaultUser", apodo.getNombreItem(), apodo.getPrecioItem(), apodo.getTipoItem(), apodo.getContenido());
                System.out.println("Apodo " + apodo.getNombreItem() + " insertado en la base de datos.");
            } else {
                System.out.println("Apodo " + apodo.getNombreItem() + " ya existe en la base de datos. No se insertará.");
            }
        }
        System.out.println("Apodos insertados en la base de datos.");

        // Forzar la recarga de datos en el modelo de la tabla
        modeloApodos.setRowCount(0); // Limpiar el modelo
        llenarTablaApodos(modeloApodos); // Volver a cargar los apodos desde la base de datos
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
        ArrayList<Item> comprasUsuario = BDs.obtenerCompras(usuario.getNombreUsuario()); // Obtener compras del usuario

        // Crear una lista con los nombres de los ítems comprados para facilitar la comparación
        HashSet<String> nombresComprados = new HashSet<>();
        for (Item comprado : comprasUsuario) {
            nombresComprados.add(comprado.getNombreItem());
        }

        if (listaItems.isEmpty()) {
            System.out.println("No hay ítems disponibles para mostrar.");
            return;
        }

        // Limpiar el modelo para evitar duplicados si se vuelve a llenar
        modeloIcono.setRowCount(0);

        for (Item item : listaItems) {
            boolean comprado = nombresComprados.contains(item.getNombreItem());

            // Aplicar el método cargarImagen al contenido del ítem
            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80);
            if (comprado) {
                // Si el ítem ya fue comprado, mostrar como "Comprado"
                modeloIcono.addRow(new Object[]{
                        icono, // Icono procesado como ImageIcon
                        "Comprado" // Mostrar el estado como comprado
                });
            } else {
                modeloIcono.addRow(new Object[]{
                        icono, // Icono procesado como ImageIcon
                        new Object[]{item.getPrecioItem(), cargarImagen("imagenes/coin_sin_fondo.png", 30, 30)} // Precio y moneda
                });
            }
        }
    }

    private void llenarTablaMonedas(DefaultTableModel modeloDinero) {
        // Obtener la lista de monedas desde la base de datos
        ArrayList<Item> listaMonedas = BDs.obtenerItemsPorTipo("moneda"); // Cambia "moneda" si tienes otro tipo en la BD

        for (Item item : listaMonedas) {
            // Aplicar el método cargarImagen al contenido del ítem
            ImageIcon icono = cargarImagen(item.getContenido(), 80, 80); // Ajusta el tamaño según necesidad
            modeloDinero.addRow(new Object[]{
                    icono, // Imagen del ítem procesada como ImageIcon
                    new Object[]{item.getPrecioItem(), cargarImagen("imagenes/coin_sin_fondo.png", 30, 30)} // Precio y moneda
            });
        }
    }

    private void llenarTablaApodos(DefaultTableModel modeloApodos) {
        try {
            // Verificar si hay datos en la base de datos
            ArrayList<Item> listaApodos = BDs.obtenerItemsPorTipo("label");
            if (listaApodos.isEmpty()) {
                System.out.println("No hay apodos disponibles para mostrar.");
                return;
            }

            // Cargar fuentes personalizadas
            Font customFontBatman = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Batman.ttf")).deriveFont(48f);
            Font customFontSs = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Saiyan-Sans.ttf")).deriveFont(48f);
            Font customFontAve = Font.createFont(Font.TRUETYPE_FONT, new File("textos/AVENGEANCE HEROIC AVENGER.ttf")).deriveFont(48f);
            Font customFontTer = Font.createFont(Font.TRUETYPE_FONT, new File("textos/terminator real nfi.ttf")).deriveFont(30f);
            Font customFontGlad = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Marav2.ttf")).deriveFont(40f);
            Font maestroFont = Font.createFont(Font.TRUETYPE_FONT, new File("textos/Karate.ttf")).deriveFont(40f);

            // Registrar fuentes en el sistema gráfico
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFontBatman);
            ge.registerFont(customFontSs);
            ge.registerFont(customFontAve);
            ge.registerFont(customFontTer);
            ge.registerFont(customFontGlad);
            ge.registerFont(maestroFont);

            // Mapear apodos con fuentes personalizadas
            Map<String, Font> fuentesApodos = new HashMap<>();
            fuentesApodos.put("Batman", customFontBatman);
            fuentesApodos.put("Super Saiyan", customFontSs);
            fuentesApodos.put("Vengador", customFontAve);
            fuentesApodos.put("Terminator", customFontTer);
            fuentesApodos.put("Gladiador", customFontGlad);
            fuentesApodos.put("Maestro", maestroFont);

            // Iterar sobre los apodos y agregarlos al modelo
            for (Item apodo : listaApodos) {
                Font fuente = fuentesApodos.getOrDefault(apodo.getNombreItem(), new Font("Arial", Font.PLAIN, 24));
                agregarApodo(modeloApodos, apodo.getNombreItem(), fuente, apodo.getPrecioItem(), "imagenes/coin_sin_fondo.png");
            }

        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar fuentes personalizadas: " + e.getMessage());
        }
    }

    private void agregarApodo(DefaultTableModel modeloApodos, String texto, Font fuente, int precio, String rutaIcono) {
        JLabel apodoLabel = new JLabel(texto);
        apodoLabel.setFont(fuente);
        JPanel apodoPanel = new JPanel();
        apodoPanel.setBackground(Color.WHITE);
        apodoPanel.add(apodoLabel);
        modeloApodos.addRow(new Object[]{
                apodoPanel, // Apodo renderizado en un JPanel
                new Object[]{precio, cargarImagen(rutaIcono, 30, 30)} // Precio y moneda
        });
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

    private class ApodoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                // Si el valor es un JPanel, se devuelve directamente
                return (JPanel) value;
            } else {
                // Renderizado predeterminado
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
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
        private JTable table; // Parámetro para la tabla
        private Map<Point, Boolean> estadoCeldas; // Parámetro para el estado de las celdas
        private Usuario usuario; // Usuario actual

        public ButtonCellEditor(JTable table, Map<Point, Boolean> estadoCeldas, Usuario usuario) {
            this.table = table;
            this.estadoCeldas = estadoCeldas;
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
        Usuario usuario = new Usuario(null, null, null);
        VentanaTienda2 ventana = new VentanaTienda2(usuario);
        ventana.setVisible(true);
    }
}