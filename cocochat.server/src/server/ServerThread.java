package server;

import dao.GroupDao;
import dao.UserDao;
import models.Group;
import models.User;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private String nombre;
    private User user = new User();
    private Group group = new Group();
    UserDao userDao = new UserDao();
    GroupDao groupDao = new GroupDao();
    List<User> userList = new ArrayList<User>();
    Socket sc;

    public ServerThread(DataInputStream in, DataOutputStream out, Socket sc){
        this.in = in;
        this.out = out;
        this.sc = sc;
    }

    @Override
    public void run(){
        String opc = "";
        try{
            while(true) {
                opc = in.readUTF();
                switch(opc) {
                    case "register":
                        out.writeUTF("OK");
                        out.flush();
                        // Paso 2: Leer el objeto User del ObjectInputStream
                        objIn = new ObjectInputStream(sc.getInputStream());
                        user = (User) objIn.readObject();
                        if(userDao.createUser(user)) {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        objIn.close();
                        break;
                    case "login":
                        user = (User)objIn.readObject();
                        user = userDao.loginUser(user.getName(), user.getPassword());
                        if(user != null) {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "delete":
                        user = (User)objIn.readObject();
                        if(userDao.deleteUser(user.getIdUser())){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getUserById":
                        user = userDao.getUserById(Integer.parseInt(in.readUTF()));
                        if (user != null)
                        {
                            objOut.writeObject(user);
                        }else {
                            objOut.writeObject(null);
                        }
                        break;
                    case "getAllUser":
                        userList = userDao.getAllUsers();
                        objOut.writeObject(userList);
                        break;
                    case "createGroup":
                        group = (Group)objIn.readObject();
                        break;
                    default:
                        out.writeUTF("Solo puedes seleccionar 1 o 2");
                        break;
                }
            }

        } catch (IOException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
