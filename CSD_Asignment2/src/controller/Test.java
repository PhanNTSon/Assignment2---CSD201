/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Phan Sơn
 */
public class Test {

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date n = dateFormat.parse("12-5-2024");
            System.out.println(n.toString());
        } catch (Exception e) {
        }
    }
}
