package solvers;

import graphics.Window;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import model.Point;
import model.Solution;
import model.TSPProblem;

public class HullSolver extends Solver {
	TSPProblem problem;
	boolean[] visited;
//	@Override
//	public void setup(TSPProblem problem) {
//		// TODO Auto-generated method stub
//		
//	}
	public HullSolver(TSPProblem _problem) {
		problem = _problem;
	}

	@Override
	public Solution solve() {
		Solution s = monotoneChain();
		LinkedList<Point> visit = new LinkedList<Point>();
		LinkedList<Point> unvisit = new LinkedList<Point>();
		for(int i = 0; i < problem.size; i++) {
			if(visited[i]) {
				visit.add(problem.points[i]);
			}
			else unvisit.add(problem.points[i]);
		}
		System.out.println();
		// Pairwise comparison: find min-dist, OR
		// find max(min-dist)
		while(unvisit.size() > 0) {
//			findClosest(s, visit, unvisit);
			findFarthest(s, visit, unvisit);
		}
		
		return s;
	}
	
	/**
	 * Solves problem, with animation
	 * @param w Window in which to show solution
	 * @return
	 */
	public Solution solve(Window w, boolean closest) {
		Solution s = monotoneChain();
		LinkedList<Point> visit = new LinkedList<Point>();
		LinkedList<Point> unvisit = new LinkedList<Point>();
		for(int i = 0; i < problem.size; i++) {
			if(visited[i]) {
				visit.add(problem.points[i]);
			}
			else unvisit.add(problem.points[i]);
		}
		w.addSolution(s);
		// Pairwise comparison: find min-dist, OR
		// find max(min-dist)
		while(unvisit.size() > 0) {
			if(closest) {
				findClosest(s, visit, unvisit);				
			} else {
				findFarthest(s, visit, unvisit);
			}
			try {
				Thread.sleep(10000/problem.size);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			w.repaint();
		}
		
		return s;
	}
	
	public void findClosest(Solution s, LinkedList<Point> visit, LinkedList<Point> unvisit) {
		double bestCost = Double.MAX_VALUE;
		int[] bestPoints = new int[3];
		for(Point p : unvisit) {
			int start = visit.get(0).id;
			
			int prev = start;
			int curr = s.links[start].getNext();
			double prevdist = problem.distance(prev, p.id);
			while(curr != start) {
				double cost = prevdist - problem.distance(prev, curr);
				prevdist = problem.distance(curr, p.id);
				cost += prevdist;
				if(cost < bestCost) {
					bestCost = cost;
					bestPoints[0] = p.id;
					bestPoints[1] = prev;
					bestPoints[2] = curr;
				}
				int temp = curr;
				curr = s.links[curr].getNext();
				prev = temp;
			}
		}
		//System.out.println("Connecting " + bestPoints[0] + " to " + bestPoints[1] + " and " + bestPoints[2]);
		//s.printSolution();
		s.insertPoint(bestPoints[0], bestPoints[1], bestPoints[2]);
//		s.addLink(bestPoints[0], bestPoints[0]);
//		s.switchLinks(bestPoints[0], bestPoints[0], bestPoints[1], bestPoints[2]);
		visit.add(problem.points[bestPoints[0]]);
		unvisit.remove(problem.points[bestPoints[0]]);
	}
	
	public void findFarthest(Solution s, LinkedList<Point> visit, LinkedList<Point> unvisit) {
		double worstCost = -Double.MAX_VALUE;
		int[] worstPoints = new int[3];
		for(Point p : unvisit) {
			double bestCost = Double.MAX_VALUE;
			int[] bestPoints = new int[3];
			int start = visit.get(0).id;
			
			int prev = start;
			int curr = s.links[start].getNext();
			double prevdist = problem.distance(prev, p.id);
			while(curr != start) {
				double cost = prevdist - problem.distance(prev, curr);
				prevdist = problem.distance(curr, p.id);
				cost += prevdist;
				if(cost < bestCost) {
					bestCost = cost;
					bestPoints[0] = p.id;
					bestPoints[1] = prev;
					bestPoints[2] = curr;
				}
				int temp = curr;
				curr = s.links[curr].getNext();
				prev = temp;
			}
			
			if(bestCost > worstCost) {
				worstCost = bestCost;
				worstPoints = bestPoints;
			}
		}
		//System.out.println("Connecting " + bestPoints[0] + " to " + bestPoints[1] + " and " + bestPoints[2]);
		//s.printSolution();
		s.insertPoint(worstPoints[0], worstPoints[1], worstPoints[2]);
//		s.addLink(worstPoints[0], worstPoints[0]);
//		s.switchLinks(worstPoints[0], worstPoints[0], worstPoints[1], worstPoints[2]);
		visit.add(problem.points[worstPoints[0]]);
		unvisit.remove(problem.points[worstPoints[0]]);
	}
	
	private double ccw(Point p1, Point p2, Point p3) {
		return (p2.x - p1.x)*(p3.y - p1.y) - (p2.y - p1.y)*(p3.x - p1.x);
	}

	private Solution monotoneChain() {
		visited = new boolean[problem.size];
		
		Solution s = new Solution(problem);
		List<Point> points = Arrays.asList(problem.points.clone());
		Collections.sort(points, new PointComparator());
		
		Point[] hull = new Point[2*problem.size];
		int k = 0;
		
		// Make upper hull
		for(Point p : points) {
			while(k >= 2 && ccw(hull[k-2], hull[k-1], p) < 0) {
				k--;
			}
			hull[k] = p;
			k++;
		}
		
		// Make lower hull
		int t = k + 1;
		for(int i = problem.size - 2; i >= 0; i--) {
			Point p = points.get(i);
			while(k >= t && ccw(hull[k-2], hull[k-1], p) < 0) {
				k--;
			}
			hull[k] = p;
			k++;
		}
		
		// Move edges to solution
		for(int i = 1; i < k; i++) {
			s.addLink(hull[i-1].id, hull[i].id);
			visited[hull[i].id] = true;
		}
		//s.addLink(hull[k-1].id, hull[0].id);
		
		return s;
	}
	
	private class PointComparator implements Comparator<Point> {
		@Override
		public int compare(Point p1, Point p2) {
			if(p1.x == p2.x) return Double.compare(p1.y, p2.y);
			return Double.compare(p1.x, p2.x);
		}
		
	}
}
