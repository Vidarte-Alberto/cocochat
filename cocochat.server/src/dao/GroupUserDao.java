package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connections.DatabaseConnection;
import models.Group;
import models.GroupUser;

public class GroupUserDao {
    private final Connection connection;

    public GroupUserDao() {
        this.connection = DatabaseConnection.getConnection();;
    }

    public boolean createGroupUser(GroupUser groupUser) {
        String query = "INSERT INTO Grupo_usuarios (id_grupo, id_usuario) " +
                "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupUser.getIdGroup());
            preparedStatement.setInt(2, groupUser.getIdUser());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<GroupUser> getGroupUsersById(int idGroup) {
        List<GroupUser> groupUsers = new ArrayList<>();
        String query = "SELECT * FROM Grupo_usuarios WHERE id_grupo = ?";

        return getGroupUsers(idGroup, groupUsers, query);
    }

    public List<GroupUser> getGroupUsersByUser(int idUser) {
        List<GroupUser> groupUsers = new ArrayList<>();
        String query = "SELECT * FROM Grupo_usuarios WHERE id_usuario = ?";

        return getGroupUsers(idUser, groupUsers, query);
    }

    private List<GroupUser> getGroupUsers(int idUser, List<GroupUser> groupUsers, String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groupUsers.add(mapResultSetToGroupUser(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groupUsers;
    }

    public boolean deleteGroupUser(int idGroupUser) {
        String query = "DELETE FROM Grupo_usuarios WHERE id_grupo_usuarios = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idGroupUser);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private GroupUser mapResultSetToGroupUser(ResultSet resultSet) throws SQLException {
        return new GroupUser(
                resultSet.getInt("id_grupo_usuario"),
                resultSet.getInt("id_grupo"),
                resultSet.getInt("id_usuario")
        );
    }
}
