package NumberRecognize;


import com.sun.media.jai.codec.SeekableStream;
import java.io.InputStream;
import java.awt.image.renderable.ParameterBlock;
import java.io.FileInputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.Raster;
import java.io.FileNotFoundException;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.sun.media.jai.codec.BMPEncodeParam;
import java.awt.image.BufferedImage;

/**
 * <p>Title: </p>
 *  Описывает BMP изображение.
 * <p>Description: </p>
 *  На вход подается BMP изображение 24 бита
 *  преобразует растр в матрицу
 *  так же перезаписыванет файл или создает новый.
 *
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BMP {
  private String FileName;
  private RenderedOp op;
  private Raster RastBMP;
  private int [][] Matrix;
  private int Height;
  private int Width;
  private BufferedImage BI;


  public BMP(String f,String ToDo) throws FileNotFoundException {

    if (ToDo.equals("open"))
    {
      FileName=f;

      InputStream is = new FileInputStream(f);
      SeekableStream s = SeekableStream.wrapInputStream(is, false);
       // Create the ParameterBlock and add the SeekableStream to it.
       ParameterBlock pb = new ParameterBlock();
       pb.add(s);

       // Perform the BMP operation
       op=JAI.create("BMP", pb);
       RastBMP =op.getData();

       Height=RastBMP.getHeight();
       Width=RastBMP.getWidth();
       //System.out.print(Height+"  "+Width+"\n");
       Matrix=new int[Height][Width];
       for (int i=0;i<Height;i++)
       {
         for (int j=0;j<Width;j++)
         {
           Matrix[i][j]=RastBMP.getSample(j,i,0);
         }
       }
     }
     if (ToDo.equals("new"))
     {
       FileName=f;
     }
  }
  public void WriteBMP() throws FileNotFoundException, IOException {
    BI=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
    OutputStream os = new FileOutputStream(FileName);
    BMPEncodeParam param = new BMPEncodeParam();
    ImageEncoder enc = ImageCodec.createImageEncoder("BMP", os, param);
    for (int i=0;i<Height;i++)
    {
      for (int j=0;j<Width;j++)
      {
        int c= Matrix[i][j];
        BI.setRGB(j,i,c*256*256+c*256+c);
      }

    }
    enc.encode(BI);
    os.close();
  };

  public void SetMatrixX255(double [][] NewMatrix)
  {
     Matrix=new int[NewMatrix.length][NewMatrix[0].length];
     for (int i=0;i<NewMatrix.length;i++)
        for (int i1=0;i1<NewMatrix[0].length;i1++)
          Matrix[i][i1]=(int)(NewMatrix[i][i1]*255);
      Width=Matrix[0].length;
      Height=Matrix.length;
  }


  public void SetMatrix(int [][] NewMatrix)
  {
      Matrix=NewMatrix;
      Width=Matrix[0].length;
      Height=Matrix.length;
  }

  public int [][] ReturnMatrix()
  {
      return Matrix;
  }
}
