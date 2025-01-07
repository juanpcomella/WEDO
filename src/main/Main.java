package src.main;

import src.gui.StartingWindows.VentanaBienvenida;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Crear la ventana principal
            SwingUtilities.invokeLater(() -> {
                // Tu ventana principal
                VentanaBienvenida ventanaBienvenida = new VentanaBienvenida();
                ventanaBienvenida.setVisible(true);
                ventanaBienvenida.setLocationRelativeTo(null);

            });
        }

    }

