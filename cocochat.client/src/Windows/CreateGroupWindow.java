package Windows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class CreateGroupWindow extends JFrame {
    public CreateGroupWindow() {
        super("Crear Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

        // Crear un panel principal con un fondo claro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.pink); // Fondo claro

        // Crear un campo de entrada para el nombre del grupo
        JTextField groupNameField = new JTextField();
        groupNameField.setBorder(BorderFactory.createTitledBorder("Nombre del Grupo"));
        mainPanel.add(groupNameField, BorderLayout.NORTH);

        // Crear un área de descripción del grupo
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBorder(BorderFactory.createTitledBorder("Descripción"));
        mainPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Crear un botón para crear el grupo
        JButton createGroupButton = createMaterialButton("Crear Grupo", Color.GREEN);
        mainPanel.add(createGroupButton, BorderLayout.SOUTH);

        // Manejar eventos del botón de creación del grupo
        createGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String groupName = groupNameField.getText();
                String description = descriptionArea.getText();
                // Aquí puedes implementar la lógica para crear el grupo con el nombre y la descripción proporcionados
                // Por ahora, simplemente mostramos un mensaje
                JOptionPane.showMessageDialog(null, "Grupo creado: " + groupName);
                dispose(); // Cierra la ventana
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
        button.setPreferredSize(new Dimension(100, 40)); // Ajustar el tamaño del botón
        return button;
    }
}
