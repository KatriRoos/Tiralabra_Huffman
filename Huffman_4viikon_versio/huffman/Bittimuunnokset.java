/*
 * Luokka sisältää metodit char[] -taulukon joka sisältää ykkösiä ja
 * nollia, muutoksen byte[] -taulkoksi ja toisinpäin
 */
package huffman;

/**
 *
 * @author Katri Roos
 */
public class Bittimuunnokset {
    
  /** Maski tavun 1. bitille */
  private final int BIT_0 = 1;

  /** Maski tavun 2. bitille */
  private final int BIT_1 = 0x02;

  /** Maski tavun 3. bitille */
  private final int BIT_2 = 0x04;

  /** Maski tavun 4. bitille */
  private final int BIT_3 = 0x08;

  /** Maski tavun 5. bitille */
  private final int BIT_4 = 0x10;

  /** Maski tavun 6. bitille */
  private final int BIT_5 = 0x20;

  /** Maski tavun 7. bitille */
  private final int BIT_6 = 0x40;

  /** Maski tavun 8. bitille */
  private final int BIT_7 = 0x80;

  private final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };
  

  public byte[] tavuiksi(char[] merkit) {

    // get length/8 times bytes with 3 bit shifts to the right of the length
    byte[] tavut = new byte[merkit.length >> 3];
    
    /*
     * We decr index jj by 8 as we go along to not recompute indices using
     * multiplication every time inside the loop.
     */
    for (int i = 0, j = merkit.length - 1; i < tavut.length; i++, j -= 8) {
      for (int bits = 0; bits < BITS.length; bits++) {
        if (merkit[j - bits] == '1') {
          tavut[i] |= BITS[bits];
        }
      }
    }
  
    return tavut;
  }

  public char[] merkeiksi(byte[] tavut) {
    
    // get 8 times the bytes with 3 bit shifts to the left of the length
    char[] merkit = new char[tavut.length << 3];
    /*
     * We decr index jj by 8 as we go along to not recompute indices using
     * multiplication every time inside the loop.
     */
    for (int i = 0, j = merkit.length - 1; i < tavut.length; i++, j -= 8) {
      for (int bits = 0; bits < BITS.length; bits++) {
        if ((tavut[i] & BITS[bits]) == 0) {
          merkit[j - bits] = '0';
        } else {
          merkit[j - bits] = '1';
        }
      }
    }
    return merkit;
  }
}
