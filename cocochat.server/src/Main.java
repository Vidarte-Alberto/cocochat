import server.ServerThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(5050);
            Socket sc;

            System.out.println("Servidor iniciado");
            while (true) {

                sc = server.accept();
                System.out.println("Nueva conexion aceptada");

                ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());


                ServerThread thread = new ServerThread(in, out, sc);
                thread.start();


            }
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}