package com.chat.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MessageDAO {

    public void saveMessage(int senderId, int receiverId, String content) {
        try {
            Connection conn = Db.getConnection();

            String sql = "INSERT INTO messages(sender_id, receiver_id, content) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}