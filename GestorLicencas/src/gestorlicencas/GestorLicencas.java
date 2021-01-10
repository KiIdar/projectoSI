package gestorlicencas;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import Licenca.Licenca;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

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
       
       gl.enviarLicenca();
       
       
       

        //usei o debugg para ver de maneira facil a variavel licenca, está cá tudo o que mandei
        System.out.println("debugg point here");
    }
    
    public void enviarLicenca() throws NoSuchAlgorithmException{
        CBC cbc = new CBC();
        Assimetrica assimetrica = new Assimetrica();
        Ficheiros f = new Ficheiros();
        
        byte[] chave = cbc.generateKey();
        byte[] iv = cbc.generateIV();
        
        KeyPair k = KeyStorage.getKeys("keystore.jks", "123456", "nome");
        System.out.println("Privada:"+k.getPrivate().getEncoded());
        System.out.println("Publica:"+k.getPublic().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(k.getPublic().getEncoded()));
        
        
    }
    
}
