package gestorlicencas;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import Licenca.Licenca;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.cert.CertificateException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

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
    
    public void enviarLicenca(Licenca licenca) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, InvalidKeySpecException, SignatureException{
        CBC cbc = new CBC();
        Assimetrica assimetrica = new Assimetrica();
        Ficheiros f = new Ficheiros();
        Assinatura assinatura = new Assinatura();
        
        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();
        //TODO guardar isto numa chave assimetrica usando a chave publica da calculadora
        
        KeyPair k = KeyStorage.getKeys("keystore.jks", "123456", "nome");
        System.out.println("Privada:"+k.getPrivate().getEncoded());
        System.out.println("Publica:"+k.getPublic().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(k.getPublic().getEncoded()));
        
        SealedObject sealedObject = cbc.encryptLicenca(licenca, chave, iv);
        SignedObject signedObject = assinatura.signLicenca(sealedObject);
        
        //TODO guardar o signedObject e qualquer outra coisa que falta, que nao tenho 100% certeza por isso falo contigo
    }
    
}
