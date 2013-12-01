package Solvers;

import Model.Edge;
import Model.Point;

public abstract class Solver {
	
	/**
	 * Attempts to solve the TSP problem given in the constructor
	 * @return Array of edges for a full TSP path
	 */
	public abstract Edge[] solve();
	
	/**
	 * Sets up solver with a TSP problem
	 * @param problem TSP instance to solve
	 */
	public abstract void setup(Point[] problem);
}
