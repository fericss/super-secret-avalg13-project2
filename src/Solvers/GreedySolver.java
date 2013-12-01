package Solvers;

import Model.Edge;
import Model.Point;

public class GreedySolver extends Solver {
	Point[] points;
	
	public GreedySolver(){}
	public GreedySolver(Point[] problem) {
		setup(problem);
	}

	@Override
	public Edge[] solve() {
		Edge[] tour = new Edge[points.length];
		Boolean[] used = new Boolean[points.length];
		for(int i = 1; i < points.length; i++) {
			used[i] = false;
		}
		used[0] = true;
		//   tour[0] = 0
		//   used[0] = true
		for(int i = 1; i < points.length; i++)
		{
			int best = -1;
			double bestdist = Double.MAX_VALUE;
			Point p_i = points[i];
			if(i == 1) {
				p_i = points[0];
			} else {
				p_i = tour[i-1].to;
			}
			for(int j = 0; j < points.length; j++)
			{
				double dist = p_i.distanceArea(points[j]);
				if(!used[j] && (dist < bestdist)) {
					bestdist = dist;
					best = j;
				}
			}
			used[best] = true;
			tour[i] = new Edge(p_i, points[best]);
		}
		tour[0] = new Edge(tour[points.length-1].to, points[0]);
		return tour;
	}

	@Override
	public void setup(Point[] problem) {
		points = problem;		
	}

}
