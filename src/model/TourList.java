package model;

import java.util.LinkedList;


public class TourList {
	TourNode currNode;
	public class TourNode {
		TourNode next;
		TourNode prev;
		public Point p;
		
		public TourNode(Point _p) {
			p = _p;
			next = this;
			prev = this;
		}
	}
	
	public LinkedList<Point> getTour() {
		TourNode start = currNode;
		LinkedList<Point> tour = new LinkedList<Point>();
		do {
			tour.add(currNode.p);
			next();
		} while(currNode != start);
		return tour;
	}
	
	public TourNode getCurr() {
		return currNode;
	}		
	public void next() {
		currNode = currNode.next;
	}
	public TourNode getNext() {
		next();
		return getCurr();
	}
	
	public void insert(Point p) {
		insert(new TourNode(p));
	}
	public void insert(TourNode node) {
		if(currNode == null) {
			currNode = node;
		} else {
			insert(node, currNode.prev);
		}
	}
	
	public void insert(Point p, TourNode after) {
		insert(new TourNode(p), after);
	}
	public void insert(TourNode node, TourNode after) {
		TourNode next = after.next;
		after.next = node;
		node.prev = after;
		
		node.next = next;
		next.prev = node;
	}
	public void insertList(TourList list, TourNode after) {
		TourNode node = list.getCurr();
		TourNode next = after.next;
		after.next = node;
		node.prev = after;
		
		TourNode prev = node.prev;
		prev.next = next;
		next.prev = prev;
	}
}