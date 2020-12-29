/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import Licenca.Licenca;


/**
 *
 * @author Utilizador
 */
public class GestorLicencas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        Assimetrica assimetrica = new Assimetrica();

        Ficheiros ficheiro = new Ficheiros();

        KeyPair keyPair = assimetrica.getKeyPair();
        //System.out.println(assimetrica.decrypt(ficheiro.lerFicheiro("mensagem.txt"), keyPair.getPrivate()));
        
        CBC cbc = new CBC();
        byte[] iv = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\iv.txt"), keyPair.getPrivate());
        byte[] chave = assimetrica.decrypt(ficheiro.lerFicheiro("ToSend\\chaveSimetrica.txt"), keyPair.getPrivate());
          
        Licenca licenca = cbc.decrypt(chave, iv);
        
        //usei o debugg para ver de maneira facil a variavel licenca, está cá tudo o que mandei
        System.out.println("debugg point here");
    }

}
