package Testes;

import InformacaoSistema.CPU;
import InformacaoSistema.HostName;
import InformacaoSistema.MAC;
import InformacaoSistema.MotherBoard;
import Protecao.Protecao;
import java.io.IOException;
import java.net.Inet4Address;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author Utilizador
 */
public class testar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
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
            protecao.getPCInfo();
             */
            //-------------------------------------------------------------------//
            //Testar Cifra assimetrica
            //protecao.testarAssimetrica();
            //Testar Validar
            //protecao.validarPerguntas();
            //escrever licença e ler
            //protecao.crearLicenca();
            //protecao.instanciarLicenca();
            //Testar infoSistema
            CPU cpu = new CPU();
            MAC mac = new MAC();
            HostName hostname = new HostName();
            MotherBoard motherboard = new MotherBoard();

            //cpu.Info();
            //mac.getIP();
            //hostname.getHost();
            //motherboard.serial();
            //-------------------------------------------------------------------//
            //Testar Cifra assimetrica
            //protecao.testarAssimetrica();
            //Tesrar Validar
            //protecao.validarPerguntas();
            //escrever licença e ler
            //protecao.instanciarLicenca();
            //protecao.criarLicenca();
           

        } catch (Exception ex) {
            Logger.getLogger(testar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
