package NumberRecognize;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.*;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DumpLayerOuts {
  private PrintStream ps;

  public DumpLayerOuts(String File)
  {
    try {
     OutputStream os = new  FileOutputStream(File) ;
     this.ps=new PrintStream(os);
   }
    catch (FileNotFoundException ex)
    {
      System.out.print("Не могу слить в файл, его нет!");
      System.exit(2);
    }
  }

  public void Dump(double [][][] o)  {
    for (int i=0;i<o.length;i++)
    {
        ps.print("Плоскость номер "+i+"\n");

        for (int k=0;k<o[0].length;k++)
        {
          for (int k1=0;k1<o[0][0].length;k1++)
          {
            ps.print(o[i][k][k1]+" ");

          }
          ps.print('\n');
        }
     }

    }

   public void CloseFile(){
     ps.close();
   }


}
