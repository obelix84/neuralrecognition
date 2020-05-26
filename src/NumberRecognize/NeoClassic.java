package NumberRecognize;


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
public class NeoClassic {
  private String ConfDir="..\\Config\\";
  private String CrConDir="CrossConnections\\";
  private String CSConDir="S_C_Connections\\";
  private String ConfFile;
  private int NumSplanes;
  private int NumCplanes;
  private Splane [] SLayers;
  private Cplane [] CLayers;

  public NeoClassic(String ConfigFile) throws FileNotFoundException,
      IOException {
    ConfFile=ConfigFile;
    FileInputStream f= new FileInputStream(ConfDir+ConfigFile);
    byte [] Conf=new byte[f.available()];
    f.read(Conf);
    readConf(Conf);
  }

   public double[][][] GetOutsSLayer(int n)
   {
     return SLayers[n].GetOuts();
   }

   public double[][][] GetOutsCLayer(int n)
   {
     return CLayers[n].GetOuts();
   }


   public void CalcAll(String Input) throws FileNotFoundException {
     //Просчитываем выход, на вход имя картинки
       BMP bmp= new BMP(Input, "open");
       //формируем входной слой, те размножаем вход на все матрицы
       int [][] I=bmp.ReturnMatrix();
       double [][][] InputL=new double[1][SLayers[0].SizePlane][SLayers[0].SizePlane];
        //for (int i=0;i<SLayers[0].NumPlanes;i++){
          for (int j=0;j<SLayers[0].SizePlane;j++)
            for (int k=0;k<SLayers[0].SizePlane;k++)
              InputL[0][j][k]=(double)(I[j][k]/255);
       // }
        //начинаем считать просчитывать по слоям по очереди, первый слой S второй С и тд

          for (int i=0;i<NumSplanes;i++){
           SLayers[i].CalcOuts(InputL);
           double [][][] o=SLayers[i].GetOuts();
           CLayers[i].CalcOuts(o);
           InputL=CLayers[i].GetOuts();
        }
   }

   private void CalcAllwithPattern(double [][] Pattern, int NumSPlaneToTrain){
           //вычисляем выходы
           double [][][] Inp=new double[1][][];
           Inp[0]=Pattern;
           for (int i=0;i<NumSPlaneToTrain;i++){
           SLayers[i].CalcOuts(Inp);
         /*DumpToBMP DTB = null;
          try {
            DTB = new DumpToBMP("S1test.bmp");
          }
          catch (FileNotFoundException ex) {
          }*/
           double [][][] o=SLayers[i].GetOuts();
          /*try {
            DTB.Dump(o);
          }
          catch (FileNotFoundException ex1) {
            System.exit(1);
          }
          catch (IOException ex1) {
          System.exit(1);
          }*/
           CLayers[i].CalcOuts(o);
           Inp=CLayers[i].GetOuts();
         /* try {
            DTB = new DumpToBMP("C1test.bmp");
            try {
              DTB.Dump(Inp);
            }
            catch (FileNotFoundException ex3) {
            }
            catch (IOException ex3) {
            }
          }
          catch (FileNotFoundException ex2) {
          }*/

         }
   }


