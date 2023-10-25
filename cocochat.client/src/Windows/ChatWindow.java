package Windows;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame {
    private JLabel label1 = new JLabel("Esperando mensaje...");
    private JTextField textField1 = new JTextField(50);
    private JButton sendButton = new JButton("Mandar");
    private JButton closeButton = new JButton("Cerrar");

    public ChatWindow() {
        super("Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(label1)
                .addComponent(textField1)
                .addComponent(sendButton)
        );
        hGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addComponent(closeButton)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(label1);
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addComponent(textField1);
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addComponent(sendButton);
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addComponent(closeButton);
        layout.setVerticalGroup(vGroup);

        // Agregamos un manejador de eventos al botón "Cerrar"
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual (ChatWindow)
                MainWindow.mainWindow.setVisible(true); // Muestra la ventana MainWindow
            }
        });


        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatWindow chatWindow = new ChatWindow();
            chatWindow.setVisible(true);
        });
    }
}
