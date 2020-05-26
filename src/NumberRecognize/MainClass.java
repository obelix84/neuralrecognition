package NumberRecognize;


import java.io.FileNotFoundException;
import java.io.IOException;

public class MainClass {
  public MainClass() {
  }

  public static void main(String[] args) throws FileNotFoundException,
      IOException {
    MainClass mainclass = new MainClass();

    NeoClassic Neo=new NeoClassic("neoclassic.conf");
     Neo.TrainWithTeacher("training.conf");
    Neo.CalcAll("..\\pic\\13.bmp");
    DumpLayerOuts dump=new DumpLayerOuts("L.out");
    double[][][]S2out=Neo.GetOutsSLayer(1);
    dump.Dump(S2out);
    /*double[][][]C4out=Neo.GetOutsCLayer(3);
    dump.Dump(C4out);*/
    //dump.Dump(Neo.GetOutsCLayer(0));
    DumpToBMP DTB=new DumpToBMP("S1.bmp");
    DTB.Dump(Neo.GetOutsSLayer(0));
    DumpToBMP DTB1=new DumpToBMP("C1.bmp");
    DTB1.Dump(Neo.GetOutsCLayer(0));
    DumpToBMP DTB2=new DumpToBMP("S2.bmp");
    DTB2.Dump(Neo.GetOutsSLayer(1));
    DumpToBMP DTB3=new DumpToBMP("C2.bmp");
    DTB3.Dump(Neo.GetOutsCLayer(1));
   /* DumpToBMP DTB4=new DumpToBMP("S3.bmp");
    DTB4.Dump(Neo.GetOutsSLayer(2));
    DumpToBMP DTB5=new DumpToBMP("C3.bmp");
    DTB5.Dump(Neo.GetOutsCLayer(2));

    DumpToBMP DTB6=new DumpToBMP("S4.bmp");
   DTB6.Dump(Neo.GetOutsSLayer(3));
   DumpToBMP DTB7=new DumpToBMP("C4.bmp");
   DTB7.Dump(Neo.GetOutsCLayer(3));
*/

    /*  int [][] CC_for_S1=new int[36][5];
             for (int i=0;i<36;i+=2)
               for (int j=-3;j<2;j+=1)
                 if ((i+j)<19)
                   CC_for_S1[i][j+3]=i+j;
                 else
                 CC_for_S1[i][j+3]=-1;
        for (int i=0;i<19;i++){
         System.out.print("\n");
         for (int j = 0; j < 5; j++)
            System.out.print(CC_for_S1[i][j]+";");
    }*/













/*    int c=7;
   /* int k=c/2;
    for (int i=0;i<c;i++)
   {
     for (int j=0;j<c;j++)
     {
       double Z=(double)1/(1+Math.abs(i-k)+Math.abs(j-k));
       System.out.print(Z+"  ");
     }
     System.out.print("\n");
   }*/


/*
    BMP bmp=new BMP("..\\Images\\ã13êðàé2.bmp","open");
    BMP bmp1=new BMP("..\\Images\\ã13êðàé21new.bmp","new");
    int [][] M=bmp.ReturnMatrix();

    LineStat LS = new LineStat(M,0);

    int [][] len=LS.returnLens();
    int [][] M1=new int [len.length][len[0].length];

    for (int i=0;i<len.length;i++)
      for (int j = 0; j < len[0].length; j++)
        M1[i][j]=255;


    for (int i=0;i<len.length;i++)
    {
      for (int j = 0; j < len[0].length; j++)
      {
        if (len[i][j]>5 & len[i][j]<10)
        {
          int j1=j;
          try
          {
            while(j1!=j-len[i][j])
            {
              M1[i][j1]=0;
              j1--;
            }

          }
          catch(ArrayIndexOutOfBoundsException exception)
          {
            continue;
          }
        }
      }
    }
    len=LS.returnVertLens();
    for (int j=0;j<len[0].length;j++)
    {
      for (int i = 0; i < len.length; i++)
      {
        if (len[i][j]>5 & len[i][j]<10)
        {
          int i1=i;
          try
          {
            while(i1!=i-len[i][j])
            {
              M1[i1][j]=0;
              i1--;
            }

          }
          catch(ArrayIndexOutOfBoundsException exception)
          {
            continue;
          }
        }
      }
    }

    bmp1.SetMatrix(M1);
    bmp1.WriteBMP();












    /* BMP bmp=new BMP("..\\Images\\pm101cipher6.bmp","open");
    int [][] M=bmp.ReturnMatrix();
    Prim Zalivka=new Prim(M,100);
    Zalivka.start();
    M=Zalivka.ReturnField();
    // äåëàåì âíåøíþþ ãðàíèöó ñèìâîëà âòîðûì ôðîíòîì ðàñòåêàíèÿ æèäêîñòè
    int [][] newM=new int[M.length][M[0].length];

    for (int i=0;i<M.length;i++)
       for (int j=0;j<M[0].length;j++)
         newM[i][j]=M[i][j];
    int ty=M.length/8;
    int tx=M[0].length/8;

    for (int i=1;i<(M.length-1);i++)
    {
      for (int j=1;j<(M[0].length-1);j++)
      {
        /*ÄËß ÑËÓ×Àß
           * * *
           * = *
           * # *
         */
        /*
        if (M[i][j]==100 & M[i+1][j]==255)
        {
          for (int k=i;k<=i+ty;k++)
          {
            try
            {
              if (M[k][j] == 0 )
              {
                 newM[k][j]=100;
                 break;
               }
            }
            catch(ArrayIndexOutOfBoundsException exception)
            {
              break;
            }
          }
        }
        /*ÄËß ÑËÓ×Àß
           * * *
           * # *
           * = *
         */
        /*
        if (M[i][j]==255 & M[i+1][j]==100)
        {
          for (int k=i;k>=i-ty;k--)
          {
            try
            {
              if (M[k][j] == 0 )
              {
                 newM[k][j]=100;
                 break;
              }
            }
            catch(ArrayIndexOutOfBoundsException exception)
            {
              break;
            }
          }
        }
        /*ÄËß ÑËÓ×Àß
          * * *
          * = #
          * * *
        */
       /*
       if (M[i][j]==100 & M[i][j+1]==255)
       {
         for (int k=j;k<=j+tx;k++)
         {
           try
           {
             if (M[i][k] == 0 )
             {
                newM[i][k]=100;
                break;
             }
           }
           catch(ArrayIndexOutOfBoundsException exception)
           {
             break;
           }
         }
       }
       /*ÄËß ÑËÓ×Àß
         * * *
         * # =
         * * *
       */
     /*
      if (M[i][j]==255 & M[i][j+1]==100)
      {
        for (int k=j;k>=j-tx;k--)
        {
          try
          {
            if (M[i][k] == 0 )
            {
               newM[i][k]=100;
               break;
            }
          }
          catch(ArrayIndexOutOfBoundsException exception)
          {
            break;
          }
        }
      }

      }
    }

    int [][] razn= new int [M.length][M[0].length];

    */
    /*PrimInside Zalivka1=new PrimInside(newM,100);
    Zalivka1.start();*/
    /*
    Prim Zalivka1=new Prim(newM,100);
    Zalivka1.start();
    for (int i=0;i<M.length;i++)
       for (int j=0;j<M[0].length;j++)
         razn[i][j]=newM[i][j]-M[i][j];

    BMP bmp1=new BMP("..\\Images\\pm101cipher6_1.bmp","new");
    bmp1.SetMatrix(razn);
    bmp1.WriteBMP();*/

  }
}
