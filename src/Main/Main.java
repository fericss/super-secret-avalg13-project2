package Main;
import java.util.Scanner;

import Model.Edge;
import Model.Point;
import Solvers.*;


public class Main {
	public static Window window;
	public static void main(String[] args){

		
		
		Scanner sc = new Scanner(System.in);
		int numPoints = Integer.parseInt(sc.nextLine());
		Point [] points = new Point[numPoints];
		
		for(int i = 0; i<numPoints;i++){
			String line = sc.nextLine();
			String [] values = line.split(" ");
			points[i]=new Point(Double.parseDouble(values[0]),Double.parseDouble(values[1]));
		}
		
		
		window = new Window(points);
//		StupidSolver amagawd = new StupidSolver(points);
		Edge [] solution = new StupidSolver(points).solve();
//		Edge [] solution = new BruteSolver(points).solve();

		window.addEdges(solution);
		
		System.out.println(Edge.solutionLength(solution));
//		TwoOpt t2 = new TwoOpt();
//		solution = new ThreeOpt().opt(solution);
		//System.out.println(Edge.solutionLength(solution));
		
		window.repaint();
		
		
	}
}
