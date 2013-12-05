package solvers;
import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;




public class StupidSolver extends Solver {
	
	TSPProblem problem;
	public StupidSolver(TSPProblem prob){
		problem = prob;
	}
	
	
	public Solution solve(){
		
		Solution solution = new Solution(problem);


		for(int i = 0; i<problem.size-1;i++){
			
			solution.addLink(new Edge(problem.points[i],problem.points[i+1]));
		}
		solution.addLink(new Edge(	problem.points[problem.size-1],
									problem.points[0]));
		

		
		return solution;
	}
}
