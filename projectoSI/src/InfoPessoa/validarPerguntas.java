/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfoPessoa;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class validarPerguntas {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean isValidNome(String nome) {
        boolean isNomeIdValid = false;
        nome = JOptionPane.showInputDialog(null, "Escreva o seu nome:");

        String expression = "^[A-Z][a-z]* [A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nome);

        while (matcher.find()) {

            /*if (matcher.matches()) {
                System.out.println("Nome final: " + nome);
                isNomeIdValid = true;
            } else {*/
                nome = JOptionPane.showInputDialog(null, "Escreva o seu nome:");
                isNomeIdValid = false;
            //}
        }

        return isNomeIdValid;
    }

    /*
    public void isValidEmail(String email) {
        boolean inputAccepted = false;
        String title = "Please anter a number";
        int initialType = JOptionPane.QUESTION_MESSAGE;

        while (!inputAccepted) {
            email = JOptionPane.showInputDialog(null, "Escreva o seu e-mail:");

            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
            Matcher regex = p.matcher(email);
            //Matcher regex = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (regex.find()) {
                JOptionPane.showMessageDialog(null, "Please enter only string");
            } else {
                JOptionPane.showMessageDialog(null, "Email aceite!");
            }

            //return isEmailIdValid;
        }

    

     */
    public boolean isValidNumTelemovel(String numero) {
        boolean isNumIdValid = false;
        if (numero != null && numero.length() > 0) {
            String expression = "/9[1236][0-9]{7}|2[1-9][0-9]{7}/";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(numero);
            if (matcher.matches()) {
                isNumIdValid = true;
            }
        }
        return isNumIdValid;
    }

    public boolean isValidCC(String cc) {
        boolean isCCIdValid = false;
        if (cc != null && cc.length() > 0) {
            String expression = "^[0-9]{8}[ -]*[0-9][A-Z]{2}[0-9]$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(cc);
            if (matcher.matches()) {
                isCCIdValid = true;
            }
        }
        return isCCIdValid;
    }

}
