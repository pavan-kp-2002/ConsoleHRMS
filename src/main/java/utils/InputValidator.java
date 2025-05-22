package utils;

import java.time.LocalDate;

public class InputValidator {
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEndDateAfterStart(String start, String end) {
        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            return !endDate.isBefore(startDate);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPassword(String password) {
        return password != null && (password.length() >= 4 && password.length() <= 8);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.endsWith("@itt.com");
    }

}
