package solvers;
import model.Edge;
import model.Point;
import runnable.Main;



public class ThreeOpt {
	public void opt(Edge [] solution){
		double best_distance = Edge.solutionLength(solution);
		int n = solution.length;
		boolean breakLoop = false;
		while(true){
			double old_best_distance = best_distance;
			for(int i = 0; i<n; i++){
				for(int j = 0; j<n; j++){
					for(int k = 0; k<n; k++){

						Edge [] newSolution = findBestReconnection(i,j,k,solution);
						double new_distance = Edge.solutionLength(newSolution);

						if(new_distance < best_distance && checkIfValid(newSolution)){
							solution[i] = newSolution[i];
							solution[j] = newSolution[j];
							solution[k] = newSolution[k];
							best_distance = new_distance;
							if(!Main.KATTIS_MODE){
								Main.window.repaint();
								System.out.println("3opt: "+best_distance);
								//							try {
								//								Thread.sleep(500);
								//							} catch (InterruptedException e) {
								//								e.printStackTrace();
								//							}
							}
						}
					}
				}
			}
			if(old_best_distance==best_distance){
				if(breakLoop){
					break;
				}
				breakLoop = true;
			}
			else{
				breakLoop = false;
			}
		}
	}


	private Edge[] findBestReconnection(int i, int j, int k, Edge[] solution) {
		Edge [] returnThis = solution.clone();
		Edge [] tempArray = solution.clone();
		double best_distance = Edge.solutionLength(solution);
		if(i==j || i==k || j==k){
			return solution;
		}

		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[i].to,
				solution[j].from,solution[k].from,
				solution[j].to,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[j].from,
				solution[i].to,solution[j].to,
				solution[k].from,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[j].from,
				solution[i].to,solution[k].from,
				solution[j].to,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[j].to,
				solution[k].from,solution[i].to,
				solution[j].from,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[j].to,
				solution[k].from,solution[i].from,
				solution[i].to,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[k].from,
				solution[j].to,solution[i].to,
				solution[j].from,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		edgeSwap3opt(tempArray,i,j,k,
				solution[i].from,solution[k].from,
				solution[j].to,solution[j].from,
				solution[i].to,solution[k].to);
		if(checkIfValid(tempArray) && best_distance>Edge.solutionLength(tempArray)){
			returnThis = tempArray;
			best_distance = Edge.solutionLength(tempArray);
		}
		return returnThis;
	}


	private void edgeSwap3opt(Edge[] solution, 
			int i, int j, int k, 
			Point from, Point to, 
			Point from2, Point from3,
			Point to2, Point to3) {
		Edge p = new Edge(from, to);
		Edge q = new Edge(from2, from3);
		Edge r = new Edge(to2, to3);
		solution[i] = p;
		solution[j] = q;
		solution[k] = r;

	}


	private boolean checkIfValid(Edge[] edges) {
		boolean [] visited = new boolean[edges.length];
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
