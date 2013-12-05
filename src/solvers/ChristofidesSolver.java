package solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import model.DisjointSet;
import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;

public class ChristofidesSolver extends Solver {
	TSPProblem problem;
	
	public ChristofidesSolver(TSPProblem _prob) {
		problem = _prob;
	}

	@Override
	public Solution solve() {
		LinkedList<Edge> tree = kruskal();
		int[] degree = new int[problem.size];
		for(Edge e : tree) {
			degree[e.to.id]++;
			degree[e.from.id]++;
		}
		
		LinkedList<Point> odd = new LinkedList<Point>();
		for(int i = 0; i < problem.size; i++) {
			if(degree[i] % 2 == 1) {
				odd.add(problem.points[i]);
			}
		}
		
		LinkedList<Edge> matching = simpleMatch(odd);		
		tree.addAll(matching);
		
		// Form Eulerian circuit in tree
		
		// Shortcut Eulerian -> Hamiltonian
		
		Solution s = new Solution(problem);
		
		return null;
	}
	
	public LinkedList<Edge> eulerCircuit(LinkedList<Edge> tree) {
		ArrayList<LinkedList<Edge>> unused = new ArrayList<LinkedList<Edge>>(problem.size);
		LinkedList<Point> unfinished = new LinkedList<Point>();
		LinkedList<Edge> tour = new LinkedList<Edge>();
		
		for(Edge e : tree) {
			unused.get(e.from.id).add(e);
			unused.get(e.to.id).add(e);
		}
	
		unfinished.add(problem.points[0]);
		while(!unfinished.isEmpty()) {
			Point start = unfinished.pop();
			Point curr = start;
			LinkedList<Edge> currList = unused.get(curr.id);
			do {
				Edge e = currList.pop();
				
				//TODO build tour;
				
				if(!currList.isEmpty()) {
					unfinished.add(curr);
				}
				
				if(curr != e.from) {
					curr = e.from;
				} else {
					curr = e.to;
				}
				currList = unused.get(curr.id);
				currList.remove(e);
			} while(curr != start);
		}
		
		return tour;
	}
	
	public LinkedList<Edge> simpleMatch(LinkedList<Point> points) {
		boolean[] matched = new boolean[problem.size];
		LinkedList<Edge> matching = new LinkedList<Edge>();
		
		LinkedList<Edge> edges = new LinkedList<Edge>();
		for(Point p1 : points) {
			for(Point p2 : points) {
				if(p1 == p2) break;
				edges.add(new Edge(p1, p2));
			}
		}
		Collections.sort(edges, new EdgeComparator());
		
		for(Edge e : edges) {
			// If either vertex has been matched already, skip to next edge
			Point p1 = e.to;
			if(matched[p1.id]) continue;
			
			Point p2 = e.from;
			if(matched[p2.id]) continue;
			
			matching.add(new Edge(p1, p2));
			matched[p1.id] = true; 
			matched[p2.id] = true; 
		}
		return matching;
	}
	
	public LinkedList<Edge> kruskal() {
		DisjointSet<Point> ds = new DisjointSet<Point>();
		LinkedList<Edge> edges = new LinkedList<Edge>();
		LinkedList<Edge> tree = new LinkedList<Edge>();
		
		for(Point p : problem.points) {
			ds.makeSet(p);
			for(Point near : p.nearbyPoints) {
				edges.add(new Edge(p, near));
			}
		}
		Collections.sort(edges, new EdgeComparator());
		
		for(Edge e : edges) {
			DisjointSet<Point>.Node n1 = ds.findSet(e.from);
			DisjointSet<Point>.Node n2 = ds.findSet(e.to);
			if(n1 != n2) {
				tree.add(e);
				ds.Union(n1, n2);
			}
		}
		
		return tree;
	}
	
	public class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge e1, Edge e2) {
			double dist1 = problem.distance(e1.from.id, e1.to.id);
			double dist2 = problem.distance(e2.from.id, e2.to.id);
			return Double.compare(dist1, dist2);
		}		
	}
}
