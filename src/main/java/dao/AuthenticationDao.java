package dao;

import exception.EmailNotFoundException;
import exception.WrongPasswordException;
import models.User;
import models.UserRole;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthenticationDao {
    private final Connection connection;
    private static final int INITIAL_LEAVE_BALANCE = 10;

    public AuthenticationDao(Connection connection) {
        this.connection = connection;
    }

    public boolean signUp(User newUser) throws SQLException {
        String checkQuery = "SELECT email FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (user_name, email, password, user_role, leave_balance) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            checkStatement.setString(1, newUser.getEmail());
            if (checkStatement.executeQuery().next()) {
                System.out.println("Email already exists. Sign-up not allowed");
                return false;
            }
            insertStatement.setString(1, newUser.getUserName());
            insertStatement.setString(2, newUser.getEmail());
            insertStatement.setString(3, getHashedPassword(newUser.getPassword()));
            insertStatement.setString(4, UserRole.EMPLOYEE.name());
            insertStatement.setInt(5, INITIAL_LEAVE_BALANCE);

            int rowsInserted = insertStatement.executeUpdate();
            return rowsInserted > 0;
        }
    }

    private String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User login(String email, String password) throws SQLException, EmailNotFoundException, WrongPasswordException {
        String loginQuery = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(loginQuery)) {
            checkStatement.setString(1, email);
            var rs = checkStatement.executeQuery();
            if (!rs.next()) {
                throw new EmailNotFoundException("Email not registered.");
            }
            String storedHashPassword = rs.getString("password");
            if (!BCrypt.checkpw(password, storedHashPassword)) {
                throw new WrongPasswordException("Incorrect password.");
            }
            User user = new User();
            user.setUserName(rs.getString("user_name"));
            user.setEmail(rs.getString("email"));
            user.setUserRole(UserRole.valueOf(rs.getString("user_role")));
            return user;
        }
    }
}
