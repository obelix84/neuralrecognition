package NumberRecognize;


import java.util.Vector;

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
public class DrunkenBug {
  private int [][] M;
  Vector C;
  Vector IllBeBack;

  public DrunkenBug(int [][] M){
     this.M=M;
     C=new Vector();
     IllBeBack=new Vector();
  }

  public void Fly(int interval){//полетели... interval - на сколько пьяный жук
      //влетаем сверху справа и построчно пока не наткнмся на точечьку.. 
      // ищем, должны обязательно найти..
      PointOfContour start=new PointOfContour();
      for (int x=0;x<M.length;x++){
        for (int y=0;y<M[0].length;y++){
          if(M[x][y]==1){
            start.SetXY(x,y); //опаньки! стартовая точка
          }
        }
      }
      //летим дальше? правильно летим.. только надо запомнить точку, мы в нее еще вернемся!
      IllBeBack.add(start);

    }

}
