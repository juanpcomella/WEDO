package ProfileWindow;

package ProfileWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import BaseDeDatos.BDs;
import StartingWindows.Usuario;
import VentanaTienda.Item;

public class SeleccionarIconoPerfil extends JFrame {

    private DefaultTableModel modeloIconos;
    private JTable tablaIconos;
    private Usuario usuario;

    public SeleccionarIconoPerfil(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Seleccionar Ícono de Perfil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar la tabla de íconos
        modeloIconos = new DefaultTableModel(new Object[]{"Ícono", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición de las celdas
            }
        };
        tablaIconos = new JTable(modeloIconos);
        tablaIconos.setRowHeight(80);

        JScrollPane scrollPane = new JScrollPane(tablaIconos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para confirmar selección
        JButton btnSeleccionar = new JButton("Seleccionar Ícono");
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarIcono();
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnSeleccionar);
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar íconos comprados del usuario
        cargarIconosComprados();
    }

    private void cargarIconosComprados() {
        // Obtener los íconos comprados desde la base de datos
        ArrayList<Item> iconosComprados = BDs.obtenerComprasPorUsuarioYTipo(usuario.getNombreUsuario(), "foto");

        // Limpiar la tabla
        modeloIconos.setRowCount(0);

        // Agregar íconos comprados al modelo de la tabla
        for (Item item : iconosComprados) {
            ImageIcon icono = new ImageIcon(item.getContenido());
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            modeloIconos.addRow(new Object[]{new ImageIcon(img), item.getNombreItem()});
        }
    }

    private void seleccionarIcono() {
        int filaSeleccionada = tablaIconos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ícono.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el nombre del ícono seleccionado
        String nombreIcono = (String) modeloIconos.getValueAt(filaSeleccionada, 1);

        // Actualizar el ícono de perfil del usuario en la base de datos
        BDs.actualizarIconoPerfil(usuario.getNombreUsuario(), nombreIcono);

        JOptionPane.showMessageDialog(this, "¡Ícono de perfil actualizado!");
        dispose(); // Cerrar la ventana
    }
}
