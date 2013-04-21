
package apulaiset;

import huffman.Bittimuunnokset;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import tietorakenteet.HuffmaninPuu;

/**
 * Huffman pakkaus/purku -ohjelman IO-toiminnot.
 * 
 * @author Katri Roos
 * @since 12.4.2013
 */
public class Io {
    /**
     * Tallennetaan pakattu tiedosto.
     * 
     * @param pakattuTeksti byte[]
     * @param puu HuffmaninPuu pakkaukseen käytetty Huffmanin puu-olio.
     * @param tiedostonimi String pakatun tiedoston nimi.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void tallennaPakattuTiedostoon(byte[] pakattuTeksti, HuffmaninPuu puu, String tiedostonimi) throws FileNotFoundException, IOException    {
        //Otetaan tiedostonimestä pääte pois ja laitetaan uusi tilalle ja
         FileOutputStream fos = new FileOutputStream(tiedostonimi.substring(0, tiedostonimi.length()-4) + ".huffman", true);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeObject(puu);  
        oos.writeObject(pakattuTeksti);
        System.out.println("kirjoitettiin: " + pakattuTeksti.length);
        
        oos.close();
//        
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tiedostonimi.substring(0, tiedostonimi.length()-4) + ".huffman", true)); 
       
        //Kirjoitetaan pakattu teksti tiedoston loppuun
//        bos.write(pakattuTeksti);   
//        bos.flush();
//        bos.close();
    }
    
    /**
     * Tallennetaan purettu tiedosto.
     * 
     * @param purettuTeksti String
     * @param tiedostonimi String puretun tiedoston nimi.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void tallennaPurettuTiedostoon(String purettuTeksti, String tiedostonimi) throws FileNotFoundException, IOException    {
        //Otetaan tiedoston nimestä ja .huffman pääte pois ja laitetaan uusi tilalle.
        BufferedWriter writer = new BufferedWriter( new FileWriter((tiedostonimi.substring(0, tiedostonimi.length()- 8)) + ".txt"));
        writer.write( purettuTeksti);
        writer.close();
    }
    
     /**
     * Luetaan tekstitiedosto String-merkkiseksi.
     * Jos koodaan kotikoneella, pitää käyttää "ISO-8859-1" koodausta,
     * että näkyy ääkköset, koulussa taas "UTF-8", paitsi koulun koneella
     * JUnit testeissä pitää taas olla aikaisempi...huhuh???
     * 
     * @param tiedosto File
     * @return String tiedoston sisältö
     * @throws IOException 
     */
    public String lueTiedosto(File tiedosto) throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(tiedosto));
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei löydy.");
            System.exit(0);
        } 
            Scanner s = new Scanner(input, "ISO-8859-1");
            StringBuilder stb = new StringBuilder();
        
            while (s.hasNextLine()){
                stb.append(s.nextLine());
            }
            s.close();
                    
            return stb.toString();
       
    }
    
    /**
     * Luetaan pakatusta binääritiedostosta HuffmaninPuu olio ja
     * String-merkkinen data.
     *  
     * @param tiedosto File
     * @return Object[] taulukko missä on eka puu ja tokana luettu tiedosto.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Object[] lueBinaariTiedosto(File tiedosto) throws FileNotFoundException, IOException, ClassNotFoundException  {
        //Taulukko, missä on eka puun esijärjestys ja tokana luettu tiedosto.
        Object[] luettuTiedosto = new Object[2];    
        Bittimuunnokset b = new Bittimuunnokset();
        byte[] tavut = new byte[(int)tiedosto.length()];
       
        ObjectInputStream ois = new ObjectInputStream((new FileInputStream(tiedosto)));
        
        luettuTiedosto[0] = ois.readObject();
        Object o = new Object();
        try   {
          o = ois.readObject();
        }catch (EOFException ex)    {
            
        }
        
        String teksti = new String(b.merkeiksi((byte[])o));
        luettuTiedosto[1] = teksti;
//        System.out.println("teksti luettu: " + teksti);
        
        ois.close();
            
        //Luetaan pakattu data eli ykköset ja nollat Stringinä.
//        DataInputStream dis = new DataInputStream((new FileInputStream(tiedosto)));
//        byte[] tavut = new byte[(int)tiedosto.length()];
//        dis.readFully(tavut);
//        dis.close();
//        
//        luettuTiedosto[1] = new String(b.merkeiksi(tavut));
            
        return luettuTiedosto;
    }

}
