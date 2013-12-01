package Solvers;
import Model.Edge;
import Model.Point;


public class BruteSolver {
	Point [] points;
	private double best_distance = Double.MAX_VALUE;
	private Edge[] best_solution;
	
	
	public BruteSolver(Point [] _points){
		points = _points;
	}
	public Edge [] solve(){

		//Edge [] best_solution = new StupidSolver(points).solve();
		//double best_distance = Edge.solutionLength(best_solution);

		boolean[] visited = new boolean[points.length];
		Edge[] start = new Edge[0];
		solve(start, visited);
		
		/*
		Edge[] test = new Edge[10];
		test[0] = new Edge(points[0], points[4]);
		test[1] = new Edge(points[4], points[5]);
		test[2] = new Edge(points[5], points[3]);
		test[3] = new Edge(points[3], points[9]);
		test[4] = new Edge(points[9], points[1]);
		test[5] = new Edge(points[1], points[7]);
		test[6] = new Edge(points[7], points[6]);
		test[7] = new Edge(points[6], points[2]);
		test[8] = new Edge(points[2], points[8]);
		test[9] = new Edge(points[8], points[0]);
		return test; //*/
		return best_solution;
	}
	
	private void solve(Edge[] edges, boolean[] visited) {
		if(edges.length == points.length-1) {
			Edge[] edgenow = new Edge[edges.length+1];
			System.arraycopy( edges, 0, edgenow, 1, edges.length );
			edgenow[0] = new Edge(edges[0].to, points[0]);
			
			double dist = Edge.solutionLength(edgenow);
			if(dist < best_distance) {
				best_distance = dist;
				best_solution = edgenow;
			}
			return;
		}

		for(int i = 1; i < points.length; i++) {
			if(!visited[i]) {
				boolean[] visitnow = new boolean[visited.length];
				System.arraycopy( visited, 0, visitnow, 0, visited.length );
				visitnow[i] = true;
				
				Edge[] edgenow = new Edge[edges.length+1];
				System.arraycopy( edges, 0, edgenow, 1, edges.length );
				if(edges.length == 0) {
					edgenow[0] = new Edge(points[0], points[i]);
				} else {
					edgenow[0] = new Edge(edges[0].to, points[i]);					
				}
				
				solve(edgenow, visitnow);				
			}		
		}
	}

}
