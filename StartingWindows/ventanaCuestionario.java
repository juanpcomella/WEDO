package StartingWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BaseDeDatos.BDs;
import MainWindow.MainWindow;

public class ventanaCuestionario extends JFrame {
private double peso;
public double getPeso() {
return peso;
}

public void setPeso(double peso) {
this.peso = peso;
}

public double getAltura() {
return altura;
}

public void setAltura(double altura) {
this.altura = altura;
}

public int getOpcionDeporte() {
return opcionDeporte;
}

public void setOpcionDeporte(int opcionDeporte) {
this.opcionDeporte = opcionDeporte;
}

public int getOpcionTrabajo() {
return opcionTrabajo;
}

public void setOpcionTrabajo(int opcionTrabajo) {
this.opcionTrabajo = opcionTrabajo;
}

public int getOpcionNutricion() {
return opcionNutricion;
}

public void setOpcionNutricion(int opcionNutricion) {
this.opcionNutricion = opcionNutricion;
}

public int getOpcionAlc() {
return opcionAlc;
}

public void setOpcionAlc(int opcionAlc) {
this.opcionAlc = opcionAlc;
}

public int getOpcionFum() {
return opcionFum;
}

public void setOpcionFum(int opcionFum) {
this.opcionFum = opcionFum;
}

public int getOpcionSue() {
return opcionSue;
}

public void setOpcionSue(int opcionSue) {
this.opcionSue = opcionSue;
}

private double altura;
private int opcionDeporte;
private int opcionTrabajo;
private int opcionNutricion;
private int opcionAlc;
private int opcionFum;
private int opcionSue;
private String puntosAlc;
private String puntosTab;
    public ventanaCuestionario(Usuario usuario) {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("WEDO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Creamos varios paneles con FlowLayout centrado
        JPanel borderPanel = new JPanel (new BorderLayout());
borderPanel.setBackground(new Color(173, 216, 230));
        JPanel panelFinal = new JPanel ();
        panelFinal.setLayout(new BoxLayout(panelFinal, BoxLayout.Y_AXIS));
panelFinal.setBackground(new Color(173, 216, 230));
        JPanel panelEdad = new JPanel(new FlowLayout(FlowLayout.CENTER));
panelEdad.setBackground(new Color(173, 216, 230));
        JPanel panelSexo = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelSexo.setBackground(new Color(173, 216, 230));
        JPanel panelPeso = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelPeso.setBackground(new Color(173, 216, 230));
        JPanel panelAltura = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelAltura.setBackground(new Color(173, 216, 230));
        JPanel panelDeporte = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelDeporte.setBackground(new Color(173, 216, 230));
        JPanel panelTrabajo = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelTrabajo.setBackground(new Color(173, 216, 230));
        JPanel panelNutricion = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelNutricion.setBackground(new Color(173, 216, 230));
        JPanel panelAlc = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelAlc.setBackground(new Color(173, 216, 230));
        JPanel panelFumar = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelFumar.setBackground(new Color(173, 216, 230));
        JPanel panelSueño = new JPanel (new FlowLayout(FlowLayout.CENTER));
panelSueño.setBackground(new Color(173, 216, 230));
        JPanel panelOE = new JPanel (new FlowLayout());
panelOE.setBackground(new Color(173, 216, 230));
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
panelLogo.setBackground(new Color(173, 216, 230));
        //JButtons para darle a omitir o a enviar
        JButton omitir = new JButton ("Omitir");
omitir.setForeground(Color.WHITE);
        omitir.setBackground(new Color(50,70,90));
omitir.setFont(new Font("Tahoman", Font.BOLD, 30));
        omitir.setOpaque(true);
        JButton enviar = new JButton ("Enviar");
enviar.setForeground(Color.WHITE);
        enviar.setBackground(new Color(50,70,90));
        enviar.setOpaque(true);
enviar.setFont(new Font("Tahoman", Font.BOLD, 30));
        panelOE.add(enviar);
        panelOE.add(omitir);
       
        //JLabel Logo
        ImageIcon imagen = new ImageIcon(VentanaBienvenida.class.getResource("/imagenes/LOGO WEDO 1.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
JLabel logo = new JLabel(imagenRedimensionada);
logo.setAlignmentX(CENTER_ALIGNMENT);
       
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
        String[] meses = {" enero", " febrero", " marzo", " abril", " mayo", " junio",
                          " julio", " agosto", " septiembre", " octubre", " noviembre", " diciembre"};
        String[] diasS = dias.toArray(new String[0]);
       
        //creamos el String en el que van a ir las opciones del deporte
        String [] deporteS = {"  deportes relajados (yoga, tiro con arco, meditar, taichi, caminar, golf, bolos, pesca, etc.).", "  deportes moderados (futting, natación de manera relajada, baile, etc.). ",
        "  deportes intensos (cualquier deporte elaborado de manera competitiva).","  sedentario (no hago deporte)." };

        //creamos el String en el que van a ir las opciones del trabajo o estudio
        String [] trabajoS = {"  todo el día de pie, en bici, etc. (repartidor en bicicleta, guía turístico, policía, obrero etc.). ",
        "  medio (profesor, entrenador, secretario,etc.).","  sedentario (oficinista, teletrabajo, programador, dependiente de tienda, etc.). "};
       
        //creamos el String en el que van a ir las opciones de nutricion
        String [] nutricionS = {"  como todo casero, alimentación equilibrada y cuento las calorías.", "  todo casero, normalmente equilibrado y saludable.",
        "  como en casa normalmente, pero algunas cosas ultraprocesadas.", "  como chatarra."};
       
        //creamos el string que va a indicar las opciones de alcohol
//        String [] alcoS = {"excesivo. ", "moderado.", "casi nulo.", "abstemio."};
       
        //creamos el string que va a indicar las opciones de fumar+
//        String [] fumarS = {"diariamente.","ocasionalmente.","no fumo."};
       
        //creamos el string que va a indicar las opciones de sueño
        String [] suenoS = {"  8 o más.","  entre 7 y 8.","  entre 6 y 7.","  menos de 6."};
       
        // Creamos los JComboBox
        JComboBox<String> selectAños = new JComboBox<>(años);
        JComboBox<String> selectMes = new JComboBox<>(meses);
        JComboBox<String> selectDia = new JComboBox<>(diasS);
        JComboBox<String> selectNutricion = new JComboBox<>(nutricionS);
        JComboBox<String> selectSue = new JComboBox<>(suenoS);
         
        // creamos los CheckBox que van a ir al panelSexo
        JCheckBox hombreC = new JCheckBox("Hombre");
        hombreC.setOpaque(false);
        JCheckBox mujerC = new JCheckBox("Mujer");
        mujerC.setOpaque(false);
       
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
        JLabel alturaL = new JLabel("Altura: ");
        JLabel cms = new JLabel (" cm");
       
        //Peso en kg
        JLabel pesoL = new JLabel ("Peso: ");
        JLabel kilos = new JLabel (" kg");
       
        //JTextField para que el usuario introduzca el Peso
        JTextField pesoTF = new JTextField(2);
       


       
       
        //JTextField para que el usuario introduzca el Peso
        JTextField alturaTF = new JTextField(3);
       
       
        //JLabel que va a indicar la opcion de trabajo o estudio
        JLabel trabajoL = new JLabel("Estudio o trabajo: ");
       
        //JLabel que va a indicar la opcione de nutricion
        JLabel nutricionL = new JLabel ("Alimentacion: ");
       
        //Fuente
        Font fuente = new Font("Tahoma", Font.BOLD, 20);  // Fuente grande
        Font fuente2 = new Font("Tahoma", Font.BOLD, 15);
       
        //JLabel que va a indicar la opcion de sueño
        JLabel sueñoL = new JLabel ("Horas de sueño: ");
       
       
        hombreC.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
if (hombreC.isSelected()) {
mujerC.setSelected(false);
}
}
});
       
        mujerC.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
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
        panelPeso.add(kilos);
       
        //añadimos los elementos al panelAltura
        panelAltura.add(alturaL);
        panelAltura.add(alturaTF);
        panelAltura.add(cms);
       
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
//        panelAlc.add(alcoL);
//        panelAlc.add(espacioAlc);
//        panelAlc.add(sliderAlc);
//       
//        //añadimos los elementos al panelFumar
//        panelFumar.add(fumarL);
//        panelAlc.add(espacioTab);
//        panelFumar.add(sliderTab);
       
        //añadimos los elementos al panelSueño
        panelSueño.add(sueñoL);
        panelSueño.add(selectSue);
       
        //panelLogo añadir el logo
        panelLogo.add(logo);
       
        // Añadimos los paneles al panel final
        panelFinal.add(Box.createVerticalStrut(20));
        panelFinal.add(panelLogo);
        panelFinal.add(Box.createVerticalStrut(50));
        panelFinal.add(panelEdad);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelSexo);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelPeso);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelAltura);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelDeporte);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelTrabajo);
        panelFinal.add(Box.createVerticalStrut(40));
        panelFinal.add(panelNutricion);
        panelFinal.add(Box.createVerticalStrut(60));
        panelFinal.add(panelSueño);
        panelFinal.add(Box.createVerticalStrut(60));
        panelFinal.add(panelOE);
        panelFinal.add(Box.createVerticalStrut(20));
       
       
        // Añadimos el panel al JFrame
       
        borderPanel.add(panelFinal, BorderLayout.CENTER);
        borderPanel.add(p, BorderLayout.SOUTH);
        add(borderPanel);

     //configura fuentes más grandes para los componentes
     fechaNacL.setFont(fuente);
     sexoL.setFont(fuente);
     pesoL.setFont(fuente);
     kilos.setFont(fuente);
     alturaL.setFont(fuente);
     cms.setFont(fuente);
     deporteL.setFont(fuente);
     trabajoL.setFont(fuente);
     nutricionL.setFont(fuente);
