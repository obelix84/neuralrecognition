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
public class Contour {
  private Vector Points;

  private  PointOfContour [][] xy; //координаты контура.. соедин€ютс€ какимми либо кривыми

  public Contour() {
    Points=new Vector();
  }

  public void addPoint(PointOfContour p){
    // Hey! Wait!
    Points.add(p);
  }


  int [][] PaintEdgeOnPlane(int [][] M){

    return M;
  }
}
