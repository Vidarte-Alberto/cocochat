import Windows.ChatWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        SwingUtilities.invokeLater(() -> {
            ChatWindow chatWindow = new ChatWindow();
            chatWindow.setVisible(true);
        });
    }
}
