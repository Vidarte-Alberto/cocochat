package Models;

public class Friendship {
    private int idFriendship;
    private int userOrigin;
    private int userDestiny;
    private int idChat;

    public Friendship(){
        this.userOrigin = 0;
        this.userDestiny = 0;
        this.idChat = 0;
    }

    public Friendship(int userOrigin, int userDestiny, int idChat){
        this.userOrigin = userOrigin;
        this.userDestiny = userDestiny;
        this.idChat = idChat;
    }

    public Friendship(int userOrigin, int userDestiny){
        this.userOrigin = userOrigin;
        this.userDestiny = userDestiny;
    }

    public Friendship(int idFriendship, int userOrigin, int userDestiny, int idChat){
        this.userOrigin = userOrigin;
        this.userDestiny = userDestiny;
        this.idChat = idChat;
    }

    public int getIdFriendship() {
        return idFriendship;
    }

    public void setIdFriendship(int idFriendship) {
        this.idFriendship = idFriendship;
    }

    public int getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(int userOrigin) {
        this.userOrigin = userOrigin;
    }

    public int getUserDestiny() {
        return userDestiny;
    }

    public void setUserDestiny(int userDestiny) {
        this.userDestiny = userDestiny;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }
}
