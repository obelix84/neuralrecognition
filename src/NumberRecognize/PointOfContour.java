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
  // ���� ���������� ����� -1 ������ ����� �� ����������������! ��� ��� ���...
  public PointOfContour() {
    x=-1;
    y=-1;
  }
  //��� ��������!

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
