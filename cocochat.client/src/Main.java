import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando al servidor...");
            Socket socket = new Socket("localhost", 5050);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Conexión establecida con el servidor.");

            // Paso 1: Enviar la solicitud "register" al servidor.
            out.writeUTF("register");
            out.flush();
            System.out.println("Enviando solicitud de registro al servidor...");

            // Paso 2: Esperar una confirmación del servidor.
            String serverResponse = in.readUTF();
            System.out.println("Respuesta del servidor: " + serverResponse);

            if ("OK".equals(serverResponse)) {
                ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());

                // Paso 3: Enviar el objeto User al servidor.
                User userToRegister = new User("UsuarioPrueba", "Contraseña123");
                objOut.writeObject(userToRegister);
                objOut.flush();
                System.out.println("Enviando usuario al servidor...");

                // Esperar la respuesta del servidor
                String response = in.readUTF();
                System.out.println("Respuesta del servidor: " + response);
            } else {
                System.out.println("El servidor no está listo para recibir el objeto.");
            }

            // Cierre de conexiones
            System.out.println("Cerrando conexiones...");
            in.close();
            out.close();
            socket.close();
            System.out.println("Conexiones cerradas.");
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones en caso de error
        }
    }
}
