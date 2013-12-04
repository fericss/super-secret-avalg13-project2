package solvers;
import model.Edge;
import model.Point;
import model.Solution;
import model.Deadline;
import model.Solution.Link;
import runnable.Main;





public class TwoOpt {
	private boolean breakLoop;


	public void opt(Solution solution){
		double best_distance = solution.distance();
		int n = solution.problem.size;
		loop:
		while(true){
			int prev = solution.links[0].getNext();
			int from = 0;
			int to = -1;

			double old_best_distance = best_distance;

			loop2:
			while(to != 0) {
				to = solution.links[from].getNext(prev);
				//				dist += Math.sqrt(solution.problem.distance(current, next));
//				System.out.println("Checking link from "+from+" to "+to);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println(from+">"+to);
				Point [] nearby = solution.problem.points[from].nearbyPoints;

				for(Point p : nearby){
					int from1 = p.id;
					int to1 = solution.links[p.id].first;
					double difference = twoOptSwapDifference(from, to, from1, to1, solution);
					if(difference<0){
						best_distance+=difference;
//						System.out.println("derp?");
//						System.out.println("changing "+from+":"+to+" to "+from1+":"+to1);
						twoOptSwap(from,to,from1,to1,solution);
//						System.out.println("derp!");
//						System.out.println(solution.distance());
//						Main.window.repaint();
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
						continue loop2;
					}

				}
				prev = from;
				from = to;
			}


			//			for(int i = 0; i<n; i++){
			//				for(int j = i+1; j<n;j++){
			//					double new_distance = twoOptSwapDistance(i,j,solution);
			//					//					if(new_distance < best_distance){
			//					//						solution[i] = newSolution[i];
			//					//						solution[j] = newSolution[j];
			//					best_distance = new_distance;
			//					if(!Main.KATTIS_MODE){
			//						Main.window.repaint();
			//						System.out.println("2opt: "+best_distance);
			//
			//					}
			//				}
			//			}
			//		}

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
	
	public void opt(Solution solution, Deadline d){
		double best_distance = solution.distance();
		int n = solution.problem.size;
		loop:
		while(true){
			int prev = solution.links[0].getNext();
			int from = 0;
			int to = -1;

			double old_best_distance = best_distance;

			loop2:
			while(to != 0) {
				to = solution.links[from].getNext(prev);
				//				dist += Math.sqrt(solution.problem.distance(current, next));
//				System.out.println("Checking link from "+from+" to "+to);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println(from+">"+to);
				Point [] nearby = solution.problem.points[from].nearbyPoints;

				if(d.TimeUntil()<200){
					break loop;
				}
				for(Point p : nearby){
					int from1 = p.id;
					int to1 = solution.links[p.id].first;
					double difference = twoOptSwapDifference(from, to, from1, to1, solution);
					if(difference<0){
						best_distance+=difference;
//						System.out.println("derp?");
//						System.out.println("changing "+from+":"+to+" to "+from1+":"+to1);
						twoOptSwap(from,to,from1,to1,solution);
//						System.out.println("derp!");
//						System.out.println(solution.distance());
//						Main.window.repaint();
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
						continue loop2;
					}

				}
				prev = from;
				from = to;
			}


			//			for(int i = 0; i<n; i++){
			//				for(int j = i+1; j<n;j++){
			//					double new_distance = twoOptSwapDistance(i,j,solution);
			//					//					if(new_distance < best_distance){
			//					//						solution[i] = newSolution[i];
			//					//						solution[j] = newSolution[j];
			//					best_distance = new_distance;
			//					if(!Main.KATTIS_MODE){
			//						Main.window.repaint();
			//						System.out.println("2opt: "+best_distance);
			//
			//					}
			//				}
			//			}
			//		}

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


	private void twoOptSwap(int from, int to, int from1, int to1, Solution solution) {

		solution.changeLinkDestination(from, to, from1);
		solution.changeLinkDestination(from1, to1, from);

		solution.changeLinkDestination(to, from, to1);
		solution.changeLinkDestination(to1, from1, to);


		int prev = from;
		int current = from1;
		int next = -1;
		while(next != to1) {
//			System.out.println(from+" "+to+" >> "+from1+" "+to1);
//			System.out.println(">"+current);
			next = solution.links[current].getNext(prev);
//			System.out.println(next);
			solution.links[current].reverse();

			prev = current;
			current = next;
			if(prev==current){
				break;
			}
		}

	}


	private double twoOptSwapDifference(int from1, int to1, int from2, int to2, Solution solution) {
		if(from1 == from2 || from1 == to2 || from2 == to1 || to1==to2){
			return Double.MAX_VALUE;
		}

		double distance1 = 	solution.problem.distance(from1, to1)+
				solution.problem.distance(from2, to2);

		double distance2 = 	solution.problem.distance(from1, from2)+
				solution.problem.distance(to1, to2);
		
		return distance2-distance1;
		

	}


	//	private Edge[] twoOptSwap(int i, int j, Solution solution) {
	//
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
	//			while(true){
	//				for(Edge e : edges){
	//					if(e.from.id==p && !visited[e.to.id]){
	//						visited[e.to.id] = true;
	//						p = e.to.id;
	//						counter++;
	//						continue loop;
	//					}
	//					if(e.to.id==p && !visited[e.from.id]){
	//						visited[e.from.id] = true;
	//						p = e.from.id;
	//						counter++;
	//						continue loop;
	//					}
	//				}
	//				break;
	//			}
	//		return counter==edges.length; 
	//
	//	}
	//
	//
	//	public void opt(Edge[] solution, Deadline d) {
	//		double best_distance = Edge.solutionLength(solution);
	//		int n = solution.length;
	//		loop:
	//			while(true){
	//				double old_best_distance = best_distance;
	//				for(int i = 0; i<n; i++){
	//					for(int j = i+1; j<n;j++){
	//						if(d.TimeUntil()<100){
	//							break loop;
	//						}
	//						Edge [] newSolution = twoOptSwap(i,j,solution);
	//						double new_distance = Edge.solutionLength(newSolution);
	//						if(new_distance < best_distance){
	//							solution[i] = newSolution[i];
	//							solution[j] = newSolution[j];
	//							best_distance = new_distance;
	//							if(!Main.KATTIS_MODE){
	//								Main.window.repaint();
	//								System.out.println("2opt: "+best_distance);
	//								//						try {
	//								//							Thread.sleep(100);
	//								//						} catch (InterruptedException e) {
	//								//							e.printStackTrace();
	//								//						}
	//							}
	//						}
	//					}
	//				}
	//
	//				if(old_best_distance==best_distance){
	//					if(breakLoop){
	//						break;
	//					}
	//					breakLoop = true;
	//				}
	//				else{
	//					breakLoop = false;
	//				}
	//			}
	//
	//	}




}
