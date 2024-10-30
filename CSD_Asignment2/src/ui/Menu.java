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
    public static int displayMainMen(){
        System.out.println("----------Network Simulator Program---------");
        System.out.println("1. Routers Management");
        System.out.println("2. End Devices Management");
        System.out.println("3. Exit");
        int totalOption = 3;
        return totalOption;
    }
    
    public static int displayRouterManagementMenu(){
        System.out.println("----------Router Management---------");
        System.out.println("1. Add router");
        System.out.println("2. Remove router");
        System.out.println("3. Connect router");
        System.out.println("4. Exit");
        int total = 4;
        return total;
    }
}
