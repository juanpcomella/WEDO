package src.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Footer extends JPanel {

    public Footer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Footer");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
    }
}
