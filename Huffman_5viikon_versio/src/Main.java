
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
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Io io = new Io();
        Pakkaa p = new Pakkaa();
        Pura pu = new Pura();
//        System.out.print(io.lueTiedosto(new File("tiedostot/saimaa.txt")));
        System.out.println("");
        System.out.println("aloitetaan pakkaus!");
        p.pakkaa("tiedostot/koala.jpg");
        
        System.out.println("pakkaus valmis, aloitetaan purku");
        pu.pura("tiedostot/koala.huffman");    
        System.out.println("purku valmis");
//        System.out.print(io.lueTiedosto(new File("tiedostot/saimaa.txt")));
    }
}
