/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Random;

/**
 *
 * @author Phan Sơn
 */
public class RandomGenerator {

    public static String generateMacAddress() {
        Random rand = new Random();
        StringBuilder macAddress = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int octet = rand.nextInt(256); // Tạo một số từ 0 đến 255
            macAddress.append(String.format("%02X", octet)); // Chuyển số thành dạng hex và thêm vào chuỗi

            if (i < 5) {
                macAddress.append(":"); // Thêm dấu ":" giữa các cặp số
            }
        }

        return macAddress.toString();
    }
    
    public static String generateRandomIpInSubnet(String subnet) {
        Random random = new Random();
        int hostPart = random.nextInt(254) + 1;  // Sinh số trong khoảng [1, 254]
        return subnet + "." + hostPart;
    }
    
}
