/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformacaoSistema;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author ASUS
 */
public class Datas {

    public String getDataAtual() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        return now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
    }

    public String getDataFinal(int dia) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime d = now.plusDays(dia);

        return d.getDayOfMonth()+ "/" + d.getMonthValue()+ "/" + d.getYear();
    }

    public static void main(String[] args) {
        Datas d = new Datas();
        System.out.println("Data atual: " + d.getDataAtual());
        System.out.println("Data final: "+d.getDataFinal(10));

    }
}
