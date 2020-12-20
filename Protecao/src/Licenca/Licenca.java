/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Licenca;

import java.util.Date;

/**
 *
 * @author Utilizador
 */
public class Licenca implements java.io.Serializable{

    private byte[] chavePublica;
    //info do PC ainda tenho que ver qual queremos
    private String nomeProjecto;
    private String email;
    private String nome;
    private String numTelemovel;
    private String cc;

    //variaveis DEPOIS de se tornar uma licença oficial
    private Date dataInicio;
    private Date dataValidade;
    private String fraseRandom;

    public Licenca(byte[] chavePublica, String nomeProjecto, String email, String nome, String numTelemovel, String cc) {
        this.chavePublica = chavePublica;
        this.nomeProjecto = nomeProjecto;
        this.email = email;
        this.nome = nome;
        this.numTelemovel = numTelemovel;
        this.cc = cc;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getFraseRandom() {
        return fraseRandom;
    }

    public void setFraseRandom(String fraseRandom) {
        this.fraseRandom = fraseRandom;
    }
    
    
    
}
