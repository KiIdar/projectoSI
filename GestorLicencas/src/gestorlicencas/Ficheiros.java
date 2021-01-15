/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorlicencas;

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
        //System.out.println("Pasta com texto: " + dados);
        try {
            File myObj = new File(nomePasta);
            //myObj.getParentFile().mkdirs();
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
        /*Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);*/
        byte[] inputfileBytes = null;
        try {
            FileInputStream fis = new FileInputStream(nomePasta);
            inputfileBytes = new byte[(int) new File(nomePasta).length()];
            fis.read(inputfileBytes);
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // System.out.println("Lido byte: " + inputfileBytes);
        return inputfileBytes;
    }
}
