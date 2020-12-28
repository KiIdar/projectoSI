package Protecao;

import InfoPessoa.ValidarPerguntas;
import InformacaoSistema.DiscoRigido;
import InformacaoSistema.cpu;
import InformacaoSistema.hostName;
import InformacaoSistema.getIp;
import InformacaoSistema.getMac;
import InformacaoSistema.motherBoard;
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

        hostName hostName = new hostName();
        System.out.println("HOSTNAME:" + hostName.getHost());
        System.out.println("--------------- FEITO -------------------------");
        getIp ip = new getIp();
        System.out.println("IP: " + ip.getIp());
        getMac mac = new getMac();
        System.out.println("MAC: " + mac.getMac());
        System.out.println("---------------- FEITO ------------------------");
        cpu cpu = new cpu();
        System.out.println("CPU: " + cpu.getCPUSerial());
        System.out.println("----------------- NÃO SEI SE ESTÁ FEITO -----------------------");
        DiscoRigido dr = new DiscoRigido();
        System.out.println("DISK: " + dr.getHardDiskSerialNumber("C"));
        System.out.println("----------------- FEITO -----------------------");
        motherBoard mb = new motherBoard();
        System.out.println("MOTHERBOARD: " + mb.getMotherboardSN());
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

        hostName hn = new hostName();
        getMac mac = new getMac();
        getIp ip = new getIp();
        cpu cpu = new cpu();
        DiscoRigido dr = new DiscoRigido();
        motherBoard mb = new motherBoard();

        Licenca licenca = new Licenca(ip.getIp(), mac.getMac(), hn.getHost(), mb.getMotherboardSN(),
                cpu.getCPUSerial(), dr.getHardDiskSerialNumber("C"), null, null, null, "hi", "21423423423", null);

        System.out.println("Licenca: "+licenca);

    }

    //Teste
    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        Protecao p = new Protecao();
        //p.getPCInfo();
        p.instanciarLicenca();
    }

}
