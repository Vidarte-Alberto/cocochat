package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connections.DatabaseConnection;
import models.User;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        // Obtener la conexión a la base de datos a través de la clase DatabaseConnection
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean createUser(User user) {
        String query = "INSERT INTO Usuarios (nombre, contra, conectado, fecha_registro_fallido, registros_fallidos) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, false);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(5, 0);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM Usuarios WHERE id_usuario = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public List<User> getAllUsersConnected() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios WHERE conectado = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public List<User> getAllUsersDisconnected() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios WHERE conectado = 0";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public boolean updateUser(User user) {
        System.out.println(user.toString());
        String query = "UPDATE Usuarios SET nombre = ?, contra = ?, conectado = ?, fecha_registro_fallido = ?, registros_fallidos = ? " +
                "WHERE id_usuario = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isConnected());
            preparedStatement.setTimestamp(4, user.getDateFailedRegister());
            preparedStatement.setInt(5, user.getCountRegisterFailed());
            preparedStatement.setInt(6, user.getIdUser());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User loginUser(String name, String pass) {
        String query = "SELECT * FROM Usuarios WHERE nombre = ? AND contra = ? LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null; // Inicialmente, no se ha encontrado ningún usuario

            if (resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("id_usuario"));
                user.setName(resultSet.getString("nombre"));
                user.setPassword(resultSet.getString("contra"));
                user.setConnected(resultSet.getBoolean("conectado"));
                user.setDateFailedRegister(resultSet.getTimestamp("fecha_registro_fallido"));
                user.setCountRegisterFailed(resultSet.getInt("registros_fallidos"));
            }

            if (user != null) {
                String updateQuery = "UPDATE Usuarios SET conectado = 1 WHERE nombre = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, name);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new SQLException("Error updating connection status");
                }
            }

            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null; // Devuelve null en caso de error o si no se encuentra un usuario
        }
    }

    public boolean deleteUser(int id) {
        String query = "DELETE FROM Usuarios WHERE id_usuario = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void disconnectAllUsers() {
        String query = "UPDATE Usuarios SET conectado = 0";

        var connection = DatabaseConnection.getConnection();
        // execute update
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id_usuario"),
                resultSet.getString("nombre"),
                resultSet.getString("contra"),
                resultSet.getBoolean("conectado"),
                resultSet.getTimestamp("fecha_registro_fallido"),
                resultSet.getInt("registros_fallidos")
        );
    }
 }
