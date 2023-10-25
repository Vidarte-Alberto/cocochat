package Session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class Session {
    public static Socket getSocket() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 5050);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return socket;
    }

    public static ObjectInputStream getIn(Socket socket) {
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objIn;
    }

    public static ObjectOutputStream getOut(Socket socket) {
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objOut;
    }
}
