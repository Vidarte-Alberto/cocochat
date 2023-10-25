package Windows;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import Session.Session;
import models.*;

import java.io.*;

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

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(a -> register());


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

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario y la contraseña no pueden estar vacíos");
            return;
        }

        var socket = Session.getSocket();
        try {
            var out = Session.getOut(socket);
            var in = Session.getIn(socket);

            var user = new User(username, password);
            out.writeUTF("register");
            out.flush();
            out.writeObject(user);
            out.flush();

            String response = in.readUTF();
            if (response.equals("1")) {
                JOptionPane.showMessageDialog(this, "Registro exitoso");
            } else {
                JOptionPane.showMessageDialog(this, "Registro fallido");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Aquí puedes enviar el mensaje de inicio de sesión al servidor
        // y esperar la respuesta del servidor

        // Si el inicio de sesión fue exitoso, puedes abrir la ventana MainWindow
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);

        // Cerrar la ventana actual (LoginWindow)
        dispose();
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario y la contraseña no pueden estar vacíos");
            return;
        }

        var socket = Session.getSocket();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = Session.getOut(socket);
            in = Session.getIn(socket);

            var user = new User(username, password);
            out.writeUTF("login");
            out.flush();
            out.writeObject(user);
            out.flush();

            String response = in.readUTF();
            if (response.equals("1")) {
                JOptionPane.showMessageDialog(this, "Ingreso exitoso");
            } else {
                JOptionPane.showMessageDialog(this, "Ingreso fallido");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if ( out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if ( in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);

        dispose();
    }
}
