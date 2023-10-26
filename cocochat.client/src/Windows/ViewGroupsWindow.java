package Windows;

import Session.Session;
import models.Group;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewGroupsWindow extends JFrame {
    private JList<String> groupList;
    private DefaultListModel<String> groupListModel;
    private JButton viewGroupButton;
    private JButton closeButton;

    public ViewGroupsWindow(User user) {
        super("Ver Grupos Creados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        groupListModel = new DefaultListModel<>();
        groupList = new JList<>(groupListModel);
        JScrollPane listScrollPane = new JScrollPane(groupList);

        // Llama al método para obtener la lista de grupos del usuario
        List<Group> userGroups = listGroupUser(user);
        if (userGroups != null) {
            for (Group group : userGroups) {
                groupListModel.addElement(group.getNameGroup()); // Agregar los grupos del usuario a la lista
            }
        }

        viewGroupButton = new JButton("Ver Grupo Seleccionado");
        closeButton = new JButton("Cerrar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewGroupButton);
        buttonPanel.add(closeButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Manejar el evento de botón para ver el grupo seleccionado
        viewGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    // Aquí puedes abrir la ventana del grupo seleccionado
                    // O realizar cualquier otra acción relacionada con la visualización del grupo.
                    JOptionPane.showMessageDialog(ViewGroupsWindow.this, "Has seleccionado el grupo: " + selectedGroup);
                }
            }
        });

        // Manejar el evento de botón para cerrar la ventana
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual (ViewGroupsWindow)
                MainWindow.mainWindow.setVisible(true); // Muestra la ventana MainWindow
            }
        });

        pack();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true);
    }

    private List<Group> listGroupUser(User user) {
        // Este método se mantiene igual
        var socket = Session.getSocket();
        try {
            var out = Session.getOut(socket);
            var in = Session.getIn(socket);
            out.writeUTF("getGroupByUser");
            out.flush();
            out.writeObject(user);
            out.flush();

            String response = in.readUTF();
            if (response.equals("1")) {
                JOptionPane.showMessageDialog(this, "Grupos Cargados");
                return (ArrayList<Group>) in.readObject();
            } else {
                JOptionPane.showMessageDialog(this, "Grupos no cargados");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
