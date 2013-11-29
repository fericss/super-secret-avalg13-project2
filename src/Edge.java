
public class Edge {
	Point from;
	Point to;
	public double distance;
	public Edge(Point _from, Point _to){
		from = _from;
		to = _to;
		distance = from.distanceArea(to);
	}
	
}
