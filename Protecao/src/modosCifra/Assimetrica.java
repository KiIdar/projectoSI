/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modosCifra;

import Ficheiros.Ficheiros;
import KeyStorage.KeyStorage;
import java.io.InputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Utilizador
 */
public class Assimetrica {

    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    public KeyPair getKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Ficheiros ficheiro = new Ficheiros();
        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(ficheiro.lerFicheiro("chave privada.txt")));
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("chave publica.txt")));
        KeyPair newKeyPair = new KeyPair(publicKey, privateKey);

        return newKeyPair;
    }

    public PublicKey getPublicKeyGestor() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair k = KeyStorage.getKeys("..\\Protecao\\keystore.jks", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        System.out.println("Publica:" + k.getPublic().getEncoded());
        PublicKey publicKey = k.getPublic();
        return publicKey;
    }

    public KeyPair getKeyPairFromKeyStore() throws Exception {
        //Generated with:
        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks

        InputStream ins = Assimetrica.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword
                = //Key password
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    /*public String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        //byte[] cipherText = encryptCipher.doFinal(plainText);
        System.out.println("cyphetExte: " + cipherText);

        return Base64.getEncoder().encodeToString(cipherText);
        //return cipherText;
    }

    public String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }*/

    public static byte[] encrypt(byte[] byteText, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException {
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherBytes = decriptCipher.doFinal(byteText);

        return cipherBytes;
    }

    public static byte[] decrypt(byte[] byteText, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher decriptCipher = Cipher.getInstance("RSA");
        try {
            decriptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] plainText = null;
        try {
            plainText = decriptCipher.doFinal(byteText);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plainText;
    }
    
    public static byte[] decryptPub(byte[] byteText, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher decriptCipher = Cipher.getInstance("RSA");
        try {
            decriptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] plainText = null;
        try {
            plainText = decriptCipher.doFinal(byteText);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Assimetrica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plainText;
    }

    public PublicKey getPublicKey() {
        KeyPair k = KeyStorage.getKeys("nomedoficheiro.jks", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        System.out.println("Publica:" + k.getPublic().getEncoded());
        PublicKey publicKey = k.getPublic();
        return publicKey;
    }

}
