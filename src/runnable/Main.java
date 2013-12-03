package runnable;


import graphics.Window;

import java.util.Date;
import java.util.Scanner;



import model.*;
import solvers.*;


public class Main {
	
	public static Window window;
	
	public static boolean KATTIS_MODE = false;
	
	public static TSPProblem problem;
	public static Solution solution;
	
	
	public static void main(String[] args){
		Deadline d = new Deadline(2000);


		Scanner sc = new Scanner(System.in);
		int numPoints = Integer.parseInt(sc.nextLine());
		Point [] points = new Point[numPoints];

		for(int i = 0; i<numPoints;i++){
			/**
			 * TODO:
			 * faster way possible?
			 * 1000 reads and string parse might be slow
			 */
			String line = sc.nextLine();
			String [] values = line.split(" ");
			points[i] = new Point(Double.parseDouble(values[0]),Double.parseDouble(values[1]));
		}
		sc.close();
		problem = new TSPProblem(points);

		if(KATTIS_MODE){
			
			solution = new GreedySolver(problem).solve();
//			new TwoOpt().opt(solution,d);
//			Edge.printSolution(solution);
		}
		else{

			solution = new GreedySolver(problem).solve();
			//		Edge [] solution = new BruteSolver(points).solve();


//			new Window(points, 500,500).addSolution(points,solution);
//			window = new Window(points, 500,500).addSolution(points,solution);


			System.out.println(solution.distance());

			//		TwoOpt t2 = new TwoOpt();
//			new TwoOpt().opt(solution);
			//		new ThreeOpt().opt(solution);
			//		new TwoOpt().opt(solution);
			System.out.println("done");
			System.out.println(solution);
			System.out.println(solution.distance());

//			window.repaint();
			
		}

	}
}
