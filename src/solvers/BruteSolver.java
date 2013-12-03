//package solvers;
//import model.Edge;
//import model.Point;
//
//
//
//
//public class BruteSolver extends Solver {
//	Point [] points;
//	private double best_distance;
//	private Edge[] best_solution;
//	
//	public BruteSolver(){};
//	public BruteSolver(Point [] _points){
//		setup(_points);
//	}
//	
//	public void setup(Point[] problem) {
//		best_distance = Double.MAX_VALUE;
//		points = problem;
//	}
//	
//	public Edge [] solve(){
//		boolean[] visited = new boolean[points.length];
//		Edge[] start = new Edge[0];
//		solve(start, visited);
//		
//		return best_solution;
//	}
//	
//	private void solve(Edge[] edges, boolean[] visited) {
//		if(edges.length == points.length-1) {
//			Edge[] edgenow = new Edge[edges.length+1];
//			System.arraycopy( edges, 0, edgenow, 1, edges.length );
//			edgenow[0] = new Edge(edges[0].to, points[0]);
//			
//			double dist = Edge.solutionLength(edgenow);
//			if(dist < best_distance) {
//				best_distance = dist;
//				best_solution = edgenow;
//			}
//			return;
//		}
//
//		for(int i = 1; i < points.length; i++) {
//			if(!visited[i]) {
//				boolean[] visitnow = new boolean[visited.length];
//				System.arraycopy( visited, 0, visitnow, 0, visited.length );
//				visitnow[i] = true;
//				
//				Edge[] edgenow = new Edge[edges.length+1];
//				System.arraycopy( edges, 0, edgenow, 1, edges.length );
//				if(edges.length == 0) {
//					edgenow[0] = new Edge(points[0], points[i]);
//				} else {
//					edgenow[0] = new Edge(edges[0].to, points[i]);					
//				}
//				
//				solve(edgenow, visitnow);				
//			}		
//		}
//	}
//
//}
