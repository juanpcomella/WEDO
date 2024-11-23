import javax.swing.*;
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

public class ventanaTienda extends JFrame {
	private int hoveredColumn = -1;
	private int hoveredRow = -1;

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
        DefaultTableModel modeloIcono = new DefaultTableModel(new Object[]{"Icono", "Precio"}, 0) {
        	public boolean isCellEditable (int row, int column) {
        		return false;
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
        
        //ocultamos los tableheaders:
        iconoT.getTableHeader().setVisible(false);
        iconoT.getTableHeader().setPreferredSize(new Dimension(0,0));
        iconoT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        iconoT.setRowHeight(100);
        panelIcono.add(new JScrollPane(iconoT), BorderLayout.CENTER);

        // Cargar una imagen y redimensionarla
        ImageIcon icon1 = new ImageIcon("imagenes/Imagen1.png");
        Image img = icon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(img);

        ImageIcon icon2 = new ImageIcon("imagenes/coin.jpg");
        img = icon2.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img);

        // Agregar ejemplos de filas con un icono y precio
        modeloIcono.addRow(new Object[]{icon1, new Object[]{70, icon2}});
        modeloIcono.addRow(new Object[]{icon1, new Object[]{100, icon2}});

        // Renderizador para la columna de "Precio" (JPanel con número e imagen)
        iconoT.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                //crear un panel para la celda
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                if (column == hoveredColumn && row == hoveredRow) {
                	JButton comprarB = new JButton("Comprar");
                	return comprarB;
                } else {
                	
                }
						
						
						// TODO Auto-generated method stub
					

                //asegurar colores de selección
                if (isSelected) {
                    panel.setBackground(table.getSelectionBackground());
                } else {
                    panel.setBackground(table.getBackground());
                }

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
            

                return panel;
            }
        });

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
        
		iconoT.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
		    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		    if (column == hoveredColumn && row == hoveredRow) {
		    	JButton comprarB = new JButton("Comprar");
		    	comprarB.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea comprar?","Comprar",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE );
						
						// TODO Auto-generated method stub
						
					}
				});
		    }
		    
		    	
		 
		    	return table;

        };
		});
		class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
		    private JButton button;

		    public ButtonCellEditor() {
		    	
		        button = new JButton("Comprar");

		        // Acción del botón
		        button.addActionListener(e -> {
		            // Aquí defines la lógica del botón
		            System.out.println("¡Botón presionado en la tabla!");
		            fireEditingStopped(); // Finaliza la edición al hacer clic
		        });
		    }

			@Override
			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return button;
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				// TODO Auto-generated method stub
				if (column == hoveredColumn && row == hoveredRow) {
					value = button;
				}
				return button;
			}}
		iconoT.getColumnModel().getColumn(1).setCellEditor(new ButtonCellEditor());

		
		
		
		
        //panel para los apodos
        JPanel panelApodos = new JPanel();
        tabbedPane.addTab("Apodos", panelApodos);

        //panel para la temática
        JPanel panelTematica = new JPanel();
        tabbedPane.addTab("Temática", panelTematica);

        //agregar el tabbedPane al frame
        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
    	
        ventanaTienda ventana = new ventanaTienda();
        ventana.setVisible(true);
    }
}