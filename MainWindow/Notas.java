package MainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Notas extends JFrame{
	public Notas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500,1000));
		setLayout(new BorderLayout());
		JTextField apunteTf = new JTextField();
	}
	
	public static void main(String[] args) {
		Notas notas = new Notas();
		notas.setVisible(true);
		
	}
}
