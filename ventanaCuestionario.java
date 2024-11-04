import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ventanaCuestionario extends JFrame {
	public ventanaCuestionario() {
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("WEDO");
		
		//creamos un panel, en este caso será un flowlayout
		JPanel panelCuestionario = new JPanel (new FlowLayout());
		
		//ahora creamos los componentes que va a tener el cuestionario
		//primero, le pediremos al usuario la edad
		//String que va a contener todos los años de nacimiento disponibles
		int añoInicio = 1925;
        int añoActual = LocalDate.now().getYear();

        ArrayList<String> listaAños = new ArrayList<>();

        for (int año = añoInicio; año <= añoActual; año++) {
            listaAños.add(String.valueOf(año));
        String[] años = listaAños.toArray(new String[0]);
        String[] meses = {"enero","febrero","marzo", "abril", "mayo", "junio", "julio","agosto","septiembre","octubre","noviembre","diciembre"};
            
        JComboBox<String> selectAños = new JComboBox<>(años);
        JComboBox<String> selectMes = new JComboBox<>(meses);
        add(selectAños);
        add(selectMes);
            
	}
}
	public static void main(String[] args) {
		ventanaCuestionario ventana = new ventanaCuestionario();
		ventana.setVisible(true);
	}
}
