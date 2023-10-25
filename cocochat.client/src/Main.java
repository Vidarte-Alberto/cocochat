import java.io.*;
import java.net.Socket;
import models.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando al servidor...");
            Socket socket = new Socket("localhost", 5050);

            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());

            System.out.println("Conexión establecida con el servidor.");

            // Paso 1: Enviar la solicitud "register" al servidor.
            objOut.writeUTF("register");
            objOut.flush();
            System.out.println("Enviando solicitud de registro al servidor...");

            // Paso 2: Esperar una confirmación del servidor.
            String serverResponse = objIn.readUTF();
            System.out.println("Respuesta del servidor: " + serverResponse);

            if ("OK".equals(serverResponse)) {
                // Paso 3: Enviar el objeto User al servidor.
                User userToRegister = new User("UsuarioPrueba", "Contraseña123");
                objOut.writeObject(userToRegister);
                objOut.flush();
                System.out.println("Enviando usuario al servidor...");

                // Esperar la respuesta del servidor
                String response = objIn.readUTF();
                System.out.println("Respuesta del servidor: " + response);
            } else {
                System.out.println("El servidor no está listo para recibir el objeto.");
            }

            // Cierre de conexiones
            System.out.println("Cerrando conexiones...");
            objOut.close();
            objIn.close();
            socket.close();
            System.out.println("Conexiones cerradas.");
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones en caso de error
        }
    }
}
