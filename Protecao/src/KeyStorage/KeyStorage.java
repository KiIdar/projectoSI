/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author joaob
 */
public class KeyStorage {

    public static KeyPair getKeys(String path, String password, String alias) {

        try {
            FileInputStream is = new FileInputStream(path);
            
            KeyStore keystore = KeyStore.getInstance("jks");
            keystore.load(is, password.toCharArray());
            
            Key key = keystore.getKey(alias, password.toCharArray());
            
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
                Ficheiros f = new Ficheiros();
                f.escreverFicheiro("LicencaOficial\\certificadoKeyStores.cer", cert.getEncoded());
                
                // Get public key
                PublicKey publicKey = cert.getPublicKey();
                
                System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
                
                System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
                
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
