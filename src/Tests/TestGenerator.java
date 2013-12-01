package Tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Model.Edge;
import Model.Point;
import Solvers.GreedySolver;
import Solvers.Solver;

public class TestGenerator {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File circle = new File("tests\\circle.tst");
		circleTest(circle);
		
		for(int i = 15; i <= 1000; i*=2) {
			double start = System.currentTimeMillis();
			File f = new File("tests\\rnd" + i + ".tst");
			randTest(f, i);
			double finish = System.currentTimeMillis();
			System.out.println(i + " finished in " + (finish-start)/1000 + "s");
		}
	}
	
	public static void randTest(File f, int numPoints) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		Random rand = new Random();
		Point[] coords = new Point[numPoints];
		
		double scale = 100;
		bw.write(numPoints + "\n");
		for(int i = 0; i < numPoints; i++)
		{
			// Round to 4 decimals?
			double x = scale * rand.nextDouble();
			double y = scale * rand.nextDouble();
			coords[i] = new Point(x, y);
			bw.write(coords[i] + "\n");
		}
		
		Solver s = new GreedySolver(coords);
		Edge[] solution = s.solve();
				
		double dist = Edge.solutionLength(solution);
		bw.write(dist + "\n");
		bw.close();
	}
	
	public static void circleTest(File f) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		int numNodes = 1000;
		double scale = 100;
		Edge[] solution = new Edge[numNodes];
		Point[] coords = new Point[numNodes];
		
		double radFactor = 2 * Math.PI / numNodes;
		
		bw.write(numNodes + "\n");
		for(int i = 0; i < numNodes; i++)
		{
			// Round to 4 decimals?
			coords[i] = new Point(scale * (1 + Math.sin(i * radFactor)), 
						scale * (1 + Math.cos(i * radFactor)));
			bw.write(coords[i] + "\n");
			if(i > 0) {	// First coord has nothing to link to
				solution[i] = new Edge(coords[i-1], coords[i]);
			}
		}
		solution[0] = new Edge(coords[numNodes-1], coords[0]); // Link back to first

		Solver s = new GreedySolver(coords);
		solution = s.solve();
		
		double dist = Edge.solutionLength(solution);
		bw.write(dist + "\n");
		bw.close();
	}

}
