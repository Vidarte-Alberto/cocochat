package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Chat;

public class ChatDao {
    private final Connection connection;

    public ChatDao(Connection connection) {
        this.connection = connection;
    }

    public Chat getChatById(int id) {
        String query = "SELECT * FROM Chats WHERE id_chat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToChat(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private Chat mapResultSetToChat(ResultSet resultSet) throws SQLException {
        return new Chat(resultSet.getInt("id_chat"));
    }
}
