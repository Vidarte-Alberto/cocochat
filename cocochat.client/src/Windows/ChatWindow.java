package Windows;

import javax.swing.*;

public class ChatWindow extends JFrame {
    private JLabel label1 = new JLabel("Esperando mensaje...");
    private JTextField textField1 = new JTextField(50);
    private JButton boton1 = new JButton("Mandar");

    public ChatWindow() {
        super("Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(label1)
                .addComponent(textField1)
                .addComponent(boton1)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boton1)
        );

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatWindow chatWindow = new ChatWindow();
            chatWindow.setVisible(true);
        });
    }
}
