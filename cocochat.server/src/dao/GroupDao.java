package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connections.DatabaseConnection;
import models.Group;
import org.mariadb.jdbc.Statement;

public class GroupDao {
    private final Connection connection;

    public GroupDao() {
        // Obtener la conexión a la base de datos a través de la clase DatabaseConnection
        this.connection = DatabaseConnection.getConnection();
    }

    /*public boolean createGroup(Group group) {
        String query = "INSERT INTO Grupo (id_chat, nombre) " +
                "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, group.getIdChat());
            preparedStatement.setString(2, group.getNameGroup());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }*/

    public boolean createGroup(Group group) {
        try {
            // Paso 1: Crear un nuevo chat y obtener su ID
            String chatInsertQuery = "INSERT INTO Chats () VALUES ()";
            PreparedStatement chatInsertStatement = connection.prepareStatement(chatInsertQuery, Statement.RETURN_GENERATED_KEYS);
            chatInsertStatement.executeUpdate();

            ResultSet generatedKeys = chatInsertStatement.getGeneratedKeys();
            int chatId = -1;
            if (generatedKeys.next()) {
                chatId = generatedKeys.getInt(1);
            } else {
                // No se generó un ID de chat, manejar el error como sea necesario.
                return false;
            }

            // Paso 2: Insertar el grupo con el ID del chat
            String groupInsertQuery = "INSERT INTO Grupo (id_chat, nombre) VALUES (?, ?)";
            PreparedStatement groupInsertStatement = connection.prepareStatement(groupInsertQuery);
            groupInsertStatement.setInt(1, chatId);
            groupInsertStatement.setString(2, group.getNameGroup());

            int rowsAffected = groupInsertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public Group getGroupById(int id) {
        String query = "SELECT * FROM Grupo WHERE id_grupo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToGroup(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Group> getGroupsByChat(int idChat) {
        List<Group> groups = new ArrayList<>();
        String query = "SELECT * FROM Grupo WHERE id_chat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idChat);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(mapResultSetToGroup(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groups;
    }

    public boolean updateGroup(Group group) {
        String query = "UPDATE Grupo SET id_chat = ?, nombre = ? " +
                "WHERE id_grupo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, group.getIdChat());
            preparedStatement.setString(2, group.getNameGroup());
            preparedStatement.setInt(3, group.getIdGroup());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteGroup(int id) {
        String query = "DELETE FROM Grupo WHERE id_grupo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Group mapResultSetToGroup(ResultSet resultSet) throws SQLException {
        return new Group(
                resultSet.getInt("id_grupo"),
                resultSet.getInt("id_chat"),
                resultSet.getString("nombre")
        );
    }
}
