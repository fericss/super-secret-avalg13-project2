package runnable;


import graphics.Window;

import java.util.Date;
import java.util.Scanner;



import model.*;
import solvers.*;


public class Main {
	
	public static Window window;

	public static boolean KATTIS_MODE = false;
	
	public static int NumNearbyPoints = 15;

	public static TSPProblem problem;
	public static Solution solution;
	
	
	public static void main(String[] args){
//		long time = System.currentTimeMillis();
		Deadline deadline = new Deadline(20000);

		
		Scanner sc = new Scanner(System.in);
		int numPoints = Integer.parseInt(sc.nextLine());
		
		if(numPoints == 0) { return;}
		
		
		if(numPoints>100){
			NumNearbyPoints = 20;
		}
		else if(numPoints>50){
			NumNearbyPoints = 10;
		}
		else{
			NumNearbyPoints = numPoints - 1;
		}
		
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
//			solution = new HullSolver(problem).solve();
//			solution = new GreedySolver(problem).solve();
			solution = new ChristofidesSolver(problem).solve();

			new TwoOpt().opt(solution,deadline);
			System.out.println(solution);
			//			new TwoOpt().opt(solution,d);
			//			Edge.printSolution(solution);
//			System.out.println("-->"+deadline.TimeUntil());
//			System.out.println("--"+(System.currentTimeMillis()-time));
		}
		else{

			window = new Window(problem, 500, 500);
			if(problem.size < 5000) window.showText = true;
//			solution = new HullSolver(problem).solve();
//			solution = new HullSolver(problem).solve(window, false);
//			solution = new GreedySolver(problem).solve();
			solution = new ChristofidesSolver(problem).solve();
			//solution = new BruteSolver(points).solve();


			//new Window(problem, 500, 500).addSolution(solution);
			window.addSolution(solution);


			System.out.println(solution.distance());

			//		TwoOpt t2 = new TwoOpt();
//			new TwoOpt().opt(solution);
					new ThreeOpt().opt(solution);
			//		new TwoOpt().opt(solution);
			System.out.println("done");
//			System.out.println(solution);
			System.out.println(solution.distance());

//			window.repaint();
			
		}

	}
}
