/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modosCifra;

import Ficheiros.Ficheiros;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
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
 * @author ASUS
 */
public class assinatura {

    private Provider ccProvider;
    private KeyStore ks;
    Ficheiros f = new Ficheiros();

    public assinatura() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

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

    public static void main(String[] args) throws Exception {
        assinatura as = new assinatura();
        
    }

    public void fazAssintura(String nomeFicheiro) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        System.out.println("Ficheiro a ser gerado...");
        //Cria o ficheiro com este texto
        if (!new File("nomeFicheiro").exists()) {
            f.escreverFicheiro("texto", nomeFicheiro.getBytes());
        }
        byte[] texto = f.lerFicheiro(nomeFicheiro);
        assinatura uc = new assinatura();       

        //cria o ficheiro assinatura e cifra o texto com a chave privada
        f.escreverFicheiro("AssinaturaFicheiro", uc.getSignatureOfData(texto));

        //certificado
        Certificate cer = uc.getPublicCertificate();
        f.escreverFicheiro("certificadoChavePublica.cer", cer.getEncoded());

        //Pega na chave publica
        PublicKey pk = uc.getPublicKey(cer);
        f.escreverFicheiro("chavePublica", pk.getEncoded());
        
        
        

        System.out.println("Gerado com sucesso");

        System.out.println("Ficheiros a serem validados...");

        //certificate
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream("certificadoChavePublica.cer");
        X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);
        verificarCertificado(certificado);

        //signature
        byte[] mysignatureread = f.lerFicheiro("AssinaturaFicheiro");

        if (mysignatureread.length != 384) {
            JOptionPane.showMessageDialog(null, "Erro na assinatura.\nO comprimento da assinatura está errado!");
        } else {
            Signature mysignature = Signature.getInstance("SHA256withRSA");
            mysignature.initVerify(certificado);
            //data
            byte[] data = f.lerFicheiro("texto");
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


    //Não está 100% certo
    public static void verificarCertificado(X509Certificate cer) {
        try {
            //cer.checkValidity(new Date(1990, 1, 1)); //data que permite fazer com que o certificado fique inválido
            cer.checkValidity();
            System.out.println("certificado valido");
        } catch (Exception e) {
            System.out.println("certificado inválido");
        }
    }

    public static String bytesToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

}
