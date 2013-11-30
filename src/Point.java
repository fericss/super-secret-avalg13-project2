
public class Point {
	public static int PointNumber=0;
	
	public int id;
	public double x;
	public double y;
	public Point(double d, double e){
		id = ++PointNumber;
		x = d;
		y = e;
	}
	/**
	 * Pytharagos triangle whatever without the square root to save computations
	 * @param _p
	 * @return
	 */
	public double distanceArea(Point _p){
		double _x = (_p.x-x);
		double _y = (_p.y-y);
		return _x*_x+_y+_y;
	}

	public boolean equals(Object obj){
		return (this.id==((Point)obj).id);
		
	}
}
