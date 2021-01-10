/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Licenca;

import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author Utilizador
 */
public class Licenca implements java.io.Serializable {

    //info do PC ainda tenho que ver qual queremos
    private static final long serialVersionUID = 6529685098267757690L;
    private String nomeProjecto;
    private String email;
    private String nomeCC;
    private String numTelemovel;
    private String numeroCC;
    private String ipAddress;
    private String macAddress;
    private String hostName;
    private String serialMB;
    private String serialCPU;
    private String serialDisk;

    //variaveis DEPOIS de se tornar uma licença oficial
    //Tem de ser strings por causa dos emtodos
    private String dataInicio;
    private String dataFinal;

    public Licenca(String ipAddress, String macAddress, String hostName, String serialMB,
            String serialCPU, String serialDisk, String nomeProjecto,
            String email, String nome, String numTelemovel, String cc, String dataInicio, String dataFinal) {
        this.nomeProjecto = nomeProjecto;
        this.email = email;
        this.nomeCC = nome;
        this.numTelemovel = numTelemovel;
        this.numeroCC = cc;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.hostName = hostName;
        this.serialMB = serialMB;
        this.serialCPU = serialCPU;
        this.serialDisk = serialDisk;
        this.dataFinal=dataFinal;
        this.dataInicio=dataInicio;
    }


    public String getNomeProjecto() {
        return nomeProjecto;
    }

    public void setNomeProjecto(String nomeProjecto) {
        this.nomeProjecto = nomeProjecto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nomeCC;
    }

    public void setNome(String nome) {
        this.nomeCC = nome;
    }

    public String getNumTelemovel() {
        return numTelemovel;
    }

    public void setNumTelemovel(String numTelemovel) {
        this.numTelemovel = numTelemovel;
    }

    public String getCc() {
        return numeroCC;
    }

    public void setCc(String cc) {
        this.numeroCC = cc;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataValidade() {
        return dataFinal;
    }

    public void setDataValidade(String dataFinal) {
        this.dataFinal = dataFinal;
    }

}
