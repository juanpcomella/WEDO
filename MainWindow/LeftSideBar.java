package MainWindow;

import javax.swing.*;
import java.awt.*;

public class LeftSideBar extends JPanel {

    public LeftSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Left Sidebar");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
    }
}
