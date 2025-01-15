package com.example.java_project.common;

import com.example.java_project.DatabaseConnection;

import java.sql.*;

public class PasswordManager {
    private static String password = getPasswordFromDatabase();
    private PasswordManager() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String newPassword) {
        password = newPassword;
        updatePasswordInDatabase(newPassword);
    }
    private static String getPasswordFromDatabase() {
        String query = "SELECT pwd FROM Password WHERE num = 1";
        try (Connection conn = DatabaseConnection.doConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString("pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static void updatePasswordInDatabase(String newPassword) {
        String updateQuery = "UPDATE Password SET pwd = ? WHERE num = 1";
        try (Connection conn = DatabaseConnection.doConnect();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
