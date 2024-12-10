package ProfileWindow;

import StartingWindows.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;

import BaseDeDatos.BDs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditarPerfil extends JFrame {
    public EditarPerfil(Usuario usuario) {
        setTitle("Editar Perfil - " + usuario.getNombreUsuario());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(450, 600));

        JLabel title = new JLabel("Editar Perfil");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0,10,0));
        panel.add(title);

        try {
            BufferedImage profileImage = ImageIO.read(new File("imagenes/PERFIL.png"));
            JLabel profilePictureLabel = new JLabel(new ImageIcon(getCircularImage(profileImage, 175)));
            profilePictureLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(profilePictureLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            panel.add(new JLabel("Image not found"), BorderLayout.CENTER);
        }



        JPanel cambiarDatos = new JPanel(new GridLayout(5, 2, 10, 10)); // 2 rows, 2 columns, with padding
        cambiarDatos.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambiarDatos.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Add margins

        JButton cambiarFoto = new JButton("Cambiar foto");
        cambiarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambiarFoto.setFont(new Font("Arial", Font.PLAIN, 16));
        cambiarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de selección de íconos
                SeleccionarIconoPerfil ventanaSeleccion = new SeleccionarIconoPerfil(usuario);
                ventanaSeleccion.setVisible(true);
            }
        });

        cambiarDatos.add(cambiarFoto);

        JButton cambiarMoneda = new JButton("Cambiar moneda");
        cambiarMoneda.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambiarMoneda.setFont(new Font("Arial", Font.PLAIN, 16));
        cambiarMoneda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de selección de íconos
                SeleccionarMoneda ventanaSeleccion = new SeleccionarMoneda(usuario);
                ventanaSeleccion.setVisible(true);
            }
        });
        cambiarDatos.add(cambiarMoneda);

        JTextField username = new JTextField(usuario.getNombreUsuario(), SwingConstants.CENTER); // Align right
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setEditable(false);
        cambiarDatos.add(username);

        JButton changeUsername = new JButton("Cambiar usuario");
        changeUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        changeUsername.setMargin(new Insets(5, 10, 5, 10));
        cambiarDatos.add(changeUsername);
        boolean usrEditable[] = {false};
        changeUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usrEditable[0]) {
                    username.setEditable(true);
                    changeUsername.setText("Guardar");
                }
                else{
                	String nuevoUsuario = username.getText();
                	BDs.updateUsuario(usuario.getNombreUsuario(), nuevoUsuario);
                	usuario.setNombreUsuario(nuevoUsuario);
                	username.setEditable(false);
                    changeUsername.setText("Cambiar username");
                    
                }
                usrEditable[0] = !usrEditable[0];
            }
        });


        JTextField email = new JTextField(usuario.getCorreo(), SwingConstants.CENTER); // Align right
        email.setFont(new Font("Arial", Font.PLAIN, 16));
        email.setEditable(false);
        cambiarDatos.add(email);

        JButton changeEmail = new JButton("Cambiar email");
        changeEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        changeEmail.setMargin(new Insets(5, 10, 5, 10));
        cambiarDatos.add(changeEmail);
        boolean emailEditable[] = {false};
        changeEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emailEditable[0]) {
                    email.setEditable(true);
                    changeEmail.setText("Guardar");
                }
                else{
                	String nuevoEmail = email.getText();
                	BDs.updateEmail(usuario.getNombreUsuario(), nuevoEmail);
                	usuario.setCorreo(nuevoEmail);
                    email.setEditable(false);
                    changeEmail.setText("Cambiar email");
                }
                emailEditable[0] = !emailEditable[0];
            }
        });

        JPasswordField password = new JPasswordField(usuario.getContraseña(), SwingConstants.CENTER);
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setEditable(false);
        cambiarDatos.add(password);

        JButton changePassword = new JButton("Cambiar contraseña");
        changePassword.setFont(new Font("Arial", Font.PLAIN, 16));
        changePassword.setMargin(new Insets(5, 10, 5, 10));
        cambiarDatos.add(changePassword);
        boolean pwrdEditable[] = {false};
        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pwrdEditable[0]) {
                    password.setEditable(true);
                    changePassword.setText("Guardar");
                }
                else{
                	String nuevaContraseña = String.valueOf(password.getPassword());
                	BDs.updatePassword(usuario.getNombreUsuario(), nuevaContraseña);
                	usuario.setContraseña(nuevaContraseña);
                    password.setEditable(false);
                    changePassword.setText("Cambiar contraseña");
                }
                pwrdEditable[0] = !pwrdEditable[0];
            }
        });

//        JTextArea descripcion = new JTextArea();
//        JScrollPane descripcionScrollPane = new JScrollPane(descripcion);
//        descripcion.setFont(new Font("Arial", Font.PLAIN, 16));
//        descripcion.setLineWrap(true);
//        descripcion.setWrapStyleWord(true);
//        descripcion.setEditable(false);
//        cambiarDatos.add(descripcionScrollPane);

//        JButton changeDescripcion = new JButton("Cambiar descripcion");
//        changeDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
//        changeDescripcion.setMargin(new Insets(5, 10, 5, 10));
//        boolean descEditable[] = {false};
//        changeDescripcion.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(descEditable[0]) {
//                    descripcion.setEditable(true);
//                    changeDescripcion.setText("Guardar");
//                }
//                else{
//                	String nuevaDescripcion = descripcion.getText();
//                	
//                	descripcion.setEditable(false);
//                    changeDescripcion.setText("Cambiar username");
//                }
//                descEditable[0] = !descEditable[0];
//            }
//        });

//        cambiarDatos.add(changeDescripcion);

        panel.add(cambiarDatos);


        add(panel);
    }

    private Image getCircularImage(BufferedImage image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2d.drawImage(image, 0, 0, diameter, diameter, null);
        g2d.dispose();
        return output;
    }

    public static void main(String[] args) {
        Usuario user = new Usuario("null", "null", "null");
        EditarPerfil editarPerfil = new EditarPerfil(user);
        editarPerfil.setSize(450, 600);
        editarPerfil.setLocationRelativeTo(null);
        editarPerfil.setVisible(true);
    }
}
