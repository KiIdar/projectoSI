package Protecao;

import Ficheiros.Ficheiros;
import InfoPessoa.ValidarPerguntas;
import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import InformacaoSistema.MotherBoard;
import Licenca.Licenca;
import java.io.IOException;
import java.security.KeyPair;
import java.util.logging.Level;
import java.util.logging.Logger;
import modosCifra.Assimetrica;

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
    
    public void testarAssimetrica()
    {
        Assimetrica assimetrica = new Assimetrica();
        
          try {
            //First generate a public/private key pair
            KeyPair pair = assimetrica.generateKeyPair();
            //KeyPair pair = getKeyPairFromKeyStore();

            //Our secret message
            String message1 = "rafael";
            System.out.println("Mensagem secreta 1: " + message1);

            String message2 = "fonseca";
            System.out.println("Mensagem secreta 2: " + message2);

            //Encrypt the message
            String cipherText = assimetrica.encrypt(message1, pair.getPublic());
            String cipherText2 = assimetrica.encrypt(message2, pair.getPublic());

            System.out.println("Encripta a mensagem 1: " + cipherText);
            System.out.println("Encripta a mensagem 2: " + cipherText2);

            //Now decrypt it
            String decipheredMessage = assimetrica.decrypt(cipherText, pair.getPrivate());
            String decipheredMessage2 = assimetrica.decrypt(cipherText2, pair.getPrivate());

            System.out.println("Desencripta com a chave privada 1: " + decipheredMessage);
            System.out.println("Desencripta com a chave privada 2: " + decipheredMessage2);
        } catch (Exception ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void getPCInfo() throws IOException, InterruptedException {
        System.out.println("HOSTNAME");
        HostName hostName = new HostName();
        hostName.getHost();
        System.out.println("--------------- FEITO -------------------------");
        System.out.println("MAC");
        MAC mac = new MAC();
        mac.getMAC();
        System.out.println("---------------- FEITO ------------------------");
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
    
    public void validarPerguntas()
    {
        ValidarPerguntas validador = new ValidarPerguntas();
            
            String email="";
            String nome="";
            String numTelemovel="";
            String cc = "";
            
            validador.isValidNome(nome);
            validador.isValidNumTelemovel(numTelemovel);
            validador.isValidCC(cc);
            validador.isValidEmail(email);
    }
    public void crearLicenca() throws IOException
    {
      Licenca licenca = new Licenca(null, null, null, null,null,null,null, null, null, "hi", "21423423423", null);
      Ficheiros ficheiroControl = new Ficheiros();
      ficheiroControl.escreverLicenca(licenca);
      ficheiroControl.lerLicenca();
    }
    
    public void instanciarLicenca(){
        //String ipAddress, String macAddress,String hostName, String serialMB, String nameCPU, String versaoCPU, byte[] chavePublica, String nomeProjecto, String email, String nome, String numTelemovel, String cc
        
        MAC mac = new MAC();
        
        
        Licenca licenca = new Licenca(null, null, null, null,null,null,null, null, null, "hi", "21423423423", null);
    }
}
