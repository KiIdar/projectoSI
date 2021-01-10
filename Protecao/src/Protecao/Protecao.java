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
import Licenca.Licenca;
import Validacoes.Validar;
import static Validacoes.Validar.verificarCertificado;
import static Validacoes.Validar.writeToFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import modosCifra.Assimetrica;
import modosCifra.CBC;

public class Protecao {

    public void init(String app, String versao) {
        //TODO Criar uma licença temporaria já a meter os valores com o que se tem, neste caso a app e a versão
        //Não é para criar uma licença aqui em modo de ficheiro, só instanciala com alguns valores para depois a compararmos/criala oficialmente
        System.out.println("app: " + app + " - versao: " + versao);
    }

    public static void main(String[] args) throws Exception {
        Protecao p = new Protecao();

        p.instanciarLicenca(1);
    }

    public boolean isRegistered() {
        Ficheiros ficheiro = new Ficheiros();
        if (ficheiro.checkExistsLicence()) {
            System.out.println("Licenca existe");
            //TODO verificar se é valida
        } else {
            System.out.println("Licenca não existe");
            return false;
        }
        return true;
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

    /*public void criarLicenca() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InvalidKeySpecException, Exception {
        Licenca licenca = new Licenca(null, null, null, null, null, null, null, null, null, "hi", "21423423423", null);

        Assimetrica assimetrica = new Assimetrica();
        Ficheiros ficheiro = new Ficheiros();
        KeyPair keyPair = assimetrica.getKeyPair();

        CBC cbc = new CBC();
        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();

        byte[] iv2 = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\iv.txt"), keyPair.getPrivate());
        byte[] chave2 = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\chaveSimetrica.txt"), keyPair.getPrivate());

        cbc.encrypt(licenca, chave2, iv2);

        System.out.println(cbc.decrypt(chave2, iv2).getNome() + " . " + cbc.decrypt(chave2, iv2).getNumTelemovel());
    }*/
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
                cpu.getCPUSerial(), dr.getSerialDisk(), chave, email, nome, dcc.getNome(), numTelemovel, dcc.getCC(),
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
}
