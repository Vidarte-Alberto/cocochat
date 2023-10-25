package Windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle;


public class ViewGroupsWindow extends JFrame {
    private JList<String> groupList;
    private DefaultListModel<String> groupListModel;
    private JButton viewGroupButton;
    private JButton closeButton;

    public ViewGroupsWindow() {
        super("Ver Grupos Creados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        groupListModel = new DefaultListModel<>();
        groupList = new JList<>(groupListModel);
        JScrollPane listScrollPane = new JScrollPane(groupList);

        viewGroupButton = new JButton("Ver Grupo Seleccionado");
        closeButton = new JButton("Cerrar");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addComponent(listScrollPane);
        hGroup.addGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(viewGroupButton)
                .addComponent(closeButton)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(listScrollPane);
        vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
        vGroup.addComponent(viewGroupButton); // Añadir botón "Ver Grupo Seleccionado"
        vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
        vGroup.addComponent(closeButton); // Añadir botón "Cerrar"
        layout.setVerticalGroup(vGroup);

        // Agregar algunos grupos de ejemplo
        groupListModel.addElement("Grupo 1");
        groupListModel.addElement("Grupo 2");
        groupListModel.addElement("Grupo 3");

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
                dispose();  // Cierra la ventana actual (ChatWindow)
                MainWindow.mainWindow.setVisible(true); // Muestra la ventana MainWindow
            }
        });

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewGroupsWindow viewGroupsWindow = new ViewGroupsWindow();
            viewGroupsWindow.setVisible(true);
        });
    }
}
