package models;

public class GroupUser {
    private int idGroupUser;
    private int idGroup;
    private int idUser;

    public GroupUser(){
        this.idGroup = 0;
        this.idUser = 0;
    }

    public GroupUser(int idGroup, int idUser) {
        this.idGroup = idGroup;
        this.idUser = idUser;
    }

    public GroupUser(int idGroupUser, int idGroup, int idUser) {
        this.idGroupUser = idGroupUser;
        this.idGroup = idGroup;
        this.idUser = idUser;
    }

    public int getIdGroupUser() {
        return idGroupUser;
    }

    public void setIdGroupUser(int idGroupUser) {
        this.idGroupUser = idGroupUser;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
