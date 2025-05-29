package controller;

import service.AdminPageService;
import utils.DatabaseConnection;
import utils.ScannerHelper;
import views.AdminPageView;
import views.MenuOption;

import java.sql.Connection;

public class AdminPageController {
    private final MenuOption menuOption;
    private final AdminPageView adminPageView;

    public AdminPageController() {
        this.menuOption = new MenuOption();
        this.adminPageView = new AdminPageView();
    }

    public void runAdminPage(String userEmail) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            AdminPageService adminPageService = new AdminPageService(conn);
            while (true) {
                menuOption.showAdminMenu();
                String userChoice = adminPageView.getUserChoice();
                switch (userChoice) {
                    case "1":
                        System.out.println("Approve/Reject Pending Leave Requests selected.");
                        if (!adminPageView.showLeaveRequests(adminPageService.getPendingLeaveRequests())) {
                            break;
                        }
                        int leaveId = adminPageView.getLeaveId();
                        boolean approvalResult = adminPageView.getApprovalResult();
                        boolean processed = adminPageService.processLeaveRequest(leaveId, approvalResult);
                        if (processed) {
                            if (approvalResult) {
                                System.out.println("Leave with Leave ID " + leaveId + " successfully approved.");
                            } else {
                                System.out.println("Leave with Leave ID " + leaveId + " successfully rejected.");
                            }
                        } else {
                            System.out.println("Failed to process leave request with Leave ID " + leaveId + ".");
                        }
                        break;
                    case "2":
                        System.out.println("Update Leave Balance selected.");
                        String email = adminPageView.getEmail();
                        int newBalance = adminPageView.getNewLeaveBalance();
                        if (adminPageService.updateLeaveBalance(email, newBalance)) {
                            System.out.println("Updated the Balance with new Balance for User: " + userEmail);
                        } else {
                            System.out.println("Enter the correct email to update.");
                        }

                        break;
                    case "3":
                        System.out.println("Show Approved Leaves selected.");
                        adminPageView.showLeaveRequests(adminPageService.getApprovedLeaveRequests());
                        break;
                    case "4":
                        System.out.println("Show Rejected leaves selected.");
                        adminPageView.showLeaveRequests(adminPageService.getRejectedLeaveRequests());
                        break;
                    case "5":
                        System.out.println("Exiting Admin Menu...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }
                System.out.println("\nPress any button to continue...");
                ScannerHelper.getScanner().nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}