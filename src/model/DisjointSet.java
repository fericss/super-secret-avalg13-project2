package model;

import java.util.HashMap;

/**
 * Disjoint-set Forest implementation
 * 
 * See http://en.wikipedia.org/wiki/Disjoint-set_data_structure#Disjoint-set_forests
 * 
 * @author Micke
 *
 * @param <Type> Type of object in the sets
 */
public class DisjointSet<Type> {
	//ArrayList<Node> sets;
	HashMap<Type, Node> map = new HashMap<Type, Node>();
	private int numSets = 0;
	
	public int numSets() {
		return numSets;
	}
	
	public void makeSet(Type t) {
		Node n = new Node(t);
		//sets.add(n);
		map.put(t, n);
		numSets++;
	}
	
	public Node findSet(Type t) { return findSet( map.get(t) ); }
	
	public Node findSet(Node n) {
		if(n.parent != n) {
			n.parent = findSet(n.parent);
		}
		return n.parent;
	}
	
	public void Union(Type x, Type y) {
		Union(map.get(x), map.get(y));
	}
	
	public void Union(Node x, Node y) {
		Node xRoot = findSet(x);
		Node yRoot = findSet(y);
		if(xRoot == yRoot) return;
		
		if(xRoot.rank < yRoot.rank) {
			xRoot.parent = yRoot;
		} else if(xRoot.rank > yRoot.rank) {
			yRoot.parent = xRoot;				
		} else {
			yRoot.parent = xRoot;
			xRoot.rank++;
		}
		numSets--;
	}
	
	public class Node {
		Node next;
		Node parent;
		Type object;
		int rank;

		public Node(Type _obj) {
			object = _obj;
			parent = this;
			next = null;
			rank = 0;
		}
		
		public Type getObj() {
			return object;
		}
	}
}