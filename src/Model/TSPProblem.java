package Model;

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
				dist[i][j] = 0;
			}
		}
	}
	
	public double distance(int i, int j) {
		if(i == j) return 0;
		
		if(dist[i][j] == 0) {
			double d = points[i].distanceArea(points[j]);
			dist[i][j] = d;
			dist[j][i] = d;
		}
		return dist[i][j];
	}
	
}