//     alcoL.setFont(fuente);
//     fumarL.setFont(fuente);
     sueñoL.setFont(fuente);
fechaNacL.setForeground(new Color(50,70,90));
sexoL.setForeground(new Color(50,70,90));
pesoL.setForeground(new Color(50,70,90));
     kilos.setForeground(new Color(50,70,90));
alturaL.setForeground(new Color(50,70,90));
     cms.setForeground(new Color(50,70,90));
deporteL.setForeground(new Color(50,70,90));
trabajoL.setForeground(new Color(50,70,90));
nutricionL.setForeground(new Color(50,70,90));
//alcoL.setForeground(new Color(50,70,90));
//fumarL.setForeground(new Color(50,70,90));
sueñoL.setForeground(new Color(50,70,90));


//     fechaNacL.setFont(largeFont);
//     sexoL.setFont(largeFont);
//     pesoL.setFont(largeFont);
//     alturaL.setFont(largeFont);
//     deporteL.setFont(largeFont);
//     trabajoL.setFont(largeFont);
//     nutricionL.setFont(largeFont);
//     alcoL.setFont(largeFont);
//     fumarL.setFont(largeFont);
//     sueñoL.setFont(largeFont);
//     omitir.setFont(largeFont);
//     enviar.setFont(largeFont);

     //aplica la fuente a los JComboBox y JTextFields
