package Protecao;

import Ficheiros.Ficheiros;
import InfoPessoa.ValidarPerguntas;
import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import InformacaoSistema.MotherBoard;
import Licenca.Licenca;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import modosCifra.Assimetrica;
import modosCifra.CBC;

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

    public void testarAssimetrica() {
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

    public void validarPerguntas() {
        ValidarPerguntas validador = new ValidarPerguntas();

        String email = "";
        String nome = "";
        String numTelemovel = "";
        String cc = "";

        validador.isValidNome(nome);
        validador.isValidNumTelemovel(numTelemovel);
        validador.isValidCC(cc);
        validador.isValidEmail(email);
    }

    public void criarLicenca() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        Licenca licenca = new Licenca(null, null, null, null, null, null, null, null, null, "hi", "21423423423", null);

        CBC cbc = new CBC();
        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();

        cbc.encrypt(licenca, chave, iv);

        System.out.println(cbc.decrypt(chave, iv).getNome() + " . " + cbc.decrypt(chave, iv).getNumTelemovel());
    }

    public void instanciarLicenca() throws Exception {
        //byte[] chavePublica, String nomeProjecto, String email, String nome, String numTelemovel, String cc

        CPU cpu = new CPU();
        MAC mac = new MAC();
        HostName hostname = new HostName();
        MotherBoard motherboard = new MotherBoard();
        
        mac.getIp();
        mac.getMac();
        hostname.getHost();
        motherboard.serial();
        cpu.Info();
        

        // string to byte[]
        byte[] bytes = mac.getMAC().getBytes();
        // convert byte[] to string
        String getMac = new String(bytes, StandardCharsets.UTF_8);
        
        

        Licenca licenca = new Licenca(mac.getIp(),getMac, hostname.getHost(), motherboard.serial(),
                cpu.OSname(), cpu.OSversion(), null, null, null, "hi", "21423423423", null);
        
        System.out.println(licenca);
    }
    

}
