
import apulaiset.Io;
import huffman.Pakkaa;
import huffman.Pura;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Huffmanin main-luokka.
 * 
 * @author Katri Roos
 * @since 12.4.2013
 */
public class Main {
      /**
     * Testausta...
     * @param args 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Io io = new Io();
        Pakkaa p = new Pakkaa();
        Pura pu = new Pura();
        System.out.print(io.lueTiedosto(new File("tiedostot/saimaa.txt")));
        System.out.println("");
        p.pakkaa("tiedostot/saimaa.txt");
        
        pu.pura("tiedostot/saimaa.huffman", p.juuri);    
        System.out.print(io.lueTiedosto(new File("tiedostot/saimaa.txt")));
    }
}
