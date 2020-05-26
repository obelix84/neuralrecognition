package NumberRecognize;

/**
 * <p>Title: </p>
 *Абстрактный класс для клеточного автомата
 * <p>Description: </p>
 * только для целочисленных значений клеток
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
    //перебираем все все клетки
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
          // получаем окружение клеток
          int[][] env = environment(i, j);
          //смотрим окружение клеток
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

  //окружение клетки
  abstract int [][]environment(int x, int y);
  //правила функционирования автомата
  abstract boolean Rules(int [][] env,int i,int j, int am);
  //правило остановки работы
  abstract boolean StoppingRule(int [] [] Field_new,int [] [] Field);

}
