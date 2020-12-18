/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.net.InetAddress;

/**
 *
 * @author Utilizador
 */
public class HostName {
    String hostname = "Teste";

    public String getHost() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            System.out.println("HostName: "+hostname);
        } catch (Exception ex) {
            System.out.println("Erro:"+ex);
        }
        return hostname;
    }
}
