// src/main/java/service/HomePageService.java
package service;

import dao.AuthenticationDao;
import exception.EmailNotFoundException;
import exception.WrongPasswordException;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;

public class HomePageService {
    private final AuthenticationDao authenticationDao;

    public HomePageService(Connection connection) {
        this.authenticationDao = new AuthenticationDao(connection);
    }

    public boolean signUp(User newUser) throws SQLException {
        return authenticationDao.signUp(newUser);
    }

    public User login(String email, String password) throws EmailNotFoundException, WrongPasswordException, SQLException {
        return authenticationDao.login(email, password);
    }
}