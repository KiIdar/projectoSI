/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import Licenca.Licenca;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
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

/**
 *
 * @author Utilizador
 */
public class Assinatura {

    public Assinatura() {
    }

    public Licenca getLicenca() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException, ClassNotFoundException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        Ficheiros ficheiro = new Ficheiros();
        Signature verificationEngine = Signature.getInstance("SHA256withRSA");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(ficheiro.lerFicheiro("ToSend\\chavePublica.txt")));

        verificationEngine.initVerify(publicKey);

        /*FileInputStream fileInputStream = new FileInputStream("ToSend\\licenca.aes"); //abre
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream); //abre
        ObjectInputStream objectIn = new ObjectInputStream(bufferedInputStream); //abre
        fileInputStream.close();
        bufferedInputStream.close();
        objectIn.close();*/
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
        
        if(signedObject.verify(publicKey, verificationEngine))
        {
            System.out.println("Assinatura valida!");
            return decryptLicenca(sealedObject, cipher);
        }
        else
        {
            System.out.println("Assinatura não valida!");
        }
        
        return null;
    }

    private Licenca decryptLicenca(SealedObject sealedObject, Cipher cipher) {
        CBC cbc = new CBC();
        Licenca licenca;
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
        return null;
    }

}
