
public class Edge {
	Point from;
	Point to;
	//public double distance;
	public Edge(Point _from, Point _to){
		from = _from;
		to = _to;
	}
	public double distance(){
		return from.distanceArea(to);
	}
	
	
	static double solutionLength(Edge [] solution){
		double dist = 0.0;
		for(Edge e : solution){
			dist+= Math.sqrt(e.distance());
		}
		return dist;
		
	}
	

	public boolean equals(Object obj){
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Edge))
            return false;
		return (this.from.equals(((Edge)obj).from) && this.to.equals(((Edge)obj).to));
		
	}
}
