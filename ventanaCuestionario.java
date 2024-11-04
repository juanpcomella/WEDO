import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ventanaCuestionario extends JFrame {
    public ventanaCuestionario() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("WEDO");

        // Creamos varios paneles con FlowLayout centrado
        JPanel panelFinal = new JPanel (new GridLayout(10,1));
        JPanel panelEdad = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelSexo = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelPeso = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelAltura = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelDeporte = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelTrabajo = new JPanel (new FlowLayout(FlowLayout.CENTER));
        
        
        // Creamos las listas de años y meses
        int añoInicio = 1925;
        int añoActual = LocalDate.now().getYear();

        ArrayList<String> listaAños = new ArrayList<>();
        for (int año = añoInicio; año <= añoActual; año++) {
            listaAños.add(String.valueOf(año));
        }
        
        ArrayList<String> dias = new ArrayList<>();
        
        for (int dia = 1; dia<=31; dia++) {
        	dias.add(String.valueOf(dia));
        }
        
        String[] años = listaAños.toArray(new String[0]);
        String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", 
                          "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        String[] diasS = dias.toArray(new String[0]);
        
        //creamos el String en el que van a ir las opciones del deporte
        String [] deporteS = {"deportes relajados (yoga, tiro con arco, meditar, Taichi, caminar, golf, bolos, pesca, etc.)", "deportes moderados (futting, natación de manera relajada, baile, etc.) ", 
        						"deportes intensos (cualquier deporte elaborado de manera competitiva)","sedentario (no hago deporte)" };

        //creamos el String en el que van a ir las opciones del trabajo o estudio
        String [] trabajoS = {"Todo el día de pie, en bici, etc. (repartidor en bicicleta, guía turístico, policía,obrero etc.) ",
        						"Medio (profesor, entrenador, secretario,etc.)","sedentario (oficinista, teletrabajo, programador, dependiente de tienda, etc.)"};
        
        // Creamos los JComboBox
        JComboBox<String> selectAños = new JComboBox<>(años);
        JComboBox<String> selectMes = new JComboBox<>(meses);
        JComboBox<String> selectDia = new JComboBox<>(diasS);
        
        // creamos los CheckBox que van a ir al panelSexo
        JCheckBox hombreC = new JCheckBox("Hombre");
        JCheckBox mujerC = new JCheckBox("Mujer");
        
        // JComboBox de las opciones de deporte
        JComboBox <String> selectDeporte = new JComboBox<>(deporteS);
        
        //JComboBox de las opciones de trabajo
        JComboBox <String> selectTrabajo = new JComboBox<>(trabajoS);        
        
        //Creamos el JLabel en el que aparece "fecha de nacimiento"
        JLabel fechaNacL = new JLabel("Fecha de nacimiento: ");
        
        // Creamos el JLabel en el que aparece deporte:
        JLabel deporteL = new JLabel ("Deporte: ");
        
        // Creamos el JLabel en el que aparece "sexo"
        JLabel sexoL = new JLabel("Sexo: ");
        
        //Altura en cm
        JLabel alturaL = new JLabel("Altura(cm): ");
        
        //Peso en kg
        JLabel pesoL = new JLabel ("Peso(kg): ");
        
        //JTextField para que el usuario introduzca el Peso
        JTextField pesoTF = new JTextField(2);
        
        //JTextField para que el usuario introduzca el Peso
        JTextField alturaTF = new JTextField(3);
        
        //JLabel que va a indicar la opcion de trabajo o estudio
        JLabel trabajoL = new JLabel("Estudio o trabajo: ");
        
        

        
        hombreC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (hombreC.isSelected()) {
					mujerC.setSelected(false);
				}
			}
		});
        
        mujerC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (mujerC.isSelected()) {
					hombreC.setSelected(false);
				}
			}
		});
        
        
        // Añadimos los elementos al panelEdad
        panelEdad.add(fechaNacL);
        panelEdad.add(selectAños);
        panelEdad.add(selectMes);
        panelEdad.add(selectDia);
                
        
        // Añadimos los elementos al panelSexo
        panelSexo.add(sexoL);
        panelSexo.add(hombreC);
        panelSexo.add(mujerC);
        
        //añadimos los elementos al panelPeso
        panelPeso.add(pesoL);
        panelPeso.add(pesoTF);
        
        //añadimos los elementos al panelAltura
        panelAltura.add(alturaL);
        panelAltura.add(alturaTF);
        
        //añadimos los elementos al panelDeporte
        panelDeporte.add(deporteL);
        panelDeporte.add(selectDeporte);
        
        //añadimos los elementos la panelTrabajo
        panelTrabajo.add(trabajoL);
        panelTrabajo.add(selectTrabajo);
        
        // Añadimos los paneles al panel final    
        panelFinal.add(panelEdad);
        panelFinal.add(panelSexo);
        panelFinal.add(panelPeso);
        panelFinal.add(panelAltura);
        panelFinal.add(panelDeporte);
        panelFinal.add(panelTrabajo);
        
        
        // Añadimos el panel al JFrame
        add(panelFinal);
    }

    public static void main(String[] args) {
        ventanaCuestionario ventana = new ventanaCuestionario();
        ventana.setVisible(true);
    }
}
