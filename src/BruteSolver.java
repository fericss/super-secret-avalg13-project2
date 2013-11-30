
public class BruteSolver {
	Point [] points;
	public BruteSolver(Point [] _points){
		points = _points;
	}
	public Edge [] solve(){

		Edge [] best_solution = new StupidSolver(points).solve();
		double best_distance = Edge.solutionLength(best_solution);

		/**
		 * Insert more code here
		 */
		
		
		return best_solution;
	}

}
