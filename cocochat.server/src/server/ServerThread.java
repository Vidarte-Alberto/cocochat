package server;

import dao.GroupDao;
import dao.UserDao;
import models.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nombre;
    private User user = new User();
    private Group group = new Group();
    UserDao userDao = new UserDao();
    GroupDao groupDao = new GroupDao();
    List<User> userList = new ArrayList<User>();
    Socket sc;

    public ServerThread(ObjectInputStream in, ObjectOutputStream out, Socket sc){
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
                        user = (User) in.readObject();
                        if(userDao.createUser(user)) {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "login":
                        user = (User)in.readObject();
                        user = userDao.loginUser(user.getName(), user.getPassword());
                        if(user != null) {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "delete":
                        user = (User)in.readObject();
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
                            out.writeObject(user);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "getAllUser":
                        userList = userDao.getAllUsers();
                        out.writeObject(userList);
                        break;
                    case "createGroup":
                        group = (Group)in.readObject();
                        break;
                    default:
                        out.writeUTF("Solo puedes seleccionar 1 o 2");
                        break;
                }
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
