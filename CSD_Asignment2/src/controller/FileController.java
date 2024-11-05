/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Phan Sơn
 */
public class FileController {

    public ArrayList<String> readInFile(String filePath) {
        ArrayList<String> listIn = new ArrayList<>();
        File file = new File(filePath);
        try {
            // If file not exist
            if (file.createNewFile()) {
                return null;
            }

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                listIn.add(fileReader.nextLine());
            }

        } catch (Exception e) {
        }
        return listIn;
    }

    public void writeToFile(ArrayList<String> list, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String s : list) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {

        }
    }
}
