package solvers;
import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;




public class GreedySolver extends Solver {
	TSPProblem problem;
	public GreedySolver(TSPProblem prob){
		problem = prob;
	}
	
	@Override
	public Solution solve() {
		Point[] points = problem.points;
		Solution tour = new Solution(problem);
		Boolean[] used = new Boolean[points.length];
		for(int i = 1; i < points.length; i++) {
			used[i] = false;
		}
		used[0] = true;
		//   tour[0] = 0
		//   used[0] = true
		Point p_i = null;
		for(int i = 0; i < points.length-1; i++)
		{
			int best = -1;
			double bestdist = Double.MAX_VALUE;
			if(i == 0) {
				p_i = points[0];
			}
			else if(tour.links[p_i.id].second==-1){
				p_i = points[tour.links[p_i.id].first];
			}
			else {

				p_i = points[tour.links[p_i.id].second];

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
			tour.addLink(new Edge(p_i, points[best]));
		}
		
		tour.addLink(points[tour.links[p_i.id].second].id, points[0].id);
		return tour;
	}
}
