package NumberRecognize;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 *
 * ���������� �������� ������� ��� �������� ����
 * �������� ���������� ������
 *
 * </p>
 *
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SimpleNeuron {
  private double [][][] Sw;//������������ ���� ����� �������� � ������� ��������
  private double [][] Tw;//���� ����� ������� � ���������� ��������
  private double w0;//������������� ��� �� ���������� ������� �� �������� �������
  private double out;
  private double inhibout;
  private double sum=0;

  public SimpleNeuron(int height,int width, int numconnections){
    w0=0;
    out=-1;
    inhibout=0;
    Sw=new double[numconnections][height][width];
    //��������� ������������� ����
    Tw=new double [height][width];
    int k=height/2;
    for (int i=0;i<Tw.length;i++)
    {
      for (int j=0;j<Tw[0].length;j++)
      {
        Tw[i][j]=(double)1/(1+Math.abs(i-k)+Math.abs(j-k));
        sum+=Tw[i][j];
      }
    }

    for (int i=0;i<Tw.length;i++)
      for (int j=0;j<Tw[0].length;j++)
        Tw[i][j]=Tw[i][j]/sum;

  }

  public void SetW0(double w)
 {
   w0=w;
 }

 public double GetW0()
 {
    return w0;
 }

 public double GetOut()
  {
    return out;
  }

  public double[][][] GetSw()
 {
   return Sw;
 }

 public double[][] GetTw()
{
  return Tw;
}


  public void SetSw(int numcon, int i, int j, double w)
  {
    /*try
    {*/
      Sw[numcon][i][j] = w;
    /*}
    catch(ArrayIndexOutOfBoundsException exception)
    {
       System.out.print("������� ���� ����� � ������� ��� ������� ������� �������!");
       System.exit(2);
    }*/
  }

  public double GetInhibitoryNeuronOut(){
    return inhibout;
  }

  public double CalcInhibitoryNeuronOut(double [][][] Cout)
  {
    double v=0;
    for (int plane=0;plane<Cout.length;plane++)
      for (int i=0;i<Sw[0].length;i++)
      {
        for (int j=0;j<Sw[0][0].length;j++)
        {
          v+=Tw[i][j]*Cout[plane][i][j]*Cout[plane][i][j];
        }
      }
    v=Math.sqrt(v);

    return v;
  }

  public void CalcOutput(double [][][] Cout)
  {
    //��������� ������������ �����
    double e=0;
    for (int plane=0;plane<Cout.length;plane++)
      for (int i=0;i<Sw[0].length;i++)
      {
        for (int j=0;j<Sw[0][0].length;j++)
        {
          e+=Sw[plane][i][j]*Cout[plane][i][j];
        }
      }
    //��������� ���������� �����
    inhibout=this.CalcInhibitoryNeuronOut(Cout);
    //inhibout=Math.sqrt(inhibout);
    // ��������� �����
    double x =(double)(1+e)/(1+w0*inhibout)-1;
    if(x>=0)
      out=x;
    else
      out=0;
  }
}
