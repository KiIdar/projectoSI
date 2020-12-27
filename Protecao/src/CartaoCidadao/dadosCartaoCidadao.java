package CartaoCidadao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 *
 * @author ASUS
 */
public class dadosCartaoCidadao {
       //Mostra o nome da pessoa do cc

    public void providers(){
        Provider[] prov = Security.getProviders();

        for (int i = 0; i < prov.length; i++) {
            System.out.println(i + "- nome do provider: " + prov[i].getName());
        }
    }

    public Certificate provider() throws Exception {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks = null;
        ks = KeyStore.getInstance("PKCS11",prov);
        ks.load(null,null);
        Enumeration<String> als = ks.aliases();
        Certificate c = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
        //System.out.println("Certificado: " + c);

        while (als.hasMoreElements()) {
            System.out.println(als.nextElement());
        }

        String tudoCertificado = c.toString();
        //System.out.println(tudoCertificado);
        String[] testeNome = tudoCertificado.split("CN=");
        String nome[] = testeNome[1].split(",");
        System.out.println("Nome:"+nome[0]);

        return null;
    }
    
     public static void main(String[] args) throws Exception {
        dadosCartaoCidadao d = new dadosCartaoCidadao();
        d.providers();
        d.provider();
    }

}
