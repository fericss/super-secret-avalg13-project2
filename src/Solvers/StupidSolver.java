package Solvers;
import Model.Edge;
import Model.Point;


public class StupidSolver extends Solver {
	Point [] points;
	public StupidSolver(Point [] _points){
		setup(_points);
	}
	
	public StupidSolver() {	}

	public void setup(Point[] problem) {
		points = problem;		
	}
	
	public Edge [] solve(){
		
		Edge [] solution = new Edge[points.length];
		
		for(int i = 0; i<points.length-1;i++){
			solution[i] = new Edge(points[i],points[i+1]);
		}
		solution[points.length-1] = new Edge(points[points.length-1],points[0]);
		return solution;
	}
}
