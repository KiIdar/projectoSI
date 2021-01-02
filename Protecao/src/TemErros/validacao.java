/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemErros;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class validacao {
     private Provider ccProvider;
    private KeyStore ks;

    public validacao() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

        this.ccProvider = Security.getProvider("SunPKCS11-CartaoCidadao");
        this.ks = KeyStore.getInstance("PKCS11", ccProvider);
        this.ks.load(null, null);

    }

    public byte[] getSignatureOfData(byte[] bytesToSign) throws KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        PrivateKey pk = (PrivateKey) ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
        Signature sig = Signature.getInstance("SHA256withRSA", this.ccProvider);

        sig.initSign(pk); //chave privada
        sig.update(bytesToSign); //texto a assinar
        return sig.sign(); // assinar (é a assinatura)
    }

    public Certificate getPublicCertificate() throws KeyStoreException {
        return this.ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
    }

    public PublicKey getPublicKey(Certificate cer) {
        return cer.getPublicKey();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        validacao uc = new validacao();

        System.out.println("Ficheiro a ser gerado...");
        //Cria o ficheiro com este texto

        boolean existeTexto = (new File("texto")).exists();
        boolean existeHash = (new File("hash")).exists();

        boolean certificadoChavePublica = (new File("certificadoChavePublica.cer")).exists();

        if (existeTexto) {
            byte[] hash = readFromFile("hash");
            if (existeHash) {
                System.out.println("Existe hash!");
                //cria o ficheiro assinatura e cifra o texto com a chave privada
                writeToFile("AssinaturaFicheiro", uc.getSignatureOfData(hash));
                //certificado
                Certificate cer = uc.getPublicCertificate();

                //writeToFile("certificadoChavePublica.cer", cer.getEncoded());

                //Pega na chave publica
                PublicKey pk = uc.getPublicKey(cer);
                writeToFile("chavePublica", pk.getEncoded());

                System.out.println("Gerado com sucesso");

                System.out.println("Ficheiros a serem validados...");

                //Forma 1
                //converter certificado formatado em Base64 em certificado X509
                CertificateFactory certificateFactory;
                X509Certificate certificado = null;
                try {
                    certificateFactory = CertificateFactory.getInstance("X.509");
                    byte[] teste = readFromFile("certificadoChavePublica.cer");
                    certificado = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(teste));
                    ByteArrayInputStream s = new ByteArrayInputStream(teste);

                    Scanner scanner = new Scanner(s);
                    scanner.useDelimiter("\\Z");//To read all scanner content in one String
                    String data = "";
                    if (scanner.hasNext()) {
                        data = scanner.next();
                    }
                    writeToFile("data", data.getBytes());
                    System.out.println(data);

                } catch (CertificateException e) {
                    e.printStackTrace();
                }

                //X509Certificate certificado = (X509Certificate) certificate;
                //Forma 2
                //certificate
                //CertificateFactory fact = CertificateFactory.getInstance("X.509");
                //FileInputStream is = new FileInputStream("certificadoChavePublica.cer");
                //X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);
                verificarCertificado(certificado);

                if (verificarCertificado(certificado) == false) {
                    System.out.println("Certificado inválido!");

                } else {
                    System.out.println("Ceritifcado válido!");
                    boolean[] keyUsage = certificado.getKeyUsage();
                    if (keyUsage[5]) {
                        // CA certificate
                        System.out.println("CA");
                    } else {
                        // User certificate
                        System.out.println("USER");
                    }

                    //signature
                    byte[] mysignatureread = readFromFile("AssinaturaFicheiro");

                    if (mysignatureread.length != 384) {
                        JOptionPane.showMessageDialog(null, "Erro na assinatura.\nO comprimento da assinatura está errado!");
                    } else {
                        Signature mysignature = Signature.getInstance("SHA256withRSA");
                        mysignature.initVerify(certificado);
                        //data
                        byte[] data = readFromFile("hash");
                        mysignature.update(data);

                        boolean verifies = mysignature.verify(mysignatureread);

                        if (verifies == false) {
                            JOptionPane.showMessageDialog(null, "Assinatura errada.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Assinatura correta.");
                        }

                        System.out.println("signature verifies: " + verifies);
                        System.out.println("data : " + bytesToString(data));
                    }
                }

            } else {
                //hash
                String thash = generateHash("texto", "MD5");
                writeToFile("hash", thash.getBytes());
                System.out.println("Hash: " + bytesToString(thash.getBytes()));
                System.out.println("Ficheiro hash criado com sucesso!");
            }

        } else {//Se não existe, cria o ficheiro!
            //cria ficheiro texto
            String texto = "TESTE RAFAEL FONSECA";
            writeToFile("texto", texto.getBytes());
            System.out.println("Ficheiro criado!");

        }

    }

    public static void writeToFile(String nomeFicheiro, byte[] conteudo) {
        try {
            FileOutputStream fos = new FileOutputStream(nomeFicheiro);
            fos.write(conteudo);
            fos.close();
        } catch (Exception e) {
            System.out.println("erro a escrever ficheiro: " + e);
        }

    }

    public static byte[] readFromFile(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] fileBytes = new byte[fis.available()];
            fis.read(fileBytes);
            return fileBytes;
        } catch (Exception e) {
            System.out.println("erro a ler ficheiro: " + e);
        }
        return null;

    }

    public static boolean verificarCertificado(X509Certificate cer) {
        boolean verifica = false;
        try {
            //cer.checkValidity(new Date(1990, 1, 1)); //data que permite fazer com que o certificado fique inválido
            cer.checkValidity();
            verifica = true;
            //System.out.println("certificado valido");
        } catch (Exception e) {
            verifica = false;
            //System.out.println("certificado inválido");
        }
        return verifica;
    }

    public static String bytesToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    //Hash
    public static String BytesToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static String generateHash(String filename, String algorithm) {
        // Examples : MD5, SHA-1, SHA-256
        try {
            byte[] fileToHash = readFromFile(filename);
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashed = digest.digest(fileToHash);
            return BytesToHexString(hashed);
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
