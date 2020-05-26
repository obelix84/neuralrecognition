package NumberRecognize;

/**
 * <p>Title: </p>
 *����������� ����� ��� ���������� ��������
 * <p>Description: </p>
 * ������ ��� ������������� �������� ������
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class CellularAutomata {
  public int [][] Field;
  private int active_mark;

  public CellularAutomata(int [][] Field, int am)
  {
    this.Field=Field;
    active_mark=am;
  }

  public int [][] ReturnField()
  {
    return Field;
  }
  public void start()
  {
    //���������� ��� ��� ������
    int a=Field.length;
    int [][] newField=new int[Field.length][Field[0].length];
    for (int i = 0; i < Field.length; i++) {
      for (int j = 0; j < Field[0].length; j++)
        newField[i][j]=Field[i][j];
    }
    for(;;)
    {
      for (int i = 0; i < Field.length; i++) {
        for (int j = 0; j < Field[0].length; j++) {
          // �������� ��������� ������
          int[][] env = environment(i, j);
          //������� ��������� ������
          boolean active = Rules(env, i, j, active_mark);
          if (active) {
            newField[i][j] = active_mark;
          }
        }
      }
     if(StoppingRule(newField,Field))
        break;
     for (int i = 0; i < Field.length; i++) {
       for (int j = 0; j < Field[0].length; j++)
        Field[i][j]=newField[i][j];
    }

  } }

  //��������� ������
  abstract int [][]environment(int x, int y);
  //������� ���������������� ��������
  abstract boolean Rules(int [][] env,int i,int j, int am);
  //������� ��������� ������
  abstract boolean StoppingRule(int [] [] Field_new,int [] [] Field);

}
