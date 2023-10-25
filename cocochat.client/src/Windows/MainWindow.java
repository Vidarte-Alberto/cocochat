package Windows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    static MainWindow mainWindow;

    public MainWindow() {
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

        // Crear un panel principal con un fondo claro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.setBackground(Color.pink); // Fondo claro

        // Crear botones personalizados con un aspecto de Material Design
        JButton chatButton = createMaterialButton("Abrir Chat", Color.BLUE);
        JButton createGroupButton = createMaterialButton("Crear Grupo", Color.GREEN);
        JButton userListButton = createMaterialButton("Lista de Usuarios", Color.ORANGE);
        JButton viewGroupButton = createMaterialButton("Ver Grupo", Color.PINK);

        // Agregar los botones al panel principal
        mainPanel.add(chatButton);
        mainPanel.add(createGroupButton);
        mainPanel.add(userListButton);
        mainPanel.add(viewGroupButton);

        // Manejar los eventos de los botones
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementa la lógica para abrir la ventana de chat
                new ChatWindow();
            }
        });

        createGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementa la lógica para abrir la ventana de creación de grupo
                new CreateGroupWindow();
            }
        });

        userListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementa la lógica para abrir la ventana de lista de usuarios
            }
        });

        viewGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementa la lógica para abrir la ventana de visualización de grupos
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
        button.setPreferredSize(new Dimension(300, 50)); // Ajustar el tamaño del botón
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Cambiar la fuente del texto
        return button;
    }

}
