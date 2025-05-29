package controller;

import exception.EmailNotFoundException;
import exception.WrongPasswordException;
import models.User;
import models.UserRole;
import service.HomePageService;
import utils.ScannerHelper;
import views.AdminPage;
import views.EmployeePageView;
import views.HomePageView;
import views.MenuOption;

import java.sql.SQLException;

public class HomePageController {
    private final HomePageView homePageView;
    private final MenuOption menuOption;
    private final HomePageService homePageService;
    private final AdminPage adminPage;
    private final EmployeePageController employeePageController;

    public HomePageController() {
        this.homePageView = new HomePageView();
        this.menuOption = new MenuOption();
        this.homePageService = new HomePageService();
        this.adminPage = new AdminPage();
        this.employeePageController = new EmployeePageController();
    }

    public void runHomePage() {
        boolean running = true;
        while (running) {
            menuOption.showMainMenu();
            String userChoice = homePageView.getUserChoice();
            switch (userChoice) {
                case "1":
                    // Handle signup
                    User user = new User();
                    user.setUserName(homePageView.getUserName());
                    user.setEmail(homePageView.getEmail());
                    user.setPassword(homePageView.getPassword());
                    user.setUserRole(UserRole.EMPLOYEE);
                    try {
                        if (homePageService.signUp(user)) {
                            homePageView.showSignupSuccess(user.getUserName(), user.getEmail());
                        }
                    } catch (SQLException e) {
                        System.out.println("Error during signup: " + e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                case "2":
                    // Handle login
                    try {
                        User loggedInUser = homePageService.login(homePageView.getEmail(), homePageView.getPassword());
                        System.out.println("Welcome " + loggedInUser.getUserName() + " !");
                        if (loggedInUser.getUserRole() == UserRole.ADMIN) {
                            adminPage.showAdminMenuAndHandleChoice();
                        } else if (loggedInUser.getUserRole() == UserRole.EMPLOYEE) {
                            employeePageController.runEmployeePage(loggedInUser.getEmail());
                        } else {
                            System.out.println("You don't have a proper role to access this application.");
                        }
                    } catch (EmailNotFoundException emailNotFoundExc) {
                        System.out.println(emailNotFoundExc.getMessage());
                    } catch (WrongPasswordException wrongPasswordExc) {
                        System.out.println(wrongPasswordExc.getMessage());
                    } catch (SQLException exc) {
                        System.out.println("Error while login: " + exc.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                case "3":
                    // Handle exit
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    //Handle invalid choice
                    System.out.println("Invalid choice");
                    pressEnterToContinue();
            }
        }
    }

    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        ScannerHelper.getScanner().nextLine();
    }
}
