package solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import runnable.Main;

import model.DisjointSet;
import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;
import model.TourList;
import model.TourList.TourNode;

public class ChristofidesSolver extends Solver {
	TSPProblem problem;
	
	public ChristofidesSolver(TSPProblem _prob) {
		problem = _prob;
	}

	@Override
	public Solution solve() {
		// Find spanning tree using kruskal's algorithm
		LinkedList<Edge> tree = kruskal();
//		System.out.println("krusktree" + tree.size() + "/" + problem.size);
		
		// Find odd-degree nodes
		int[] degree = new int[problem.size];
		for(Edge e : tree) {
			degree[e.to.id]++;
			degree[e.from.id]++;
		}
		LinkedList<Point> odd = new LinkedList<Point>();
		for(int i = 0; i < problem.size; i++) {
//			if(degree[i] == 0) System.out.println("tree missing: " + i); 
			if(degree[i] % 2 == 1) {
				odd.add(problem.points[i]);
			}
		}
		
		// Find a perfect matching
		LinkedList<Edge> matching = greedyMatch(odd);
//		System.out.println("odd: " + odd.size() + ", matches: " + matching.size());
		
		// Combine matching with spanning tree
		tree.addAll(matching);
//		System.out.println("finaltree:" + tree.size());

//		if(!Main.KATTIS_MODE) {
//			Edge[] edges = new Edge[tree.size()];
//			int i = 0;
//			for(Edge e : tree) {
//				edges[i++] = e;
//			}
//			Main.window.addEdges(edges);
//		}
		// Form Eulerian circuit in combined tree
		LinkedList<Point> eCircuit = eulerCircuit(tree);
//		System.out.println("eSize:" + eCircuit.size());
		
		// Shortcut Eulerian -> Hamiltonian		
		Solution s = shortcut(eCircuit);
		
		return s;
	}
	
	public Solution shortcut(LinkedList<Point> eCircuit) {
		boolean[] visited = new boolean[problem.size];
		Solution s = new Solution(problem);
		
		Point prev = null;
		Point first = null;
		for(Point p : eCircuit) {
			if (visited[p.id]) {
				continue;
			}
			if(prev != null) {
				s.addLink(prev.id, p.id);
			} else {
				first = p;
			}
			visited[p.id] = true;
			prev = p;
		}
		s.addLink(prev.id, first.id);
//		for(int i = 0; i < problem.size; i++) {
//			if(!visited[i]) System.out.println(i + " was not visited");
//		}
		return s;
	}
	
	public LinkedList<Point> eulerCircuit(LinkedList<Edge> tree) {
		ArrayList<LinkedList<Edge>> unused = new ArrayList<LinkedList<Edge>>(problem.size);
		for(int i = 0; i < problem.size; i++) {
			unused.add(new LinkedList<Edge>());
		}
		LinkedList<TourNode> unfinished = new LinkedList<TourNode>();
		TourList tour = new TourList();
		
		// Put each edge in the list of unused ones for corresponding nodes
		for(Edge e : tree) {
//			System.out.println("TreeEdge: "  + e.from.id + "-" + e.to.id);
			unused.get(e.from.id).add(e);
			unused.get(e.to.id).add(e);
		}
		
		// Add the first point to the tour, and to the list of unfinished nodes
		tour.insert(problem.points[0]);
		unfinished.add(tour.getCurr());
		
		while(!unfinished.isEmpty()) {
			TourNode start = unfinished.pop();
			TourNode curr = start;
			tour.setCurr(curr);
			LinkedList<Edge> currList = unused.get(curr.p.id);
			// Check if any edges remain before we do anything
			if(currList.isEmpty()) continue;
//			System.out.println("Starting from p" + start.p.id);
			do {
				// Get the first remaining edge from the current node
				Edge e = currList.pop();
				if(!currList.isEmpty()) {
					// If that edge wasn't the last one, we're not finished with this node
					unfinished.add(curr);
				}
				
				// Get next point and put it in tour, then move to that node
				Point next = (curr.p != e.from ? e.from : e.to);
				tour.insert(next, curr);
				curr = tour.getNext();
//				System.out.print(curr.p.id + ", ");
				
				// Remove the traversed edge from the list of unused ones in the target node
				currList = unused.get(curr.p.id);
				currList.remove(e);
			} while(curr.p != start.p);
				// Stop when we get back to the starting point
				// Note: we'll already have added the edge leading back to this node, so the tour is intact
//			System.out.println();
//			Main.window.addEdges(tour.getEdges());
		}
//		System.out.println("Returning");
		return tour.getTour();
	}
	
	public LinkedList<Edge> greedyMatch(LinkedList<Point> points) {
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
//			for(Point near : problem.points) {	//TODO: p.nearbyPoints?
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
		
		int numSets = ds.numSets();
		if(numSets > 1) {
			// NearbyPoints not enough to form a complete tree, we have to merge sets			
			// TODO: using O(n) stupid solution, use O(n^2) for better results?
			
			Point[] sets = new Point[numSets]; // We want one member from each set in here
			int n = 0;
			for(int i = 0; i < numSets; i++) {
				while(n < problem.size) {
					Point p = problem.points[n++];
					Point rep = ds.findSet(p).getObj();	// Representative for the disjoint set
					
					boolean bad = false;	// Does this representative appear in some other set?
					for(int j = 0; j < i; j++) {
						if(sets[j] == rep) {
							bad = true;
							break;
						}
					}
					if(!bad) {
						sets[i] = rep;
						break;
					}
				}
			}
			
			// Add edges between representatives of each set
			for(int i = 0; i < numSets; i++) {
				Point p1 = sets[i];
				Point p2 = sets[(i+1)%numSets];
				tree.add(new Edge(p1, p2));
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
