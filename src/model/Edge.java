package model;


public class Edge {
	public Point from;
	public Point to;
	//public double distance;
	public Edge(Point _from, Point _to){
		from = _from;
		to = _to;
	}
	public double distance(){
		return from.distanceArea(to);
	}


	public static double solutionLength(Edge [] solution){
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
	public static void printSolution(Edge[] edges) {
		boolean[] visited = new boolean[edges.length];
		int p = edges[0].from.id;
		loop:
			while(true){
				for(Edge e : edges){

					if(e.from.id==p && !visited[e.to.id]){
						visited[e.to.id] = true;
						p = e.to.id;
						System.out.println(p);
						continue loop;
					}
					if(e.to.id==p && !visited[e.from.id]){
						visited[e.from.id] = true;
						p = e.from.id;
						System.out.println(p);
						continue loop;
					}
				}
				break;
			}

		// TODO Auto-generated method stub

	}
}
