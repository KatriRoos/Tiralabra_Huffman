
package tietorakenteet;

import java.io.Serializable;

/**
 * Luokka toteuttaa binääripuun, mitä käytetään Huffmanin puun luomiseen.
 * 
 * @implements Serializable
 * @author Katri Roos
 * @since 10.4.2013
 */
public class HuffmaninPuu implements Serializable {
    private PuuSolmu juuri;
    
    /**
     * Konstruktorissa alustetaan puulle juuri.
     * 
     * @param juuri PuuSolmu Huffmanin puun juuri
     */
    public HuffmaninPuu(PuuSolmu juuri)   {
        this.juuri = juuri;
    }
    
    /**
     * Metodi palauttaa Huffmanin puun juuren.
     * 
     * @return PuuSolmu palauttaa puun juuren.
     */
    public PuuSolmu getJuuri()   {
        return this.juuri;
    }
        
    
}
