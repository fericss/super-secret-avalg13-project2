//package solvers;
//import model.Edge;
//import model.Solution;
//import runnable.Deadline;
//import runnable.Main;
//
//
//
//
//
//public class TwoOpt {
//	private boolean breakLoop;
//
//
//	public void opt(Solution solution){
//		double best_distance = Edge.solutionLength(solution);
//		int n = solution.length;
//		while(true){
//			double old_best_distance = best_distance;
//			for(int i = 0; i<n; i++){
//				for(int j = i+1; j<n;j++){
//					Edge [] newSolution = twoOptSwap(i,j,solution);
//					double new_distance = Edge.solutionLength(newSolution);
//					if(new_distance < best_distance){
//						solution[i] = newSolution[i];
//						solution[j] = newSolution[j];
//						best_distance = new_distance;
//						if(!Main.KATTIS_MODE){
//						Main.window.repaint();
//						System.out.println("2opt: "+best_distance);
//
//						}
//					}
//				}
//			}
//			
//			if(old_best_distance==best_distance){
//				if(breakLoop){
//					break;
//				}
//				breakLoop = true;
//			}
//			else{
//				breakLoop = false;
//			}
//		}
//	}
//
//
//	private Edge[] twoOptSwap(int i, int j, Edge[] solution) {
//		if(i==j){
//			return solution;
//		}
//		if(solution[i].from.equals(solution[j].from)){
//			return solution;
//		}
//		if(solution[i].to.equals(solution[j].to)){
//			return solution;
//		}
//		Edge p = new Edge(solution[i].from, solution[j].from);
//		Edge q = new Edge(solution[i].to, solution[j].to);
//
//		Edge [] returnThis = solution.clone();
//		returnThis[i] = p;
//		returnThis[j] = q;
//		
//		if(!checkIfValid(returnThis)){
//			p = new Edge(solution[i].to, solution[j].from);
//			q = new Edge(solution[i].from, solution[j].to);
//			returnThis[i] = p;
//			returnThis[j] = q;
//			
//			return returnThis;
//		}
//		return returnThis;
//	}
//
//
//	private boolean checkIfValid(Edge[] edges) {
//		boolean [] visited = new boolean[edges.length+1];
//		for(int i = 0; i < visited.length; i++){
//			visited[i] = false;	//Note: this might already be done when allocating, research?
//		}
//		int p = edges[0].from.id;
//		int counter = 1;
//		visited[p] = true;
//		loop:
//		while(true){
//			for(Edge e : edges){
//				if(e.from.id==p && !visited[e.to.id]){
//					visited[e.to.id] = true;
//					p = e.to.id;
//					counter++;
//					continue loop;
//				}
//				if(e.to.id==p && !visited[e.from.id]){
//					visited[e.from.id] = true;
//					p = e.from.id;
//					counter++;
//					continue loop;
//				}
//			}
//			break;
//		}
//		return counter==edges.length; 
//		
//	}
//
//
//	public void opt(Edge[] solution, Deadline d) {
//		double best_distance = Edge.solutionLength(solution);
//		int n = solution.length;
//		loop:
//		while(true){
//			double old_best_distance = best_distance;
//			for(int i = 0; i<n; i++){
//				for(int j = i+1; j<n;j++){
//					if(d.TimeUntil()<100){
//						break loop;
//					}
//					Edge [] newSolution = twoOptSwap(i,j,solution);
//					double new_distance = Edge.solutionLength(newSolution);
//					if(new_distance < best_distance){
//						solution[i] = newSolution[i];
//						solution[j] = newSolution[j];
//						best_distance = new_distance;
//						if(!Main.KATTIS_MODE){
//						Main.window.repaint();
//						System.out.println("2opt: "+best_distance);
////						try {
////							Thread.sleep(100);
////						} catch (InterruptedException e) {
////							e.printStackTrace();
////						}
//						}
//					}
//				}
//			}
//			
//			if(old_best_distance==best_distance){
//				if(breakLoop){
//					break;
//				}
//				breakLoop = true;
//			}
//			else{
//				breakLoop = false;
//			}
//		}
//		
//	}
//
//
//
//
//}