
package tietorakenteet;

import java.io.Serializable;

/**
 * Luokka Huffmanin puun solmuja varten.
 * Luokasta löytyy kentät solmun painolle tai merkille jos
 * solmusta tulee lehti.
 * 
 * @implements Serializable
 * @author Katri Roos
 * @since 10.4.2013
 */
 /**
     * Huffmanin puun solmu, mikä sisältää tiedon solmun painosta tai merkistä
     * ja tietää oman vasemman tai oikean lapsen.
     */
    public class PuuSolmu implements Serializable, Comparable<PuuSolmu>   {
        private char merkki;
        private int paino;
        private PuuSolmu vasen, oikea;
        
        /**
         * @param merkki char 
         * @param paino int 
         * @param vasen PuuSolmu 
         * @param oikea Puusolmu  
         */
        public PuuSolmu(char merkki, int paino, PuuSolmu vasen, PuuSolmu oikea)   {
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
            return this.merkki;
        }

        /**
         * @return int paino
         */
        public int getPaino() {
            return this.paino;
        }

        /**
         * @return Puusolmu vasen
         */
        public PuuSolmu getVasen() {
            return this.vasen;
        }

        /**
         * @return PuuSolmu oikea
         */
        public PuuSolmu getOikea() {
            return this.oikea;
        }

    @Override
    public int compareTo(PuuSolmu t) {
        if(this.paino < t.paino)    {
            return -1;
        }
        else    {
            return 1;
        }
    }
    }
