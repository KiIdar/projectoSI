/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.SealedObject;

/**
 *
 * @author Utilizador
 */
public class Assinatura {

    public Assinatura() {
    }

    public void getSealedObject() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException, ClassNotFoundException {
        Ficheiros ficheiro = new Ficheiros();
        SignedObject so = null;
        Signature verificationEngine = Signature.getInstance("SHA256withRSA");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("ToSend\\chavePublica.txt")));

        verificationEngine.initVerify(publicKey);

        FileInputStream fileInputStream = new FileInputStream("ToSend\\licenca.aes"); //abre
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream); //abre
        ObjectInputStream objectIn = new ObjectInputStream(bufferedInputStream); //abre
        fileInputStream.close();
        bufferedInputStream.close();
        objectIn.close();
        SealedObject message = (SealedObject) objectIn.readObject(); // read message
        System.out.println("msg: " + message);
        byte[] signature = (byte[]) objectIn.readObject(); // read signature, hmmm

        if (verificationEngine.verify(signature)) {
            System.out.println("Assinatura verificada");
            try {
                Object myobj = so.getObject();
            } catch (java.lang.ClassNotFoundException e) {
            }
        };
    }

}
