package Validacoes;

import Ficheiros.Ficheiros;
import Licenca.Licenca;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
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
import java.security.SignedObject;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import modosCifra.Assimetrica;
import modosCifra.CBC;

public class Validar {

    Ficheiros f = new Ficheiros();

    private Provider ccProvider;
    private KeyStore ks;

    public Validar() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        this.ccProvider = Security.getProvider("SunPKCS11-CartaoCidadao");
        this.ks = KeyStore.getInstance("PKCS11", ccProvider);
        this.ks.load(null, null);
    }

    public SignedObject getSignatureOfData(SealedObject licenca) throws KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnrecoverableKeyException, IOException, InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey pk = (PrivateKey) ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
        Signature sig = Signature.getInstance("SHA256withRSA", this.ccProvider);

        SignedObject signedobject = new SignedObject(licenca, pk, sig);

        /////////////testar//////////
        Ficheiros ficheiro = new Ficheiros();
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("ToSend\\chavePublica.txt")));
        Signature sig2 = Signature.getInstance(publicKey.getAlgorithm());
        boolean verified = signedobject.verify(publicKey, sig2);
        System.out.println("Is signed Object verified = " + verified);
        //Get Object
        SealedObject unsignedObject = (SealedObject) signedobject.getObject();
        Assimetrica assimetrica = new Assimetrica();
        KeyPair keyPair = assimetrica.getKeyPair();
        CBC cbc = new CBC();
        byte[] iv = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\iv.txt"), keyPair.getPrivate());
        byte[] chave = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\chaveSimetrica.txt"), keyPair.getPrivate());

        Licenca licencaTest = cbc.decrypt(chave, iv);
        
        System.out.println("debugg point here");
        ////////////////////////////
        return signedobject;

        /*sig.initSign(pk); //chave privada
        sig.update(bytesToSign); //texto a assinar

        return sig.sign(); // assinar (é a assinatura)*/
    }

    public Certificate getPublicCertificate() throws KeyStoreException {
        return this.ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
    }

    public PublicKey getPublicKey(Certificate cer) {
        return cer.getPublicKey();
    }

    /* public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, InvalidKeyException, SignatureException, UnrecoverableKeyException {

        System.out.println("Ficheiro a ser gerado...");
        //Cria o ficheiro com este texto
        String texto = "texto a testar..";
        writeToFile("texto", texto.getBytes());

        Validar uc = new Validar();

        //cria o ficheiro assinatura e cifra o texto com a chave privada
        writeToFile("AssinaturaFicheiro", uc.getSignatureOfData(texto.getBytes()));

        //certificado
        Certificate cer = uc.getPublicCertificate();
        writeToFile("certificadoChavePublica", cer.getEncoded());

        //Pega na chave publica
        PublicKey pk = uc.getPublicKey(cer);
        writeToFile("chavePublica", pk.getEncoded());

        System.out.println("Gerado com sucesso");

        System.out.println("Ficheiros a serem validados...");

        //certificate
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream("certificadoChavePublica");
        X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);
        verificarCertificado(certificado);

        //signature
        byte[] mysignatureread = readFromFile("AssinaturaFicheiro");

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
            System.out.println("Assignature: ");
        }
    }*/
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
