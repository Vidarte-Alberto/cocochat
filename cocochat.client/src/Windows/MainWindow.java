package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    static MainWindow mainWindow; // Mantén una referencia estática a la instancia de MainWindow

    public MainWindow() {
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear botones para abrir las diferentes ventanas
        JButton chatButton = new JButton("Abrir Chat");
        JButton createGroupButton = new JButton("Crear Grupo");
        JButton userListButton = new JButton("Lista de Usuarios");
        JButton viewGroupButton = new JButton("Ver Grupo");

        // Manejar eventos de los botones
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Oculta la ventana actual (MainWindow)
                setVisible(false);
                // Abre la ventana de chat
                ChatWindow chatWindow = new ChatWindow();
                chatWindow.setVisible(true);
            }
        });

        createGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Oculta la ventana actual (MainWindow)
                setVisible(false);
                // Abre la ventana de creación de grupo
                CreateGroupWindow createGroupWindow = new CreateGroupWindow();
                createGroupWindow.setVisible(true);
            }
        });

        userListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Oculta la ventana actual (MainWindow)
                setVisible(false);
                // Abre la ventana de lista de usuarios
                UserListWindow userListWindow = new UserListWindow();
                userListWindow.setVisible(true);
            }
        });

        viewGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Oculta la ventana actual (MainWindow)
                setVisible(false);
                // Abre la ventana para ver un grupo
                ViewGroupsWindow viewGroupsWindow = new ViewGroupsWindow();
                viewGroupsWindow.setVisible(true);
            }
        });

        // Crear el diseño de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(chatButton);
        buttonPanel.add(createGroupButton);
        buttonPanel.add(userListButton);
        buttonPanel.add(viewGroupButton);

        // Agregar los botones al contenido del JFrame
        getContentPane().add(buttonPanel);

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
