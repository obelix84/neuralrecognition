package NumberRecognize;

import java.io.InputStream;
import java.io.FileInputStream;
import java.awt.image.renderable.ParameterBlock;
import com.sun.media.jai.codec.SeekableStream;
import java.io.FileNotFoundException;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.Raster;
import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.ImageCodec;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import com.sun.java.swing.plaf.gtk.SynthScrollBarUI.ArrowButtonListener;
//import java.awt.Point;
import java.awt.image.BufferedImage;

public class ImageReBuild {

  private RenderedOp op;
  private Raster RastBMP;
  private String DecodFile;
  private String EncodFile;
  private int Width;
  private int Height;
  private BufferedImage BI;

  public ImageReBuild(String InputFile,String OutputFile) throws FileNotFoundException, IOException {
      DecodFile=InputFile;
      EncodFile=OutputFile;
  }
  public void ReadRaster() throws FileNotFoundException {
         // Wrap the InputStream in a SeekableStream.
         InputStream is = new FileInputStream(DecodFile);
         SeekableStream s = SeekableStream.wrapInputStream(is, false);
         // Create the ParameterBlock and add the SeekableStream to it.
         ParameterBlock pb = new ParameterBlock();
         pb.add(s);

         // Perform the BMP operation
         op=JAI.create("BMP", pb);
         RastBMP =op.getData();

         Height=RastBMP.getHeight();
         Width=RastBMP.getWidth();
       //System.out.println(Height+"  "+Width);
  };
  public void WriteBMP() throws FileNotFoundException, IOException {
    OutputStream os = new FileOutputStream(EncodFile);
    BMPEncodeParam param = new BMPEncodeParam();
    ImageEncoder enc = ImageCodec.createImageEncoder("BMP", os, param);
    enc.encode(BI);
    os.close();


  };

  public void TransformRaster(){
    BI=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
    double max=0;
    double min=0;
    double summ=0;
    for(int x=1;x<Width-1;x++)
        for(int y=1;y<Height-1;y++)
        {
          double rob=Roberts(RastBMP.getSample(x,y-1,0),
                                RastBMP.getSample(x,y+1,0),
                                RastBMP.getSample(x-1,y,0),
                                RastBMP.getSample(x+1,y,0),
                                RastBMP.getSample(x,y,0));
         summ=summ+rob;
         if(rob<min)
            min=rob;
          if(rob>max)
            max=rob;

        }
      summ=(double)summ/((Width-1)*(Height-1));
      summ=Math.abs(summ);
    //System.out.println(min+" "+max+" "+summ);
     double kmax=max/255;
     double kmin=min/255;
     //System.out.println(kmin+" "+kmax);
      for(int x=1;x<Width-1;x++){
       //System.out.println(kmin+"----- "+kmax);

        for (int y = 1; y < Height - 1; y++) {
          /*System.out.println(x+"  "+y+"   "+RastBMP.getSample(x, y - 1, 0)+
           " "+RastBMP.getSample(x, y + 1, 0)+
           " "+RastBMP.getSample(x-1, y, 0)+
           " "+RastBMP.getSample(x+1, y, 0)+
           " "+RastBMP.getSample(x, y, 0)*/
             // );
          double rob = Roberts(RastBMP.getSample(x, y - 1, 0),
                            RastBMP.getSample(x, y + 1, 0),
                            RastBMP.getSample(x - 1, y, 0),
                            RastBMP.getSample(x + 1, y, 0),
                            RastBMP.getSample(x, y, 0));
         rob-=summ;
        //System.out.println( rob);
          if (rob > 0)
            BI.setRGB(x, y,
                      (int) (rob / kmax) * 256 + (int) (rob / kmax) * 256 * 256 +
                      (int) (rob / kmax));
          if (rob < 0)
            BI.setRGB(x, y,
                      (int) (rob / kmin) * 256 + (int) (rob / kmin) * 256 * 256 +
                      (int) (rob / kmin));
       }
      }

  }
  private double Roberts(int up,int down,int left,int right, int cur){
    //System.out.println(up+"+++"+down+"+++"+left+"+++"+right+"+++"+cur);
    double rob;
    if ((up+down+left+right)==0)
      return 0;
    else{
      rob=(double)(up+down+left+right-4*cur)/(up+down+left+right);
    }
      //System.out.println("---"+rob);
    return rob;
  }
}
