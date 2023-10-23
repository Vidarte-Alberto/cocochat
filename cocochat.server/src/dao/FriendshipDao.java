package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Friendship;

public class FriendshipDao {
    private final Connection connection;

    public FriendshipDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createFriendship(Friendship friendship) {
        String query = "INSERT INTO Amistad (usuario_origen, usuario_destino, id_chat) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, friendship.getUserOrigin());
            preparedStatement.setInt(2, friendship.getUserDestiny());
            preparedStatement.setInt(3, friendship.getIdChat());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Friendship getFriendshipById(int id) {
        String query = "SELECT * FROM Amistad WHERE id_amistad = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToFriendship(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Friendship> getFriendshipsByUser(int userId) {
        List<Friendship> friendships = new ArrayList<>();
        String query = "SELECT * FROM Amistad WHERE usuario_origen = ? OR usuario_destino = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    friendships.add(mapResultSetToFriendship(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return friendships;
    }

    public boolean deleteFriendship(int id) {
        String query = "DELETE FROM Amistad WHERE id_amistad = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Friendship mapResultSetToFriendship(ResultSet resultSet) throws SQLException {
        return new Friendship(
                resultSet.getInt("id_amistad"),
                resultSet.getInt("usuario_origen"),
                resultSet.getInt("usuario_destino"),
                resultSet.getInt("id_chat")
        );
    }
}
