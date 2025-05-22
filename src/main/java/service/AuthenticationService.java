package service;

import models.User;
import models.UserRole;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AuthenticationService {
    public boolean signUp(User newUser) {
        String checkQuery = "SELECT email FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (user_name, email, password,user_role) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            // Duplicate Email Check
            checkStatement.setString(1, newUser.getEmail());
            if (checkStatement.executeQuery().next()) {
                System.out.println("Email already exists. Sign-up not allowed");
                return false;
            }
            // Insert new user
            insertStatement.setString(1, newUser.getUserName());
            insertStatement.setString(2, newUser.getEmail());
            insertStatement.setString(3, newUser.getPassword());
            insertStatement.setString(4, UserRole.EMPLOYEE.name());
            int rowsInserted = insertStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Error during sign-up: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public User loginAndGetUser(String email, String password) {
        String loginQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(loginQuery)) {
            checkStatement.setString(1, email);
            checkStatement.setString(2, password);
            var rs = checkStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setUserRole(UserRole.valueOf(rs.getString("user_role")));
                return user;
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
