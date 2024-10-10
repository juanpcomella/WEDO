import javax.swing.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {

        setTitle("Inserta tu Usuario");
        setSize(700, 500);


    }

    public static void main(String[] args) {
        LoginWindow window = new LoginWindow();

        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
