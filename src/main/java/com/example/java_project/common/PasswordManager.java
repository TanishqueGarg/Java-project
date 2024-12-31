package com.example.java_project.common;

import com.example.java_project.DatabaseConnection;

import java.sql.*;

public class PasswordManager {

    // Initially load the password from the database
    private static String password = getPasswordFromDatabase();

    // Private constructor to prevent instantiation
    private PasswordManager() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    // Get the current password
    public static String getPassword() {
        return password;
    }

    // Set a new password, and update it in the database
    public static void setPassword(String newPassword) {
        password = newPassword;
        updatePasswordInDatabase(newPassword);
    }

    // Fetch the password from the database
    private static String getPasswordFromDatabase() {
        String query = "SELECT pwd FROM Password WHERE num = 1";
        try (Connection conn = DatabaseConnection.doConnect();  // Use your connection manager
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString("pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if there's an error or no result
    }

    // Update the password in the database
    private static void updatePasswordInDatabase(String newPassword) {
        String updateQuery = "UPDATE Password SET pwd = ? WHERE num = 1";

        try (Connection conn = DatabaseConnection.doConnect(); // Use your connection manager
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
