import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VentanaRegistroDef extends JFrame {

    public VentanaRegistroDef() {
        setTitle("WEDO - Registrate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton volverButton = new JButton("<< Volver");
        volverButton.setFont(new Font("Monospaced", Font.PLAIN, 10));
        volverButton.setContentAreaFilled(false);
        volverButton.setBorderPainted(false);
        volverButton.setFocusable(false);
        panelVolver.add(volverButton);
        panel.add(panelVolver, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridBagLayout());
        datos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("Bienvenido a WEDO", JLabel.CENTER);
        titulo.setFont(new Font("Monospaced", Font.PLAIN, 16));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 10, 5, 10);
        datos.add(titulo, gbc);

        JLabel introducirDatos = new JLabel("Introduce tus datos", JLabel.CENTER);
        introducirDatos.setFont(new Font("Monospaced", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10);
        datos.add(introducirDatos, gbc);

        // Espacio entre texto e inputs, aquí va el logo.
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridheight = 1;
        datos.add(Box.createVerticalStrut(20), gbc);

        gbc.gridwidth = 1;

        JLabel username = new JLabel("Usuario");
        username.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 0, 10);
        datos.add(username, gbc);

        JTextField usernameTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(usernameTF, gbc);

        JLabel email = new JLabel("Email");
        email.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(email, gbc);

        JTextField emailTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(emailTF, gbc);

        JLabel password = new JLabel("Contraseña");
        password.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(password, gbc);

        JPasswordField passwordTF = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordTF, gbc);

        JLabel passwordConf = new JLabel("Confirmar Contraseña");
        passwordConf.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(passwordConf, gbc);

        JPasswordField passwordConfTF = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordConfTF, gbc);

        JButton registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        registerButton.setPreferredSize(new Dimension(150, 50));
        registerButton.setMaximumSize(new Dimension(150, 50));
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        datos.add(registerButton, gbc);

        // Lógica de Validación
        // Estos usuarios y contraseñas son temporales, luego se implementarán en la base de datos.
        HashMap<String, String> users = new HashMap<>();
        users.put("juanpcomella", "password");
        users.put("adrianbaz", "12345");
        users.put("anderorma", "contraseña");
        users.put("ikergamboa", "98765");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTF.getText();
                String email = emailTF.getText();
                String password = new String(passwordTF.getPassword());


                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor rellena todos los datos.");
                } else if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(null, "Ese usuario ya existe");
                }else if (passwordTF != passwordConfTF) {
                    JOptionPane.showMessageDialog(null,"Las contraseñas no son iguales.");
                }else{
                    JOptionPane.showMessageDialog(null, "Cuenta Creada! \nBienvenido a WEDO!");
                }
            }
        });

        panel.add(datos, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        VentanaRegistroDef window = new VentanaRegistroDef();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
