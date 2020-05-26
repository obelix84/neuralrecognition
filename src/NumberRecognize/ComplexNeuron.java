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
public class ComplexNeuron {

  private double [][]Uw;//фиксированные веса от простого нейрона к сложному
  private double out;
  private double a;
  private double sum=0;

  public ComplexNeuron(int width, int height, double a)
  {
    out=-1;
    this.a=a;
    //формируем фиксированные веса
    Uw=new double [height][width];
    int k=height/2;

    for (int i=0;i<Uw.length;i++)
    {
      for (int j=0;j<Uw[0].length;j++)
      {
        Uw[i][j]=(double)1/(1+Math.abs(i-k)+Math.abs(j-k));
        sum+=Uw[i][j];
      }
    }

    for (int i=0;i<Uw.length;i++)
      for (int j=0;j<Uw[0].length;j++)
         Uw[i][j]=Uw[i][j]/sum;


  }

  public double GetOut()
  {
    return out;
  }

  public void CalcOut(double [][][]Sout)
  {
    double c_in=0;
    //считаем вход нейрона
    for (int plane=0;plane<Sout.length;plane++)
      for (int i=0;i<Uw.length;i++)
      {
        for (int j=0;j<Uw[0].length;j++)
        {
          c_in+=Uw[i][j]*Sout[plane][i][j];
        }
      }
    // а теперь формируем выход
    double с=c_in/(a+c_in);
    if (с>=0)
      out=с;
    else
      out=0;

  }

}
