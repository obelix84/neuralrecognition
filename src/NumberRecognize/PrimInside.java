package NumberRecognize;



public class PrimInside extends CellularAutomata
{

  public PrimInside(int [][] Field, int am)
  {
     super(Field,am);
  }

/**
   * Rules
   *
   * @param env int[][]
   * @param i int
   * @param j int
   * @param am int
   * @return boolean
   * @todo Implement this diplom.CellularAutomata method
   */
  boolean Rules(int[][] env, int i, int j, int am) {
    if (env[1][1]==am)
    {
      return true;
    }
    if (env[1][1]!=am)
    {
     int count_of_am=0;
     int count_of_border=0;
      for(int i1=0;i1<3;i1++){
        for (int j1 = 0; j1 < 3; j1++) {
          if (env[i1][j1] == am)
            count_of_am++;
          if (env[i1][j1] == 0)
            count_of_border++;
        }
      }
     if (count_of_border>1)
       return false;
     else if (count_of_am>=1)
       return true;
    }
    return false;
  }


  /**
   * environment
   *
   * @param x int
   * @param y int
   * @return int[][]
   * @todo Implement this diplom.CellularAutomata method
   */
  int[][] environment(int x, int y) {
    int [][] env=new int[3][3];

    for (int i=0;i<3;i++)
      for (int j=0;j<3;j++)
      {
        try
        {
          env[i][j]=Field[x+i-1][y+j-1];
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
          env[i][j]=100;
        }
      }
   return env;
   }



  boolean StoppingRule(int[][] Field_new, int[][] Field) {
    int count=0;
    for (int i=0;i<Field.length;i++)
      for (int j=0;j<Field[0].length;j++)
        if (Field[i][j]!=Field_new[i][j])
          count++;
    if (count==0)
         return true;
    else
         return false;
  }
}
