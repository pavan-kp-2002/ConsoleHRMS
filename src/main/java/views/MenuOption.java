package views;

public class MenuOption {
    public void displayMenu() {
        System.out.println("Welcome to HRMS Console App!");
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public void showAdminMenu() {
        System.out.println("Admin Page:");
        System.out.println("1. Pending Leave Requests");
        System.out.println("2. Approved Requests");
        System.out.println("3. Rejected Requests");
        System.out.println("4. Exit to Main Menu");
        System.out.print("Enter your choice: ");
    }

    public void showEmployeeMenu() {
        System.out.println("Employee Page:");
        System.out.println("1. Apply for Leave");
        System.out.println("2. View Applied Leaves");
        System.out.println("3. View Leave Balance");
        System.out.println("4. Exit to Main Menu");
        // Add employee actions here
    }
}
