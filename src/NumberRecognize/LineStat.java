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
public class LineStat {
  private int [][] M;
  private int [] x;
  private int [] y;
  private int [][] len;
  private int [][] vert_len;
  private int color_of_line;

  public LineStat(int [][] M, int color_of_line) {
    this.M=M;
    this.color_of_line=color_of_line;

    int [] count_of_lines= new int[M.length];
    for (int i=0;i<M.length;i++)
    {
      count_of_lines[i] = 0;
      for (int j = 0; j < M[0].length; j++) {
        if (M[i][j] == color_of_line) {
          count_of_lines[i]++;
          try {
            while (M[i][j] == color_of_line) {
              j++;
            }
          }
          catch (ArrayIndexOutOfBoundsException exception) {
            continue;
          }
        }
      }
      System.out.print(count_of_lines[i]+"\n");
    }

    int count_all=0;
    for (int i=0;i<count_of_lines.length;i++)
         count_all+=count_of_lines[i];

    len=new int[M.length][M[0].length];
    for (int i=0;i<M.length;i++)
      for (int j=0;j<M[0].length;j++)
          len[i][j]=0;

    for (int i=0;i<M.length;i++)
    {
     for (int j = 0; j < M[0].length; j++)
     {
        if (M[i][j] == color_of_line)
        {
          int temp_j=j;
          try
          {
            while (M[i][j] == color_of_line)
            {
              j++;
            }
            len[i][j]=j-temp_j;
          }
          catch (ArrayIndexOutOfBoundsException exception)
          {
            len[i][j-1]=len[0].length-temp_j;
            continue;
          }
          //System.out.print(len[i][j]+"\n");

        }
      }
      //System.out.print(count_of_lines[i]+"\n");
    }

    vert_len=new int[M.length][M[0].length];
    for (int i=0;i<M.length;i++)
      for (int j=0;j<M[0].length;j++)
          vert_len[i][j]=0;

   for (int j=0;j<M[0].length;j++)
   {
    for (int i = 0; i < M.length; i++)
    {
       if (M[i][j] == color_of_line)
       {
         int temp_i=i;
         try
         {
           while (M[i][j] == color_of_line)
           {
             i++;
           }
           vert_len[i][j]=i-temp_i;
         }
         catch (ArrayIndexOutOfBoundsException exception)
         {
           vert_len[i-1][j]=vert_len.length-temp_i;
           continue;
         }
         //System.out.print(len[i][j]+"\n");

       }
     }
     //System.out.print(count_of_lines[i]+"\n");
   }


  }

  public int[][] returnLens()
  {
    return len;
  }

  public int[][] returnVertLens()
  {
    return vert_len;
  }

}
