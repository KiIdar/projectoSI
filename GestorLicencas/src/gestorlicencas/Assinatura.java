/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import Licenca.Licenca;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Utilizador
 */
public class Assinatura {

    private Provider provider;
    private KeyStore ks;

    public Assinatura() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

        this.provider = Security.getProvider("SunPKCS11-CartaoCidadao");
        this.ks = KeyStore.getInstance("PKCS11", provider);
        this.ks.load(null, null);

        /*this.ks.load(null, "123456".toCharArray());
        FileInputStream is = new FileInputStream("keystore.jks");
        ks = KeyStore.getInstance("jks");
        ks.load(is, "123456".toCharArray());
        System.out.println("sup");
        this.ks.load(null, null);  */
       /* FileInputStream is = new FileInputStream("keystore.jks");
        ks = KeyStore.getInstance("jks");
        ks.load(is, "123456".toCharArray());*/
    }

    private Licenca decryptLicenca(SealedObject sealedObject, Cipher cipher) {
        CBC cbc = new CBC();
        Licenca licenca = null;
        try {
            licenca = cbc.decrypt(cipher, sealedObject);
        } catch (IOException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Assinatura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return licenca;
    }

    public SignedObject signLicenca(SealedObject sealedObject, byte[] chave, byte[] iv) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException, CertificateException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        SignedObject signedObject = null;

        /*KeyPair k = KeyStorage.getKeys("keystore.jks", , "nome");
        //chave privada do gestorLicenca
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(k.getPrivate().getEncoded()));
        //Afinal o provider na assinatura.getInstance é para que mesmo???
        Signature sig = Signature.getInstance("SHA256withRSA"); // <--- aqui estava ("SHA256withRSA","SHA256withRSA", this.ccProvider)

        signedObject = new SignedObject(sealedObject, privateKey, sig);
         */
        Ficheiros f = new Ficheiros();
        KeyPair k = KeyStorage.getKeys("keystore.jks", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        System.out.println("Publica:" + k.getPublic().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(k.getPublic().getEncoded()));

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(k.getPrivate());

        signedObject = new SignedObject(sealedObject, k.getPrivate(), signature);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKey key = new SecretKeySpec(chave, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        File file = new File("LicencaOficial\\licenca.aes");
        file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);
        ObjectOutputStream oos = new ObjectOutputStream(cos);
        oos.writeObject(signedObject);
        oos.close();
        fos.close();
        bos.close();
        cos.close();

        //signature.update(sealedObject);
        byte[] assinatura = signature.sign();

        return signedObject;
    }

    public Licenca getLicenca() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException, ClassNotFoundException, NoSuchPaddingException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException {
        Ficheiros ficheiro = new Ficheiros();
        Assinatura uc = new Assinatura();
        Signature verificationEngine = Signature.getInstance("SHA256withRSA");

        //KeyFactory kf = KeyFactory.getInstance("RSA");
        //PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("ToSend\\chavePublica.txt")));
        //verificationEngine.initVerify(publicKey);
        //certificate
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream("ToSend\\certificadoChavePublica.cer");
        X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);

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
        rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL, PKIXRevocationChecker.Option.NO_FALLBACK));
        PKIXCertPathValidatorResult result = null;
        try {
//Do the velidation
            result = (PKIXCertPathValidatorResult) cpv.validate(cp, pkixParams);
            System.out.println("Certificado Válido");
            System.out.println("Issuer of trust anchor certificate: "
                    + result.getTrustAnchor().getTrustedCert().getIssuerDN().getName());

            Certificate cer = uc.getPublicCertificate();

            //Pega na chave publica
            PublicKey pk = uc.getPublicKey(cer);
            ficheiro.escreverFicheiro("ToSend\\chavePublica", pk.getEncoded());

            Assimetrica assimetrica = new Assimetrica();
            KeyPair keyPair = assimetrica.getKeyPair();
         

            byte[] iv = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\iv.txt"), keyPair.getPrivate());
            byte[] chave = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\chaveSimetrica.txt"), keyPair.getPrivate());
            SecretKey key = new SecretKeySpec(chave, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            File file = new File("ToSend\\licenca.aes");
            file.getParentFile().mkdirs();
            FileInputStream fos = new FileInputStream(file);
            BufferedInputStream bos = new BufferedInputStream(fos);
            CipherInputStream cos = new CipherInputStream(bos, cipher);
            ObjectInputStream objectIn = new ObjectInputStream(cos);
            SignedObject signedObject = (SignedObject) objectIn.readObject();
            SealedObject sealedObject = (SealedObject) signedObject.getObject();

            if (signedObject.verify(uc.getPublicKey(cer), verificationEngine)) {
                System.out.println("Assinatura valida!");
                return decryptLicenca(sealedObject, cipher);
            } else {
                System.out.println("Assinatura não valida!");
            }

        } catch (CertPathValidatorException cpve) {
            System.out.println("Validation failure, cert[" + cpve.getIndex() + "] :" + cpve.getMessage());
        }

        return null;
    }

    public Certificate getPublicCertificate() throws KeyStoreException {
        return this.ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
    }

    public PublicKey getPublicKey(Certificate cer) {
        return cer.getPublicKey();
    }

}
