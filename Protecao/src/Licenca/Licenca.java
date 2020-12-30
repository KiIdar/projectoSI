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

    private byte[] chavePublica;
    //info do PC ainda tenho que ver qual queremos
    private String nomeProjecto;
    private String email;
    private String nome;
    private String numTelemovel;
    private String cc;
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
    private String fraseRandom;

    public Licenca(String ipAddress, String macAddress, String hostName, String serialMB,
            String serialCPU, String serialDisk, byte[] chavePublica, String nomeProjecto,
            String email, String nome, String numTelemovel, String cc, String dataInicio, String dataFinal) {
        this.chavePublica = chavePublica;
        this.nomeProjecto = nomeProjecto;
        this.email = email;
        this.nome = nome;
        this.numTelemovel = numTelemovel;
        this.cc = cc;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.hostName = hostName;
        this.serialMB = serialMB;
        this.serialCPU = serialCPU;
        this.serialDisk = serialDisk;
        this.dataFinal=dataFinal;
        this.dataInicio=dataInicio;
    }

    public byte[] getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(byte[] chavePublica) {
        this.chavePublica = chavePublica;
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
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumTelemovel() {
        return numTelemovel;
    }

    public void setNumTelemovel(String numTelemovel) {
        this.numTelemovel = numTelemovel;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
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

    public String getFraseRandom() {
        return fraseRandom;
    }

    public void setFraseRandom(String fraseRandom) {
        this.fraseRandom = fraseRandom;
    }

}
