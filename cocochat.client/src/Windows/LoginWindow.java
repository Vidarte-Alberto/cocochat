package Windows;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LoginWindow extends JFrame {
    private JLabel usernameLabel = new JLabel("Nombre de Usuario:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Contraseña:");
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Iniciar Sesión");
    private JButton registerButton = new JButton("Registrarse");

    public LoginWindow() {
        super("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(Alignment.CENTER);
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(usernameLabel)
                .addComponent(usernameField)
        );
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(passwordLabel)
                .addComponent(passwordField)
        );
        hGroup.addGroup(layout.createSequentialGroup()
                .addComponent(loginButton)
                .addComponent(registerButton)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addContainerGap();
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(usernameLabel)
                .addComponent(usernameField)
        );
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
        );
        vGroup.addPreferredGap(ComponentPlacement.UNRELATED);
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(loginButton)
                .addComponent(registerButton)
        );
        vGroup.addContainerGap();
        layout.setVerticalGroup(vGroup);

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
