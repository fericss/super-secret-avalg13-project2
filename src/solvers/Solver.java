package solvers;
import model.Solution;
import model.TSPProblem;


public abstract class Solver {
//	TSPProblem problem;
//	public Solver(TSPProblem _problem) {
//		problem = _problem;
//	}
	/**
	 * Attempts to solve the TSP problem given in the constructor
	 * @return Array of edges for a full TSP path
	 */
	public abstract Solution solve();
	
}
