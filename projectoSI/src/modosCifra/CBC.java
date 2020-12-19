/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modosCifra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class CBC {

    private static String algoritmo = "AES";

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException, Exception {

        Scanner s = new Scanner(System.in);

        //2.1.1
        //Creating a KeyGenerator object        
        String texto = JOptionPane.showInputDialog(null, "Nome do ficheiro com o texto:");
        String textoEncriptado = JOptionPane.showInputDialog(null, "Nome do ficheiro para o texto encriptado:");
        String textoDeseincriptado = JOptionPane.showInputDialog(null, "Nome do ficheiro para o texto desincriptado:");
        String chave = JOptionPane.showInputDialog(null, "Nome do ficheiro para a chave:");

        generateKey(chave, algoritmo);

        String algcript = JOptionPane.showInputDialog(null, "Insira o algcript:");

        Random rand = new SecureRandom();
        byte[] bytes = new byte[16];
        rand.nextBytes(bytes);

        System.out.println("Bytes desencrypt:" + bytes);

        //MODO CBC
        algcript = "AES/CBC/PKCS5PADDING";
        System.out.println("Algcript:" + algcript);

        encryptString(bytes, texto, textoEncriptado, chave, algcript, algoritmo);
        desencryptDES(bytes, textoEncriptado, textoDeseincriptado, chave, algcript, algoritmo);

    }

    public static void generateKey(String chave, String alg) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            //Creating a KeyGenerator object with Des
            KeyGenerator keyGen;

            keyGen = KeyGenerator.getInstance(alg);
            //Creating a Secret key using Key Generator 
            SecretKey key = keyGen.generateKey();

            //Get key to bytes
            byte[] keybytes = key.getEncoded();
            //Save key to file
            try (FileOutputStream fos = new FileOutputStream(chave);) {
                fos.write(keybytes);
            } catch (Exception ex) {
            }

        } catch (NoSuchAlgorithmException ex) {
        }
    }

    public static void encryptString(byte[] bytes, String toEncrypt, String encrypted, String chave, String algcript, String alg) throws Exception {
        try {

            //ir buscar a chave do ficheiro
            Path pathKeyFile = Paths.get(chave);
            byte[] keybytes = Files.readAllBytes(pathKeyFile);

            //passar para bytes
            SecretKey key = new SecretKeySpec(keybytes,/* 0, keybytes.length,*/ alg);

            //Create cifra instancia DES
            Cipher encryptCipher = Cipher.getInstance(algcript);

            //Init cifra com a chave que veio do ficheiro
            System.out.println("Bytes encrypt:" + bytes);

            //CBC
            IvParameterSpec iv = new IvParameterSpec(bytes);
            try (FileOutputStream fos = new FileOutputStream(encrypted);) {
                fos.write(iv.getIV());
            }
            System.out.println("iv do encriptado: " + iv.toString());

            encryptCipher.init(Cipher.ENCRYPT_MODE, key, iv);

            //buscar ficheiro a encriptar e passar para bytes
            FileInputStream fis = new FileInputStream(toEncrypt);
            byte[] inputfileBytes = new byte[(int) fis.available()];
            fis.read(inputfileBytes);

            //encriptar o ficheiro e guardar os bytes
            byte[] outputBytes = encryptCipher.doFinal(inputfileBytes);

            //Save encriptacao num file
            try (FileOutputStream fos = new FileOutputStream(encrypted);) {
                fos.write(outputBytes);

            }

        } catch (Exception ex) {
            System.out.println("ERRO AO ENCRIPTAR:" + ex);
        }

    }

    public static void desencryptDES(byte[] bytes, String toDesencrypt, String desencrypted, String chave, String algcript, String alg) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        try {

            //ir buscar a chave do ficheiro
            Path pathKeyFile = Paths.get(chave);
            byte[] keybytes = Files.readAllBytes(pathKeyFile);

            SecretKey key = new SecretKeySpec(keybytes/*, 0, keybytes.length*/, alg);
            System.out.println("key desincriptar:" + key);

            //Create cifra instancia DES
            Cipher decryptCipher = Cipher.getInstance(algcript);

            //CBC
            //Lê o ficheiro com o vetor encriptado
            FileInputStream f = new FileInputStream(toDesencrypt);
            byte[] inputfile = new byte[(int) f.available()];
            f.read(inputfile);

            IvParameterSpec iv = new IvParameterSpec(bytes);
            System.out.println("iv do desencriptado: " + iv.toString());

            //Init cifra com a chave que veio do ficheiro   
            decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);

            //buscar ficheiro a desencriptar e passar para bytes
            FileInputStream fis = new FileInputStream(toDesencrypt);
            byte[] inputfileBytes = new byte[(int) new File(toDesencrypt).length()];
            fis.read(inputfileBytes);

            //desencriptar o ficheiro e guardar os bytes
            byte[] outputBytes = decryptCipher.update(inputfileBytes);
            //byte[] outputBytes = decryptCipher.doFinal(inputfileBytes);

            //Save desencriptacao num file
            try (FileOutputStream fos = new FileOutputStream(desencrypted);) {
                fos.write(outputBytes);
            }

        } catch (Exception ex) {
            System.out.println("ERRO AO DESINCRIPTAR:" + ex);
        }
    }
}
