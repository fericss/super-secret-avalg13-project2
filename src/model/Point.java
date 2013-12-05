package model;

import java.util.HashSet;

import runnable.Main;



public class Point {
	public static int PointNumber=0;

	public int id;
	public double x;
	public double y;

	public HashSet<Point> nearbyPoints;

	public Point(double d, double e){
		id = PointNumber++;
		x = d;
		y = e;
		nearbyPoints = new HashSet<Point>();
	}

	public void findNearbyPoints(Solution sol){
		int y = 0;
		Point[] candidates = new Point[Main.NumNearbyPoints];
		for(Point p : sol.problem.points){
			if(p == this) continue;
			double distance = sol.problem.distance(p.id, this.id);
			if(y < Main.NumNearbyPoints) {
				candidates[y++] = p;
			} else {
				for(int i = 0; i < Main.NumNearbyPoints; i++){
					if(sol.problem.distance(candidates[i].id, this.id) > distance){
						if(i < candidates.length-1){
							candidates[i+1] = candidates[i];
						}
						candidates[i] = p;
						break;
					}
				}
			}
		}
		for(Point p : candidates) {
			nearbyPoints.add(p);
			p.nearbyPoints.add(this);
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
