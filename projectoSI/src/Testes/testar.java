package Testes;

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
        Protecao protecao = new Protecao();

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
        //-------------------------------------------------------------------//
        
        
        //Testar Cifra assimetrica
       //protecao.testarAssimetrica();
       
       //Tesrar Validar
       //protecao.validarPerguntas();
       
       //escrever licença e ler
       protecao.crearLicenca();
    }
}
