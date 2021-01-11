/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
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
import javax.swing.JOptionPane;

/**
 *
 * @author rFael
 */
public class teste {
   
    public byte[] getSignatureOfData(byte[] bytesToSign) throws KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        KeyPair k = KeyStorage.getKeys("certificado.cer", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        Signature sig = Signature.getInstance("SHA256withRSA");

        sig.initSign(k.getPrivate()); //chave privada
        sig.update(bytesToSign); //texto a assinar
        return sig.sign(); // assinar (é a assinatura)
    }
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, InvalidKeyException, SignatureException, UnrecoverableKeyException {

        System.out.println("Ficheiro a ser gerado...");
        //Cria o ficheiro com este texto
        String texto = "texto a testar..";
        writeToFile("texto", texto.getBytes());
        teste uc = new teste();
        
         KeyPair k = KeyStorage.getKeys("certificado.cer", "123456", "nome");
        //System.out.println("Privada:" + k.getPrivate().getEncoded());
        //System.out.println("Publica:" + k.getPublic().getEncoded());

        //cria o ficheiro assinatura e cifra o texto com a chave privada
        writeToFile("textoAssinado", uc.getSignatureOfData(texto.getBytes()));

        System.out.println("Gerado com sucesso");

        System.out.println("Ficheiros a serem validados...");

        //certificate
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        
        FileInputStream is = new FileInputStream("certificado.cer");
        
        X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);
        
        
        verificarCertificado(certificado);
        //signature
        byte[] mysignatureread = readFromFile("textoAssinado");

        if (mysignatureread.length != 384) {
            JOptionPane.showMessageDialog(null, "Erro na assinatura.\nO comprimento da assinatura está errado!");
        } else {
            Signature mysignature = Signature.getInstance("SHA256withRSA");
            mysignature.initVerify(certificado);
            //data
            byte[] data = readFromFile("texto");
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
    
   
    
    public static void verificarCertificado(X509Certificate cer) {
        try {
            //cer.checkValidity(new Date(1990, 1, 1)); //data que permite fazer com que o certificado fique inválido
            cer.checkValidity();
            System.out.println("certificado valido");
        } catch (Exception e) {
            System.out.println("certificado inválido");
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


    public static String bytesToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

}
