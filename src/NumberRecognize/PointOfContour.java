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
public class PointOfContour {
  private int x;
  private int y;
  // если координаты равны -1 значит точка не инициализирована! вот так вот...
  public PointOfContour() {
    x=-1;
    y=-1;
  }
  //для удобства!

  public void SetXY(int x, int y){
    //In My Heart is Desire..
    this.x=x;
    //Stay Away!
    this.y=y;
  }


  public void SetX(int x){
    //In My Heart is Desire..
    this.x=x;
  }

  public void SetY(int y){
    this.y=y;
  }


}
