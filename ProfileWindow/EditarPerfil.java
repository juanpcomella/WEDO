package ProfileWindow;

import StartingWindows.Usuario;

import javax.swing.*;
import java.awt.*;

public class EditarPerfil extends JFrame {
    public EditarPerfil(Usuario usuario) {
        setTitle("Editar Perfil - " + usuario.getNombreUsuario());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(700, 300);

        JLabel title = new JLabel("Editar Perfil");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Usuario user = new Usuario(null, null, null);
        EditarPerfil editarPerfil = new EditarPerfil(user);
        editarPerfil.setSize(450, 600);
        editarPerfil.setLocationRelativeTo(null);
        editarPerfil.setVisible(true);
    }
}
