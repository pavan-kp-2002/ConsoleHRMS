package utils;

import java.time.LocalDate;

public class InputValidator {
    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 8;
    public static final String EMAIL_ENDS_WITH = "@itt.com";

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
        return password != null && (password.length() >= MIN_LENGTH && password.length() <= MAX_LENGTH);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.endsWith(EMAIL_ENDS_WITH);
    }

}
