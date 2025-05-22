package utils;

import java.util.Scanner;

public class ScannerHelper {
    private static final Scanner scanner = new Scanner(System.in);

    private ScannerHelper() {
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
