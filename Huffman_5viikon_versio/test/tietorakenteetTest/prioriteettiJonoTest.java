
package tietorakenteetTest;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietorakenteet.PrioriteettiJono;
import tietorakenteet.PuuSolmu;

/**
 * Testataan PrioriteettiJono-luokan toimintaa.
 * 
 * @author Katri Roos
 * @since 12.4.2013
 */
public class prioriteettiJonoTest {
    private PrioriteettiJono pj;
    
    public prioriteettiJonoTest() {
        this.pj = new PrioriteettiJono();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pj = new PrioriteettiJono();
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Solmujen lisäys metodi testejä varten.
     */
    private void lisataanSolmuja()   {
        for (int i = 0; i < 110; i++) {
            pj.enqueue(new PuuSolmu(' ', i, null, null));
        }
    }
    
    /**
     * Solmujen lisäys metodi testejä varten.
     * Lisätään solmuja niiden painot laskevassa järjestyksessä
     */
    private void lisataanSolmujaLaskevassa()   {
        for (int i = 100; i > 0; i--) {
            pj.enqueue(new PuuSolmu(' ', i, null, null));
        }
    }
    
    /**
     * Solmujen lisäys metodi testejä varten.
     * Lisätään solmuja random järjestyksessä.
     */
    private void lisataanRandomSolmuja()   {
        //Lisätään pienin solmu 1;
        pj.enqueue(new PuuSolmu(' ', 1, null, null));
        for (int i = 0; i > 100; i++) {
            Random r = new Random();
            int randomluku = r.nextInt(100) + 2;
            pj.enqueue(new PuuSolmu(' ', randomluku, null, null));
        }
    }
    
    /**
     * Testataan onko jono tyhjä. Pitäisi palauttaa true;
     */
     @Test
     public void onkoTyhja() {
         assertEquals(true, pj.onkoTyhja());
     }
     
     /**
     * Testataan, että tyhjän jono koko on nolla.
     */
     @Test
     public void tyhjanKoko() {
         assertEquals(0, pj.koko());
     }
     
     /**
     * Testataan jonon kokoa yhdellä solmulla.
     */
     @Test
     public void yksiSolmuJonossa() {
         pj.enqueue(new PuuSolmu('h', 0, null, null));
         assertEquals(1, pj.koko());
     }
     
     /**
     * Testataan jonon kokoa, kun poistetaan jonon ainut solmu
     */
    @Test
    public void poistetaanAinutSolmu() {
        pj.enqueue(new PuuSolmu('h', 0, null, null));
        pj.dequeue();
        assertEquals(0, pj.koko());
    }
    
    /**
     * Lisätään jonoon paljon solmuja ja katsotaan, miten jonon koon
     * lisäys toimii.
     */
    @Test
    public void lisataanSolmujaPaljon() {
        lisataanSolmuja();
        assertEquals(110, pj.koko());
    }
    
    /**
     * Lisätään jonoon paljon solmuja ja sitten poistetaan niin monta, että
     * jono pitäisi pienentyä.
     */
    @Test
    public void lisataanJaPoistetaanSolmujaPaljon() {
        lisataanSolmuja();
        System.out.println(pj.koko());
        for (int i = 0; i < 70; i++) {
            pj.dequeue();
        }
        assertEquals(40, pj.koko());
    }
    
    /**
     * Testataan, että jonon järjestys toimii.
     */
    @Test
    public void onkoJonoJarjestyksessa() {
        lisataanSolmujaLaskevassa();
        int paino = pj.dequeue().getPaino();
        assertEquals(1, paino);
    }
    
    /**
     * Testataan, että jonon järjestys toimii kun lisätään samoja painoja
     * sisältäviä solmuja uudestaan.
     */
    @Test
    public void onkoJonoJarjestyksessaTuplat() {
        lisataanSolmujaLaskevassa();
        lisataanSolmujaLaskevassa();
        int paino = pj.dequeue().getPaino();
        assertEquals(1, paino);
    }
    
    /**
     * Testataan, että jonon järjestys toimii kun lisätään uusia solmuja
     * random painoilla.
     */
    @Test
    public void onkoJonoJarjestyksessaRandom() {
        lisataanRandomSolmuja();
        int paino = pj.dequeue().getPaino();
        assertEquals(1, paino);
    }
}
