package MainWindow;

import javax.swing.*;
import java.awt.*;

public class RightSideBar extends JPanel {

    public RightSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Right Sidebar");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
    }
}