selectAños.setFont(fuente);
     selectMes.setFont(fuente);
     selectDia.setFont(fuente);
     selectNutricion.setFont(fuente);
     selectSue.setFont(fuente);
     selectDeporte.setFont(fuente);
     selectTrabajo.setFont(fuente);
     pesoTF.setFont(fuente);
     alturaTF.setFont(fuente);
selectAños.setForeground(new Color(50,70,90));
selectMes.setForeground(new Color(50,70,90));
selectDia.setForeground(new Color(50,70,90));
selectNutricion.setForeground(new Color(50,70,90));
selectSue.setForeground(new Color(50,70,90));
selectDeporte.setForeground(new Color(50,70,90));
selectTrabajo.setForeground(new Color(50,70,90));
pesoTF.setForeground(new Color(50,70,90));
alturaTF.setForeground(new Color(50,70,90));

//     selectAños.setFont(largeFont);
//     selectMes.setFont(largeFont);
//     selectDia.setFont(largeFont);
//     selectNutricion.setFont(largeFont);
//     selectSue.setFont(largeFont);
//     selectDeporte.setFont(largeFont);
//     selectTrabajo.setFont(largeFont);
//     pesoTF.setFont(largeFont);
//     alturaTF.setFont(largeFont);

     //aplica la fuente a los JCheckBox
hombreC.setForeground(new Color(50,70,90));
hombreC.setFont(fuente);
mujerC.setForeground(new Color(50,70,90));
mujerC.setFont(fuente);

//     hombreC.setFont(largeFont);
//     mujerC.setFont(largeFont);

     //envolver el panel final en un JScrollPane
     JScrollPane scrollPane = new JScrollPane(panelFinal);
     scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

     //añadir el JScrollPane al JFrame
     add(scrollPane, BorderLayout.CENTER);
   

   
     
     //calculo de rango segun los datos
     //recogemos todos los datos y segun estos el usuario entrara en un rango u otro
     


   
    //calculamos el indice de masa corporal
