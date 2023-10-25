package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecoverPassword extends JFrame {
    private JLabel usernameLabel = new JLabel("Nombre de Usuario:");
    private JTextField usernameField = new JTextField(20);
    private JLabel newPasswordLabel = new JLabel("Nueva Contraseña:");
    private JPasswordField newPasswordField = new JPasswordField(20);
    private JButton changeButton = new JButton("Cambiar Contraseña");

    public RecoverPassword() {
        super("Recuperar Contraseña");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(usernameLabel)
                .addComponent(usernameField)
        );
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(newPasswordLabel)
                .addComponent(newPasswordField)
        );
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(changeButton)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addContainerGap();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(usernameLabel)
                .addComponent(usernameField)
        );
        vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(newPasswordLabel)
                .addComponent(newPasswordField)
        );
        vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(changeButton)
        );
        vGroup.addContainerGap();
        layout.setVerticalGroup(vGroup);
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual (ChatWindow)
                MainWindow.mainWindow.setVisible(true); // Muestra la ventana MainWindow
            }
        });
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecoverPassword recoverPassword = new RecoverPassword();
            recoverPassword.setVisible(true);
        });
    }
}

