import java.io.*;
import java.net.Socket;
import java.util.List;

import models.*;

public class Main_Test {
    public static void main(String[] args) {
        try {
            System.out.println("Conectando al servidor...");
            Socket socket = new Socket("localhost", 5050);

            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());

            System.out.println("Conexión establecida con el servidor.");

            boolean exit = false; // Variable para controlar si el cliente quiere salir

            while (!exit) {
                // Mostrar opciones al cliente
                System.out.println("Opciones:");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Seleccionar todos los usuarios");
                System.out.println("3. Salir");
                System.out.print("Elija una opción: ");

                // Leer la opción del cliente desde la entrada estándar
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String choice = reader.readLine();

                if ("1".equals(choice)) {
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
                } if ("2".equals(choice)) {
                    // El cliente elige obtener todos los usuarios
                    objOut.writeUTF("getAllUser"); // Enviar la solicitud al servidor
                    objOut.flush();

                    // Recibir la lista de usuarios del servidor
                    List<User> userList = (List<User>) objIn.readObject();
                    if (userList != null) {
                        System.out.println("Lista de usuarios:");
                        for (User user : userList) {
                            System.out.println(user);
                        }
                    } else {
                        System.out.println("No se pudo obtener la lista de usuarios.");
                    }
                } else if ("3".equals(choice)) {
                    // El cliente elige salir
                    exit = true;
                    objOut.writeUTF("exit"); // Enviar la señal de salida al servidor
                    objOut.flush();
                }else {
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
                }
            }

            // Cierre de conexiones
            System.out.println("Cerrando conexiones...");
            objOut.close();
            objIn.close();
            socket.close();
            System.out.println("Conexiones cerradas.");
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones en caso de error
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
