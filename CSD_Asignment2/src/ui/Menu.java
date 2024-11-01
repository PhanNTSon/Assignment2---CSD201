/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author Phan Sơn
 */
public class Menu {

    public static int displayMainMen() {
        System.out.println("----------Network Simulator Program---------");
        System.out.println("1. Routers Management");
        System.out.println("2. End Devices Management");
        System.out.println("3. Exit");
        int totalOption = 3;
        return totalOption;
    }

    public static int displayRouterManagementMenu() {
        System.out.println("----------Router Management---------");
        System.out.println("1. Add router");
        System.out.println("2. Remove router");
        System.out.println("3. Connect router");
        System.out.println("4. Display all Routers");
        System.out.println("5. Exit");
        int total = 5;
        return total;
    }

    public static int displayLaptopManagementMenu() {
        System.out.println("----------Laptop Management---------");
        System.out.println("1. Add laptop");
        System.out.println("2. Remove laptop");
        System.out.println("3. Login laptop");
        System.out.println("4. Exit");
        int total = 4;
        return total;
    }
    
    public static int displayLaptopInterfaceMenu(){
        System.out.println("----------Laptop Interface---------");
        System.out.println("1. Connect laptop to Router");
        System.out.println("2. Sending email to Laptop");
        System.out.println("3. Ping Router");
        
    }
}
