package Windows;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupWindow extends JFrame {
    private JLabel groupNameLabel = new JLabel("Nombre del grupo:");
    private JTextField groupNameField = new JTextField(20);
    private JLabel usersLabel = new JLabel("Usuarios del grupo:");
    private JTextArea usersTextArea = new JTextArea(5, 20);
    private JButton createGroupButton = new JButton("Crear Grupo");
    private JButton closeButton = new JButton("Cerrar");

    public CreateGroupWindow() {
        super("Crear Grupo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addComponent(groupNameLabel)
                .addComponent(usersLabel)
        );
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(groupNameField)
                .addComponent(usersTextArea)
        );
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(createGroupButton)
                .addComponent(closeButton)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(groupNameLabel)
                .addComponent(groupNameField)
                .addComponent(createGroupButton)
        );
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(usersLabel)
                .addComponent(usersTextArea)
                .addComponent(closeButton)
        );
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
            CreateGroupWindow createGroupWindow = new CreateGroupWindow();
            createGroupWindow.setVisible(true);
        });
    }
}
