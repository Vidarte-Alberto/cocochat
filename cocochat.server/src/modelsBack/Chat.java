package modelsBack;

import java.io.Serializable;

public class Chat implements Serializable {
    private int idChat;

    public Chat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }
}
