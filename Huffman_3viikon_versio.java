
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * Tietorakenteet ja algoritmit -harjoitustyö
 * Huffman-pakkausohjelma aloitettu 17.3.2013
 * Katri Roos
 * 
 */

/**
 *
 * @author Katri Roos
 */
public class Huffman {
    
    /**
     * Merkkien määrä.
     */
    private static final int merkkeja = 256;
    private PuuSolmu juuri;
    
    /**
     * Metodi on testausta varten, koska puuta ei ole vielä tallennettu mukaan
     * tiedostoon.
     * 
     * @return PuuSolmu Huffmanin puun juuri
     */
    public PuuSolmu getJuuri()   {
        return this.juuri;
    }
    
    /**
     * Tiedoston pakkaaminen.
     * 
     * @param String pakattava tiedosto
     * @throws IOException 
     */
    public void pakkaa(String tiedosto) throws IOException     {
        //Luetaan tekstiä tiedostosta. Tulevaisuudessa tiedosto annetaan
        //ohjelmalle parametrinä.
        String teksti = lueTiedosto(new File(tiedosto));
      
        //Luodaan taulukko merkkien esiintymistiheyksille
        int[] merkkienTiheys = esiintymisTiheys(teksti);
        
        //Tehdään Huffman puu
        juuri = teePuu(merkkienTiheys);
        
        //Luodaan taulukko merkkien koodeille
        String[] koodiTaulu = new String[merkkeja];
        teeKoodiTaulu(koodiTaulu, "", juuri);
        
        //Pakataan teksti
        String pakattuTeksti = "";
        
        for (int i = 0; i < teksti.length(); i++) {
            pakattuTeksti = pakattuTeksti + koodiTaulu[teksti.charAt(i)];
        }
        
        //Muutetaan pakattu teksti String tavuiksi? tallennusta varten
        byte[] tavut = merkitTavuiksi(pakattuTeksti.toCharArray());
        
        //Tallennetaan tiedostoon. HUOM! Puu pitäisi vielä tallentaa myös
        //Parametrinä annetaan myös tiedoston nimi, mistä tässätapauksessa
        //otetaan pois D:\\ alusta ja .txt lopusta
        tallennaTiedostoon(tavut, tiedosto.substring(3, tiedosto.length()-4));
    }
    
    /**
     * Puretaan pakattu tiedosto.
     * 
     * @param String pakattuTeksti
     * @param PuuSolmu juuri, Huffmanin puun juuri
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void pura(String tiedosto, PuuSolmu juuri) throws FileNotFoundException, IOException  {
        //Tarkastetaan, päättyykö tiedostonimi .huffman
        if(tiedosto.substring(tiedosto.length()- 8, tiedosto.length()).equalsIgnoreCase(".huffman"))   {
            String pakattuTeksti = lueBinaariTiedosto(new File(tiedosto));
            PuuSolmu s = juuri;
            String purettu = "";
            //Käydään puutaläpi ja haetaan merkit muuttujaan purettu
            for (int i = 0; i < pakattuTeksti.length(); i++) {
                char bitti = pakattuTeksti.charAt(i);

                if(bitti == '1')    {
                    s = s.getOikea();
                }
                else if(bitti == '0')   {
                    s = s.getVasen();
                }
                if(s.ollaankoLehdessa())   {
                    purettu = purettu + s.getMerkki();
                    s = juuri;        
                }
            }
            
            //Tallennetaan tiedostoon
            tallennaPurettuTiedostoon(purettu, tiedosto.substring(3, tiedosto.length()- 8));
        }
        else    {
            System.out.println("Ei ole purettava tiedosto.");
            System.exit(0);
        }
    }
    
    /**
     * http://www.javacodegeeks.com/2010/11/java-best-practices-char-to-byte-and.html
     * -Sivulta löydetty koodin pätkä, minkä tarkkaa toimintaa en täysin tajua...
     * 
     * @param char[] merkit, Pakatun tekstin ykköset ja nollat char-merkkeinä
     * @return byte[] merkit tavuina?
     */
    public byte[] merkitTavuiksi(char[] merkit)    {
        byte[] b = new byte[merkit.length << 1];
	for(int i = 0; i < merkit.length; i++) {
            int bpos = i << 1;
            b[bpos] = (byte) ((merkit[i]&0xFF00)>>8);
            b[bpos + 1] = (byte) (merkit[i]&0x00FF);
	}
	return b;
    }
    
