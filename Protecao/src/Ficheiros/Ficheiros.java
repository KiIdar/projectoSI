/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheiros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Utilizador
 */
public class Ficheiros {

    public void escreverFicheiro(String nomePasta, byte[] dados) {
        try {
            File myObj = new File(nomePasta);
            myObj.getParentFile().mkdirs();
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
    

    public byte[] lerFicheiro(String nomePasta) {
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

    public boolean checkExistsLicence()
    {
        File myObj = new File("LicencaOficial\\licenca.aes");
        System.out.println(myObj.getAbsolutePath()); 
        
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        
        if (myObj.exists()) {
            return true;
        } else {
            return false;
        }
    }
    /*public void escreverLicenca(Licenca licenca) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(buffer);

        oos.writeObject(licenca);

        oos.close();

        byte[] rawData = buffer.toByteArray();
        escreverFicheiro("test.txt", rawData);
    }*/

    /*public void lerLicenca() throws IOException {
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
    }*/
}
