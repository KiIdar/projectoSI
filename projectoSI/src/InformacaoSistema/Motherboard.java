/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Motherboard {

    public String serial() throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("wmic", "baseboard", "get", "serialnumber");
        Process process = pb.start();
        process.waitFor();
        String serialNumber = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                process.getInputStream()))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.length() < 1 || line.startsWith("SerialNumber")) {
                    continue;
                }
                serialNumber = line;
                break;
            }
        }
        System.out.println("Serial:"+serialNumber);
        return serialNumber;
    }

}
