package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Message;

public class MessageDao {
    private final Connection connection;

    public MessageDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createMessage(Message message) {
        String query = "INSERT INTO Mensaje (id_chat, id_usuario, hora_mensaje, mensaje) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, message.getIdChat());
            preparedStatement.setInt(2, message.getIdUser());
            preparedStatement.setTimestamp(3, message.getDateMessage());
            preparedStatement.setString(4, message.getMessage());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Message getMessageById(int id) {
        String query = "SELECT * FROM Mensaje WHERE id_mensaje = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToMessage(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getMessagesByChat(int idChat) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM Mensaje WHERE id_chat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idChat);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    messages.add(mapResultSetToMessage(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public boolean updateMessage(Message message) {
        String query = "UPDATE Mensaje SET id_chat = ?, id_usuario = ?, hora_mensaje = ?, mensaje = ? " +
                "WHERE id_mensaje = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, message.getIdChat());
            preparedStatement.setInt(2, message.getIdUser());
            preparedStatement.setTimestamp(3, message.getDateMessage());
            preparedStatement.setString(4, message.getMessage());
            preparedStatement.setInt(5, message.getIdMessage());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteMessage(int id) {
        String query = "DELETE FROM Mensaje WHERE id_mensaje = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Message mapResultSetToMessage(ResultSet resultSet) throws SQLException {
        return new Message(
                resultSet.getInt("id_mensaje"),
                resultSet.getInt("id_chat"),
                resultSet.getInt("id_usuario"),
                resultSet.getTimestamp("hora_mensaje"),
                resultSet.getString("mensaje")
        );
    }
}
