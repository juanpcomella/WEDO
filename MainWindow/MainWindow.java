package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame{
	
    private JDateChooser dateChooser;
    
    public MainWindow() {
        setTitle("WEDO");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        //APARTADO BOTON MAS, IZQUIERDA DE LA PANTALLA
        JButton botonMas = new JButton("+");
        JPopupMenu menuEventos = new JPopupMenu();
        JMenuItem opcion1 = new JMenuItem("Añadir evento personal");
        JMenuItem opcion2 = new JMenuItem("Añadir evento grupal");
        menuEventos.add(opcion1);
        menuEventos.add(opcion2);
        botonMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                menuEventos.show(botonMas, botonMas.getWidth() / 2, botonMas.getHeight() / 2);
            }
        });
        opcion1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ventanaEventoPersonal();
            }
        });
        opcion2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ventanaEventoGrupal();
            }
        });
        //APARTADO PRINCIPAL (CALENDARIO)
        //APARTADO TIENDA, ARRIBA DE LA PANTALLA
        JButton botonTienda = new JButton();
        botonTienda.setSize(200,100);
        JPopupMenu menuTienda = new JPopupMenu();
        menuTienda.setLayout(new GridLayout(2,1));
        JPanel panelMonedas = new JPanel();
        panelMonedas.setLayout(new GridLayout(1,2));
        JMenuItem monedas =  new JMenuItem("Monedas disponibles: ");
        JMenuItem monedasUsuario = new JMenuItem("XX MONEDAS");
        panelMonedas.add(monedas);
        panelMonedas.add(monedasUsuario);
        menuTienda.add(panelMonedas);
        JMenuItem verTienda = new JMenuItem("Ver tienda completa");
        menuTienda.add(verTienda);
        verTienda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ventanaTienda();
            }
        });
        botonTienda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                menuTienda.show(botonTienda, botonTienda.getHeight()/2, botonTienda.getWidth()/2);
            }
        });
        panel.add(botonTienda);
        panel.add(botonMas);
        //APARTADO HABIT TRACKER, ARRIBA DE LA PANTALLA
    }
    private void ventanaEventoPersonal() {
        JDialog ventanaEmergente = new JDialog();
        ventanaEmergente.setTitle("Nuevo Evento");
        ventanaEmergente.setSize(400, 300);
        ventanaEmergente.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1,3));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,3));
        JLabel etiquetaNombre = new JLabel("Nombre del evento:");
        JTextField campoNombre = new JTextField();

        JLabel etiquetaFecha = new JLabel("Fecha:");
        dateChooser = new JDateChooser();
        JLabel fechaInicio = new JLabel("Hora de inicio");
        Integer[] horasArray = new Integer[25];
        for (int i = 0; i < 25; i++) {
            horasArray[i] = i;
        }
        Integer[] minutosArray = new Integer[61];
        for (int i = 0; i < 61; i++) {
            minutosArray[i] = i;
        }
        JComboBox<Integer> horas = new JComboBox<Integer>(horasArray);
        JComboBox<Integer> minutos = new JComboBox<Integer>(minutosArray);
        JComboBox<Integer> segundos = new JComboBox<Integer>(minutosArray);
        panel1.add(horas);
        panel1.add(minutos);
        panel1.add(segundos);
        JLabel fechafinal = new JLabel("Hora de finalización");
        JComboBox<Integer> horasFinal = new JComboBox<Integer>(horasArray);
        JComboBox<Integer> minutosFinal = new JComboBox<Integer>(minutosArray);
        JComboBox<Integer> segundosFinal = new JComboBox<Integer>(minutosArray);
        panel2.add(horasFinal);
        panel2.add(minutosFinal);
        panel2.add(segundosFinal);
        JLabel descripcion = new JLabel("Breve descripción");
        JTextField texto = new JTextField();
        JLabel etiquetaCategorias = new JLabel("Categoria");
        JComboBox<Categorias> categorias = new JComboBox<>(Categorias.values());
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEvento = campoNombre.getText();
                Date fechaEvento = dateChooser.getDate();
                if (nombreEvento.isEmpty() || fechaEvento == null) {
                    JOptionPane.showMessageDialog(ventanaEmergente, "Evento vacio, te falta por añadir el nombre del evento o la fecha");
                    ventanaEmergente.dispose();
                } else {
                    JOptionPane.showMessageDialog(ventanaEmergente, "Evento '" + nombreEvento + "' añadido con fecha: " + fechaEvento);
                    ventanaEmergente.dispose();
                }
            }
        });

        JPanel panelGuardar = new JPanel();
        botonGuardar.setBackground(Color.BLACK);
        panelGuardar.add(botonGuardar, CENTER_ALIGNMENT);
        panel.add(etiquetaNombre);
        panel.add(campoNombre);
        panel.add(etiquetaFecha);
        panel.add(dateChooser);
        panel.add(fechaInicio);
        panel.add(panel1);
        panel.add(fechafinal);
        panel.add(panel2);
        panel.add(descripcion);
        panel.add(texto);
        panel.add(etiquetaCategorias);
        panel.add(categorias);
        ventanaEmergente.add(panel);
    }
    private void ventanaEventoGrupal() {
        JDialog ventanaEmergente = new JDialog();
        ventanaEmergente.setSize(400, 300);
        ventanaEmergente.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1,3));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,3));
        JLabel etiquetaNombre = new JLabel("Nombre del evento:");
        JTextField campoNombre = new JTextField();

        JLabel etiquetaFecha = new JLabel("Fecha:");
        dateChooser = new JDateChooser();
        JLabel fechaInicio = new JLabel("Hora de inicio");
        Integer[] horasArray = new Integer[25];
        for (int i = 0; i < 25; i++) {
            horasArray[i] = i;
        }
        Integer[] minutosArray = new Integer[61];
        for (int i = 0; i < 61; i++) {
            minutosArray[i] = i;
        }
        JComboBox<Integer> horas = new JComboBox<Integer>(horasArray);
        JComboBox<Integer> minutos = new JComboBox<Integer>(minutosArray);
        JComboBox<Integer> segundos = new JComboBox<Integer>(minutosArray);
        panel1.add(horas);
        panel1.add(minutos);
        panel1.add(segundos);
        JLabel fechafinal = new JLabel("Hora de finalización");
        JComboBox<Integer> horasFinal = new JComboBox<Integer>(horasArray);
        JComboBox<Integer> minutosFinal = new JComboBox<Integer>(minutosArray);
        JComboBox<Integer> segundosFinal = new JComboBox<Integer>(minutosArray);
        panel2.add(horasFinal);
        panel2.add(minutosFinal);
        panel2.add(segundosFinal);
        JLabel descripcion = new JLabel("Breve descripción");
        JTextField texto = new JTextField();
        JLabel invitar = new JLabel("Invitar a");
        JTextField campoInvitar = new JTextField();
        JLabel etiquetaCategorias = new JLabel("Categoria");
        JComboBox<Categorias> categorias = new JComboBox<>(Categorias.values());
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEvento = campoNombre.getText();
                Date fechaEvento = dateChooser.getDate();
                if (nombreEvento.isEmpty() || fechaEvento == null) {
                    JOptionPane.showMessageDialog(ventanaEmergente, "Evento vacio, te falta por añadir el nombre del evento o la fecha");
                    ventanaEmergente.dispose();
                } else {
                    JOptionPane.showMessageDialog(ventanaEmergente, "Evento '" + nombreEvento + "' añadido con fecha: " + fechaEvento);
                    ventanaEmergente.dispose();
                }
            }
        });
        panel.add(etiquetaNombre);
        panel.add(campoNombre);
        panel.add(etiquetaFecha);
        panel.add(dateChooser);
        panel.add(fechaInicio);
        panel.add(panel1);
        panel.add(fechafinal);
        panel.add(panel2);
        panel.add(descripcion);
        panel.add(texto);
        panel.add(invitar);
        panel.add(campoInvitar);
        panel.add(etiquetaCategorias);
        panel.add(categorias);
        ventanaEmergente.add(panel);
    }

    private void ventanaTienda() {
        JPanel panel = new JPanel();
    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}