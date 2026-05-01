package com.chat.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {

    public int getUser(String username) {
        try {
            Connection conn = Db.getConnection();

            String select = "SELECT id FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            String insert = "INSERT INTO users(username, password) VALUES(?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            ps2.setString(1, username);
            ps2.setString(2, "123");

            ps2.executeUpdate();

            ResultSet keys = ps2.getGeneratedKeys();

            if (keys.next()) {
                return keys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}