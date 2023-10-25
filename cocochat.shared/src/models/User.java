package models;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
    private int idUser;
    private String name;
    private String password;
    private Boolean connected;
    private Timestamp dateFailedRegister;
    private int countRegisterFailed;

    public User() {
        this.name = "";
        this.password = "";
        this.connected = false;
        this.dateFailedRegister = null;
        this.countRegisterFailed = 0;
    }

    public User(String name, String password, Boolean connected, Timestamp dateFailedRegister, int countRegisterFailed) {
        this.name = name;
        this.password = password;
        this.connected = connected;
        this.dateFailedRegister = dateFailedRegister;
        this.countRegisterFailed = countRegisterFailed;
    }

    public User(int idUser, String name, String password, Boolean connected, Timestamp dateFailedRegister, int countRegisterFailed) {
        this.idUser = idUser;
        this.name = name;
        this.password = password;
        this.connected = connected;
        this.dateFailedRegister = dateFailedRegister;
        this.countRegisterFailed = countRegisterFailed;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", connected=" + connected +
                ", dateFailedRegister=" + dateFailedRegister +
                ", countRegisterFailed=" + countRegisterFailed +
                '}';
    }


    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Timestamp getDateFailedRegister() {
        return dateFailedRegister;
    }

    public void setDateFailedRegister(Timestamp dateFailedRegister) {
        this.dateFailedRegister = dateFailedRegister;
    }

    public int getCountRegisterFailed() {
        return countRegisterFailed;
    }

    public void setCountRegisterFailed(int countRegisterFailed) {
        this.countRegisterFailed = countRegisterFailed;
    }
}
