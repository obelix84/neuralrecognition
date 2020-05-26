package NumberRecognize;

import java.awt.image.DataBuffer;

public class DataBMP24bitGS
    extends DataBuffer {
  private int[][] iArray;

  /**
   * Returns the requested data array element from the specified bank as an
   * integer.
   *
   * @param bank the specified bank
   * @param i the index of the requested data array element
   * @return the data array element at the specified index from the specified
   *   bank at the specified index.
   * @todo Implement this java.awt.image.DataBuffer method
   */
  public int getElem(int bank, int i) {
    return iArray[bank][i];
  }

  /**
   * Sets the requested data array element in the specified bank from the given
   * integer.
   *
   * @param bank the specified bank
   * @param i the specified index into the data array
   * @param val the data to set the element in the specified bank at the
   *   specified index in the data array
   * @todo Implement this java.awt.image.DataBuffer method
   */
  public void setElem(int bank, int i, int val) {
    iArray[bank][i]=val;
  }

  DataBMP24bitGS(int dataType, int size,int numbanks){
        super(dataType, size,numbanks);
        iArray=new int[numbanks][size];
  }
}
