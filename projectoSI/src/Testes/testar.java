package Testes;

import InfoPessoa.validarPerguntas;
import Protecao.Protecao;
import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modosCifra.assimétrica;

/**
 *
 * @author Utilizador
 */
public class testar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner reader = new Scanner(System.in);

        //Perguntas utilizador
        /*
            validarPerguntas v = new validarPerguntas();
            
            String email="";
            String nome="";
            String numTelemovel="";
            String cc = "";
            
            v.isValidNome(nome);
            v.isValidNumTelemovel(numTelemovel);
            v.isValidCC(cc);
            v.isValidEmail(email);
         */
        /**
         * ********************************************************
         */
        /*
            System.out.print("Enter two numbers: ");
            
            // nextDouble() reads the next double from the keyboard
            double first = reader.nextDouble();
            double second = reader.nextDouble();
            
            System.out.print("Enter an operator (+, -, *, /): ");
            char operator = reader.next().charAt(0);
            
            double result;
            
            switch (operator) {
            case '+':
            result = first + second;
            break;
            
            case '-':
            result = first - second;
            break;
            
            case '*':
            result = first * second;
            break;
            
            case '/':
            result = first / second;
            break;
            
            // operator doesn't match any case constant (+, -, *, /)
            default:
            System.out.printf("Error! operator is not correct");
            return;
            }
            
            System.out.println(first + " " + operator + " " + second + " = " + result);*/
        //Biblioteca adicionada e testtada
        /*
            Protecao test = new Protecao();
            test.getPCInfo();
         */
        //Cifra assimétrica
        assimétrica assimetrica = new assimétrica();

        try {
            //First generate a public/private key pair
            KeyPair pair = assimetrica.generateKeyPair();
            //KeyPair pair = getKeyPairFromKeyStore();

            //Our secret message
            String message1 = "rafael";
            System.out.println("Mensagem secreta 1: " + message1);

            String message2 = "fonseca";
            System.out.println("Mensagem secreta 2: " + message2);

            //Encrypt the message
            String cipherText = assimetrica.encrypt(message1, pair.getPublic());
            String cipherText2 = assimetrica.encrypt(message2, pair.getPublic());

            System.out.println("Encripta a mensagem 1: " + cipherText);
            System.out.println("Encripta a mensagem 2: " + cipherText2);

            //Now decrypt it
            String decipheredMessage = assimetrica.decrypt(cipherText, pair.getPrivate());
            String decipheredMessage2 = assimetrica.decrypt(cipherText2, pair.getPrivate());

            System.out.println("Desencripta com a chave privada 1: " + decipheredMessage);
            System.out.println("Desencripta com a chave privada 2: " + decipheredMessage2);
        } catch (Exception ex) {
            Logger.getLogger(testar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
