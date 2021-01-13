/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.crypto.Mac;

/**
 *
 * @author Utilizador
 */
public class GetIp {
    
    public String getIp() {
        try {
            java.net.InetAddress i = java.net.InetAddress.getLocalHost();
            String ip = i.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "NO-IP";
        }
    }

    public static void main(String[] args) throws SocketException {
        GetIp mac = new GetIp();
        System.out.println("Ip: "+mac.getIp());
        System.out.println("Mac: ");
    }

}
