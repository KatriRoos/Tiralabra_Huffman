
package tietorakenteet;

import tietorakenteet.HuffmaninPuu;

/**
 * First in - First out jono, mikä järjestää Huffmanin puun solmut nousevaan
 * järjestykseen. Jono on totutettu minimikekona.
 * 
 * @author Katri Roos
 * @since 10.4.2013
 */
public class PrioriteettiJono {
    private PuuSolmu[] minKeko;
    private int koko;
    
    /**
     * Prioriteettijonon koko on aluksi 100.
     */
    public PrioriteettiJono()   {
        this.minKeko = new PuuSolmu[100];
        this.koko = 0;
    }
    
    /**
     * Palauttaa jonon pituuden.
     * 
     * @return int prioriteettijonon koko.
     */
    public int koko()   {
        return koko;
    }
    
    /**
     * Onko prioriteettijono tyhjä.
     * 
     * @return boolean true jos jono on tyhjä, muuten false.
     */
    public boolean onkoTyhja()  {
        if(koko == 0)   {
            return true;
        }
        else    {
            return false;
        }
    }
 
    /**
     * Palauttaa arvoltaan pienimmän olion,
     * mutta ei poista sitä.
     * 
     * @return Object pienin
     */
    public Object peek(){
        return minKeko[0];
    }
 
    /**
     * Metodi lisää solmun arvoltaan oikeaan paikaan jonoon.
     * @param s PuuSolmu lisättävä Huffmanin puun solmu.
     */
    public void enqueue(PuuSolmu s)  {
        //Jos ollaan keon vikassa paikassa, tuplataan keon koko.
        if(koko == minKeko.length-1){
            PuuSolmu[] isompiKeko = new PuuSolmu[minKeko.length * 2];
            for (int i = 0; i < minKeko.length; i++) {
                isompiKeko[i] = minKeko[i];
            }
            minKeko = isompiKeko;
        }
        minKeko[koko] = s;
        koko++;
        //Tehdään heapify-operaatio keossa ylöspäin.
        heapifyUp(koko - 1);
    }
 
    /**
     * Palauttaa ja poistaa prioriteettijonon pienimmän Huffmanin puun solmun.
     * 
     * @return PuuSolmu pienin solmu.
     */
    public PuuSolmu dequeue()    {
        //Jos jono on tyhjä.
        if(onkoTyhja()) {
            throw new RuntimeException();
        }
        //Jos jonossa on tavaraa alle neljäs osa, sen koko pienennetään puoleen.
        else if(this.koko > 50 && this.koko < minKeko.length/4)   {
            PuuSolmu[] pienempiKeko = new PuuSolmu[minKeko.length / 2];
            for (int i = 0; i < minKeko.length; i++) {
                pienempiKeko[i] = minKeko[i];
            }
            minKeko = pienempiKeko;
        }
        //Otetaan pienin ja siirretään viimeinen ekaksi.
        PuuSolmu pienin = minKeko[0];
        koko--;
        minKeko[0] = minKeko[koko];
        minKeko[koko] = null;
        //Tehdään heapify keossa alaspäin.
        heapifyDown(0);
        return pienin;
    }
 
    /**
     * Minimikeko operaatio, millä varmistetaan kekoehto uutta solmua
     * lisättäessä.
     * 
     * @param solmu int minimikeon indeksi, mistä lähdetään ylöspäin
     *              tarkistamaan kekoehtoa.
     */
    private void heapifyUp(int solmu) {
        int vanhempi = (solmu - 1) / 2;
        //Jos ollaan juuressa, lopetetaan.
        if(solmu < 0) {
            return;
        }
        //Jos solmun paino on pienempi kuin vanhemman paino
        if(minKeko[solmu].getPaino() < minKeko[vanhempi].getPaino())  {
            //Vaihdetaan paikkaa vanhemman kanssa.
            PuuSolmu valiaikainen = minKeko[vanhempi];
            minKeko[vanhempi] = minKeko[solmu];
            minKeko[solmu] = valiaikainen;
            solmu = vanhempi;
            //Kutsutaan uudelleen vanhemmalle.
            heapifyUp(solmu);
        }
    }
 
    /**
     * Minimikeko operaatio, millä varmistetaan kekoehto kun pienin solmu
     * poistetaan.
     * 
     * @param solmu int minimikeon indeksi, mistä lähdetään alaspäin
     *              tarkistamaan kekoehtoa.
     */
    private void heapifyDown(int solmu)   {
        int vasen = 2 * solmu + 1;
        int oikea = vasen + 1;
 
        //Jos ollaan keon viimeisellä rivillä.
        if(solmu > koko / 2)    {
            return;
        }
 
        //Katsotaan kumpi lapsista on pienempi jos niitä on.
        int pienempiLapsi = 0;
 
        if(minKeko[vasen] == null && minKeko[oikea] != null)    {
            pienempiLapsi = oikea;
        }
        else if(minKeko[oikea] == null && minKeko[vasen] != null)    {
            pienempiLapsi = vasen;
        }
        else if(minKeko[vasen] == null && minKeko[oikea] == null)   {
            return;
        }
        else if(minKeko[vasen].getPaino() < minKeko[oikea].getPaino())   {
            pienempiLapsi = vasen;
        }
        else    {
            pienempiLapsi = oikea;
        }
 
        //Jos pienempi lapsi on vasen ja sen paino on pienempi ku nykyisen solmun.
        if(pienempiLapsi == vasen && minKeko[vasen].getPaino() < minKeko[solmu].getPaino()){
            //Vaihdetaan solmujen paikkaa ja kutsutaan metodi vasemmalle lapselle.
            int valiaikainen = vasen;
            minKeko[vasen] = minKeko[solmu];
            minKeko[solmu] = minKeko[valiaikainen];
            heapifyDown(vasen);
        }
        //Muuten sama homma oikealle lapselle.
        else if(pienempiLapsi == oikea && minKeko[oikea].getPaino() < minKeko[solmu].getPaino()) {
            int valIaikainen = oikea;
            minKeko[oikea] = minKeko[solmu];
            minKeko[solmu] = minKeko[valIaikainen];
            heapifyDown(oikea);
        }
        else    {
            return;
        }
    }
}
