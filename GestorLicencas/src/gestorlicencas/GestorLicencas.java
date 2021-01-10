package gestorlicencas;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import Licenca.Licenca;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

        //usei o debugg para ver de maneira facil a variavel licenca, está cá tudo o que mandei
        System.out.println("debugg point here");
    }
    
}
