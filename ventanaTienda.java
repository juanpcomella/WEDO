import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ventanaTienda extends JFrame {

    public ventanaTienda() {
        // Configuración de la ventana principal
        setTitle("Tienda");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TabbedPane para las secciones
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel para los iconos
        JPanel panelIcono = new JPanel(new BorderLayout());
        tabbedPane.addTab("Iconos de perfil", panelIcono);

        // Modelo de la tabla de iconos
        DefaultTableModel modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0);
        JTable iconoT = new JTable(modeloIcono);
        iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        iconoT.setRowHeight(100);
        panelIcono.add(new JScrollPane(iconoT), BorderLayout.CENTER);

        // Cargar una imagen y redimensionarla
        ImageIcon icon1 = new ImageIcon("Imagen1.png");
        Image img = icon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(img);

        ImageIcon icon2 = new ImageIcon("Imagen2.png");
        img = icon2.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img);

        // Agregar ejemplos de filas con un icono y precio
        modeloIcono.addRow(new Object[]{icon1, new Object[]{70, icon1}});
        modeloIcono.addRow(new Object[]{icon2, new Object[]{100, icon2}});

        // Renderizador para la columna de "Precio" (JPanel con número e imagen)
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                // Crear un panel para la celda
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                // Asegurar colores de selección
                if (isSelected) {
                    panel.setBackground(table.getSelectionBackground());
                } else {
                    panel.setBackground(table.getBackground());
                }

                // Verificar que el valor sea un Object[]
                if (value instanceof Object[]) {
                    Object[] cellData = (Object[]) value;

                    // Agregar el número al panel
                    JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                    panel.add(numberLabel);

                    // Agregar la imagen al panel
                    if (cellData[1] instanceof Icon) {
                        JLabel iconLabel = new JLabel((Icon) cellData[1]);
                        panel.add(iconLabel);
                    }
                }

                return panel;
            }
        });

        // Renderizador para la columna de "Icono" (solo imagen)
        iconoT.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                if (value instanceof Icon) {
                    return new JLabel((Icon) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        // Panel para los apodos
        JPanel panelApodos = new JPanel();
        tabbedPane.addTab("Apodos", panelApodos);

        // Panel para la temática
        JPanel panelTematica = new JPanel();
        tabbedPane.addTab("Temática", panelTematica);

        // Agregar el tabbedPane al frame
        add(tabbedPane);

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        // Crear e iniciar la ventana
        ventanaTienda ventana = new ventanaTienda();
        ventana.setVisible(true);
    }
}
