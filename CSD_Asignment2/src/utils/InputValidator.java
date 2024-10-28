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

    public static String getMacAddress(ArrayList<String> macAddressList) {
        Scanner sc = new Scanner(System.in);

        int choice = getIntegerInput("Manual or random? (1 for"
                + " manual, 2 for random)",
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

    public static String getIpAddress() {
        Scanner sc = new Scanner(System.in);
        // Loop until input valid 
        while (true) {
            System.out.print("Enter IP Address: ");
            String input = sc.nextLine();
            String ipPattern = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|[1-9]?[0-9])$";
            if (!input.matches(ipPattern)) {
                System.out.println("Invalid IP address.");
            } else {
                return input;
            }
        }
    }

    public static String getSubnetMask() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter Subnet mask: ");
            String input = sc.nextLine();
            if (isValidOcteOfSubnetMask(input)) {
                return input;
            } else {
                System.out.println("Invalid Subnet mask.");
            }
        }
    }

    public static boolean isValidOcteOfSubnetMask(String subnetMask) {
        String[] octetsParts = subnetMask.split("\\.");
        // If length is not 4 then return false
        if (octetsParts.length != 4) {
            return false;
        }

        int[] validValues = {0, 128, 192, 224, 240, 248, 252, 254, 255};

        // Loop through each octet and check if, return false if one of them invalid
        for (String octet : octetsParts) {

            try {
                int value = Integer.parseInt(octet);
                boolean isValidOctet = false;

                // Check if octet value is valid
                for (int valid : validValues) {
                    // If found a valid value, break loop
                    if (value == valid) {
                        isValidOctet = true;
                        break;
                    }
                }

                // If octet value is not valid then return false
                if (!isValidOctet) {
                    return false;
                }
            } catch (Exception e) { // Case can't parse Octet into number, return false
                return false;
            }
        }
        return true;
    }
}
