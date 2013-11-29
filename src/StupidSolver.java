
public class StupidSolver {
	Point [] points;
	public StupidSolver(Point [] _points){
		points = _points;
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
