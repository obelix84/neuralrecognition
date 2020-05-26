package NumberRecognize;


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
public class Cplane {
  private double [][][]outs;
  private int [][]CrossConnections;
  private int [][]S_C_connections;
  private ComplexNeuron Neurons[][][];// ������ ������-����� ���������,
  //������ � ������ ���������� �������
  public int NumPlanes;
  public int SizePlane;
  public int SizeRF;


  public Cplane(int np,int sp,int srf, int [][] CC,int [][] SC, double a)
  {
    S_C_connections=SC;
    NumPlanes=np;
    SizePlane=sp;
    SizeRF=srf;
    CrossConnections=CC;
    //�������������� �������
    Neurons=new  ComplexNeuron[np][sp][sp];
    outs=new double[np][sp][sp];
    for (int i=0;i<np;i++){
      for (int j=0;j<sp;j++){
        for (int k=0;k<sp;k++){
          Neurons[i][j][k]=new ComplexNeuron(srf,srf,a);
        }
      }
    }
   }

   private double [][] GetOutFromOneMatrixRF(double Input [][],int j, int k)
   {
     double[][] RF = new double[SizeRF][SizeRF];
     //����� j,k ������
     //���������� ����������� ����
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

   public void CalcOuts(double [][][] Input)
   {
     double [][][]Sout;
     //�� ���� �������� � ����
     for (int i=0;i<S_C_connections.length;i++)
     { //��������� ����� �������� ���������� ����������
       //������ ������� ������ � ������� �� � ����������
       Sout=new double[S_C_connections[i].length][SizeRF][SizeRF];
       //����� j,k ������ ������ ���������
       //���������� ����������� ����
          double [][]RF;
          for (int j=0;j<SizePlane;j++){
            for (int k = 0; k < SizePlane; k++) {
              //�������� ����������� ���� �� ���� �������� ������� ����������� � ������ � �������
              for (int c=0;c<S_C_connections[i].length;c++){
                RF=this.GetOutFromOneMatrixRF(Input[S_C_connections[i][c]-1],j,k);
                Sout[c]=RF;
              }
              Neurons[i][j][k].CalcOut(Sout);
              outs[i][j][k]=Neurons[i][j][k].GetOut();
            }
          }
     }
   }

   public double [][][] GetOuts()
   {

     return outs;
   }


}
