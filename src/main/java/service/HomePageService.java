package service;

import dao.AuthenticationDao;
import exception.EmailNotFoundException;
import exception.WrongPasswordException;
import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class HomePageService {

    public boolean signUp(User newUser) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            AuthenticationDao authenticationDao = new AuthenticationDao(connection);
            return authenticationDao.signUp(newUser);
        }
    }

    public User login(String email, String password) throws EmailNotFoundException, WrongPasswordException, SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            AuthenticationDao authenticationDao = new AuthenticationDao(connection);
            return authenticationDao.login(email, password);
        }
    }
}
