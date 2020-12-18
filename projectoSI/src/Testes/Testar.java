/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import InformacaoSistema.Motherboard;
import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public class Testar {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("HOSTNAME");
        HostName hostName = new HostName();
        hostName.getHost();
        System.out.println("--------------- FEITO -------------------------");
        System.out.println("MAC");
        MAC mac = new MAC();
        mac.getMAC();
        System.out.println("---------------- NÃO FEITO ------------------------");
        System.out.println("CPU");
        CPU cpu = new CPU();
        cpu.Info();
        System.out.println("----------------- FEITO -----------------------");
        System.out.println("MOTHERBOARD");
        Motherboard motherboard = new Motherboard();
        motherboard.serial();
        System.out.println("----------------------------------------");

    }

}
