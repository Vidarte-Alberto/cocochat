package models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {
    private int idMessage;
    private int idChat;
    private int idUser;
    private Timestamp dateMessage;
    private String message;

    public Message() {
        this.idChat = 0;
        this.idUser = 0;
        this.dateMessage = null;
        this.message = "";
    }

    public Message(int idChat, int idUser, Timestamp dateMessage, String message) {
        this.idChat = idChat;
        this.idUser = idUser;
        this.dateMessage = dateMessage;
        this.message = message;
    }

    public Message(int idMessage, int idChat, int idUser, Timestamp dateMessage, String message) {
        this.idMessage = idMessage;
        this.idChat = idChat;
        this.idUser = idUser;
        this.dateMessage = dateMessage;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", idChat=" + idChat +
                ", idUser=" + idUser +
                ", dateMessage=" + dateMessage +
                ", message='" + message + '\'' +
                '}';
    }


    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Timestamp dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
