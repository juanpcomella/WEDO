package MainWindow;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class tiendaWindow extends JFrame { 

    public tiendaWindow() {
        setTitle("WEDO");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel();
        JPanel panelSur = new JPanel();
        JPanel panelOeste = new JPanel();
        JPanel panelCentro = new JPanel(); 
        JPanel panelEste = new JPanel();

        getContentPane().add(panelNorte, BorderLayout.NORTH);
       
        getContentPane().add(panelSur, BorderLayout.SOUTH);
        getContentPane().add(panelOeste, BorderLayout.WEST);
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        getContentPane().add(panelEste, BorderLayout.EAST);
        
        JLabel labelBienvenida = new JLabel("BIENVENIDO A LA TIENDA DE WEDO");
        labelBienvenida.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        panelNorte.add(labelBienvenida);
    }

    public static void main(String[] args) {
        tiendaWindow window = new tiendaWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true); 
    }
}
