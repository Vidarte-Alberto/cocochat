package server;

import dao.GroupDao;
import dao.GroupUserDao;
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
    private GroupUser groupUser = new GroupUser();
    UserDao userDao = new UserDao();
    GroupDao groupDao = new GroupDao();
    GroupUserDao groupUserDao = new GroupUserDao();
    List<User> userList = new ArrayList<User>();
    List<Group> groupUsersList = new ArrayList<Group>();
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
                        user = (User) in.readObject();
                        if(userDao.createUser(user)) {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "login":
                        user = (User)in.readObject();
                        System.out.println(user.getName() + " " + user.getPassword());
                        user = userDao.loginUser(user.getName(), user.getPassword());
                        if ( user != null) {
                            out.writeUTF("1");
                            out.writeObject(user);
                        } else {
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
                        if (userList != null)
                        {
                            out.writeObject(userList);
                            out.flush();
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getAllUserConnected":
                        userList = userDao.getAllUsersConnected();
                        if (userList != null)
                        {
                            out.writeObject(userList);
                            out.flush();
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getAllUserDisconnected":
                        userList = userDao.getAllUsersDisconnected();
                        if (userList != null)
                        {
                            out.writeObject(userList);
                            out.flush();
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "updateUser", "signOut":
                        if (userDao.updateUser((User) in.readObject()))
                        {
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "createGroup":
                        group = (Group) in.readObject();
                        groupDao.createGroup(group);
                        group = groupDao.getGroupByName(group.getNameGroup());
                        user = (User)in.readObject();
                        groupUser.setIdGroup(group.getIdGroup());
                        groupUser.setIdUser(user.getIdUser());
                        if (groupUserDao.createGroupUser(groupUser))
                        {

                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getGroupByUser":
                        user = (User) in.readObject();
                        groupUsersList = groupDao.getGroupsByUser(user.getIdUser());
                        System.out.println(groupUsersList.toString());
                        if (groupUsersList != null)
                        {
                            out.writeUTF("1");
                            out.flush();
                            out.writeObject(groupUsersList);
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "exit":
                        out.writeUTF("exit");
                        out.flush();
                        closeConnection();
                        return;
                    default:
                        out.writeUTF("Solo puedes seleccionar 1 o 2");
                        break;
                }
                out.flush();
            }

        } catch (EOFException e) {
            closeConnection();
            return;
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void closeConnection() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (sc != null) {
                sc.close();
            }
        } catch (IOException e) {
            // Maneja la excepción de cierre de conexión si es necesario
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
