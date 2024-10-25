/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.DateTimeException;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class InputValidator {

    public static int getIntegerInput(String mess, int MIN_BOUND, int MAX_BOUND) {
        Scanner sc = new Scanner(System.in);
        int input;
        while (true) {
            try {
                System.out.print(mess);
                input = Integer.parseInt(sc.nextLine());
                if (input < MIN_BOUND || input > MAX_BOUND) {
                    throw new IndexOutOfBoundsException("ERROR. ACCEPT IN "
                            + "RANGE [" + MIN_BOUND + " - " + MAX_BOUND + "]");
                }
                return input;
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int getIntegerInput(String mess) {
        return getIntegerInput(mess,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
    }

    /**
     * This method get and check if input is valid as double number.
     *
     * @param mess
     * @param MIN_BOUND
     * @param MAX_BOUND
     * @return
     */
    public static double getDoubleInput(String mess, double MIN_BOUND, double MAX_BOUND) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(mess);
                String input = sc.nextLine();
                double output = Double.parseDouble(input);
                if (output < MIN_BOUND || output > MAX_BOUND) {
                    throw new IndexOutOfBoundsException("ERROR. ACCEPT IN "
                            + "RANGE [" + MIN_BOUND + " - " + MAX_BOUND + "]");
                }
                return output;
            } catch (NumberFormatException e) {
                System.out.println("WRONG FORMAT. ONLY ENTER NUMBERS.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Similar as above.
     *
     * @param mess
     */
    public static double getDoubleInput(String mess) {
        return getDoubleInput(mess,
                -Double.MAX_VALUE,
                Double.MAX_VALUE);
    }

    public static boolean getContinueOption(String mess) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(mess);
            String input = sc.nextLine();
            if (input.matches("[yY]")) {
                return true;
            } else if (input.matches("[nN]")) {
                return false;
            } else {
                System.out.println("Error. Invalid option.");
            }
        }
    }

    public static String getNormalString(String mess, int max_length) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(mess);
            String input = sc.nextLine();
            if (!input.matches("[a-zA-Z0=9\\s]+")) {
                System.out.println("Error. No special characters contains.");
            } else if (input.length() > max_length) {
                System.out.println("Error. Maximum " + max_length + " characters.");
            } else {
                return input;
            }
        }
    }
}
