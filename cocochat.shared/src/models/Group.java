package models;

import java.io.Serializable;

public class Group implements Serializable {
    private int idGroup;
    private int idChat;
    private String nameGroup;

    public Group() {
        this.idChat = 0;
        this.nameGroup = "";
    }

    public Group(int idChat, String nameGroup) {
        this.idChat = idChat;
        this.nameGroup = nameGroup;
    }

    public Group(int idGroup, int idChat, String nameGroup) {
        this.idGroup = idGroup;
        this.idChat = idChat;
        this.nameGroup = nameGroup;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idGroup=" + idGroup +
                ", idChat=" + idChat +
                ", nameGroup='" + nameGroup + '\'' +
                '}';
    }



    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }
}
