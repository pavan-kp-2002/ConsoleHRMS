package controller;

import models.User;
import service.AuthenticationService;

public class UserController {
    private final AuthenticationService authenticationService = new AuthenticationService();

    public boolean handleSignUp(String userName, String email, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        return authenticationService.signUp(user);
    }

    public boolean handleLogin(String email, String password) {
        return authenticationService.login(email, password);
    }
}
