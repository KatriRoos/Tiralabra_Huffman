
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
      
        //Luodaan taulukko merkkien koodeille HASHMAP TAI JOKU MUU KU TAULU
        String[] koodiTaulu = new String[merkkeja];
        apu.teeKoodiTaulu(koodiTaulu, "" , puu.getJuuri());
        
        //Pakataan teksti
        //String pakattuTeksti = "";
        
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < teksti.length(); i++) {
            //pakattuTeksti += koodiTaulu.get(teksti.charAt(i));//;
            builder.append(koodiTaulu[teksti.charAt(i)]);
        }
        String pakattuTeksti = builder.toString();
       
        //Muutetaan pakattu teksti String tavuiksi tallennusta varten
        byte[] tavut = apu.merkitTavuiksi(pakattuTeksti.toCharArray());
        
        //Tallennetaan puu pakatun tekstin kanssa tiedostoon.
        io.tallennaPakattuTiedostoon(tavut, puu, tiedosto);
        
    }
 
}
