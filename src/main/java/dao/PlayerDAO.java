package dao;

import model.Player;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private Connection conn;


    // Constructor: Lấy kết nối từ DBConnection
    public PlayerDAO() {
        this.conn = DBConnection.getConnection();
        if (this.conn == null) {
            throw new RuntimeException("❌ Không thể kết nối đến database!");
        }
    }

    public Connection getConnection() {
        return conn;
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
