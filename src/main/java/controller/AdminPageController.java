package controller;

import utils.ScannerHelper;
import views.MenuOption;

public class AdminPageController {
    private final MenuOption menuOption = new MenuOption();

    public void runAdminPage() {
        while (true) {
            menuOption.showAdminMenu();
            String userChoice = ScannerHelper.getScanner().nextLine();
            switch (userChoice) {
                case "1":
                    System.out.println("Pending Leaves selected");
                    // Add logic for applying leave
                    break;
                case "2":
                    System.out.println("Approved Leaves selected.");
                    // Add logic for viewing leave status
                    break;
                case "3":
                    System.out.println("Rejected leaves selected.");
                    // Add logic for viewing leave balance
                    break;
                case "4":
                    System.out.println("Exiting Admin Menu...");
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again");
            }
            System.out.println("\nPress any button to continue...");
            ScannerHelper.getScanner().nextLine();
        }
    }
}
