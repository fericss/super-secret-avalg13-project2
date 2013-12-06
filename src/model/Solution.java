package model;

import java.util.ArrayList;

public class Solution {
	public Link[] links;
	public TSPProblem problem;
	
	public Solution(TSPProblem _problem) {
		problem = _problem;
		links = new Link[problem.size];
	}
	
	/**
	 * Use if you dare.
	 * @param from
	 * @param old_to
	 * @param new_to
	 */
	public void changeLinkDestination(int from, int old_to, int new_to){
		if(links[from].next==old_to){
			links[from].next=new_to;
		}
		else if(links[from].prev==old_to){
			links[from].prev=new_to;
		}
	}
	
	/**
	 * Switches the links (from1 -> to1) and (from2 -> to2) such that
	 * (from1 -> from2) and (to2 -> to1)
	 * 
	 * CAREFUL with ordering, no error checks
	 * 
	 * @param from1
	 * @param to1
	 * @param from2
	 * @param to2
	 */
	/*
	 * High-tech ASCII visualization:
	 * 
	 * (f1)-->(t1)	->	(f1)   (t1)
	 * 				->	 |		^
	 *				->	 v		|	 
	 * (t2)<--(f2)	->	(f2)   (t2)
	 * 
	 */
	public void switchLinks(int from1, int to1, int from2, int to2) {
		Link lFrom1 = links[from1];
		Link lFrom2 = links[from2];
		Link lTo1 = links[to1];
		Link lTo2 = links[to2];
		
		// Make sure every link is pointing in the right direction
		// I.e. the first index should be swapped
//		if(lFrom1.first != to1) lFrom1.reverse();
//		if(lTo1.second != from1) lTo1.reverse();	
//		if(lFrom2.first != to2) lFrom2.reverse();
//		if(lTo2.second != from2) lTo2.reverse();
		
		// Reverse the direction between t1 & f2
		for(int curr = from2; curr != from1;) {
			Link cLink = links[curr];
			cLink.reverse();
			curr = cLink.next;
		}
		
		// Make the swaps
		lFrom1.next = from2;
		lFrom2.prev = from1;
		
		lTo1.next = to2;		
		lTo2.prev = to1;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("0\n");
		int current = links[0].getNext();
		
		while(current != 0) {
			sb.append(current + "\n");			
			current = links[current].getNext();
		}
		
		return sb.toString();
	}
	
	public ArrayList<Integer> getSequence() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int i;
    	for(i = 0; i < links.length; i++) {
    		if(links[i] != null) break;  
    	}
    	
    	list.add(i);
		int current = links[i].getNext();
		
		while(current != i) {
			list.add(current);
			
			int next = links[current].getNext();
			current = next;
		}
		return list;		
	}
	
	public void insertPoint(int point, int after, int before) {
		addLink(after, point);
		addLink(point, before);
	}
	
	public void addLink(Edge e) {
		addLink(e.from.id, e.to.id);
	}
	public void addLink(int from, int to) {
		if(links[from] == null) links[from] = new Link();
		links[from].next = to;
		if(links[to] == null) links[to] = new Link();
		links[to].prev = from;
	}
	
	public class Link {
		public int next;
		public int prev;
		
		public Link() {
			next = -1;
			prev = -1;
		}
		
		/**
		 * Swap direction
		 */
		public void reverse() {
			next += prev;			// f = f+s
			prev = next - prev;		// s = (f+s) - s = f
			next = next - prev;		// f = (f+s) - f = s
		}
		
		public int getNext() {
			return next;
		}
		
	} //Link

	
//	public Solution(int size) {
//		links = new Link[size];
//	}
//	public static void main(String[] args) {
//		Solution s = new Solution(4);
//		for(int i = 0; i < 4; i++) {
//			s.addLink(i, (i+1)%4);
//		}
//		System.out.println(s);
//		s.switchLinks(0, 1, 2, 3);
//		System.out.println(s);
//	}

	public double distance() {
		double dist = 0;
		int current = 0;
		int next = -1;
		while(next != 0) {
			next = links[current].getNext();
			dist += problem.distance(current, next);
			current = next;
		}
		return dist;
	}

	public void printSolution() {
		// TODO Auto-generated method stub
		System.out.println(this);
		
	}
	
} //Solution
