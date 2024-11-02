/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Phan Sơn
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
     * @return
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
            System.out.print(mess);
            String input = sc.nextLine();
            if (!input.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("Error. No special characters contains.");
            } else if (input.length() > max_length) {
                System.out.println("Error. Maximum " + max_length + " characters.");
            } else {
                return input;
            }
        }
    }

    public static String getRouterName(String mess) {
        return getNormalString(mess, 50);
    }

    public static String getLaptopName(String mess) {
        return getNormalString(mess, 50);
    }

    public static String getMacAddress(ArrayList<String> macAddressList) {
        Scanner sc = new Scanner(System.in);

        int choice = getIntegerInput("MAC Address: Manual or random? (1 for"
                + " manual, 2 for random): ",
                1, 2);
        // If user want to manually enter
        if (choice == 1) {
            // Loop until input valid 
            while (true) {

                System.out.print("Enter MAC Address: ");
                String input = sc.nextLine();
                String macPattern = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
                if (!input.matches(macPattern)) {
                    System.out.println("Invalid MAC address.");
                } else if (macAddressList.contains(input)) {
                    System.out.println("Already exist MAC Address");
                } else {
                    return input;
                }
            }
        } else {    // User want to random
            String randomMAC = "";
            // Loop while random is already exist list
            do {
                randomMAC = RandomGenerator.generateMacAddress();
            } while (macAddressList.contains(randomMAC));
            return randomMAC;
        }

    }

    public static String getIpAddress(ArrayList<String> publicIPList, String subnet) {

        Scanner sc = new Scanner(System.in);

        int choice = getIntegerInput("IP Address: Manual or random? (1 for"
                + " manual, 2 for random): ",
                1, 2);
        // If user want to manually enter
        if (choice == 1) {
            // Loop until input valid 
            while (true) {

                System.out.print("Enter IP Address: ");
                String input = sc.nextLine();
                String ipPattern = "^" + subnet + "\\.(\\d{1,3})$";  // Chỉ chấp nhận các địa chỉ trong subnet
                if (!input.matches(ipPattern)) {
                    System.out.println("Invalid IP address or out of subnet range.");
                } else if (publicIPList.contains(input)) {
                    System.out.println("IP Address already exists.");
                } else {
                    return input;
                }
            }
        } else {    // User want to random
            String randomIP = "";
            // Loop while random is already exist list
            do {
                randomIP = RandomGenerator.generateRandomIpInSubnet(subnet);
            } while (publicIPList.contains(randomIP));
            return randomIP;
        }

    }

}