  private void readConf(byte [] conf) throws IOException {
     String c=new String(conf);
     c=c.replaceAll("(\r\n)+","\n");
     String[] par=c.split("#");
     //обрабатываем первую строку, она содержит число плоскостей
     String[] a=par[0].split("\n");
     for(int i=0;i<a.length;i++){
       String[] temp = a[i].split("=");
       //вытаскиваем параметры
       if (temp[0].equals("NumSplanes"))
         this.NumSplanes=Integer.parseInt(temp[1]);
       if (temp[0].equals("NumCplanes"))
         this.NumCplanes=Integer.parseInt(temp[1]);
     }
     //Создаем массивы плоскостей
     SLayers=new Splane[NumSplanes];
     CLayers=new Cplane[NumCplanes];
     //индексы номеров плоскостей в будующем пригодится
     int S=0;
     int C=0;
     //обрабатываем конфиги плоскостей
     for (int i=1;i<par.length;i++){
       a=par[i].split("\n"); //разбиваем на строки
       String [] temp=a[1].split("=");//на параметры разбиваем
       if (temp[1].equals("S")){// если S плоскость
         int NP=0;//число плоскостей
         int size=0;//размер плоскости
         int RF=0;//размер рецептивного поля
         String CC="";//Имя файла со связями рецептивного поля
         //все параметры извлекаем
         for(int j=2;j<a.length;j++){
           temp=a[j].split("=");
           if (temp[0].equals("NumPlane"))
             NP=Integer.parseInt(temp[1]);
           if (temp[0].equals("XYSize"))
             size=Integer.parseInt(temp[1]);
           if (temp[0].equals("RFSize"))
             RF=Integer.parseInt(temp[1]);
           if (temp[0].equals("CConnectionsConfFile"))
             CC=temp[1];
         }
         //надо извлечь связи РП из файла связей, извлекаем

         FileInputStream CCF=new FileInputStream(ConfDir+CrConDir+CC);
         byte [] CCon=new byte[CCF.available()];
         CCF.read(CCon);//читаем
         String g=new String(CCon);
         a=g.split("\n");
         //создаем  массив соединений
         int[][] CrossConnections=new int[size][RF];
         for (int m=0;m<size;m++){
           String [] t=a[m].split(";");
           for (int n=0;n<RF;n++){
             CrossConnections[m][n]=Integer.parseInt(t[n]);
           }
         }
         //а теперь туда где им самое место =)
         if (S==0)
           SLayers[S]=new Splane(NP,size,RF,CrossConnections,1);
         else
           SLayers[S]=new Splane(NP,size,RF,CrossConnections,CLayers[S-1].NumPlanes);
         S++;
       }
       if (temp[1].equals("C")){// если C плоскость
         int NP=0;//число плоскостей
         int size=0;//размер плоскости
         int RF=0;//размер рецептивного поля
         double al=0;//параметр внутри сети
         String CC="";//Имя файла со связями рецептивного поля
         String SC="";//Имя файла с SC связями плоскостей
         //все параметры извлекаем
         for(int j=2;j<a.length;j++){
              temp=a[j].split("=");
            if (temp[0].equals("NumPlane"))
                NP=Integer.parseInt(temp[1]);
            if (temp[0].equals("XYSize"))
                size=Integer.parseInt(temp[1]);
            if (temp[0].equals("RFSize"))
                RF=Integer.parseInt(temp[1]);
            if (temp[0].equals("CConnectionsConfFile"))
                CC=temp[1];
            if (temp[0].equals("SCConnectionFile"))
                SC=temp[1];
            if (temp[0].equals("a"))
                al=Double.parseDouble(temp[1]);
         }
         //надо извлечь связи РП из файла связей, извлекаем
         FileInputStream CCF=new FileInputStream(ConfDir+CrConDir+CC);
         byte [] CCon=new byte[CCF.available()];
         CCF.read(CCon);//читаем
         String g=new String(CCon);
         a=g.split("\n");
         //создаем  массив соединений
         int[][] CrossConnections=new int[size][RF];
         for (int m=0;m<size;m++){
           String [] t=a[m].split(";");
           for (int n=0;n<RF;n++){
              CrossConnections[m][n]=Integer.parseInt(t[n]);
           }
         }
         //надо извлечь связи СЦ плоскостей из файла связей, извлекаем
         FileInputStream SCF=new FileInputStream(ConfDir+CSConDir+SC);
         byte [] SCcon=new byte[SCF.available()];
         SCF.read(SCcon);//читаем
         String g1=new String(SCcon);
         String[] a1=g1.split("\n");
         int[][] SCConnections=new int[NP][];
         for (int m=0;m<NP;m++){
           String [] t=a1[m].split(";");
           SCConnections[m]=new int[t.length-1];
           for (int n=0;n<t.length-1;n++){
             SCConnections[m][n]=Integer.parseInt(t[n]);
             //System.out.print(SCConnections[m][n]+" ");
           }
         }
         //а теперь туда где им самое место =)
         CLayers[C]=new Cplane(NP,size,RF,CrossConnections,SCConnections,al);
         C++;
       }
     }
   }

   public void TrainWithTeacher(String Conf) throws FileNotFoundException,
      IOException {
     FileInputStream f= new FileInputStream(ConfDir+Conf);
     byte [] Con=new byte[f.available()];
     f.read(Con);
     String c=new String(Con);
     c=c.replaceAll("(\r\n)+","\n");
     String[] par=c.split("\n");
     String[] a1=par[0].split("=");
     String [] a2=a1[1].split(":");
     double [] alfa=new double[a2.length];
     for (int k=0;k<a2.length;k++)
       alfa[k]=Double.parseDouble(a2[k]);

     a1=par[1].split("=");
     a2=a1[1].split(":");
     double [] alfa0=new double[a2.length];
     for (int k=0;k<a2.length;k++)
       alfa0[k]=Double.parseDouble(a2[k]);

     a1=par[2].split("=");
     a2=a1[1].split(":");
     int [] times=new int[a2.length];
     for (int k=0;k<a2.length;k++)
         times[k]=Integer.parseInt(a2[k]);

     for (int i=3;i<par.length;i++){
       String []a=par[i].split(":");
       int NumL=Integer.parseInt(a[0]);
       int NumP=Integer.parseInt(a[1]);
       BMP bmp= new BMP("..\\pic\\TrainingPatterns\\"+a[2], "open");
       int [][] M=bmp.ReturnMatrix();
       double [][] NorM=new double[M.length][M[0].length];
       for (int j=0;j<M.length;j++)
         for (int k=0;k<M[0].length;k++){
           NorM[j][k]=(double)(M[j][k]/255);
         }

       /*double alfa=Double.parseDouble(a[3]);
       double alfa0=Double.parseDouble(a[4]);
       int times=Integer.parseInt(a[5]);*/
       this.TrainPlaneWithTeacher(NumL-1,NumP-1,NorM,times[NumL-1],alfa[NumL-1],alfa0[NumL-1]);
     }
   }


