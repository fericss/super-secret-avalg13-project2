package model;

public class TSPProblem {
	public Point[] points;
	public int size;
	private double[][] dist;
	
	public TSPProblem(Point[] _points) {
		points = _points;
		size = points.length;
		dist = new double[points.length][points.length];
		
		for(int i = 0; i < points.length; i++) {
			for(int j = 0; j < points.length; j++) {
				dist[i][j] = Double.MAX_VALUE;
			}
		}
		for(Point p : points){
			p.findNearbyPoints(this);
		}
	}
	
	public double distance(int i, int j) {
		if(i == j) return 0;
		
		if(dist[i][j] == Double.MAX_VALUE) {
			double d = Math.sqrt(points[i].distanceArea(points[j]));
			dist[i][j] = d;
			dist[j][i] = d;
		}
		return dist[i][j];
	}
	
}
