package MainWindow;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class RightSideBar extends JPanel {

    public RightSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        JPanel objetivos = new JPanel();
        objetivos.setLayout(new BoxLayout(objetivos, BoxLayout.Y_AXIS));
        objetivos.setBackground(Color.DARK_GRAY);
        objetivos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel objetivosLabel = new JLabel("Objetivos");
        objetivosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        objetivosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        objetivosLabel.setForeground(Color.WHITE);
        objetivos.add(objetivosLabel);

        JTextArea objetivosText = new JTextArea(30, 10);
        JScrollPane objetivosScrollPane = new JScrollPane(objetivosText);
        objetivosText.setEditable(false);
        objetivosText.setFont(new Font("Arial", Font.PLAIN, 18));
        objetivosText.setLineWrap(true);
        objetivosText.setWrapStyleWord(true);

        // El hacer que el TextArea actúe como lista enumerada se hizo con ayuda de chatgpt
        objetivosText.setText("1. ");
        objetivosText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Evitar que JTextArea añada una nueva línea automáticamente
                    String[] lines = objetivosText.getText().split("\n");
                    int lineNumber = lines.length + 1; // Calcular el número de la nueva línea
                    objetivosText.append("\n" + lineNumber + ". ");
                }
            }
        });

        objetivos.add(objetivosScrollPane);

        JButton objetivosButton = new JButton("Editar Objetivos");
        objetivosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        objetivosButton.setFont(new Font("Arial", Font.PLAIN, 14));
        boolean objBtnEditable[] = {false};

        objetivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(objBtnEditable[0]) {
                    objetivosText.setEditable(true);
                    objetivosButton.setText("Guardar");
                }
                else{
                    objetivosText.setEditable(false);
                    objetivosButton.setText("Editar Objetivos");
                }
                objBtnEditable[0] = !objBtnEditable[0];
            }
        });

        objetivos.add(objetivosButton);

        add(objetivos);

        JPanel habitos = new JPanel();
        habitos.setLayout(new BoxLayout(habitos, BoxLayout.Y_AXIS));
        habitos.setBackground(Color.DARK_GRAY);
        habitos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel habitosLabel = new JLabel("Habitos");
        habitosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        habitosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        habitosLabel.setForeground(Color.WHITE);
        habitos.add(habitosLabel);

        JTextArea habitosText = new JTextArea(30, 10);
        JScrollPane habitosScrollPane = new JScrollPane(habitosText);
        habitosText.setEditable(false);
        habitosText.setFont(new Font("Arial", Font.PLAIN, 18));
        habitosText.setLineWrap(true);
        habitosText.setWrapStyleWord(true);

        habitosText.setText("1. ");
        habitosText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Evitar que JTextArea añada una nueva línea automáticamente
                    String[] lines = habitosText.getText().split("\n");
                    int lineNumber = lines.length + 1; // Calcular el número de la nueva línea
                    habitosText.append("\n" + lineNumber + ". ");
                }
            }
        });

        habitos.add(habitosScrollPane);

        JButton habitosBoton = new JButton("Editar Habitos");
        habitosBoton.setAlignmentX(Component.CENTER_ALIGNMENT);
        habitosBoton.setFont(new Font("Arial", Font.PLAIN, 14));
        boolean habBtnEditable[] = {false};

        habitosBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(habBtnEditable[0]) {
                    habitosText.setEditable(true);
                    habitosBoton.setText("Guardar");
                }
                else{
                    habitosText.setEditable(false);
                    habitosBoton.setText("Editar Habitos");
                }
                habBtnEditable[0] = !habBtnEditable[0];
            }
        });

        habitos.add(habitosBoton);

        add(habitos);
    }
}