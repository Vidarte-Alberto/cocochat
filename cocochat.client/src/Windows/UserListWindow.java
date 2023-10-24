package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

import Models.User;

public class UserListWindow extends JFrame {
    private JList<User> connectedUsersList;
    private JList<String> disconnectedUsersList;
    private DefaultListModel<User> connectedUsersModel;
    private DefaultListModel<String> disconnectedUsersModel;
    private MainWindow mainWindow; // Referencia a la ventana principal

    public UserListWindow() { // Agrega un parámetro al constructor
        super("Lista de Usuarios");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.mainWindow = mainWindow;

        connectedUsersModel = new DefaultListModel<>();
        disconnectedUsersModel = new DefaultListModel<>();
        connectedUsersList = new JList<>(connectedUsersModel);
        disconnectedUsersList = new JList<>(disconnectedUsersModel);

        // Agregar algunos usuarios de ejemplo
        connectedUsersModel.addElement(new User("User 1","123",true, Timestamp.from(ZonedDateTime.now().toInstant()),1));
        connectedUsersModel.addElement(new User("User 2","123",true, Timestamp.from(ZonedDateTime.now().toInstant()),1));
        disconnectedUsersModel.addElement("Usuario 3");
        disconnectedUsersModel.addElement("Usuario 4");

        JLabel connectedLabel = new JLabel("Usuarios Conectados");
        JLabel disconnectedLabel = new JLabel("Usuarios Desconectados");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JPanel(new BorderLayout()) {{
                    add(connectedLabel, BorderLayout.NORTH);
                    add(new JScrollPane(connectedUsersList), BorderLayout.CENTER);
                }},
                new JPanel(new BorderLayout()) {{
                    add(disconnectedLabel, BorderLayout.NORTH);
                    add(new JScrollPane(disconnectedUsersList), BorderLayout.CENTER);
                }});

        splitPane.setDividerLocation(250); // Ajusta el ancho de la división

        JButton exitButton = new JButton("Salir");

        // Agregar el botón "Salir" en la parte inferior de la ventana
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(exitButton, BorderLayout.SOUTH);

        // Personaliza el aspecto del splitPane y el botón "Salir" si es necesario
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);

        // Manejar el evento de botón para salir
        exitButton.addActionListener(e -> {
            setVisible(false); // Oculta la ventana actual (UserListWindow)
            MainWindow.mainWindow.setVisible(true); // Muestra la ventana MainWindow
        });

        // Agregar un botón para enviar solicitud de amistad junto a cada usuario conectado
        connectedUsersList.setCellRenderer(new UserCellRenderer());

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            UserListWindow userListWindow = new UserListWindow(); // Pasa la referencia a MainWindow
            userListWindow.setVisible(true);
        });
    }


    // Clase para representar un usuario

    // Clase para personalizar la apariencia de las celdas de la lista de usuarios conectados
    class UserCellRenderer extends JPanel implements ListCellRenderer<User> {
        private JLabel nameLabel;
        private JButton addButton;

        public UserCellRenderer() {
            setLayout(new BorderLayout());

            nameLabel = new JLabel();
            addButton = new JButton("Enviar solicitud");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para enviar la solicitud de amistad
                    // Puedes implementar esto según tus necesidades
                    JOptionPane.showMessageDialog(UserListWindow.this, "Solicitud de amistad enviada.");
                }
            });

            add(nameLabel, BorderLayout.CENTER);
            add(addButton, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value.getName());
            return this;
        }
    }
}
