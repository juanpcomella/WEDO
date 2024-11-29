package VentanaTienda;

import javax.swing.*;
import javax.swing.border.Border;
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
    private Map<Point, Boolean> estadoCeldas = new HashMap<>();
    private boolean compra;

    public VentanaTienda() {
        // Configuración de la ventana principal
        setTitle("Tienda");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Marcar el estado de las celdas
        Map<Point, Boolean> estadoCeldas = new HashMap<>();

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
        iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        iconoT.setRowHeight(100);

        panelIcono.add(new JScrollPane(iconoT), BorderLayout.CENTER);

        // Dinero almacenado aquí, luego se hará un parseInt para convertir
        String dinero = "0";

        // Parseo de dinero
        int money = Integer.parseInt(dinero);

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

        ImageIcon icon2 = cargarImagen("imagenes/coin.jpg", 50, 50);

        // Agregar ejemplos de filas con un icono y precio
        modeloIcono.addRow(new Object[]{logoHomer, new Object[]{85, icon2}});
        modeloIcono.addRow(new Object[]{logoOso, new Object[]{120, icon2}});
        modeloIcono.addRow(new Object[]{logoPerro, new Object[]{200, icon2}});
        modeloIcono.addRow(new Object[]{logoUnicornio, new Object[]{70, icon2}});
        modeloIcono.addRow(new Object[]{logoPlayaPalmeras, new Object[]{140, icon2}});
        modeloIcono.addRow(new Object[]{perfilMonje, new Object[]{300, icon2}});

        // Renderizador para la columna de "Precio" (JPanel con número e imagen)
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                if (selectedRow != -1 && row == selectedRow && compra) {
                    JLabel comprado = new JLabel("Comprado");
                	iconoT.setValueAt(comprado, selectedRow, 1);
                    return comprado;
                }

                if (column == hoveredColumn && row == hoveredRow) {
                    JButton comprarB = new JButton("Comprar");
                    return comprarB;
                } else {
                    if (value instanceof Object[]) {
                        Object[] cellData = (Object[]) value;
                        JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                        panel.add(numberLabel);

                        if (cellData[1] instanceof Icon) {
                            JLabel iconLabel = new JLabel((Icon) cellData[1]);
                            panel.add(iconLabel);
                        }
                    }
                }
                return panel;
            }
        });

        JLabel dineroL = new JLabel(icon2);
        JPanel panelNorteIcono = new JPanel();
        JLabel stringDinero = new JLabel(dinero);
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);
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
            private Object componente;

            public ButtonCellEditor() {
                button = new JButton("Comprar");
                button.addActionListener(e -> {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea comprar?", "Comprar", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        comprado = new JLabel("comprado");
                        componente = iconoT.getValueAt(selectedRow, 1);
                        if (componente ==comprado) {
                        	JOptionPane.showMessageDialog(null, "Ya ha comprado este objeto", "Error", JOptionPane.ERROR_MESSAGE);
                            iconoT.setValueAt(comprado, selectedRow, 1);

                        }
                        System.out.println("Compra realizada.");
                        estadoCeldas.put(celda, true);
                        compra = true;
                        iconoT.setValueAt(comprado, selectedRow, 1);
                        panel.repaint();
                    } else {
                        System.out.println("Compra cancelada.");
                        compra = false;
                        estadoCeldas.put(celda, false);
                    }
                    fireEditingStopped();
                });

                panel = new JPanel(new BorderLayout());
                panel.add(button, BorderLayout.CENTER);
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                selectedRow = row;
                button.setText("Comprar");
                valorOriginal = value;
                    return panel;

                
            }

            @Override
            public Object getCellEditorValue() {
                return valorOriginal;
            }
        }

        iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor());

        // Panel para los apodos
        JPanel panelApodos = new JPanel();
        tabbedPane.addTab("Apodos", panelApodos);

        // Panel para la temática
        JPanel panelTematica = new JPanel();
        tabbedPane.addTab("Temática", panelTematica);

        // Agregar el tabbedPane al frame
        add(tabbedPane);
        setVisible(true);
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
