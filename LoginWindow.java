import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {

        setTitle("Inserta tu Usuario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // Crear Titulo
        JLabel titulo = new JLabel("Bienvenido a WEDO", JLabel.CENTER);
        titulo.setFont(new Font("Monospaced", Font.PLAIN, 20));
        titulo.setBounds(496/2,60, 204, 100);

        panel.add(titulo);

        // Crear Username input
        JLabel username = new JLabel("Usuario");
        JTextField usernameTF = new JTextField(20);



    }

    public static void main(String[] args) {
        LoginWindow window = new LoginWindow();

        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
