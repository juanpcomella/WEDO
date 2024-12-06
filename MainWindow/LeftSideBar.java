package MainWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class LeftSideBar extends JPanel {

    public LeftSideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(173, 216, 230));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(50,70,90));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBorder(new LineBorder(new Color(173, 216, 230),10));
        JButton button1 = new JButton("+");


        panel.add(button1);




        add(panel);
    }
}
