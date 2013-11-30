import java.math.BigInteger;
import java.util.Scanner;

import javax.swing.JFrame;


public class Main {
	static Window window;
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
//		Edge [] solution = amagawd.solve();
		Edge [] solution = new BruteSolver(points).solve();

		window.addEdges(solution);
		
		System.out.println(Edge.solutionLength(solution));
//		TwoOpt t2 = new TwoOpt();
//		solution = t2.opt(solution);
		//System.out.println(Edge.solutionLength(solution));
		
		window.repaint();
		
		
	}
}
