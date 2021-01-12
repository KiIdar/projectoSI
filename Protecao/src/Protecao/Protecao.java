package Protecao;

import CartaoCidadao.dadosCartaoCidadao;
import Ficheiros.Ficheiros;
import InfoPessoa.ValidarPerguntas;
import InformacaoSistema.discoRigido;
import InformacaoSistema.cpu;
import InformacaoSistema.datas;
import InformacaoSistema.hostName;
import InformacaoSistema.getIp;
import InformacaoSistema.getMac;
import InformacaoSistema.motherBoard;
import KeyStorage.KeyStorage;
import Licenca.Licenca;
import Validacoes.Validar;
import static Validacoes.Validar.writeToFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
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
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import modosCifra.Assimetrica;
import modosCifra.CBC;

public class Protecao {
    
    private Provider provider;
    private KeyStore ks;

    public Protecao() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

        this.provider = Security.getProvider("SunPKCS11-CartaoCidadao");
        this.ks = KeyStore.getInstance("PKCS11", provider);
        this.ks.load(null, null);
                
        /*this.ks.load(null, "123456".toCharArray());
        FileInputStream is = new FileInputStream("keystore.jks");
        ks = KeyStore.getInstance("jks");
        ks.load(is, "123456".toCharArray());
        System.out.println("sup");*/
    }

    public void init(String app, String versao) {
        //TODO Criar uma licença temporaria já a meter os valores com o que se tem, neste caso a app e a versão
        //Não é para criar uma licença aqui em modo de ficheiro, só instanciala com alguns valores para depois a compararmos/criala oficialmente
        System.out.println("app: " + app + " - versao: " + versao);
    }

    public static void main(String[] args) throws Exception {
        Protecao p = new Protecao();

        //p.instanciarLicenca(1);
        p.isRegistered();
    }

    public PublicKey getPublicKey(Certificate cer) {
        return cer.getPublicKey();
    }
    
    public Certificate getPublicCertificate() throws KeyStoreException {
        return this.ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
    }
    
    

    public boolean isRegistered() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException, IOException, ClassNotFoundException, CertificateException, KeyStoreException, CertPathBuilderException, SignatureException {
        Ficheiros ficheiro = new Ficheiros();
        if (ficheiro.checkExistsLicence()) {
            System.out.println("Licenca existe");
            //TODO verificar se é valida

            Protecao uc = new Protecao();
            Signature verificationEngine = Signature.getInstance("SHA256withRSA");

            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            FileInputStream is = new FileInputStream("LicencaOficial\\certificadoKeyStores.cer");
            X509Certificate certificado = (X509Certificate) fact.generateCertificate(is);
/*
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
                */
                Certificate cer = uc.getPublicCertificate();

                //Pega na chave publica
                PublicKey pk = uc.getPublicKey(cer);

                Assimetrica assimetrica = new Assimetrica();

                byte[] iv = assimetrica.decrypt(ficheiro.lerFicheiro("LicencaOficial\\iv"), getPrivate());
                byte[] chave = assimetrica.decrypt(ficheiro.lerFicheiro("LicencaOficial\\chaveSimetrica"), getPrivate());
                
                SecretKey key = new SecretKeySpec(chave, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

                File file = new File("LicencaOficial\\licenca.aes");
                file.getParentFile().mkdirs();
                FileInputStream fos = new FileInputStream(file);
                BufferedInputStream bos = new BufferedInputStream(fos);
                CipherInputStream cos = new CipherInputStream(bos, cipher);
                ObjectInputStream objectIn = new ObjectInputStream(cos);
                SignedObject signedObject = (SignedObject) objectIn.readObject();
                SealedObject sealedObject = (SealedObject) signedObject.getObject();
                System.out.println("cheguei chegando");
                if (signedObject.verify(uc.getPublicKey(cer), verificationEngine)) {
                    System.out.println("Assinatura valida!");
                    return true;
                } else {
                    System.out.println("Assinatura não valida!");
                    return false;
                }

            /*} catch (CertPathValidatorException cpve) {
                System.out.println("Validation failure, cert[" + cpve.getIndex() + "] :" + cpve.getMessage());
                return false;
            }*/

        } else {
            System.out.println("Licenca não existe");
            return false;
           
        }        
    }
    
    private Licenca decryptLicenca(SealedObject sealedObject, Cipher cipher) {
        CBC cbc = new CBC();
        Licenca licenca = null;
        try {
            licenca = cbc.decrypt(cipher, sealedObject);
        } catch (IOException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return licenca;
    }

    public boolean startRegistration() {
        //TODO Processo de registar e criar uma licença
        return true;
    }

    public void showLicenceInfo() {
        //TODO Desencriptar a licença e apresentala ao utilizador
    }

    /* public void testarAssimetrica() {
        Assimetrica assimetrica = new Assimetrica();
        Ficheiros ficheiro = new Ficheiros();
        try {
            //First generate a public/private key pair
            KeyPair pair = assimetrica.generateKeyPair();
            //KeyPair pair = getKeyPairFromKeyStore();

            //Our secret message
            String message1 = "Ola do outro lado!";
            System.out.println("Mensagem secreta 1: " + message1);

            String message2 = "fonseca";
            System.out.println("Mensagem secreta 2: " + message2);

            //escrever as chaves num ficheiro
            //ficheiro.escreverFicheiro("chave publica.txt", pair.getPublic().getEncoded());
            //ficheiro.escreverFicheiro("chave privada.txt", pair.getPrivate().getEncoded());
            //tentar voltar ter as chaves
            //KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
            //PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(ficheiro.lerFicheiro("chave privada.txt")));
            //PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("chave publica.txt")));
            //KeyPair newKeyPair = new KeyPair(publicKey, privateKey);
            //Encrypt the message
            String cipherText = assimetrica.encrypt(message1, assimetrica.getPublicKey());
            String cipherText2 = assimetrica.encrypt(message2, pair.getPublic());

            System.out.println("Encripta a mensagem 1: " + cipherText);
            System.out.println("Encripta a mensagem 2: " + cipherText2);

            //escrever ficheiro com a encriptada para passar ao outro
            //ficheiro.escreverFicheiro("mensagem.txt", cipherText.getBytes());
            //Now decrypt it
            //String decipheredMessage = assimetrica.decrypt(cipherText, newKeyPair.getPrivate());
            String decipheredMessage2 = assimetrica.decrypt(cipherText2, pair.getPrivate());

            //System.out.println("Desencripta com a chave privada 1: " + decipheredMessage);
            System.out.println("Desencripta com a chave privada 2: " + decipheredMessage2);
        } catch (Exception ex) {
            Logger.getLogger(Protecao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public void getPCInfo() throws IOException, InterruptedException {
        hostName hostName = new hostName();
        System.out.println("HOSTNAME:" + hostName.getHost());
        getIp ip = new getIp();
        System.out.println("IP: " + ip.getIp());
        getMac mac = new getMac();
        System.out.println("MAC: " + mac.getMac());
        cpu cpu = new cpu();
        System.out.println("CPU: " + cpu.getCPUSerial());
        discoRigido dr = new discoRigido();
        System.out.println("DISK: " + dr.getSerialDisk());
        motherBoard mb = new motherBoard();
        System.out.println("MOTHERBOARD: " + mb.getMotherboardSN());
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

    public void instanciarLicenca(int dias) throws Exception {

        hostName hn = new hostName();
        getMac mac = new getMac();
        getIp ip = new getIp();
        cpu cpu = new cpu();
        discoRigido dr = new discoRigido();
        motherBoard mb = new motherBoard();

        dadosCartaoCidadao dcc = new dadosCartaoCidadao();
        ValidarPerguntas validador = new ValidarPerguntas();
        datas datas = new datas();

        //System.out.println(licenca.getCc());
        CBC cbc = new CBC();
        Assimetrica assimetrica = new Assimetrica();
        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();

        String email = "";
        validador.isValidEmail(email);

        String nome = "";
        validador.isValidNome(nome);

        String numTelemovel = "";
        validador.isValidNumTelemovel(numTelemovel);

        Licenca licenca = new Licenca(ip.getIp(), mac.getMac(), hn.getHost(), mb.getMotherboardSN(),
                cpu.getCPUSerial(), dr.getSerialDisk(), email, nome, dcc.getNome(), numTelemovel, dcc.getCC(),
                datas.getDataAtual(), datas.getDataFinal(dias));

        Ficheiros ficheiros = new Ficheiros();

        ficheiros.escreverFicheiro("ToSend\\chaveSimetrica.txt", assimetrica.encrypt(chave, assimetrica.getPublicKey()));
        ficheiros.escreverFicheiro("ToSend\\iv.txt", assimetrica.encrypt(iv, assimetrica.getPublicKey()));

        Validar uc = new Validar();

        Certificate cer = uc.getPublicCertificate();
        writeToFile("ToSend\\certificadoChavePublica.cer", cer.getEncoded());
        PublicKey pk = uc.getPublicKey(cer);
        writeToFile("ToSend\\chavePublica.txt", pk.getEncoded());

        cbc.encrypt(licenca, chave, iv);

    }

    private PrivateKey getPrivate() {
        
        KeyPair k = KeyStorage.getKeys("chavesCliente.jks", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        PrivateKey privateKey = k.getPrivate();
        return privateKey;
    }
}
