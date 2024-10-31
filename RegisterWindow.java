import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class RegisterWindow extends JFrame {

    public RegisterWindow() {

        setTitle("WEDO - Registrate");
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

        // Crear email input
        JLabel email = new JLabel("Email");
        JTextField emailTF = new JTextField(50);

        email.setBounds(180, 210, 200, 20);
        email.setFont(new Font("Monospaced", Font.PLAIN, 16));
        emailTF.setBounds(300, 210, 250, 20);

        panel.add(email);
        panel.add(emailTF);

        // Crear el password input
        JLabel password = new JLabel("Contraseña");
        JPasswordField passwordTF = new JPasswordField(50);

        password.setBounds(180, 250, 200, 20);
        password.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordTF.setBounds(300, 250, 250, 20);

        panel.add(password);
        panel.add(passwordTF);

        // Crear el enter button
        JButton loginButton = new JButton("Registrarse");
        loginButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        loginButton.setSize(130, 50);
        loginButton.setLocation(350 - loginButton.getWidth()/2, 290);

        panel.add(loginButton);

        // Temporal hashmap for user/password
        HashMap<String, String> users = new HashMap<>();
        users.put("juanpcomella", "password");
        users.put("adrianbaz", "12345");
        users.put("anderorma", "contraseña");
        users.put("ikergamboa", "98765");

        // Login authentication logic
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTF.getText();
                String email = emailTF.getText();
                String password = new String(passwordTF.getPassword());


                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor rellena todos los datos.");
                } else if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(null, "Ese usuario ya existe");
                }else{
                    JOptionPane.showMessageDialog(null, "Cuenta Creada! \nBienvenido a WEDO!");
                }
            }
        });

        JButton volverButton = new JButton("<< Volver");
        volverButton.setFont(new Font("Monospaced", Font.PLAIN, 10));
        volverButton.setSize(100, 30);
        volverButton.setLocation(70 - volverButton.getWidth()/2, 15);

        volverButton.setContentAreaFilled(false);
        volverButton.setBorderPainted(false);
        volverButton.setFocusable(false);


        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrimeraVentana primeraVentana = new PrimeraVentana();
                primeraVentana.setLocationRelativeTo(null);
                primeraVentana.setVisible(true);

                dispose();
            }
        });

        panel.add(volverButton);


    }

    public static void main(String[] args) {
        RegisterWindow window = new RegisterWindow();

        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
