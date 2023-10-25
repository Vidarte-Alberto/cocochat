package Windows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Session.Session;
import models.User;

public class UserListWindow extends JFrame {
    private JList<User> connectedUsersList;
    private JList<User> disconnectedUsersList;
    private DefaultListModel<User> connectedUsersModel;
    private DefaultListModel<User> disconnectedUsersModel;
    private List<User> model;
    private MainWindow mainWindow; // Referencia a la ventana principal

    public UserListWindow() { // Agrega un parámetro al constructor
        super("Lista de Usuarios");
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.mainWindow = mainWindow;

        connectedUsersModel = new DefaultListModel<>();
        disconnectedUsersModel = new DefaultListModel<>();
        connectedUsersList = new JList<>(connectedUsersModel);
        disconnectedUsersList = new JList<>(disconnectedUsersModel);

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
        disconnectedUsersList.setCellRenderer(new UserCellRenderer());

        // Cargar la lista de usuarios conectados
        loadConnectedUsers();
        loadDisconnectedUsers();

        pack();
    }

    private void loadConnectedUsers() {
        List<User> users = listUsersConnected();
        if (users != null) {
            connectedUsersModel.clear(); // Limpia el modelo actual
            for (User user : users) {
                connectedUsersModel.addElement(user);
            }
        }
    }

    private void loadDisconnectedUsers() {
        List<User> users = listUsersDisconnected();
        if (users != null) {
            disconnectedUsersModel.clear(); // Limpia el modelo actual
            for (User user : users) {
                disconnectedUsersModel.addElement(user);
            }
        }
    }

    private List<User> listUsersDisconnected() {
        var socket = Session.getSocket();
        try {
            var out = Session.getOut(socket);
            var in = Session.getIn(socket);
            out.writeUTF("getAllUserDisconnected");
            out.flush();
            List<User> user = (ArrayList<User>) in.readObject();
            out.flush();

            String response = in.readUTF();
            if (response.equals("1")) {
                JOptionPane.showMessageDialog(this, "Usuarios Cargados");
                return user;
            } else {
                JOptionPane.showMessageDialog(this, "Usuarios no cargados");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> listUsersConnected() {
        var socket = Session.getSocket();
        try {
            var out = Session.getOut(socket);
            var in = Session.getIn(socket);
            out.writeUTF("getAllUserConnected");
            out.flush();
            List<User> user = (ArrayList<User>) in.readObject();
            out.flush();

            String response = in.readUTF();
            if (response.equals("1")) {
                JOptionPane.showMessageDialog(this, "Usuarios Cargados");
                return user;
            } else {
                JOptionPane.showMessageDialog(this, "Usuarios no cargados");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Clase para personalizar la apariencia de las celdas de la lista de usuarios conectados
    class UserCellRenderer extends JPanel implements ListCellRenderer<User> {
        private JLabel nameLabel;
        private JButton addButton;

        public UserCellRenderer() {
            setLayout(new BorderLayout());

            nameLabel = new JLabel();
            addButton = new JButton("Agregar");
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
