package views;

import utils.InputValidator;
import utils.ScannerHelper;

public class HomePageView {
    public String getUserChoice() {
        System.out.println("Enter Choice: ");
        return ScannerHelper.getScanner().nextLine();
    }

    public String getUserName() {
        System.out.println("Enter Name: ");
        return ScannerHelper.getScanner().nextLine();
    }

    public String getEmail() {
        String email;
        do {
            System.out.println("Enter Email: ");
            email = ScannerHelper.getScanner().nextLine();
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email, email should end with @itt.com. Try again.");
            }
        } while (!InputValidator.isValidEmail(email));
        return email;
    }

    public String getPassword() {
        String password;
        do {
            System.out.println("Enter Password: ");
            password = ScannerHelper.getScanner().nextLine();
            if (!InputValidator.isValidPassword(password)) {
                System.out.println("Password must be between " + InputValidator.MIN_LENGTH + " to " + InputValidator.MAX_LENGTH + " Characters");
            }
        } while (!InputValidator.isValidPassword(password));
        return password;
    }

    public void showSignupSuccess(String userName, String email) {
        System.out.println("Sign up successful");
        System.out.println("Welcome " + userName);
        System.out.println("You can login with your email " + email + " and password");
    }
}
