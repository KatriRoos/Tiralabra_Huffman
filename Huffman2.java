
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

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
    
    /**
     * Tiedoston pakkaaminen.
     */
    public void pakkaa() throws FileNotFoundException, IOException     {
        //Luetaan tekstiä tiedostosta
        InputStream tiedosto = lueTiedostoTavuina();
        StringWriter sw = new StringWriter();
        char[] teksti = new char[1024];
        int luettu = 0;
 
                while((luettu = tiedosto.read()) != -1) {
                    sw.write(teksti, 0, luettu);
                }
        
        //Käytetään mallitekstiä luokan lopussa ja luodaan
        //taulukko merkkien esiintymistiheyksille.
        int[] merkkienTiheys = esiintymisTiheys(teksti());
        
        //Luodaan taulukko merkkien esiintymistiheyksille
//        int s = tiedosto.read();
//        System.out.println(s);
//        int[] merkkienTiheys = esiintymisTiheys(teksti.toString());
        
        //TESTIÄ
        for (int i = 0; i < merkkienTiheys.length; i++) {
            if(merkkienTiheys[i] != 0)
                System.out.println(merkkienTiheys[i] + "  " + (char)i);
        }
        System.out.println("");
        
        //Tehdään Huffman puu
        Huffman.PuuSolmu juuri = teePuu(merkkienTiheys);
        
        //Luodaan taulukko koodeille
        String[] koodiTaulu = new String[merkkeja];
        teeKoodiTaulu(koodiTaulu, "", juuri);
        
        //TESTIÄ
        for (int i = 0; i < koodiTaulu.length; i++) {
            if(koodiTaulu[i] != null)
                System.out.println(koodiTaulu[i] + "  " + (char)i);
        }
        System.out.println("");
        
        String pakattuTeksti = "";
        //Pakataan teksti
        for (int i = 0; i < teksti().length(); i++) {
            pakattuTeksti = pakattuTeksti + koodiTaulu[teksti().charAt(i)];
        }
        
        //TESTATAAN
        System.out.println(pakattuTeksti);
        
        //TESTATAAN
        pura(pakattuTeksti, juuri);
    }
    
    /**
     * Pakatun tiedoston purkaminen.
     * 
     * @param String pakattuTeksti
     * @param PuuSolmu juuri 
     */
    public void pura(String pakattuTeksti, PuuSolmu juuri)  {
        PuuSolmu s = juuri;
        String purettu = "";
        
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
        System.out.println(purettu);
    }
       
    /**
     * 
     * @return int[] Taulukko, mikä sisältää merkkien
     * esiintymistiheyden.
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
     * Luetaan tiedosto.
     * 
     * @return InpupStream 
     * @throws FileNotFoundException 
     */
    public InputStream lueTiedostoTavuina() throws FileNotFoundException  {
        InputStream input = new BufferedInputStream(new FileInputStream("D:\\jakalat.txt"));
        return input;
    }
    
    /**
     * Huffmanin puun solmu, mikä sisältää tiedon solmun painosta tai merkistä
     * ja tietää oman vasemman tai oikean lapsen.
     */
    private class PuuSolmu  {
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
         * @param PuuSolmu s
         * @return boolean True, jos kyseessä lehti,
         * muuten false.
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
     * 
     * @param int [] esiintymisTiheys
     * @return PuuSolmu Huffmanin puun juuri
     */
    public Huffman.PuuSolmu teePuu(int[] esiintymisTiheys)    {
        //Jono, mihin solmut laitetaan järjestyksessä.
        Comparator<Huffman.PuuSolmu> comparator = new Huffman.PuuSolmuComparator();
        PriorityQueue<Huffman.PuuSolmu> jono = 
            new PriorityQueue<Huffman.PuuSolmu>(esiintymisTiheys.length/2, comparator);
       
        for (char i = 0; i < esiintymisTiheys.length; i++) {
            if(esiintymisTiheys[i] > 0) {
                jono.add(new Huffman.PuuSolmu(i, esiintymisTiheys[i], null, null));
            }
        }
        
        //Yhdistetään solmut puuksi niin, että aina kaksi pienintä yhdistetään.
        while(jono.size() > 1)  {
            Huffman.PuuSolmu vasen = jono.poll();
            
            Huffman.PuuSolmu oikea = jono.poll();
            
            //Tehdään vanhempi, mihin yhdistetään molempien lasten painot.
            Huffman.PuuSolmu vanhempi = new Huffman.PuuSolmu(' ', vasen.getPaino() + oikea.getPaino(), vasen, oikea);
            
            //Laitetaan vanhempi jonoon
            jono.add(vanhempi);
        }
        
        return jono.poll();
    }
    
    /**
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
    public String teksti()  {
        return "kissa";
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
    }
    
    /**
     * Testausta...
     * @param args 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Huffman h = new Huffman();
        h.pakkaa();
        
    }
    
}
