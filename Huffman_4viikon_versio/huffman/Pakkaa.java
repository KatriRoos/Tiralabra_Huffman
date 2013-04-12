
package huffman;

import apulaiset.*;
import java.io.File;
import java.io.IOException;
import tietorakenteet.*;

/**
 * Sisältää metodit tekstitiedoston pakkaamiseksi
 * Huffman koodin avulla.
 * 
 * @author Katri Roos
 * @since 12.4.2013
 */
public class Pakkaa {
    private static final int merkkeja = 256;
    //TESTIÄ VARTEN KUNNES PUUN TALLENNUS ONNISTUU
    public static PuuSolmu juuri;
    private Io io = new Io();
    private MuutApulaiset apu = new MuutApulaiset();
    
    /**
     * Tiedoston pakkaaminen.
     * 
     * @param tiedosto String
     * @throws IOException 
     */
    public void pakkaa(String tiedosto) throws IOException     {
        //Luetaan tekstiä tiedostosta.
        String teksti = io.lueTiedosto(new File(tiedosto));
      
        //Luodaan taulukko merkkien esiintymistiheyksille
        int[] merkkienTiheys = apu.esiintymisTiheys(teksti);
       
        //Tehdään Huffman puu
        HuffmaninPuu puu = new HuffmaninPuu(apu.teePuu(merkkienTiheys));
        
        //TESTIÄ VARTEN KUNNES PUU TALLENNETTU
        this.juuri = puu.getJuuri();
        
        //Luodaan taulukko merkkien koodeille
        String[] koodiTaulu = new String[merkkeja];
        apu.teeKoodiTaulu(koodiTaulu, "" , puu.getJuuri());
        
        //Pakataan teksti
        String pakattuTeksti = "";
        
        for (int i = 0; i < teksti.length(); i++) {
            pakattuTeksti = pakattuTeksti + koodiTaulu[teksti.charAt(i)];
        }
        
        //Muutetaan pakattu teksti String tavuiksi tallennusta varten
        byte[] tavut = apu.merkitTavuiksi(pakattuTeksti.toCharArray());
        
        //Tallennetaan puu pakatun tekstin kanssa tiedostoon.
        io.tallennaPakattuTiedostoon(tavut, puu, tiedosto);
        
    }
}
