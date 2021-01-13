package CartaoCidadao;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Enumeration;

public class DadosCartaoCidadao {

    String cc[];
    String nome[];

    public void providers() {
        Provider[] prov = Security.getProviders();

        for (int i = 0; i < prov.length; i++) {
            System.out.println(i + "- nome do provider: " + prov[i].getName());
        }
    }

    public String getCC() throws Exception {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks = null;
        ks = KeyStore.getInstance("PKCS11", prov);
        ks.load(null, null);
        Enumeration<String> als = ks.aliases();
        Certificate c = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");

        String tudoCertificado = c.toString();
        String[] testeCC = tudoCertificado.split("SERIALNUMBER=BI");
        cc = testeCC[1].split(",");

        return cc[0];
    }

    public String getNome() throws Exception {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks = null;
        ks = KeyStore.getInstance("PKCS11", prov);
        ks.load(null, null);
        Enumeration<String> als = ks.aliases();
        Certificate c = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
        String tudoCertificado = c.toString();
        String[] testeNome = tudoCertificado.split("CN=");
        nome = testeNome[1].split(",");

        return nome[0];
    }

    //Teste
    public static void main(String[] args) throws Exception {
        DadosCartaoCidadao d = new DadosCartaoCidadao();
        //d.providers();
        System.out.println("CC:" + d.getCC());
        System.out.println("Nome:" + d.getNome());

    }

}
