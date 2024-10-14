import javax.swing.*;
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

        // Añadir Logo

        // Crear Username input
        JLabel username = new JLabel("Usuario");
        JTextField usernameTF = new JTextField(50);

        username.setBounds(180, 170, 200, 20);
        username.setFont(new Font("Monospaced", Font.PLAIN, 16));
        usernameTF.setBounds(300, 170, 250, 20);

        panel.add(username);
        panel.add(usernameTF);

        // Crear el password input
        JLabel password = new JLabel("Contraseña");
        JPasswordField passwordTF = new JPasswordField(50);

        password.setBounds(180, 210, 200, 20);
        password.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordTF.setBounds(300, 210, 250, 20);

        panel.add(password);
        panel.add(passwordTF);

        // Crear el enter button
        JButton login = new JButton("Login");
        login.setFont(new Font("Monospaced", Font.PLAIN, 16));
        login.setSize(100, 50);
        login.setLocation(350 - login.getWidth()/2, 250);

        panel.add(login);


    }

    public static void main(String[] args) {
        LoginWindow window = new LoginWindow();

        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
