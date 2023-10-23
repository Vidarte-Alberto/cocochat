package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            preparedStatement.setBoolean(3, user.isConnected());
            preparedStatement.setTimestamp(4, user.getDateFailedRegister());
            preparedStatement.setInt(5, user.getCountRegisterFailed());

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

    public boolean updateUser(User user) {
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
