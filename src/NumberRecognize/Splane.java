package NumberRecognize;

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
public class Splane {
  public int NumPlanes;
  public int SizePlane;
  public int SizeRF;
  private SimpleNeuron [][][] Neu;  // первый индекс номер плоскости,
  //второй и третий координаты нейрона
  private double [][][] outs;
  private int [][]CrossConnections;

  public Splane(int num_of_planes, int size_of_plane,int receiptive_field_size,int [][]CrossConnections,int numccon)
  {

    SizeRF=receiptive_field_size;
    NumPlanes=num_of_planes;
    SizePlane=size_of_plane;

    Neu=new SimpleNeuron[num_of_planes][size_of_plane][size_of_plane];
    outs=new double[num_of_planes][size_of_plane][size_of_plane];
    this.CrossConnections=CrossConnections;

    //нейроны организуем
    //и сразу веса выставляем нулями
    for (int i=0;i<num_of_planes;i++)
    {
      for (int j=0;j<SizePlane;j++)
      {
        for (int k=0;k<SizePlane;k++)
        {
          Neu[i][j][k]= new  SimpleNeuron(receiptive_field_size,receiptive_field_size,numccon);
          for (int s=0;s<numccon;s++)
          for (int s1=0;s1<SizeRF;s1++)
            for (int s2=0;s2<SizeRF;s2++)
              Neu[i][j][k].SetSw(s,s1,s2,0);
         }
      }
    }
  }

  public void SetNeuronWeights(int plane,int i,int j,double w[][][])
  {

    for (int s=0;s<w.length;s++)
    for (int k=0;k<SizeRF;k++)
    {
      //SetSw(int numcon, int i, int j, double w)
      for (int l=0;l<SizeRF;l++)
        Neu[plane][i][j].SetSw(s,k,l,w[s][k][l]);
    }
  }

  public double[][][] GetNeuronWeights(int plane, int i, int j)
  {
     return Neu[plane][i][j].GetSw();
  }

  public double[][] GetInhibNeuronWeights(int plane, int i, int j)
  {
     return Neu[plane][i][j].GetTw();
  }

  public double GetW0(int plane, int i, int j)
  {
      return Neu[plane][i][j].GetW0();
  }


  public void SetW0(int plane, int i, int j,double w0)
  {
        Neu[plane][i][j].SetW0(w0);
  }


  public double[][][] GetOuts()
  {
    return outs;
  }

  private double [][] GetOutFromOneMatrixRF(double Input [][],int j, int k)
  {
    double[][] RF = new double[SizeRF][SizeRF];
    //берем j,k нейрон
    //перебираем рецептивное поле
    for (int vert = 0; vert < SizeRF; vert++) {
      for (int gor = 0; gor < SizeRF; gor++) {
        if (CrossConnections[j][vert] >= 0 & CrossConnections[k][gor] >= 0) {
          RF[vert][gor] = Input[CrossConnections[j][vert]][CrossConnections[k][gor]];
        }
        else {
          RF[vert][gor] = 0;
        }
      }
    }
     return RF;
  }

  public double [][][] GetOutRF(double [][][] Input, int i, int j){
    double[][][]RF=new double[Input.length][SizeRF][SizeRF];
    //берем j,k нейрон каждой плоскости
    //перебираем рецептивное поле
    for (int InpPlane=0;InpPlane<Input.length;InpPlane++)
         RF[InpPlane]=this.GetOutFromOneMatrixRF(Input[InpPlane],i,j);
    return RF;
  }

  public void CalcOuts(double [][][] Input)
  {
    // берем входную матрицу
    // рецептивное поле
    // пропускаем через каждый нейрон
    double[][][]RF=new double[Input.length][SizeRF][SizeRF];
    //все плоскости перебираем
    for(int i=0;i<NumPlanes;i++)
    {
      for(int j=0;j<SizePlane;j++)
      {
        for(int k=0;k<SizePlane;k++)
        {
          //берем j,k нейрон каждой плоскости
          //перебираем рецептивное поле
          for (int InpPlane=0;InpPlane<Input.length;InpPlane++)
            RF[InpPlane]=this.GetOutFromOneMatrixRF(Input[InpPlane],j,k);
          //теперь подаем на нейрон
          Neu[i][j][k].CalcOutput(RF);
          outs[i][j][k]=Neu[i][j][k].GetOut();
        }

      }
    }

  }

}


/*
 for (int vert=0;vert<SizeRF;vert++)
            {
              for (int gor=0;gor<SizeRF;gor++)
              {
                if(CrossConnections[j][vert]>=0 & CrossConnections[k][gor]>=0)
                {
                    RF[gor][vert]=Input[i][CrossConnections[j][vert]][CrossConnections[k][gor]];
                }
                else
                {
                  RF[gor][vert]=0;
                }
              }
            }
*/
