package Testes;

import Licenca.Licenca;
import Protecao.Protecao;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilizador
 */
public class Calculadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, Exception {

        Scanner reader = new Scanner(System.in);
        Protecao protecao = new Protecao();
        
        Class c = new Object() {}.getClass().getEnclosingClass();
        protecao.init(c.getName(), "3");
       
        if(protecao.isRegistered())
        {
            System.out.println("You got it!");
        }
        else
        {
           if(protecao.startRegistration())
           {
               System.out.println("Registration successfull!");
           }
           else
           {
               System.out.println("Something went wrong.");
           }
        }

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
        //protecao.getPCInfo();
        //-------------------------------------------------------------------//
        //Testar Cifra assimetrica
        //protecao.testarAssimetrica();
        //Testar Validar
        //protecao.validarPerguntas();
        //escrever licença e ler
        //protecao.crearLicenca();
        //protecao.instanciarLicenca();
        //Testar infoSistema
        //-------------------------------------------------------------------//
        //Testar Cifra assimetrica
        //protecao.testarAssimetrica();
        //Tesrar Validar
        //protecao.validarPerguntas();
        //escrever licença e ler
        //protecao.instanciarLicenca();
        //protecao.criarLicenca();
        //protecao.testarAssimetrica();
    }
}
