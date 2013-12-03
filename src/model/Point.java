package model;

import runnable.Main;



public class Point {
	public static int PointNumber=0;

	public int id;
	public double x;
	public double y;

	public Point [] nearbyPoints;

	public Point(double d, double e){
		id = PointNumber++;
		x = d;
		y = e;
	}

	public void findNearbyPoints(Solution sol){
		nearbyPoints = new Point[Main.NumNearbyPoints];
		for(Point p : sol.problem.points){
			double distance = sol.problem.distance(p.id, this.id);
			for(int i = 0; i<nearbyPoints.length ; i++){
				if(nearbyPoints[i]==null){
					nearbyPoints[i] = p;
					break;
				}
				else if(sol.problem.distance(nearbyPoints[i].id, this.id)>distance){
					if(i<nearbyPoints.length-1){
						nearbyPoints[i+1] = nearbyPoints[i];
					}
					nearbyPoints[i] = p;
					break;
				}
			}
		}

	}

	/**
	 * Pytharagos triangle whatever without the square root to save computations
	 * @param _p
	 * @return
	 */
	public double distanceArea(Point _p){
		double _x = (_p.x-x);
		double _y = (_p.y-y);
		return _x*_x+_y*_y;
	}

	public boolean equals(Object obj){
		return (this.id==((Point)obj).id);

	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}
