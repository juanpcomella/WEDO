package StartingWindows;

import javax.mail.*;
import javax.mail.internet.*;

import BaseDeDatos.BDs;

import java.util.Properties;

public class EnviarCorreoRecuperacion {

    public static void enviarCorreo(String correoDestino) {
     
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        final String usuarioCorreo = "wedo.assistance@gmail.com"; 
        final String contrasenaCorreo = "efko hjnz ldme jpzk";

        // Crear la sesión de correo con las propiedades y las credenciales
        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuarioCorreo, contrasenaCorreo);
            }
        });

        try {
            // Crear el mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuarioCorreo));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject("Recuperación de contraseña");
            message.setText("Tu contrase es "+BDs.getPassword(BDs.pasarDeEmailAUsername(correoDestino)));

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado exitosamente a " + correoDestino);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

