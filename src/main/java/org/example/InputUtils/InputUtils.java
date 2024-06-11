package org.example.InputUtils;

import java.util.Scanner;

public class InputUtils {
    private final static Scanner scanner = new Scanner(System.in);
    public static int readInt() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input, try again");
                scanner.nextLine();
            }
        }
    }
}

