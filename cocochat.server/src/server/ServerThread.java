package server;

import dao.*;
import models.*;
import connections.DatabaseConnection;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Connection connection = DatabaseConnection.getConnection();
    private String nombre;
    private User user = new User();
    private UserDao userDao = new UserDao();
    private List<User> userList = new ArrayList<User>();
    private int chatId;
    private Chat chat = new Chat(chatId);
    private ChatDao chatDao = new ChatDao(connection);
    private Group group = new Group();
    private GroupDao groupDao = new GroupDao(connection);
    private List<Group> groupList = new ArrayList<Group>();
    private GroupUser groupUser = new GroupUser();
    private GroupUserDao groupUserDao = new GroupUserDao(connection);
    private List<GroupUser> groupUserList = new ArrayList<GroupUser>();
    private Friendship friendship = new Friendship();
    private FriendshipDao friendshipDao = new FriendshipDao(connection);
    private List<Friendship> friendshipList = new ArrayList<Friendship>();
    private Message message;
    private MessageDao messageDao;
    private List<Message> messagesList = new ArrayList<Message>();

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
                    case "updateUser":
                        user = (User)in.readObject();
                        if(userDao.updateUser(user)){
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
                        if(groupDao.createGroup(group)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getGroupById":
                        group = groupDao.getGroupById(Integer.parseInt(in.readUTF()));
                        if (group != null)
                        {
                            out.writeObject(group);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "getGroupsByChat":
                        groupList = groupDao.getGroupsByChat(Integer.parseInt(in.readUTF()));
                        out.writeObject(groupList);
                        break;
                    case "updateGroup":
                        group = (Group)in.readObject();
                        if(groupDao.updateGroup(group)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "deleteGroup":
                        group = (Group)in.readObject();
                        if(groupDao.deleteGroup(group.getIdGroup())){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "createGroupUser":
                        groupUser = (GroupUser) in.readObject();
                        if(groupUserDao.createGroupUser(groupUser)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getGroupUsersById":
                        groupUserList = groupUserDao.getGroupUsersById(Integer.parseInt(in.readUTF()));
                        if (groupUser != null)
                        {
                            out.writeObject(groupUser);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "getGroupUsersByUser":
                        groupUserList = groupUserDao.getGroupUsersByUser(Integer.parseInt(in.readUTF()));
                        out.writeObject(groupUserList);
                        break;
                    case "deleteGroupUser":
                        groupUser = (GroupUser)in.readObject();
                        if(groupUserDao.deleteGroupUser(groupUser.getIdGroupUser())){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "createFriendship":
                        friendship = (Friendship) in.readObject();
                        if(friendshipDao.createFriendship(friendship)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getFriendshipById":
                        friendship = friendshipDao.getFriendshipById(Integer.parseInt(in.readUTF()));
                        if (friendship != null)
                        {
                            out.writeObject(friendship);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "getFriendshipsByUser":
                        friendshipList = friendshipDao.getFriendshipsByUser(Integer.parseInt(in.readUTF()));
                        out.writeObject(friendshipList);
                        break;
                    case "deleteFriendship":
                        friendship = (Friendship)in.readObject();
                        if(friendshipDao.deleteFriendship(friendship.getIdFriendship())){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getChatById":
                        chat = chatDao.getChatById(Integer.parseInt(in.readUTF()));
                        if (chat != null)
                        {
                            out.writeObject(chat);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "createMessage":
                        message = (Message) in.readObject();
                        if(messageDao.createMessage(message)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "getMessageById":
                        message = messageDao.getMessageById(Integer.parseInt(in.readUTF()));
                        if (message != null)
                        {
                            out.writeObject(message);
                        }else {
                            out.writeObject(null);
                        }
                        break;
                    case "getMessagesByChat":
                        messagesList = messageDao.getMessagesByChat(Integer.parseInt(in.readUTF()));
                        out.writeObject(messagesList);
                        break;
                    case "updateMessage":
                        message = (Message)in.readObject();
                        if(messageDao.updateMessage(message)){
                            out.writeUTF("1");
                        }else {
                            out.writeUTF("0");
                        }
                        break;
                    case "deleteMessage":
                        message = (Message)in.readObject();
                        if(messageDao.deleteMessage(message.getIdMessage())){
                            out.writeUTF("1");
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
