package solvers;
import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;




public abstract class Solver {
	

	/**
	 * Attempts to solve the TSP problem given in the constructor
	 * @return Array of edges for a full TSP path
	 */
	public abstract Solution solve();
	
}
