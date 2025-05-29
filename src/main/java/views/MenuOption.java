package views;

public class MenuOption {
    public void showMainMenu() {
        System.out.println("Welcome to HRMS Console App!");
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    public void showAdminMenu() {
        System.out.println("Admin Page:");
        System.out.println("1. Approve/Reject Leave Requests");
        System.out.println("2. Update Leave Balance");
        System.out.println("3. Approved Requests list");
        System.out.println("4. Rejected Requests list");
        System.out.println("5. Exit to Main Menu");
    }

    public void showEmployeeMenu() {
        System.out.println("Employee Page:");
        System.out.println("1. Apply for Leave");
        System.out.println("2. View Applied Leaves");
        System.out.println("3. View Leave Balance");
        System.out.println("4. Exit to Main Menu");
    }
}
