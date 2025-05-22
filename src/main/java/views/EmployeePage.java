package views;

import dao.LeaveRequestDao;
import models.LeaveRequest;
import service.LeaveService;
import utils.DatabaseConnection;
import utils.InputValidator;
import utils.ScannerHelper;

import java.sql.Connection;
import java.time.LocalDate;

public class EmployeePage {
    private final MenuOption menuOption = new MenuOption();

    public void showEmployeeMenuAndHandleChoice(String userEmail) {
        String choice;
        while (true) {
            menuOption.showEmployeeMenu();
            choice = ScannerHelper.getScanner().nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Apply for Leave selected.");
                    String startDate;
                    do {
                        System.out.println("Enter start date (yyyy-mm-dd): ");
                        startDate = ScannerHelper.getScanner().nextLine();
                        if (!InputValidator.isValidDate(startDate)) {
                            System.out.println("Invalid date format. Try again.");
                        }
                    } while (!InputValidator.isValidDate(startDate));

                    String endDate;
                    do {
                        System.out.println("Enter end date (yyyy-mm-dd): ");
                        endDate = ScannerHelper.getScanner().nextLine();
                        if (!InputValidator.isValidDate(endDate) || !InputValidator.isEndDateAfterStart(startDate, endDate)) {
                            System.out.println("Invalid end date. It must be after or equal to start date and in yyyy-mm-dd format.");
                        }
                    } while (!InputValidator.isValidDate(endDate) || !InputValidator.isEndDateAfterStart(startDate, endDate));

                    System.out.println("Enter reason: ");
                    String reason = ScannerHelper.getScanner().nextLine();

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        LeaveService leaveService = new LeaveService(conn);
                        leaveService.applyLeave(userEmail, LocalDate.parse(startDate), LocalDate.parse(endDate), reason);
                        System.out.println("Leave applied successfully and pending approval.");
                    } catch (Exception e) {
                        System.out.println("Error applying leave: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("View Leave Status selected.");
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        LeaveService leaveService = new LeaveService(conn);
                        java.util.List<LeaveRequest> leaves = leaveService.getAppliedLeaves(userEmail);
                        if (leaves.isEmpty()) {
                            System.out.println("No leave applications found.");
                        } else {
                            System.out.println("Your Leave Applications:");
                            for (LeaveRequest req : leaves) {
                                System.out.println("ID: " + req.getId() +
                                        ", From: " + req.getStartDate() +
                                        ", To: " + req.getEndDate() +
                                        ", Status: " + req.getStatus() +
                                        ", Reason: " + req.getReason());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error fetching leave status: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("View Leave Balance selected.");
                    break;
                case "4":
                    System.out.println("Exiting Employee Menu...");
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again");
            }
            System.out.println("\nPress Enter to continue...");
            ScannerHelper.getScanner().nextLine();
        }
    }
}
