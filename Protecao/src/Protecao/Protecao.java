package Protecao;

import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import InformacaoSistema.MotherBoard;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Utilizador
 */
public class Protecao {

    public void test() {
        System.out.println("it worked!");
    }

    public void getPCInfo() throws IOException, InterruptedException {
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
        System.out.println("DISK");
        System.out.println(cpu.DiskInfo());
        System.out.println("----------------- FEITO -----------------------");
        System.out.println("MOTHERBOARD");
        MotherBoard motherboard = new MotherBoard();
        motherboard.serial();
        System.out.println("----------------------------------------");
    }
}
