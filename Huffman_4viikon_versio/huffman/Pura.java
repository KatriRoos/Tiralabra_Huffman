
package huffman;

import apulaiset.Io;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import tietorakenteet.PuuSolmu;

/**
 * Huffman pakkaus/purkamis -ohjelman tekstitiedosto purkamistoimet.
 * 
 * @author Katri Roos
 * @since 12.4.2013
 */
public class Pura {
    private Io io;
    
    /**
     * Otetaan ohjelman IO-toimminnot käyttöön.
     */
    public Pura()   {
        io = new Io();
    }
    
      /**
     * Puretaan pakattu tiedosto.
     * 
     * @param tiedosto String
     * @param juuri PuuSolmu VÄLIAIKAINEN KUNNES SAADAAN PUU TALLENNETTUA!!
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void pura(String tiedosto, PuuSolmu juuri) throws FileNotFoundException, IOException  {
        //Tarkastetaan, päättyykö tiedostonimi .huffman
        if(tiedosto.substring(tiedosto.length()- 8, tiedosto.length()).equalsIgnoreCase(".huffman"))   {
            //Haetaan puu ja data
            String[] pakattuTeksti = io.lueBinaariTiedosto(new File(tiedosto));
            String esijarjestys = pakattuTeksti[0];
          
//            HuffmaninPuu hp = (HuffmaninPuu)pakattuTeksti[0];
            PuuSolmu s = juuri;//hp.getJuuri();
            String purettu = "";
            String pakattu = (String)pakattuTeksti[1];
            
            //Käydään puuta läpi ja haetaan merkit muuttujaan purettu
            for (int i = 0; i < pakattu.length(); i++) {
                char bitti = pakattu.charAt(i);

                if(bitti == '1')    {
                    s = s.getOikea();
                }
                else if(bitti == '0')   {
                    s = s.getVasen();
                }
                if(s.ollaankoLehdessa())   {
                    purettu = purettu + s.getMerkki();
                    //Mennään takaisin juureen.
                    s = juuri;     
                }
            }
            
            //Tallennetaan tiedostoon
            io.tallennaPurettuTiedostoon(purettu, tiedosto);
        }
        else    {
            System.out.println("Ei ole purettava tiedosto.");
            System.exit(0);
        }  
    }
    
}