enviar.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

Boolean pasarAlMain = false;

if(pesoTF.getText().isEmpty() || alturaTF.getText().isEmpty()) {
JOptionPane.showMessageDialog(null,"Faltan campos por rellenar.");
}else {

try {
Double.parseDouble(pesoTF.getText());
Double.parseDouble(alturaTF.getText());
pasarAlMain = true;

}catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(null,"Datos introducidos no válidos.");
}
if(pasarAlMain) {
JOptionPane.showMessageDialog(null,"Cuestionario completado con éxito.");
MainWindow mw = new MainWindow(usuario);
mw.setVisible(true);
dispose();
}
}



int peso = Integer.parseInt(pesoTF.getText());
double altura = Double.parseDouble(alturaTF.getText()) / 100;
int opcionDeporte = selectDeporte.getSelectedIndex();
int opcionTrabajo = selectTrabajo.getSelectedIndex();
int opcionNutricion = selectNutricion.getSelectedIndex();
int opcionSue = selectSue.getSelectedIndex();
//   int opcionFum = selectFum.getSelectedIndex();
//int opcionAlc = selectAlc.getSelectedIndex();
int edad = añoActual-(Integer.parseInt((String) selectAños.getSelectedItem()));

//calculamos el indice de masa corporal
double Imc = peso/(altura*altura);

//ahora realizaremos el conteo de los puntos:
double puntos =0;
String rango;

//caso del imc:
//cabe recalcar que hay casos en los que el sobrepeso no es peligroso o malo
if (Imc<18.5) {
//infrapeso
puntos-=1;
} else if (Imc>18.5 && Imc<24.9) {
//peso normal
puntos+=0;
} else if (Imc>24.9 && Imc<29.9) {
//sobrepeso
puntos-=1.5;
} else if (Imc>29.9 && Imc<34.9) {
//obesidad leve
puntos-=2;
} else {
//obesidad
puntos-=3;
}

//ahora caso de deporte
if (opcionDeporte==0) {
//deportes relajados
puntos+=0;
} else if (opcionDeporte==1) {
puntos+=1;
} else if (opcionDeporte==2) {
puntos+=2;
} else {
puntos-=1;
}

//caso de estudio o trabajo:
if (opcionTrabajo==0) {
//todo el dia en bici, etc. muchas calorias quemadas aunque no se haga deporte
puntos+=2;
} else if (opcionTrabajo==1) {
//medio
puntos+=0;
} else {
//trabajo de estar sentado, apenas nada quemado
puntos-=1;
}

//caso alimentacion
if (opcionNutricion==0) {
//todo casero
puntos+=2;
} else if (opcionNutricion==1) {
puntos+=1;
} else if (opcionNutricion==2) {
puntos+=0;
} else {
//como chatarra, -2 porque la alimentacion es un factor muy a tener en cuenta
puntos-=2;
}



//caso dormir:
if (opcionSue==0) {
puntos+=2;
} else if (opcionSue==1) {
puntos+=1;
} else if (opcionSue==2) {
puntos+=0;
} else {
puntos-=1;
}
//caso edad
if (edad<16) {
puntos+=0;
} else if (edad>16 && edad<20) {
puntos+=1;
} else if (edad>20 && edad<30) {
puntos+=2;
} else if (edad>30 && edad<40) {
puntos+=1;
} else if (edad>40 && edad<50) {
puntos+=0;
} else if (edad>50 && edad<65) {
puntos-=1;
} else {
puntos-=2;
}

double multiplicador;
if(puntos > -10 & puntos <-5) {
	multiplicador = 0.7;
}else if(puntos > -5 & puntos < 0) {
	multiplicador = 0.8;
}else if(puntos > 0 & puntos < 5){
	multiplicador = 1.2;
}else {
	multiplicador = 1.3;
}
//BDs.updateMultiplicador(usuario.getNombreUsuario(), multiplicador);

}
   
    });
     
//listener de omitir
omitir.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {
int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea omitir el cuestionario?","Omitir",
JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE );
if (respuesta == JOptionPane.YES_OPTION) {
//BDs.updateMultiplicador(usuario.getNombreUsuario(), 1);
//System.out.println(BDs.getMulti(usuario.getNombreUsuario())+"**");
MainWindow ventanaPrincipal = new MainWindow(usuario);
ventanaPrincipal.setVisible(true);
dispose();
}
}
});
    }

}
