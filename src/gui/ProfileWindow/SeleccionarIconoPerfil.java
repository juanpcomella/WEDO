package src.gui.ProfileWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import src.db.BDs;
import src.domain.Usuario;
import src.domain.Item;

public class SeleccionarIconoPerfil extends JFrame {

    private JPanel panelIconos;
    private Usuario usuario;
    private String iconoSeleccionado;

    public SeleccionarIconoPerfil(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Seleccionar Ícono de Perfil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para mostrar los botones de íconos
        panelIconos = new JPanel();
        panelIconos.setLayout(new GridLayout(3, 3, 10, 10)); // Configuración de la cuadrícula
        panelIconos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(panelIconos), BorderLayout.CENTER);

        // Botón para guardar la selección
        JButton btnGuardar = new JButton("Guardar Selección");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarIconoSeleccionado();
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnGuardar);
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar los íconos comprados del usuario
        cargarIconosComprados();
    }

    private void cargarIconosComprados() {
        // Obtener los íconos comprados desde la base de datos
        ArrayList<Item> iconosComprados = BDs.obtenerComprasPorUsuarioYTipo(usuario.getNombreUsuario(), "foto");

        // Limpiar el panel de íconos
        panelIconos.removeAll();

        // Crear un botón por cada ícono comprado
        for (Item item : iconosComprados) {
            ImageIcon icono = new ImageIcon(item.getContenido());
            Image img = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JButton botonIcono = new JButton(new ImageIcon(img));
            botonIcono.setToolTipText(item.getNombreItem()); // Nombre del ícono como tooltip
            botonIcono.setFocusPainted(false);
            botonIcono.setContentAreaFilled(false);
            botonIcono.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            // Añadir ActionListener para seleccionar el ícono
            botonIcono.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    iconoSeleccionado = item.getNombreItem(); // Guardar el nombre del ícono seleccionado
                    actualizarSeleccion(botonIcono); // Resaltar el ícono seleccionado
                }
            });

            panelIconos.add(botonIcono);
        }

        // Refrescar el panel
        panelIconos.revalidate();
        panelIconos.repaint();
    }

    private void actualizarSeleccion(JButton botonSeleccionado) {
        // Quitar el borde resaltado de todos los botones
        for (Component componente : panelIconos.getComponents()) {
            if (componente instanceof JButton) {
                ((JButton) componente).setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }
        // Resaltar el botón seleccionado
        botonSeleccionado.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
    }

    private void guardarIconoSeleccionado() {
        if (iconoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un ícono antes de guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Actualizar el ícono de perfil del usuario en la base de datos
        BDs.actualizarIconoPerfil(usuario.getNombreUsuario(), iconoSeleccionado);

        JOptionPane.showMessageDialog(this, "¡Ícono de perfil actualizado!");
        dispose(); // Cerrar la ventana
    }


}
