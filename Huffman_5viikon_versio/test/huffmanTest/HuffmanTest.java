package huffmanTest;

import apulaiset.Io;
import apulaiset.MuutApulaiset;
import huffman.Pakkaa;
import huffman.Pura;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietorakenteet.*;

/**
 * Luokan huffman.Huffman testausta.
 * 
 * @author Katri Roos
 * @since 5.4.2013
 */
public class HuffmanTest {
    private int merkkeja;
    private Io io;
    private Pakkaa p;
    private Pura pu;
    private MuutApulaiset apu;
        
    public HuffmanTest() {
        merkkeja = 256;
        io = new Io();
        p = new Pakkaa();
        pu = new Pura();
        apu = new MuutApulaiset();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**********************************************************************/
    
    /**
     * Metodin esiintymisTiheys(String teksti) testi, missä
     * tyhjä String parametrinä.
     */
     @Test
     public void esiintymisTieheysTyhja() {
         //Laskuri laskee, monen merkin esiintyminen taulukkoon on laskettu
         int laskuri = 0;
         int[] tiheys = apu.esiintymisTiheys("");
         for (int i = 0; i < tiheys.length; i++) {
             if(tiheys[i] != 0) {
                 System.out.println(tiheys[i] + "  " + (char)i);
                 laskuri++;
             }
         }   
         assertEquals(0, laskuri);
     }
     
     /**
     * Metodin esiintymisTiheys(String teksti) testi.
     */
     @Test
     public void esiintymisTieheys() {
         int[] tiheys = apu.esiintymisTiheys("saimaaspa");
         //Tulostetaan tiheydet.
         for (int i = 0; i < tiheys.length; i++) {
             if(tiheys[i] != 0) {
                 System.out.println(tiheys[i] + "  " + (char)i);
             }
         }   
         assertEquals(4, tiheys['a']);
         assertEquals(1, tiheys['i']);
         assertEquals(1, tiheys['m']);
         assertEquals(1, tiheys['p']);
         assertEquals(2, tiheys['s']);
     }
     
     /**********************************************************************/
     
     /**
      * Metodin lueTiedosto(File tiedosto) testi kun tiedostoa ei ole
      */
     @Test
     public void lueTiedostoEiole() throws IOException {
         //miten testataan?
     }
     
     /**
      * Metodin lueTiedosto(File tiedosto) testi. Parametrinä tiedosto haa.txt,
      * missä myös ääkkösiä.
      */
     @Test
     public void lueTiedostoSkandit() throws IOException {
         String s = io.lueTiedosto(new File("tiedostot/haa.txt"));
         assertEquals("hähhää.", s);
     }
     
     /**********************************************************************/
     
     /**
      * Metodin teePuu(int[] esiintymisTiheys) testi. Parametrinä esiintymistiheys-
      * taulu, missä a=4, i=1, m=1, p=1 ja s=2. Puun esijärjestyksen pitäisi olla
      * 9a52im3ps
      */
     @Test
     public void esiintymisTiheysTesti() {
         int[] tiheys = apu.esiintymisTiheys("saimaaspa");
         PuuSolmu s = apu.teePuu(tiheys);
         //käydään läpi esijärjestys
         String esijarjestys = "";
         Pino pino = new Pino();
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
         assertEquals("9a52im3ps", esijarjestys);
     }
     
     /**********************************************************************/
     
     /**
      * Metodin pakkaa(String tiedosto) testi.
      * Käytetään taas tekstiä saimaaspa, mikä on nyt tekstitiedostossa saimaa.txt
      */
     @Test
     public void pakkausTesti() throws IOException, FileNotFoundException, ClassNotFoundException {
         p.pakkaa("tiedostot/saimaa.txt");
         Object[] s = io.lueBinaariTiedosto(new File("tiedostot/saimaa.huffman"));
         System.out.print((String)s[1]);         
     }
     
     /**********************************************************************/
     
     /**
      * Metodin pakkaa(String tiedosto) testi.
      * Käytetään taas tekstiä saimaaspa, mikä on nyt tekstitiedostossa saimaa.txt
      */
     @Test
     public void purkuTesti() throws IOException, FileNotFoundException, ClassNotFoundException {
         p.pakkaa("tiedostot/saimaa.txt");
         pu.pura("tiedostot/saimaa.huffman");
      
         assertEquals("saimaaspa", io.lueTiedosto(new File("tiedostot/saimaa.txt")));
     }
}
