
package tietorakenteetTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietorakenteet.Pino;
import tietorakenteet.PuuSolmu;

/**
 * Luokan tietorakenteet.Pino testausta.
 * 
 * @author Katri Roos
 * @since 10.4.2013
 */
public class PinoTest {
    private Pino pino;
    
    public PinoTest() {
        this.pino = new Pino();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pino = new Pino();
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Lisätään pinoon kymmenen puun solmua
     */
    private void lisaaPinoonKymmenen()  {
        for (int i = 0; i < 10; i++) {
             pino.push(i);
        }
    }
    
    /**
     * Testataan että tyhä pino palauttaa true.
     */
     @Test
     public void onkoTyhjaTyhja() {
         assertEquals(true, pino.onkoTyhja());
     }
     
     /**
      * Lisätään pinoon tavaraa ja katsotaan, että onkoTyhja() palauttaa false.
      * Testaa samalla myös push() metodia.
      */
     @Test
     public void eihanOleTyhja() {
         lisaaPinoonKymmenen();
         assertEquals(false, pino.onkoTyhja());
     }
     
     /**
      * Laitetaan pinoon 10 tavaraa ja katsotaan, että pinonKoko() palauttaa 10.
      */
     @Test
     public void montaTavaraa() {
         lisaaPinoonKymmenen();
         assertEquals(10, pino.pinonKoko());
     }
     
     /**
      * Testataan Pop() metodia. Laitetaan taas pinoon kamaa ja otetaan
      * viisi pois ja katsotaan, että palauttaa pituudeksi viisi.
      */
     @Test
     public void poppausTesti() {
         lisaaPinoonKymmenen();
         for (int i = 0; i < 5; i++) {
             pino.pop();
         }
         assertEquals(5, pino.pinonKoko());
     }
     
     /**
      * Testataan push() metodia, kun uusi lisättävä on 101 olio.
      */
     @Test
     public void pinonKasvatus() {
         for (int i = 0; i < 101; i++) {
             pino.push(i);
         }
         assertEquals(101, pino.pinonKoko());
     }
     
     /**
      * Testataan pop() metodia, kun poppaus aloitetaan 101 ja popataan 10
      * asti
      */
     @Test
     public void pinonPienennys() {
         for (int i = 0; i <= 101; i++) {
             pino.push(i);
         }
         for (int i = 101; i > 9; i--) {
             pino.pop();
         }
         assertEquals(10, pino.pinonKoko());
     }
}
