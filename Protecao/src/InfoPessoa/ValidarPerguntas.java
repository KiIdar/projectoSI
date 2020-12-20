/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfoPessoa;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Utilizador
 */
public class ValidarPerguntas {
     public void isValidNome(String nome) {
        nome = JOptionPane.showInputDialog(null, "Escreva o seu nome completo:");

        String expression = "^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]{0,}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        while (!pattern.matcher(nome).find()) {

            JOptionPane.showMessageDialog(null, "Nome inválido.\nTente novamente.");
            nome = (JOptionPane.showInputDialog(null, "Por favor, escreva o seu nome completo:"));
        }
        System.out.println("Nome final: "+nome);
    }
    
    
    public void isValidEmail(String email) {
        email = JOptionPane.showInputDialog(null, "Escreva o seu email:");

        String expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        while (!pattern.matcher(email).find()) {

            JOptionPane.showMessageDialog(null, "Nome inválido.\nTente novamente.");
            email = (JOptionPane.showInputDialog(null, "Por favor, escreva o seu email:"));
        }
        System.out.println("E-mail final: "+email);
    }
    
    public void isValidNumTelemovel(String numero) {
        numero = JOptionPane.showInputDialog(null, "Escreva o seu numero:");

        String expression = "^9[1236][0-9]{7}|2[1-9][0-9]{7}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        while (!pattern.matcher(numero).find()) {

            JOptionPane.showMessageDialog(null, "Nome inválido.\nTente novamente.");
            numero = (JOptionPane.showInputDialog(null, "Por favor, escreva o seu numero:"));
        }
        System.out.println("Numero telemovel final: "+numero);
    }
    
    public void isValidCC(String cc) {
        cc = JOptionPane.showInputDialog(null, "Escreva o seu número do cartão cidadão final:");

        String expression = "^9[1236][0-9]{7}|2[1-9][0-9]{7}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        while (!pattern.matcher(cc).find()) {

            JOptionPane.showMessageDialog(null, "Nome inválido.\nTente novamente.");
            cc = (JOptionPane.showInputDialog(null, "Por favor, escreva o seu número do cartão cidadão final:"));
        }
        System.out.println("número do cartão cidadão final: "+cc);
    }
}
