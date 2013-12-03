//package runnable;
//
//
//
//
//
//
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import solvers.*;
//import model.*;
//
//public class TestRunner {
//	public Point[] points;
//	public double refscore;
//	
//	public TestRunner(File f) throws FileNotFoundException {
//		Scanner sc = new Scanner(f);
//
//		int numPoints = Integer.parseInt(sc.nextLine());
//		points = new Point[numPoints];
//		
//		for(int i = 0; i < numPoints; i++){
//			String line = sc.nextLine();
//			String [] values = line.split(" ");
//			points[i]=new Point(Double.parseDouble(values[0]),
//						Double.parseDouble(values[1]) );
//		}
//		if(sc.hasNextLine()) {
//			refscore = Double.parseDouble(sc.nextLine());			
//		}
//	}
//	
//	public void runTest(Solver s) {
//		s.setup(points);
//		Solution solution = s.solve();
//		
//		new TwoOpt().opt(solution);
//		
//		double dist = Edge.solutionLength(solution);
//		double roundFactor = 10000;
//		System.out.println(Math.round(dist*roundFactor) / roundFactor + " (" 
//				+ Math.round(refscore/dist*roundFactor) / roundFactor 
//				+ "x better than reference)");
//	}
//	
//	public static void runTests(Solver s, File[] files) throws FileNotFoundException {
//		for(File f : files) {
//			TestRunner tr = new TestRunner(f);
//			System.out.print(f.getName() + ": ");
//			tr.runTest(s);			
//		}
//	}
//	
//	public static void runTests(Solver s, File indexfile) throws FileNotFoundException {
//		ArrayList<File> files = new ArrayList<File>();
//		Scanner sc = new Scanner(indexfile);
//		while(sc.hasNextLine()) {
//			files.add(new File("tests\\" + sc.nextLine()));
//		}
//		File[] a_files = new File[files.size()];
//		files.toArray(a_files);
//		runTests(s, a_files);
//	}
//	
//	
//	public static void main(String[] args) throws IOException {
//		runTests(new StupidSolver(), new File("tests\\tests1.lst"));
//		runTests(new GreedySolver(), new File("tests\\tests1.lst"));
//	}
//}
