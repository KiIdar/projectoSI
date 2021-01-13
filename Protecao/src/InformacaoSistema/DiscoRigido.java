/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class DiscoRigido {

    public String getSerialDisk() {

        String line = "";

        try {
            Process p = Runtime.getRuntime().exec("cmd /c vol C:");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = reader.readLine()) != null) {
                if (line.contains("Serial Number")) {
                    line = line.replace("Volume Serial Number is ", "");
                    line = line.trim();
                    return line;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(DiscoRigido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscoRigido.class.getName()).log(Level.SEVERE, null, ex);
        }

        return line;
    }

    private List<String> getSystemIdList() {

        List<String> systemId = new ArrayList<String>();

        try {
            Process p = Runtime.getRuntime().exec("cmd /c wmic diskdrive get serialNumber");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 1 && !line.contains("SerialNumber")) {
                    line = line.trim();
                    systemId.add(line);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(DiscoRigido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DiscoRigido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return systemId;
    }

    public static void main(String[] args) {
        DiscoRigido d = new DiscoRigido();
        System.out.println("Serial:"+d.getSerialDisk());
        System.out.println("te"+d.getSystemIdList());
    }
}