    /**
     * Tallennetaan pakattu tiedosto.
     * 
     * @param byte[] pakattuTeksti
     * @param String pakatun tiedoston nimi
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void tallennaTiedostoon(byte[] pakattuTeksti, String tiedostonnimi) throws FileNotFoundException, IOException    {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\" + tiedostonnimi + ".huffman"));
        bos.write(pakattuTeksti);
        bos.flush();
        bos.close();
    }
    
    /**
     * Tallennetaan purettu tiedosto.
     * 
     * @param String pakattuTeksti
     * @param String pakatun tiedoston nimi
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void tallennaPurettuTiedostoon(String pakattuTeksti, String tiedostonnimi) throws FileNotFoundException, IOException    {
        BufferedWriter writer = new BufferedWriter( new FileWriter("D:\\" + tiedostonnimi + ".txt"));
        writer.write( pakattuTeksti);
        writer.close();
    }
       
    /**
     * Pakattavan tekstin merkkien esiintymistiheyksien selvittäminen.
     * 
     * @param String teksti
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
     * Luetaan tekstitiedosto String-merkkiseksi.
     * 
     * @param File tiedosto, Luettava tiedosto
     * @return String tiedoston sisältö
     * @throws IOException 
     */
    public String lueTiedosto(File tiedosto) throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(tiedosto));
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei löydy.");
            System.exit(0);
        } 
            Scanner s = new Scanner(input, "ISO-8859-1");
            StringBuilder stb = new StringBuilder();
            while (s.hasNextLine()){
                stb.append(s.nextLine());
            }
            s.close();
            return stb.toString();
       
    }
    
    /**
     * Luetaan pakattu binääritiedosto String-merkkiseksi.
     * Metodin loppuosa on myös:
     * http://www.javacodegeeks.com/2010/11/java-best-practices-char-to-byte-and.html
     * -Sivulta löydetty koodin pätkä, minkä tarkkaa toimintaa en täysin tajua...
     * 
     * @param File tiedosto, Pakattu tiedosto
     * @return String merkkijono ykkösiä ja nollia
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String lueBinaariTiedosto(File tiedosto) throws FileNotFoundException, IOException  {
        byte[] tavut = new byte[(int)tiedosto.length()];
        DataInputStream dis = new DataInputStream((new FileInputStream(tiedosto)));
        dis.readFully(tavut);
        dis.close();
        
        char[] buffer = new char[tavut.length >> 1];
	 for(int i = 0; i < buffer.length; i++) {
	  int bpos = i << 1;
	  char c = (char)(((tavut[bpos]&0x00FF)<<8) + (tavut[bpos+1]&0x00FF));
	  buffer[i] = c;
	 }
	 return new String(buffer);
    }
    
    /**
     * Huffmanin puun solmu, mikä sisältää tiedon solmun painosta tai merkistä
     * ja tietää oman vasemman tai oikean lapsen.
     */
    public class PuuSolmu  {
        private char merkki;
        private int paino;
        private Huffman.PuuSolmu vasen, oikea;
        
        /**
         * @param char merkki
         * @param int paino
         * @param PuuSolmu vasen
         * @param Puusolmu oikea 
         */
        public PuuSolmu(char merkki, int paino, Huffman.PuuSolmu vasen, Huffman.PuuSolmu oikea)   {
            this.merkki = merkki;
            this.paino = paino;
            this.vasen = vasen;
            this.oikea = oikea;
        }
        
        /**
         * Palauttaa true, jos ollaan lehdessä.
         * 
         * @return boolean
         */
        public boolean ollaankoLehdessa()   {
            if(this.oikea == null && this.vasen == null)    {
                return true;
            }
            return false;
        }

        /**
         * @return char merkki
         */
        public char getMerkki() {
            return merkki;
        }

        /**
         * @return int paino
         */
        public int getPaino() {
            return paino;
        }

        /**
         * @return Puusolmu vasen
         */
        public Huffman.PuuSolmu getVasen() {
            return vasen;
        }

        /**
         * @return PuuSolmu oikea
         */
        public Huffman.PuuSolmu getOikea() {
            return oikea;
        }
    }
    
    /**
     * Luokka puun solmujen vertailuun.
     */
    public class PuuSolmuComparator implements Comparator<Huffman.PuuSolmu>   {
        /**
         * @param t ensimmäinen vertailtava PuuSolmu
         * @param t1 toinen vertailtava Puusolmu
         * @return int -1, jos ensimmäisen solmun paino on pienempi,
         *             1, jos toinen solmu on pienenmpi 
         */
    @Override
        public int compare(Huffman.PuuSolmu t, Huffman.PuuSolmu t1)
        {
            if(t.paino < t1.paino)    {
                return -1;
            }
            return 1;
        }
    }
    
    /**
     * Metodi tekee Huffmanin puun esiintymistiheyksistä.
     * 
     * @param inmt[] esiintymisTiheys
     * @return PuuSolmu puunjuuri
     */
    public PuuSolmu teePuu(int[] esiintymisTiheys)    {
        //Jono, mihin solmut laitetaan järjestyksessä.
        Comparator<PuuSolmu> comparator = new Huffman.PuuSolmuComparator();
        PriorityQueue<PuuSolmu> jono = 
            new PriorityQueue<PuuSolmu>(esiintymisTiheys.length/2, comparator);
       
        for (char i = 0; i < esiintymisTiheys.length; i++) {
            if(esiintymisTiheys[i] > 0) {
                jono.add(new PuuSolmu(i, esiintymisTiheys[i], null, null));
            }
        }
        
        //Yhdistetään solmut puuksi niin, että aina kaksi pienintä yhdistetään.
        while(jono.size() > 1)  {
            PuuSolmu vasen = jono.poll();
            
            PuuSolmu oikea = jono.poll();
            
            //Tehdään vanhempi, mihin yhdistetään molempien lasten painot.
            PuuSolmu vanhempi = new PuuSolmu(' ', vasen.getPaino() + oikea.getPaino(), vasen, oikea);
            
            //Laitetaan vanhempi jonoon
            jono.add(vanhempi);
        }
        
        return jono.poll();
    }
    
    /**
     * Tehdään taulukko, missä indeksi viittaa merkkiin ja sisältö on
     * uusi koodi merkin pakkausta varten.
     * 
     * @param String[] koodiTaulu
     * @param String koodi
     * @param PuuSolmu s
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
    
    /**
     * Testataan ensin tällä tekstillä.
     * 
     * @return String Pakatattava teksti
     */
