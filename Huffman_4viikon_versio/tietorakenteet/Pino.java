
package tietorakenteet;

/**
 * Luokka toteuttaa pinon first in - last out periaatteella.
 * 
 * @author Katri Roos
 * @since 10.4.2013
 */
public class Pino {
    private Object[] pino;
    private int huippu;
    
    /**
     * Konstruktori luo aluksi 100 alkion pinon, mitä kasvatetaan
     * tarvittaessa.
     * 
     * Pinon huippu on aluksi 0.
     */
    public Pino()   {
        this.pino = new Object[100];
        this.huippu = 0;
    }
    
    /**
     * Metodi lisää pinoon olion.
     * 
     * @param o Object Olio, mikä pinoon laitetaan.
     */
    public void push(Object o)   {
        //Jos pino käy pieneksi, tuplataan koko.
        if(this.huippu == this.pino.length-1)   {
            Object[] uusiPino = new Object[this.huippu * 2];
            for (int i = 0; i < pino.length; i++) {
                uusiPino[i] = pino[i];
            }
            this.pino = uusiPino;
        }
        pino[huippu] = o;    
        this.huippu++;
    }
    
    /**
     * Metodi poistaa pinon ylimmän olion.
     * 
     * @return Object pinosta otettava olio.
     */
    public Object pop() {
        //Jos pino on tyhjä, heitetään poikkeus.
        if(this.huippu == 0)    {
            throw new RuntimeException();
        }
        //Jos pinosta on 2/3 tyhjana, sitä pienennetään puolella.
        else if(this.huippu > 100 && this.huippu < this.pino.length/4)   {
            Object[] uusiPino = new Object[this.pino.length/2];
            for (int i = 0; i < this.pino.length; i++) {
                uusiPino[i] = this.pino[i];
            }
            this.pino = uusiPino;
        }
        //Pienennetään huippua ja palautetaan olio.
        Object o = this.pino[huippu-1];
        this.huippu--;
        return o;
    }
    
    /**
     * Metodi kertoo onko pino tyhjä.
     * 
     * @return boolean true jos pino on tyhjä, muuten false;
     */
    public boolean onkoTyhja()  {
        if(this.huippu == 0)    {
            return true;
        }
        else    {
            return false;
        }
    }
    
    /**
     * Pinossa olevien tavaroiden määrä.
     * 
     * @return int pinon koko.
     */
    public int pinonKoko()  {
        return this.huippu;
    }
}
