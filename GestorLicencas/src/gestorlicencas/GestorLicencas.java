package gestorlicencas;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import Licenca.Licenca;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.cert.CertificateException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import sun.security.provider.DSAKeyPairGenerator;

public class GestorLicencas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        Assimetrica assimetrica = new Assimetrica();

        //System.out.println(assimetrica.decrypt(ficheiro.lerFicheiro("mensagem.txt"), keyPair.getPrivate()));
//      Licenca licenca = cbc.decrypt(chave, iv);
        Assinatura assinatura = new Assinatura();
        Licenca licenca = assinatura.getLicenca();

        GestorLicencas gl = new GestorLicencas();

        gl.enviarLicenca(licenca);
        //usei o debugg para ver de maneira facil a variavel licenca, está cá tudo o que mandei
        System.out.println("debugg point here");
    }

    public void enviarLicenca(Licenca licenca) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, InvalidKeySpecException, SignatureException, BadPaddingException {
        CBC cbc = new CBC();
        Assimetrica assimetrica = new Assimetrica();
        Ficheiros f = new Ficheiros();
        Assinatura assinatura = new Assinatura();

        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();
        //TODO guardar isto numa chave assimetrica usando a chave publica da calculadora

        KeyPair k = KeyStorage.getKeys("keystore.jks", "123456", "nome");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        System.out.println("Publica:" + k.getPublic().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(k.getPublic().getEncoded()));
        
        /*
        KeyPair k = KeyStorage.getKeys("chavesCliente.jks", "123456", "nomes");
        System.out.println("Privada:" + k.getPrivate().getEncoded());
        System.out.println("Publica:" + k.getPublic().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(k.getPublic().getEncoded()));*/

        SealedObject sealedObject = cbc.encryptLicenca(licenca, chave, iv);
        SignedObject signedObject = assinatura.signLicenca(sealedObject, chave, iv);

        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        PublicKey publicKey = reconstruct_public_key("RSA", f.lerFicheiro("ToSend\\chavePublica.txt"));

        f.escreverFicheiro("LicencaOficial\\chaveSimetrica", assimetrica.encrypt(chave, publicKey));
        f.escreverFicheiro("LicencaOficial\\iv", assimetrica.encrypt(iv, publicKey));

    }

    public PublicKey reconstruct_public_key(String algorithm, byte[] pub_key) {
        PublicKey public_key = null;

        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec pub_key_spec = new X509EncodedKeySpec(pub_key);
            public_key = kf.generatePublic(pub_key_spec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the public key, the given algorithm oculd not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the public key");
        }

        return public_key;
    }

}
