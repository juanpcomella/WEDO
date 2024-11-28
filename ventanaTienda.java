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

public class ventanaTienda extends JFrame {
private int hoveredColumn = -1;
private int hoveredRow = -1;
private int selectedRow = -1;
private int selectedColumn = -1;
private Map <Point, Boolean> estadoCeldas = new HashMap<>();
private boolean compra;

    public ventanaTienda() {
        // Configuración de la ventana principal
        setTitle("Tienda");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       
        //marcar el estado de las celdas
        Map<Point, Boolean> estadoCeldas = new HashMap<>();


        // TabbedPane para las secciones
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel para los iconos
        JPanel panelIcono = new JPanel(new BorderLayout());
        tabbedPane.addTab("Iconos de perfil", panelIcono);
       
        //Panel para l

        // Modelo de la tabla de iconos
        DefaultTableModel modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0) {
        public boolean isCellEditable (int row, int column) {
        if (column ==0) {
        return false;
        } else {
        return true;
        }
        }
        };
        JTable iconoT = new JTable(modeloIcono);
       
        iconoT.addMouseMotionListener(new MouseMotionListener() {

@Override
public void mouseMoved(MouseEvent e) {
int row = iconoT.rowAtPoint(e.getPoint());
int column = iconoT.columnAtPoint(e.getPoint());
if (column ==1) {
hoveredRow = row;
hoveredColumn = column;
} else {
hoveredRow = -1;
hoveredColumn = -1;
}
iconoT.repaint();
// TODO Auto-generated method stub

}

@Override
public void mouseDragged(MouseEvent e) {
// TODO Auto-generated method stub

}
});
        Point celda = new Point(hoveredRow, hoveredColumn);
       
        //ocultamos los tableheaders:
        iconoT.getTableHeader().setVisible(false);
        iconoT.getTableHeader().setPreferredSize(new Dimension(0,0));
        iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        iconoT.setRowHeight(100);
        panelIcono.add(new JScrollPane(iconoT), BorderLayout.CENTER);
       
        //DINERO ALMACENADO AQUI, LUEGO SE HARÁ UN PARSEINT PARA CONVERTIR
        String dinero = "0";
        //Aqui el parseint
        int money = Integer.parseInt(dinero);


        // Cargar una imagen y redimensionarla
        ImageIcon icon1 = new ImageIcon("imagenes/Imagen1.png");
        Image img = icon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(img);
       
        ImageIcon logoOso = new ImageIcon("imagenes/logoOso.png");
        Image imgOso = logoOso.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoOso = new ImageIcon(imgOso);

        ImageIcon logoPerro = new ImageIcon("imagenes/logoPerro.png");
        Image imgPerro = logoPerro.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoPerro = new ImageIcon(imgPerro);

        ImageIcon logoPanda = new ImageIcon("imagenes/logoPanda.png");
        Image imgPanda = logoPanda.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoPanda = new ImageIcon(imgPanda);

        ImageIcon logoUnicornio = new ImageIcon("imagenes/logoUnicornio.png");
        Image imgUnicornio = logoUnicornio.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoUnicornio = new ImageIcon(imgUnicornio);

        ImageIcon perfilMonje = new ImageIcon("imagenes/perfilMonje.png");
        Image imgMonje = perfilMonje.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        perfilMonje = new ImageIcon(imgMonje);

        ImageIcon logoPlayaPalmeras = new ImageIcon("imagenes/logoPlayaPalmeras.png");
        Image imgPlayaPalmeras = logoPlayaPalmeras.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoPlayaPalmeras = new ImageIcon(imgPlayaPalmeras);

        ImageIcon logoHomer = new ImageIcon("imagenes/logoHomer.png");
        Image imgHomer = logoHomer.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoHomer = new ImageIcon(imgHomer);

        ImageIcon icon2 = new ImageIcon("imagenes/coin.jpg");
        img = icon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img);
       
       

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
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                //crear un panel para la celda
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                if(selectedRow != -1 && row == selectedRow) {
                JLabel comprado = new JLabel ("Comprado");
                return comprado; //CENTRALIZAR VARIABLES E INICIALIZAR VARIABLES DE SELECCION PARA SABER LA FILA QUE ESTA SELECCIONADA
               
                }
               
                if (column == hoveredColumn && row == hoveredRow) {
                JButton comprarB = new JButton("Comprar");
                return comprarB;
                } else {
                    //verificar que el valor sea un Object[]
                    if (value instanceof Object[]) {
                        Object[] cellData = (Object[]) value;

                        // Agregar el número al panel
                        JLabel numberLabel = new JLabel(String.valueOf(cellData[0]));
                        panel.add(numberLabel);
                       

                        // Agregar la imagen al panel
                        if (cellData[1] instanceof Icon) {
                        Image img = ((ImageIcon) cellData[1]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                            JLabel iconLabel = new JLabel((Icon)cellData[1]);
                            panel.add(iconLabel);
                        }
                }


// TODO Auto-generated method stub


                //asegurar colores de selección
               
                }

                return panel;
            }
        });
       
        JLabel dineroL = new JLabel(icon2);
       
        JPanel panelNorteIcono = new JPanel();
       
        JLabel stringDinero = new JLabel(dinero);
       
        panelNorteIcono.add(dineroL);
        panelNorteIcono.add(stringDinero);
       
        panelIcono.add(panelNorteIcono,BorderLayout.NORTH);
       
       

       
        //renderizador para la columna de "Icono" (solo imagen)
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

//FUNCIONALIDAD DEL BOTON
class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
   private JPanel panel;
   private JButton button;
   private Object valorOriginal;
   private boolean comprado;

   public ButtonCellEditor() {
       //crear el botón y configurarlo
       button = new JButton("Comprar");
       button.addActionListener(e -> {
           // Acción del botón (mostrar diálogo, etc.)
           int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea comprar?", "Comprar", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
           
           if (respuesta == JOptionPane.YES_OPTION) {
               System.out.println("Compra realizada.");
               estadoCeldas.put(celda, true);
               compra = true;
               
               panel.repaint();
               
               
           } else {
               System.out.println("Compra cancelada.");
               compra = false;
               estadoCeldas.put(celda, false);
               
           }

           fireEditingStopped(); // Finalizar edición después del clic
       });

       // Crear el panel y agregar el botón
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
   
       return valorOriginal; // Devuelve un valor si es necesario
   }
}
iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor());



        //panel para los apodos
        JPanel panelApodos = new JPanel();
        tabbedPane.addTab("Apodos", panelApodos);

        //panel para la temática
        JPanel panelTematica = new JPanel();
        tabbedPane.addTab("Temática", panelTematica);

        //agregar el tabbedPane al framee
        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
   
        ventanaTienda ventana = new ventanaTienda();
        ventana.setVisible(true);
    }
}