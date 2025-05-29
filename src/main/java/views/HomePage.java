package views;

import controller.UserController;
import models.User;
import service.AuthenticationService;

import java.util.Scanner;

public class HomePage {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final MenuOption menuOption = new MenuOption();
    private final UserController userController = new UserController();

    public void showMainMenuAndGetChoice() {
        try (Scanner scanner = new Scanner(System.in)) {
            String userChoice;
            String userName;
            String email;
            String password;
            while (true) {
                menuOption.displayMenu();
                userChoice = scanner.nextLine();


                switch (userChoice) {
                    case "1":
                        System.out.println("Enter Name: ");
                        userName = scanner.nextLine();
                        System.out.println("Enter Email: ");
                        email = scanner.nextLine();
                        System.out.println("Enter Password: ");
                        password = scanner.nextLine();
                        if (userController.handleSignUp(userName, email, password)) {
                            System.out.println("Sign up successful");
                            System.out.println("Welcome " + userName + "!");
                        }
                        break;
                    case "2":
                        System.out.println("Enter Email: ");
                        email = scanner.nextLine();
                        System.out.println("Enter Password: ");
                        password = scanner.nextLine();
                        if (userController.handleLogin(email, password)) {
                            System.out.println("Login success");
                        } else {
                            System.out.println("No user exists with following email or Invalid Email/Password");
                        }
                        break;
                    case "3":
                        System.out.println("Exiting application...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
                System.out.println("\nPress any button to continue...");
                scanner.nextLine();
            }
        }
    }
}
