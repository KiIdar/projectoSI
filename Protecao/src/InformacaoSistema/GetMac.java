/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author ASUS
 */
public class GetMac {

    public InetAddress valorIp;
    public String nomeSistema, enderecoIp = "", enderecoMac = "";

    public GetMac() {
        nomeSistema = System.getProperty("os.name");
    }

    public String getIp() {

        try {
            valorIp = InetAddress.getLocalHost();
            enderecoIp = valorIp.getHostAddress();
        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return enderecoIp;
    }

    public String getMac() {
        int i = 1;

        try {
            valorIp = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(valorIp);
            byte[] valorMac = network.getHardwareAddress();
            for (i = 0; i < valorMac.length; i++) {
                enderecoMac += (String.format("%02X%s", valorMac[i], (i < valorMac.length - 1) ? "-" : ""));
            }
        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return enderecoMac;
    }

    public static void main(String[] args) {
        GetMac mac = new GetMac();
        System.out.println("MAC: " + mac.getMac());
    }
}
