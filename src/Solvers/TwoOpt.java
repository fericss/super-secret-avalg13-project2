package Solvers;
import Main.Main;
import Model.Edge;


public class TwoOpt {
	public Edge [] opt(Edge [] solution){
		double best_distance = Edge.solutionLength(solution);
		int n = solution.length;
		while(true){
			for(int i = (int)(Math.random()*n); i<n; i++){
				for(int j = 0; j<n;j++){
					Edge [] newSolution = twoOptSwap(i,j,solution);
					double new_distance = Edge.solutionLength(newSolution);
					if(new_distance < best_distance){
						solution[i] = newSolution[i];
						solution[j] = newSolution[j];
						best_distance = new_distance;
						Main.window.repaint();
						System.out.println(best_distance);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}


	private Edge[] twoOptSwap(int i, int j, Edge[] solution) {
		if(i==j){
			return solution;
		}
		if(solution[i].from.equals(solution[j].from)){
			return solution;
		}
		if(solution[i].to.equals(solution[j].to)){
			return solution;
		}
		Edge p = new Edge(solution[i].from, solution[j].from);
		Edge q = new Edge(solution[i].to, solution[j].to);

		Edge [] returnThis = solution.clone();
		returnThis[i] = p;
		returnThis[j] = q;
		
		if(!checkIfValid(returnThis)){
			p = new Edge(solution[i].to, solution[j].from);
			q = new Edge(solution[i].from, solution[j].to);
			returnThis[i] = p;
			returnThis[j] = q;
			
			return returnThis;
		}
		return returnThis;
	}


	private boolean checkIfValid(Edge[] edges) {
		boolean [] visited = new boolean[edges.length+1];
		for(int i = 0; i < visited.length; i++){
			visited[i] = false;	//Note: this might already be done when allocating, research?
		}
		int p = edges[0].from.id;
		int counter = 1;
		visited[p] = true;
		loop:
		while(true){
			for(Edge e : edges){
				if(e.from.id==p && !visited[e.to.id]){
					visited[e.to.id] = true;
					p = e.to.id;
					counter++;
					continue loop;
				}
				if(e.to.id==p && !visited[e.from.id]){
					visited[e.from.id] = true;
					p = e.from.id;
					counter++;
					continue loop;
				}
			}
			break;
		}
		return counter==edges.length; 
		
	}




}
