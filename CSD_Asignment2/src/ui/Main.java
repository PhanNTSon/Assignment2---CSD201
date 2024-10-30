/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import controller.ProgramController;
import utils.InputValidator;

/**
 *
 * @author Phan Sơn
 */
public class Main {
    public static void main(String[] args) {
        ProgramController programController = new ProgramController();
        // Loop until user exit
        while (true) {
            int max_bound = Menu.displayMainMen();
            int choice = InputValidator.getIntegerInput("Enter choice: ", 1, max_bound);
            switch (choice) {
                case 1:
                    programController.manageRouter();
                    break;
                case 2:
                    programController.manageEndDevices();
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }
}
