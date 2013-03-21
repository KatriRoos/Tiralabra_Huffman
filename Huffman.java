
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    public void pakkaa()     {
        //Käytetään mallitekstiä luokan lopussa ja luodaan
        //taulukko merkkien esiintymistiheyksille.
        int[] merkkienTiheys = esiintymisTiheys(teksti());
        
        //Tehdään Huffman puu
        PuuSolmu juuri = teePuu(merkkienTiheys);
        
        //Luodaan taulukko koodeille
        String[] koodiTaulu = new String[merkkeja];
        teeKoodiTaulu(koodiTaulu, "", juuri);
        
        String pakattuTeksti = "";
        //Pakataan teksti
        for (int i = 0; i < teksti().length(); i++) {
            pakattuTeksti = pakattuTeksti + koodiTaulu[teksti().charAt(i)];
        }
        //testataan
        System.out.println(pakattuTeksti);
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
    public InputStream lueTiedostoTavuina() throws FileNotFoundException   {
        InputStream input = new BufferedInputStream(
                      new FileInputStream("d:\\jakalat.txt"),
                      8 * 1024
        );
        return input;
    }
    
    /**
     * Huffmanin puun solmu, mikä sisältää tiedon solmun painosta tai merkistä
     * ja tietää oman vasemman tai oikean lapsen.
     */
    private class PuuSolmu  {
        private char merkki;
        private int paino;
        private PuuSolmu vasen, oikea;
        
        /**
         * @param char merkki
         * @param int paino
         * @param PuuSolmu vasen
         * @param Puusolmu oikea 
         */
        public PuuSolmu(char merkki, int paino, PuuSolmu vasen, PuuSolmu oikea)   {
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
        public boolean ollaankoLehdessa(PuuSolmu s)   {
            if(s.getOikea() == null && s.getVasen() == null)    {
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
        public PuuSolmu getVasen() {
            return vasen;
        }

        /**
         * @return PuuSolmu oikea
         */
        public PuuSolmu getOikea() {
            return oikea;
        }
    }
    
    /**
     * Luokka puun solmujen vertailuun.
     */
    public class PuuSolmuComparator implements Comparator<PuuSolmu>   {
    @Override
        public int compare(PuuSolmu t, PuuSolmu t1)
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
    public PuuSolmu teePuu(int[] esiintymisTiheys)    {
        //Jono, mihin solmut laitetaan järjestyksessä.
        Comparator<PuuSolmu> comparator = new PuuSolmuComparator();
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
     * 
     * @param String[] koodiTaulu
     * @param String koodi
     * @param PuuSolmu s 
     */
    public void teeKoodiTaulu(String[] koodiTaulu, String koodi, PuuSolmu s) {
        //Käydään puu läpi ja lisätään merkkijonoon vasemmalle mentäessä 0 ja
        //oikealle 1. Kun tullaan lehteen, lisätään koodi kooditauluun.
        if(!s.ollaankoLehdessa(s))  {
            teeKoodiTaulu(koodiTaulu, koodi = koodi + "0", s.getVasen());
            teeKoodiTaulu(koodiTaulu, koodi = koodi + "1", s.getOikea());
        }
        else    {
            koodiTaulu[s.getMerkki()] = koodi;
        }
    }
    
    /**
     * Testataan ensin tällä tekstillä.
     * 
     * @return String Pakatattava teksti
     */
    public String teksti()  {
        return "Jäkälillä on erilaistumaton sekovarsi, joka koostuu sieniosakkaasta ja yhteyttävästä levä- tai syanobakteeriosakkaasta."
                + "Joskus sekovarren muodostavat kaikki kolme osakasta. "
                + "Kasvutapansa perusteella jäkälät jaetaan rupi-, lehti- ja pensasjäkäliin. Rupijäkälät kiinnittyvät kasvualustaansa todella tiukasti,"
                + "ja ne kasvavat yleensä puiden tai kivien pinnalla, joskus jopa kasvien tai kivien sisällä. Rupijäkälät ovat yleisin jäkälätyyppi,"
                + "niiden sekovarren rakenne voi olla hyvin vaihteleva. Lehtijäkälät ovat lehtimäisiä, ja niiden sekovarsi on usein jakautunut liuskoiksi."
                + "Pensasjäkälien liuskat ja haarat ovat puolestaan litteitä tai liereitä. Ne kasvavat joko riippuvasti, kuten tummalupot ja naavat, tai pystyssä,"
                + "kuten hirvenjäkälät tai tinajäkälät.Viherlevät ja syanobakteerit sisältävät viherhiukkasia, joiden avulla ne tuottavat sokereita itselleen ja"
                + "sieniosakkaalle auringonvalosta ja hiilidioksidista. Sieni puolestaan suojelee leviä kuivumiselta ja kuljettaa niille kasvualustastaan "
                + "hankkimia mineraaleja.Jäkälät ovat siten autotrofisia eli omavaraisia ja pystyvät kasvamaan ympäristössä, "
                + "jossa ei ole lainkaan muita orgaanisia aineksia. Tältä osin jäkälät muistuttavat paljon kasveja. "
                + "Mikäli jäkälä sisältää myös syanobakteereja, saa se bakteereilta typpeä, jota ne pystyvät sitomaan ilmasta. "
                + "Jäkälien yhteyttävät fotobionttisolut sijaitsevat usein tasaisesti sekovarressa. Useimpien lehti- ja pensasjäkälien sekä monien rupijäkälien sekovarsi "
                + "on kerrostunut ja muodostunut ylä- ja alapinnan kuorikerroksesta ja niiden välissä olevasta mallosta, jonka yläosassa on fotobionttikerros. "
                + "Tiivis kuorikerros on muodostunut tiukasti pakkautuneista sienirihmoista, ja se suojaa fotobionttisoluja kulumiselta, kuivumiselta ja "
                + "liialliselta ultraviolettisäteilyltä. Alapinnan kuorikerros puuttuu esimerkiksi siimes- ja nahkajäkäliltä. "
                + "Malto on löyhää sienirihmastoa, mutta naavoilla se on tiukasti yhteenkasvaneiden rihmojen muodostama kuminauhamainen keskusjänne.";
    }
    
    /**
     * Testausta...
     * @param args 
     */
    public static void main(String[] args) {
        Huffman h = new Huffman();
        h.pakkaa();
        
    }
    
}
