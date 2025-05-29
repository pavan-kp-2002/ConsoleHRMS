package views;

import utils.InputValidator;
import utils.ScannerHelper;


public class EmployeePageView {
    private final MenuOption menuOption = new MenuOption();

    public String getUserChoice() {
        System.out.println("Enter Choice: ");
        return ScannerHelper.getScanner().nextLine();
    }

    public String getStartDate() {
        String date;
        do {
            System.out.println("Enter start date (yyyy-mm-dd): ");
            date = ScannerHelper.getScanner().nextLine();
        } while (!InputValidator.isValidDate(date));
        return date;

    }

    public String getEndDate(String startDate) {
        String endDate;
        do {
            System.out.println("Enter end date (yyyy-mm-dd): ");
            endDate = ScannerHelper.getScanner().nextLine();
        } while (!InputValidator.isValidDate(endDate) || !InputValidator.isEndDateAfterStart(startDate, endDate));
        return endDate;
    }

    public String getReason() {
        System.out.println("Enter reason: ");
        return ScannerHelper.getScanner().nextLine();
    }
}
