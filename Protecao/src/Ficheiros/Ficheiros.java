/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheiros;

import Licenca.Licenca;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilizador
 */
public class Ficheiros {

    public static void escreverFicheiro(String nomePasta, byte[] dados) {
        System.out.println("Pasta com texto: " + dados);
        try {
            File myObj = new File(nomePasta);
            OutputStream os = new FileOutputStream(myObj);

            if (myObj.exists()) {
                myObj.createNewFile();
                System.out.println("File created: " + myObj.getName());
                os.write(dados);
            } else {
                System.out.println("File already exists.");
            }
            os.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static byte[] lerFicheiro(String nomePasta) {
        byte[] inputfileBytes = null;
        try {
            FileInputStream fis = new FileInputStream(nomePasta);
            inputfileBytes = new byte[(int) new File(nomePasta).length()];
            fis.read(inputfileBytes);
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Lido byte: " + inputfileBytes);
        return inputfileBytes;
    }

    public void escreverLicenca(Licenca licenca) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(buffer);

        oos.writeObject(licenca);

        oos.close();

        byte[] rawData = buffer.toByteArray();
        escreverFicheiro("test.txt", rawData);
    }

    public void lerLicenca() throws IOException {
        Licenca licenca = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(lerFicheiro("test.txt"));
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);
            licenca = (Licenca) in.readObject();
            //....
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ficheiros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        
        System.out.println(licenca.getNome() + " - " + licenca.getNumTelemovel());
    }
}