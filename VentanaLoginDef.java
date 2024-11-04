import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VentanaLoginDef extends JFrame {

    public VentanaLoginDef() {
        setTitle("WEDO - Inicia Sesión");
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

        JLabel username = new JLabel("Usuario o Email");
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

        JLabel password = new JLabel("Contraseña");
        password.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10,0 , 10);
        datos.add(password, gbc);

        JPasswordField passwordTF = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(passwordTF, gbc);

        JCheckBox recuerdame = new JCheckBox("Recuerdame?");
        recuerdame.setFont(new Font("Monospaced", Font.PLAIN, 10));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(recuerdame, gbc);

        JButton olvidado = new JButton("¿Has olvidado tu usuario o contraseña?");
        olvidado.setFont(new Font("Monospaced", Font.PLAIN, 10));
        olvidado.setContentAreaFilled(false);
        olvidado.setBorderPainted(false);
        olvidado.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 5, 10);
        datos.add(olvidado, gbc);

        JButton loginButton = new JButton("Inicia Sesión");
        loginButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.setMaximumSize(new Dimension(150, 50));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        datos.add(loginButton, gbc);

        // Lógica de Validación
        // Estos usuarios y contraseñas son temporales, luego se implementarán en la base de datos.
        HashMap<String, String> users = new HashMap<>();
        users.put("juanpcomella", "password");
        users.put("adrianbaz", "12345");
        users.put("anderorma", "contraseña");
        users.put("ikergamboa", "98765");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTF.getText();
                String password = new String(passwordTF.getPassword());

                if (users.containsKey(username) && users.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(null, "Bienvenido a WEDO!");
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña equivocado.");
                }
            }
        });

        panel.add(datos, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        VentanaLoginDef window = new VentanaLoginDef();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
