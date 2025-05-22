package views;

import controller.UserController;
import models.User;
import models.UserRole;
import service.AuthenticationService;
import utils.InputValidator;
import utils.ScannerHelper;

public class HomePage {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final MenuOption menuOption = new MenuOption();
    private final UserController userController = new UserController();
    private final EmployeePage employeePage = new EmployeePage();
    private final AdminPage adminPage = new AdminPage();


    public void showMainMenuAndGetChoice() {
        String userChoice;
        String userName;
        String email;
        String password;
        while (true) {
            menuOption.displayMenu();
            userChoice = ScannerHelper.getScanner().nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("Enter Name: ");
                    userName = ScannerHelper.getScanner().nextLine();

                    do {
                        System.out.println("Enter Email: ");
                        email = ScannerHelper.getScanner().nextLine();
                        if (!InputValidator.isValidEmail(email)) {
                            System.out.println("Invalid email, email should end with @itt.com. Try again.");
                        }
                    } while (!InputValidator.isValidEmail(email));

                    do {
                        System.out.println("Enter Password: ");
                        password = ScannerHelper.getScanner().nextLine();
                        if (!InputValidator.isValidPassword(password)) {
                            System.out.println("Password must be between 4 to 8 Characters");
                        }
                    } while (!InputValidator.isValidPassword(password));

                    if (userController.handleSignUp(userName, email, password)) {
                        System.out.println("Sign up successful");
                        System.out.println("You can login with your email and password");
                    }
                    break;
                case "2":
                    System.out.println("Enter Email: ");
                    email = ScannerHelper.getScanner().nextLine();
                    System.out.println("Enter Password: ");
                    password = ScannerHelper.getScanner().nextLine();

                    User loggedInUser = userController.handleLogin(email, password);

                    if (loggedInUser != null) {
                        System.out.println("Welcome " + loggedInUser.getUserName() + " !");
                        if (loggedInUser.getUserRole() == UserRole.ADMIN) {
                            adminPage.showAdminMenuAndHandleChoice();
                        } else if (loggedInUser.getUserRole() == UserRole.EMPLOYEE) {
                            employeePage.showEmployeeMenuAndHandleChoice(loggedInUser.getEmail());
                        } else {
                            System.out.println("You don't have a proper role to access this application.");
                        }
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
            System.out.println("\nPress Enter to continue...");
            ScannerHelper.getScanner().nextLine();
        }
    }
}