   public void TrainPlaneWithTeacher(int NumLayer,int numplane, double [][] Pattern, int times, double alfa,double alfa0) throws
      FileNotFoundException, IOException {
     //Берем центральный нейрон
     int sizerf=SLayers[NumLayer].SizePlane;
     int centrf=((int)(sizerf/2));

     //в случае первого слоя
     if(NumLayer==0){
       for (int i = 0; i < times; i++) {
         //Получаем стартовые веса
         double[][][] w = SLayers[NumLayer].GetNeuronWeights(numplane, centrf,centrf);
         //Поправки
         double w0=SLayers[NumLayer].GetW0(numplane,centrf,centrf);
         //double[][][] delta_w = new double[w.length][w[0].length][w[0][0].length];
         double [][] t=SLayers[NumLayer].GetInhibNeuronWeights(numplane, centrf,centrf);
         for (int s=0;s<w[0].length; s++){
           for (int s1=0;s1<w[0].length; s1++){
             w[0][s][s1]+=alfa*t[s][s1]*Pattern[s][s1];
             w0+=alfa0*Pattern[s][s1];
           }
         }
         //размножаем
         for (int k=0;k<SLayers[NumLayer].SizePlane;k++){
           for (int k1=0;k1<SLayers[NumLayer].SizePlane;k1++){
             SLayers[NumLayer].SetNeuronWeights(numplane, k, k1, w);
              //поправляем в0
             SLayers[NumLayer].SetW0(numplane,k,k1,w0);
           }
         }

         }

      }else{//случай остальных слоев отличных от первого
        //помещаем паттерн в центр рецептивного поля
        double Pat[][]=this.ToCenterRF(Pattern,SLayers[0].SizePlane);
        /*BMP bmp= new BMP("..\\pic\\TrainingPatterns\\12.bmp", "new");
        bmp.SetMatrixX255(Pat);
        bmp.WriteBMP();*/
        double[][][] w= new double[1][1][1];
        double w0=0;
        for (int i = 0; i < times; i++) {
           //Получаем стартовые веса
           w = SLayers[NumLayer].GetNeuronWeights(numplane, centrf,centrf);
           w0= SLayers[NumLayer].GetW0(numplane,centrf,centrf);
           double [][] t=SLayers[NumLayer].GetInhibNeuronWeights(numplane, centrf,centrf);
           //вычисляем отклик сети на паттерн
           this.CalcAllwithPattern(Pat,NumLayer);
           //получаем значения рецептивного поля в пределах одного нейрона, от всех плоскостей сразу
           double [][][]d=SLayers[NumLayer].GetOutRF(CLayers[NumLayer-1].GetOuts(),centrf,centrf);
           for (int s0=0;s0<w.length;s0++)
             for (int s=0;s<w[0].length; s++){
               for (int s1=0;s1<w[0].length; s1++){
                 w[s0][s][s1]+=alfa*t[s][s1]*d[s0][s][s1];
                 w0+=alfa0*d[s0][s][s1];
               }
             }
           //поправляем
           SLayers[NumLayer].SetNeuronWeights(numplane, centrf,centrf, w);
           SLayers[NumLayer].SetW0(numplane,centrf,centrf,w0);

        }
        //размножаем на все нейроны плоскости
        for (int k=0;k<SLayers[NumLayer].SizePlane;k++){
           for (int k1=0;k1<SLayers[NumLayer].SizePlane;k1++){
             SLayers[NumLayer].SetNeuronWeights(numplane, k, k1, w);
              //поправляем в0
             SLayers[NumLayer].SetW0(numplane,k,k1,w0);
           }
         }

      }



   }

   private double [][]  ToCenterRF(double [][] pattern, int size){
     double [][]ret=new double[size][size];
     int c=(int)((size-pattern.length)/2);
     for (int i=c;i<pattern.length+c;i++){
       for (int j=c;j<pattern.length+c;j++){
         ret[i][j]=pattern[i-c][j-c];
       }
     }
     return ret;
   }

}
