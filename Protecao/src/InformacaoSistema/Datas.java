/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASUS
 */
public class Datas {

    //Data atual
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    //Data final
    LocalDateTime a = now.plusYears(1);

    //System.out.println("Data final: " + a);
    //System.out.println("Data inicial: " + now);
    //System.out.println("Igual?" + now.plusYears(1).isEqual(a));
}
