package Windows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class ChatWindow extends JFrame {
    public ChatWindow() {
        super("Chat Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado para cerrar solo esta ventana
        setPreferredSize(new Dimension(400, 500)); // Ajusta el tamaño
        setLocationRelativeTo(null);

        // Crear un panel principal con un fondo claro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.pink); // Fondo claro

        // Crear un área de chat
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear un campo de entrada de texto
        JTextField inputField = new JTextField();
        mainPanel.add(inputField, BorderLayout.SOUTH);

        // Crear un botón de envío
        JButton sendButton = createMaterialButton("Enviar", Color.BLUE);
        mainPanel.add(sendButton, BorderLayout.EAST);

        // Manejar eventos del botón de envío
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                chatArea.append("Tú: " + message + "\n");
                inputField.setText("");
            }
        });

        // Agregar el panel principal a la ventana
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    private JButton createMaterialButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setPreferredSize(new Dimension(80, 50)); // Ajustar el tamaño del botón
        return button;
    }
}
