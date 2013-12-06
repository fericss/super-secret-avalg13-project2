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

	public void findNearbyPoints(TSPProblem tspProblem){
		int y = 0;
		Point[] candidates = new Point[Main.NumNearbyPoints];
		double longestDist = 0;
		int longestIndex = -1;
		for(Point p : tspProblem.points){
			if(p == this) continue;
			double distance = tspProblem.distance(p.id, this.id);
			if(y < Main.NumNearbyPoints) {
				candidates[y++] = p;
				if(distance > longestDist) {
					longestDist = distance;
					longestIndex = y-1;
				}
			} else if(distance < longestDist) {
				candidates[longestIndex] = p;
				longestDist = 0;
				for(int i = 0; i < Main.NumNearbyPoints; i++) {
					double tempDist = tspProblem.distance(candidates[i].id, this.id);
					if(tempDist > longestDist){
						longestDist = tempDist;
						longestIndex = i;
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
