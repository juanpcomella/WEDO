import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import MainWindow.MainWindow;

public class VentanaCuestionario extends JFrame {
    public VentanaCuestionario() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("WEDO");

        // Creamos varios paneles con FlowLayout centrado
        JPanel borderPanel = new JPanel (new BorderLayout());
        JPanel panelFinal = new JPanel (new GridLayout(14,1));
        JPanel panelEdad = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelSexo = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelPeso = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelAltura = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelDeporte = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelTrabajo = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelNutricion = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelAlc = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelFumar = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelSueño = new JPanel (new FlowLayout(FlowLayout.CENTER));
        JPanel panelOE = new JPanel (new FlowLayout());
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //JButtons para darle a omitir o a enviar
        JButton omitir = new JButton ("omitir");
        omitir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow nuevaVentana = new MainWindow();
				nuevaVentana.setVisible(true);
				dispose();
			}
		});
        ;
        JButton enviar = new JButton ("enviar");
        enviar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MainWindow nuevaVentana = new MainWindow();
					nuevaVentana.setVisible(true);
					dispose();
				}
			});
	
        panelOE.add(enviar);
        panelOE.add(omitir);
        
        //JLabel Logo
        JLabel logoL = new JLabel("WEDO");
        logoL.setFont(new Font("Serif",Font.PLAIN, 20));
        
        //JScrollPane
        JScrollPane p = new JScrollPane(panelFinal);
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
        
        //creamos el String en el que van a ir las opciones de nutricion
        String [] nutricionS = {"como todo casero, alimentación equilibrada y cuento las calorías", "todo casero, normalmente equilibrado y saludable",
        		"como en casa normalmente, pero algunas cosas ultraprocesadas", "como chatarra"};
        		
        //creamos el string que va a indicar las opciones de alcohol
        String [] alcoS = {"excesivo ", "moderado", "casi nulo", "abstemio"};
        
        //creamos el string que va a indicar las opciones de fumar+
        String [] fumarS = {"Diariamente","Ocasionalmente","No fumo"};
        
        //creamos el string que va a indicar las opciones de sueño
        String [] suenoS = {"duermo 8 horas o más al día","duermo entre 7 y 8 horas al día","duermo entre 6 y 7 horas al día","duermo menos de 6 horas al día"};
        
        // Creamos los JComboBox
        JComboBox<String> selectAños = new JComboBox<>(años);
        JComboBox<String> selectMes = new JComboBox<>(meses);
        JComboBox<String> selectDia = new JComboBox<>(diasS);
        JComboBox<String> selectNutricion = new JComboBox<>(nutricionS);
        JComboBox<String> selectAlc = new JComboBox<>(alcoS);
        JComboBox<String> selectFum = new JComboBox<>(fumarS);
        JComboBox<String> selectSue = new JComboBox<>(suenoS);
         
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
        
        //JLabel que va a indicar la opcione de nutricion
        JLabel nutricionL = new JLabel ("Alimentacion: ");
        
        //JLabel que va a indicar la opcion de alcohol
        JLabel alcoL = new JLabel ("Consumo de alcohol:");
        
        //JLabel que va a indicar la opcion de fumar
        JLabel fumarL = new JLabel ("Consumo de tabaco");
        
        //JLabel que va a indicar la opcion de sueño
        JLabel sueñoL = new JLabel ("Horas de sueño: ");
        
        
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
        
        //añadimos los elementos al panelNutricion
        panelNutricion.add(nutricionL);
        panelNutricion.add(selectNutricion);
        
        //añadimos los elementos al panelAlc
        panelAlc.add(alcoL);
        panelAlc.add(selectAlc);
        
        //añadimos los elementos al panelFumar
        panelFumar.add(fumarL);
        panelFumar.add(selectFum);
        
        //añadimos los elementos al panelSueño
        panelSueño.add(sueñoL);
        panelSueño.add(selectSue);
        
        //panelLogo añadir el logo
        panelLogo.add(logoL);
        
        // Añadimos los paneles al panel final
        panelFinal.add(panelLogo);
        panelFinal.add(panelEdad);
        panelFinal.add(panelSexo);
        panelFinal.add(panelPeso);
        panelFinal.add(panelAltura);
        panelFinal.add(panelDeporte);
        panelFinal.add(panelTrabajo);
        panelFinal.add(panelNutricion);
        panelFinal.add(panelAlc);
        panelFinal.add(panelFumar);
        panelFinal.add(panelSueño);
        panelFinal.add(panelOE);
        
        
        // Añadimos el panel al JFrame
        
        borderPanel.add(panelFinal, BorderLayout.CENTER);
        borderPanel.add(p, BorderLayout.SOUTH);
        add(borderPanel);
        
        //ajustamos el tamaño
        Font largeFont = new Font("Georgia", Font.PLAIN, 18);  // Fuente grande

     // Configura fuentes más grandes para los componentes
     logoL.setFont(new Font("Serif", Font.BOLD, 24));
     fechaNacL.setFont(largeFont);
     sexoL.setFont(largeFont);
     pesoL.setFont(largeFont);
     alturaL.setFont(largeFont);
     deporteL.setFont(largeFont);
     trabajoL.setFont(largeFont);
     nutricionL.setFont(largeFont);
     alcoL.setFont(largeFont);
     fumarL.setFont(largeFont);
     sueñoL.setFont(largeFont);
     omitir.setFont(largeFont);
     enviar.setFont(largeFont);

     // Aplica la fuente a los JComboBox y JTextFields
     selectAños.setFont(largeFont);
     selectMes.setFont(largeFont);
     selectDia.setFont(largeFont);
     selectNutricion.setFont(largeFont);
     selectAlc.setFont(largeFont);
     selectFum.setFont(largeFont);
     selectSue.setFont(largeFont);
     selectDeporte.setFont(largeFont);
     selectTrabajo.setFont(largeFont);
     pesoTF.setFont(largeFont);
     alturaTF.setFont(largeFont);

     // Aplica la fuente a los JCheckBox
     hombreC.setFont(largeFont);
     mujerC.setFont(largeFont);

     // Envolver el panel final en un JScrollPane
     JScrollPane scrollPane = new JScrollPane(panelFinal);
     scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

     // Añadir el JScrollPane al JFrame
     add(scrollPane, BorderLayout.CENTER);

    }
    
    public static void main(String[] args) {
    	VentanaCuestionario ventana = new VentanaCuestionario();
        ventana.setVisible(true);
    }
}