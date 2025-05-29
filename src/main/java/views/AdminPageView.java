package views;

import models.LeaveRequest;
import utils.ScannerHelper;

import java.util.InputMismatchException;
import java.util.List;

public class AdminPageView {
    public String getUserChoice() {
        System.out.println("Enter Choice: ");
        return ScannerHelper.getScanner().nextLine();
    }

    public int getLeaveId() {
        System.out.println("Enter Leave ID to select for Approval/Rejection: ");
        while (true) {
            try {
                int id = ScannerHelper.getScanner().nextInt();
                ScannerHelper.getScanner().nextLine();
                return id;
            } catch (InputMismatchException e) {
                System.out.println("Only Integer value is allowed.");
                ScannerHelper.getScanner().nextLine();
            }
        }
    }

    public boolean getApprovalResult() {
        while (true) {
            System.out.println("Type Y to Approve, N to Reject: ");
            String input = ScannerHelper.getScanner().next().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
        }
    }

    public int getNewLeaveBalance() {
        System.out.println("Enter the new leave balance: ");
        while (true) {
            try {
                int newBalance = ScannerHelper.getScanner().nextInt();
                ScannerHelper.getScanner().nextLine();
                if (newBalance > 10) {
                    continue;
                }
                return newBalance;
            } catch (InputMismatchException e) {
                System.out.println("Only Integer value is allowed and it should be less than 10.");
                ScannerHelper.getScanner().nextLine();
            }
        }
    }

    public String getEmail() {
        System.out.println("Enter email: ");
        return ScannerHelper.getScanner().nextLine();
    }

    public boolean showLeaveRequests(List<LeaveRequest> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            System.out.println("No leave requests found.");
            return false;
        }
        System.out.printf("%-5s %-15s %-12s %-12s %-10s %-20s%n", "ID", "Employee", "Start Date", "End Date", "Status", "Reason");
        System.out.println("-------------------------------------------------------------------------------");
        for (LeaveRequest request : requestList) {
            System.out.printf("%-5d %-15s %-12s %-12s %-10s %-20s%n",
                    request.getId(),
                    request.getUserEmail(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getStatus(),
                    request.getReason());
        }
        return true;
    }
}
