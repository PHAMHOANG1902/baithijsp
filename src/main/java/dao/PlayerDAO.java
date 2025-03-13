package dao;

import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private Connection conn;

    public PlayerDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối đến database (Thay đổi thông tin nếu cần)
            String jdbcURL = "jdbc:mysql://localhost:3306/player_evaluation";
            String dbUser = "root";  // Thay bằng username của bạn
            String dbPassword = "";  // Thay bằng password của bạn
            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể kết nối đến database!");
        }
    }

    // Hàm thêm cầu thủ
    public void insertPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO player (name, full_name, age, index_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getFullName());
            statement.setString(3, player.getAge());
            statement.setInt(4, player.getIndexId());
            statement.executeUpdate();
        }
    }
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM player";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int playerId = rs.getInt("player_id");
                String name = rs.getString("name");
                String fullName = rs.getString("full_name");
                String age = rs.getString("age");
                int indexId = rs.getInt("index_id");

                players.add(new Player(playerId, name, fullName, age, indexId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