//    public String teksti()  {
//        return "kissa";
        //return "Jäkälillä on erilaistumaton sekovarsi, joka koostuu sieniosakkaasta ja yhteyttävästä levä- tai syanobakteeriosakkaasta."
//                + "Joskus sekovarren muodostavat kaikki kolme osakasta. "
//                + "Kasvutapansa perusteella jäkälät jaetaan rupi-, lehti- ja pensasjäkäliin. Rupijäkälät kiinnittyvät kasvualustaansa todella tiukasti,"
//                + "ja ne kasvavat yleensä puiden tai kivien pinnalla, joskus jopa kasvien tai kivien sisällä. Rupijäkälät ovat yleisin jäkälätyyppi,"
//                + "niiden sekovarren rakenne voi olla hyvin vaihteleva. Lehtijäkälät ovat lehtimäisiä, ja niiden sekovarsi on usein jakautunut liuskoiksi."
//                + "Pensasjäkälien liuskat ja haarat ovat puolestaan litteitä tai liereitä. Ne kasvavat joko riippuvasti, kuten tummalupot ja naavat, tai pystyssä,"
//                + "kuten hirvenjäkälät tai tinajäkälät.Viherlevät ja syanobakteerit sisältävät viherhiukkasia, joiden avulla ne tuottavat sokereita itselleen ja"
//                + "sieniosakkaalle auringonvalosta ja hiilidioksidista. Sieni puolestaan suojelee leviä kuivumiselta ja kuljettaa niille kasvualustastaan "
//                + "hankkimia mineraaleja.Jäkälät ovat siten autotrofisia eli omavaraisia ja pystyvät kasvamaan ympäristössä, "
//                + "jossa ei ole lainkaan muita orgaanisia aineksia. Tältä osin jäkälät muistuttavat paljon kasveja. "
//                + "Mikäli jäkälä sisältää myös syanobakteereja, saa se bakteereilta typpeä, jota ne pystyvät sitomaan ilmasta. "
//                + "Jäkälien yhteyttävät fotobionttisolut sijaitsevat usein tasaisesti sekovarressa. Useimpien lehti- ja pensasjäkälien sekä monien rupijäkälien sekovarsi "
//                + "on kerrostunut ja muodostunut ylä- ja alapinnan kuorikerroksesta ja niiden välissä olevasta mallosta, jonka yläosassa on fotobionttikerros. "
//                + "Tiivis kuorikerros on muodostunut tiukasti pakkautuneista sienirihmoista, ja se suojaa fotobionttisoluja kulumiselta, kuivumiselta ja "
//                + "liialliselta ultraviolettisäteilyltä. Alapinnan kuorikerros puuttuu esimerkiksi siimes- ja nahkajäkäliltä. "
//                + "Malto on löyhää sienirihmastoa, mutta naavoilla se on tiukasti yhteenkasvaneiden rihmojen muodostama kuminauhamainen keskusjänne.";
//    }
    
    /**
     * Testausta...
     * @param args 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Huffman h = new Huffman();
        h.pakkaa("D:\\jakalat.txt");
        //h.pura(h.lueTiedosto(new File("D:\\jakalat.huffman")), juuri);        
    }
    
}
