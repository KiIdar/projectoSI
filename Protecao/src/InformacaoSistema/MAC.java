/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 *
 * @author Utilizador
 */
public class MAC {
    InetAddress ip;
    byte[] mac;

    public String getMAC() {
        try {

            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public InetAddress getIp() {
        System.out.println("Ip:"+ip);
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public byte[] getMac() {
        System.out.println("MAC:"+mac);
        return mac;
    }

    public void setMac(byte[] mac) {
        this.mac = mac;
    }
}
