package src.gui.StartingWindows;

import javax.mail.*;
import javax.mail.internet.*;
import src.db.BDs;
import java.util.Properties;
import java.util.UUID;

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
            String codigoVerificacion;
            long tiempoActual = System.currentTimeMillis();
            long tiempoExpiracion = tiempoActual + 10 * 60 * 1000; // 10 minutos

            // Verificar si existe un código válido en la base de datos
            String codigoExistente = BDs.obtenerCodigoDeVerificacion(correoDestino);
            Long expiracionExistente = BDs.obtenerFechaExpiracion(correoDestino);
//            System.out.println(BDs.obtenerCodigoDeVerificacion(correoDestino));
//            System.out.println(codigoExistente);
//        	System.out.println(expiracionExistente);
//        	System.out.println(tiempoActual);

            if (codigoExistente != null && expiracionExistente != null && expiracionExistente > tiempoActual) {
                // Si el código existe y no ha expirado
      
                codigoVerificacion = codigoExistente;
            } else {
                // Si no existe un código o ha expirado, generar uno nuevo
                codigoVerificacion = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                BDs.eliminarPorEmail(correoDestino);
                BDs.insertarCodigosDeVerificacion(correoDestino, codigoVerificacion, tiempoExpiracion);
            }

            // Crear el mensaje de correo
//            System.out.println(codigoVerificacion);
//            System.out.println(BDs.obtenerCodigoDeVerificacion(correoDestino));

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuarioCorreo));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            message.setSubject("Recuperación de contraseña");
            message.setText("Hemos recibido un aviso de que has olvidado tu contraseña. Introduce el código adjunto en la aplicación para cambiar tu contraseña a una nueva.\n"
                    + codigoVerificacion);  // Usar el código generado o recuperado

            // Enviar el mensaje
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
