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
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.EnumSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Assinatura {

    private Provider ccProvider;
    private KeyStore ks;

    public Assinatura() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

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
        Assinatura as = new Assinatura();
        //as.fazAssintura();
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

    public void fazAssintura() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, InvalidKeyException, SignatureException, UnrecoverableKeyException {
        System.out.println("Ficheiro a ser gerado...");
        //Cria o ficheiro com este texto
        String texto = "texto a testar..";
        writeToFile("texto", texto.getBytes());
        Assinatura uc = new Assinatura();

        //cria o ficheiro assinatura e cifra o texto com a chave privada
        writeToFile("AssinaturaFicheiro", uc.getSignatureOfData(texto.getBytes()));

        //certificado
        Certificate cer = uc.getPublicCertificate();
        if (!new File("certificadoChavePublica.cer").exists()) {
            writeToFile("certificadoChavePublica.cer", cer.getEncoded());
        } 

        //Pega na chave publica
        PublicKey pk = uc.getPublicKey(cer);
        writeToFile("chavePublica", pk.getEncoded());

        System.out.println("Gerado com sucesso!!!");
        System.out.println("------------------");
        System.out.println("Ficheiros a serem validados...");
        //certificate
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream("certificadoChavePublica.cer");
        X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);

        try {
            PKIXParameters par = new PKIXParameters(ks);
            for (TrustAnchor ta : par.getTrustAnchors()) {
                X509Certificate c = ta.getTrustedCert();
                System.out.println(c.getSubjectDN().getName());
            }

            //defines the end-user certificate as a selector
            X509CertSelector cs = new X509CertSelector();
            cs.setCertificate((X509Certificate) certificado);
//Create an object to build the certification path
            CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX");
//Define the parameters to buil the certification path and provide the Trust anchor
//certificates (trustAnchors) and the end user certificate (cs)
            PKIXBuilderParameters pkixBParams = new PKIXBuilderParameters(par.getTrustAnchors(), cs);
            pkixBParams.setRevocationEnabled(false); //No revocation check
//Provide the intermediate certificates (iCerts)
            CollectionCertStoreParameters ccsp
                    = new CollectionCertStoreParameters(Arrays.asList());
            CertStore store = CertStore.getInstance("Collection", ccsp);
            pkixBParams.addCertStore(store);
//Build the certification path
            CertPath cp = null;
            try {
                CertPathBuilderResult cpbr = cpb.build(pkixBParams);
                cp = cpbr.getCertPath();
                System.out.println("Certification path built with success!");
            } catch (CertPathBuilderException ex) {
                System.out.println("It was not possible to build a certification path!");
            }

            PKIXParameters pkixParams = new PKIXParameters(par.getTrustAnchors());
//Class that performs the certification path validation
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
//Disables the previous mechanism for revocation check (pre Java8)
            pkixParams.setRevocationEnabled(false);
//Enable OCSP verification
            Security.setProperty("ocsp.enable", "true");
//Instantiate a PKIXRevocationChecker class
            PKIXRevocationChecker rc = (PKIXRevocationChecker) cpv.getRevocationChecker();
//Configure to validate all certificates in chain using only OCSP
            rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL,PKIXRevocationChecker.Option.NO_FALLBACK));
            PKIXCertPathValidatorResult result = null;
            try {
//Do the velidation
                result = (PKIXCertPathValidatorResult) cpv.validate(cp, pkixParams);
                System.out.println("Certificado Válido");
                System.out.println("Issuer of trust anchor certificate: "
                        + result.getTrustAnchor().getTrustedCert().getIssuerDN().getName());
            } catch (CertPathValidatorException cpve) {
                System.out.println("Validation failure, cert[" + cpve.getIndex() + "] :"+ cpve.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Certificado invalido");
        }
        
        //verificarCertificado(certificado);
        //Fim da validação em cadeias de certificados
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
        }
    }

    //Não usado mas deixar
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
