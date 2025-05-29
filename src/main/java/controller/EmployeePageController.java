package controller;

import models.LeaveRequest;
import service.LeaveService;
import utils.DatabaseConnection;
import utils.ScannerHelper;
import views.EmployeePageView;
import views.MenuOption;

import java.sql.Connection;
import java.time.LocalDate;

public class EmployeePageController {
    private final MenuOption menuOption;
    private final EmployeePageView employeePageView;

    public EmployeePageController() {
        this.menuOption = new MenuOption();
        this.employeePageView = new EmployeePageView();
    }

    public void runEmployeePage(String employeeEmail) {
        boolean running = true;
        while (running) {
            menuOption.showEmployeeMenu();
            String userChoice = employeePageView.getUserChoice();
            switch (userChoice) {
                case "1":
                    System.out.println("Apply for Leave selected.");
                    String startDate = employeePageView.getStartDate();
                    String endDate = employeePageView.getEndDate(startDate);
                    String reason = employeePageView.getReason();
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        LeaveService leaveService = new LeaveService(conn);
                        leaveService.applyLeave(employeeEmail, LocalDate.parse(startDate), LocalDate.parse(endDate), reason);
                        System.out.println("Leave applied successfully and pending approval.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error applying leave: " + e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                case "2":
                    System.out.println("View Leave Status selected.");
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        LeaveService leaveService = new LeaveService(conn);
                        java.util.List<LeaveRequest> leaves = leaveService.getAppliedLeaves(employeeEmail);
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
                    pressEnterToContinue();
                    break;
                case "3":
                    System.out.println("View Leave Balance selected.");
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        LeaveService leaveService = new LeaveService(conn);
                        int leaveBalance = leaveService.getLeaveBalance(employeeEmail);
                        System.out.println("Balance leaves: " + leaveBalance);
                    } catch (Exception e) {
                        System.out.println("Error in fetching leave balance " + e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                case "4":
                    System.out.println("Exiting Employee Menu...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again");
            }
        }
    }

    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        ScannerHelper.getScanner().nextLine();
    }
}
