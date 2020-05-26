package NumberRecognize;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright:</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DumpToBMP {
  private String filename;
  BMP bmp;

  public DumpToBMP(String File) throws FileNotFoundException {
    bmp=new BMP(File,"new");
  }

  public void Dump(double [][][] o) throws FileNotFoundException, IOException {

    int [][] M=new int[5*o[0].length*o.length+5*o.length+10][5*o[0][0].length];
    //разделитель из 5 пикселей
    int y=0;
    for (int c=0;c<5;c++){
      for (int j=0;j<M[0].length;j++){
        M[c][j]=120;
      }
      y++;
    }
    double max=this.findMax(o);
    System.out.print(max+"\n");
    for (int i=0;i<o.length;i++){
      for(int i1=0;i1<o[0].length;i1++)
         for(int i2=0;i2<o[0][0].length;i2++)
           for (int k=0;k<5;k++){
             for (int k1=0;k1<5;k1++){
               M[y+i1*5+k][i2*5+k1]=(int)Math.round(o[i][i1][i2]/max*255);
             }
           }
      y+=5*o[0].length;
      //разделитель из 5 пикселей
      for (int c=y;c<y+5;c++){
        for (int j=0;j<M[0].length;j++){
           M[c][j]=120;
        }
      }
      y+=5;
    }
    //разделитель из 5 пикселей
    /* for (int c=y;c<y+5;c++){
       for (int j=0;j<M[0].length;j++){
          M[c][j]=120;
       }
     }*/
     bmp.SetMatrix(M);
     bmp.WriteBMP();
   }

   private double findMax(double [][][] o){
     double max=0;
     for(int i=0;i<o.length;i++)
       for(int i1=0;i1<o[0].length;i1++)
         for(int i2=0;i2<o[0][0].length;i2++){
           if (o[i][i1][i2]>max)
             max=o[i][i1][i2];
         }
     return max;
   }
}
