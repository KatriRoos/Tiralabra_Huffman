/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apulaiset;

import huffman.Bittimuunnokset;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import tietorakenteet.*;

/**
 *
 * @author Seppo Von Klump
 */
public class MuutApulaiset {
    /**
     * Merkkien määrä.
     */
    private static final int merkkeja = 256;
       /**
     * Haetaan syötteenä saadun puun esijärjestys Stringinä.
     * 
     * @param juuri PuuSolmu
     */
    public String haePuunEsijarjestys(PuuSolmu juuri)   {
        //käydään läpi esijärjestys
         String esijarjestys = "";
         Pino pino = new Pino();
         PuuSolmu s = juuri;
         while(!pino.onkoTyhja() || s != null)  {
             if(s != null)  {
                 if(s.ollaankoLehdessa())  {
                     esijarjestys += s.getMerkki();
                 }
                 else   {
                     esijarjestys += s.getPaino() + "";
                 }
                 pino.push(s.getOikea());
                 s = s.getVasen();
             }
             else   {
                 s = (PuuSolmu)pino.pop();
             }
         }
         return esijarjestys;
    }
    
    /**
     * Muutetaan ykkös ja nolla char-merkit biteiksi ja
     * tavuiksi.
     * 
     * @param merkit char[]
     * @return byte[] tavut
     */
    public byte[] merkitTavuiksi(char[] merkit)    {
        Bittimuunnokset b = new Bittimuunnokset();
        return b.tavuiksi(merkit);
    }
    
      /**
     * Pakattavan tekstin merkkien esiintymistiheyksien selvittäminen.
     * 
     * @param teksti String
     * @return int[] taulukko, minkä indeksi vastaa merkkiä ja sisältö on esiintymistiheys
     */
    public int[] esiintymisTiheys(String teksti) {
        //Taulukko tavujen esiintymistiheyksille
        int[] tiheys = new int[merkkeja];
              
        for (int i = 0; i < teksti.length(); i++) {
            tiheys[teksti.charAt(i)]++;
        }
        
        return tiheys;  
    } 
    
      /**
     * Metodi tekee Huffmanin puun esiintymistiheyksistä.
     * 
     * @param esiintymisTiheys int[] taulukko, missä indeksinä merkki ja
     *                         arvona tiheys.
     * @return PuuSolmu puun juuri
     */
    public PuuSolmu teePuu(int[] esiintymisTiheys)    {
        //Jono, mihin solmut laitetaan järjestyksessä.
        Queue jono = new PriorityQueue();
       
        for (char i = 0; i < esiintymisTiheys.length; i++) {
            if(esiintymisTiheys[i] > 0) {
                jono.add(new PuuSolmu(i, esiintymisTiheys[i], null, null));
            }
        }
     
        //Yhdistetään solmut puuksi niin, että aina kaksi pienintä yhdistetään.
        while(jono.size() > 1)  {
            PuuSolmu vasen = (PuuSolmu)jono.poll();
            
            PuuSolmu oikea = (PuuSolmu)jono.poll();
            
            //Tehdään vanhempi, mihin yhdistetään molempien lasten painot.
            PuuSolmu vanhempi = new PuuSolmu(' ', vasen.getPaino() + oikea.getPaino(), vasen, oikea);
            
            //Laitetaan vanhempi jonoon
            jono.add(vanhempi);
        }
        
        return (PuuSolmu)jono.poll();
       
    }
    
    /**
     * Tehdään taulukko, missä indeksi viittaa merkkiin ja sisältö on
     * uusi koodi merkin pakkausta varten.
     * 
     * @param koodiTaulu String[] sisältää merkit indekseinä ja sisältö on uusi koodi
     * @param koodi String 
     * @param s PuuSolmu
     */
    public void teeKoodiTaulu(String[] koodiTaulu, String koodi, PuuSolmu s) {
        //Käydään puu läpi ja lisätään merkkijonoon vasemmalle mentäessä 0 ja
        //oikealle 1. Kun tullaan lehteen, lisätään koodi kooditauluun.
        if(s.ollaankoLehdessa())  {
            koodiTaulu[s.getMerkki()] = koodi;
        }
        else    {
            teeKoodiTaulu(koodiTaulu, koodi + "0", s.getVasen());
            teeKoodiTaulu(koodiTaulu, koodi + "1", s.getOikea());
        }
    }
    
}
