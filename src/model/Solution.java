package model;

public class Solution {
	public Link[] links;
	public TSPProblem problem;
	
	public Solution(TSPProblem _problem) {
		problem = _problem;
		links = new Link[problem.size];
		for(Point p : problem.points){
			p.findNearbyPoints(this);
		}
	}
	
	/**
	 * Use if you dare.
	 * @param from
	 * @param old_to
	 * @param new_to
	 */
	public void changeLinkDestination(int from, int old_to, int new_to){
		if(links[from].first==old_to){
			links[from].first=new_to;
		}
		else if(links[from].second==old_to){
			links[from].second=new_to;
		}
	}
	
	/**
	 * Switches the links (from1 <-> to1) and (from2 <-> to2) such that
	 * (from1 <-> to2) and (from2 <-> to1)
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
	 * (f1)---(t1)	->	(f1)   (t1)
	 *				->		 x
	 * (f2)---(t2)	->	(f2)   (t2)
	 * 
	 * 
	 * (f1)---(t1)	->	(f1)   (t1)
	 *				->	 |	 	|
	 * (t2)---(f2)	->	(t2)   (f2)
	 */
	public void switchLinks(int from1, int to1, int from2, int to2) {
		Link lFrom1 = links[from1];
		Link lFrom2 = links[from2];
		Link lTo1 = links[to1];
		Link lTo2 = links[to2];
		
		// Make sure every link is pointing in the right direction
		// I.e. the first index should be swapped
		if(lFrom1.first != to1) lFrom1.reverse();
		if(lTo1.first != from1) lTo1.reverse();	
		if(lFrom2.first != to2) lFrom2.reverse();
		if(lTo2.first != from2) lTo2.reverse();
		
		// Make the swaps
		lFrom1.first = to2;
		lTo2.first = from1;
		
		lFrom2.first = to1;
		lTo1.first = from2;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("0\n");
		int prev = 0;
		int current = links[0].getNext();
		
		while(current != 0) {
			sb.append(current + "\n");
			
			int next = links[current].getNext(prev);
			prev = current;
			current = next;
		}
		
		return sb.toString();
	}
	
	public void addLink(Edge e) {
		addLink(e.from.id, e.to.id);
	}
	public void addLink(int from, int to) {
		Link lFrom = links[from];
		if(lFrom == null) {
			links[from] = new Link();
			links[from].first = to;
		} else if(lFrom.first == -1) {
			lFrom.first = to;
		} else if(lFrom.second == -1) {
			lFrom.second = to;
		} else {
			//TODO: Exception or w/e?
			System.err.println("Couldn't add link: " + from + "-" + to);
		}
		
		Link lTo = links[to];
		if(lTo == null) {
			links[to] = new Link();
			links[to].first = from;
		} else if(lTo.first == -1) {
			lTo.first = from;
		} else if(lTo.second == -1) {
			lTo.second = from;
		} else {
			//TODO: Exception or w/e?
			System.err.println("Couldn't add link: " + from + "-" + to);
		}
	}
	
	public class Link {
		public int first;
		public int second;
		
		public Link() {
			first = -1;
			second = -1;
		}
		
		/**
		 * Swap first and second
		 */
		public void reverse() {
			first += second;			// f = f+s
			second = first - second;	// s = (f+s) - s = f
			first = first - second;		// f = (f+s) - f = s
		}
		
		public int getNext() {
			return first;
		}
		
		/**
		 * Gives the next index, coming from a given direction
		 * 
		 * @param prev Previous index
		 * @return
		 */
		public int getNext(int prev) {
			if(prev == first) {
				return second;				
			} else {
				return first;
			}
		}
		
	} //Link
	
//	public static void main(String[] args) {
//		Solution s = new Solution(4);
//		for(int i = 0; i < 4; i++) {
//			s.addLink(i, (i+1)%4);
//		}
//		System.out.println(s);
//		s.switchLinks(0, 1, 3, 2);
//		System.out.println(s);
//	}

	public double distance() {
		double dist = 0;
		int prev = links[0].getNext();
		int current = 0;
		int next = -1;
		while(next != 0) {
			next = links[current].getNext(prev);
			dist += Math.sqrt(problem.distance(current, next));
			prev = current;
			current = next;
		}
		return dist;
	}

	public void printSolution() {
		// TODO Auto-generated method stub
		System.out.println(this);
		
	}
	
} //Solution
