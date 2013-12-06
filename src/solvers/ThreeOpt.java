package solvers;
import java.util.HashSet;

import model.Deadline;
import model.Edge;
import model.Point;
import model.Solution;
import runnable.Main;



public class ThreeOpt {
	private boolean breakLoop;

	public void opt(Solution solution){
		double best_distance = solution.distance();
		loop:
			while(true){
				int i = 0;
				int i1 = -1;

				double old_best_distance = best_distance;

				loop2:
					while(i1 != 0) {
						i1 = solution.links[i].getNext();

						for(Point p : solution.problem.points[i].nearbyPoints){
							int j = p.id;
							int j1 = solution.links[p.id].next;
							for(Point q : solution.problem.points[p.id].nearbyPoints){
								
								int k = q.id;
								int k1 = solution.links[k].next;

								System.out.println("Derp1 "+i+" "+j+" "+k);
								
								int caseJump = 7;

								System.out.println("Derp2");
								double
								new_distance = 0,
								distance = threeOptSwapDifference(i, i1, j, k, j1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 0;
								}
								distance = threeOptSwapDifference(i, j, i1, j1, k, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 1;
								}
								distance = threeOptSwapDifference(i, j, i1, k, j1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 2;
								}
								distance = threeOptSwapDifference(i, j1, k, i1, j, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 3;
								}
								distance = threeOptSwapDifference(i, j1, k, j, i1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 4;
								}
								distance = threeOptSwapDifference(i, k, j1, i1, j, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 5;
								}
								distance = threeOptSwapDifference(i, k, j1, j, i1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 6;
								}
//								System.out.println(new_distance+""+best_distance);
								if(new_distance>=0){
									continue;
								}
								System.out.println((best_distance+new_distance));
								System.out.println(caseJump);
								switch(caseJump){
								case 0:
									threeOptSwap(i, i1, j, k, j1, k1, solution);
//									reverseFromTo()
									best_distance = solution.distance();
									continue loop2;
								case 1:
									threeOptSwap(i, j, i1, j1, k, k1, solution);
									best_distance = solution.distance();
									continue loop2;
								case 2:
									threeOptSwap(i, j, i1, k, j1, k1, solution);
									best_distance = solution.distance();
									continue loop2;
								case 3:
									System.out.println(">"+solution.distance());
									threeOptSwap(i, j1, k, i1, j, k1, solution);
									best_distance = solution.distance();
									System.out.println(">"+solution.distance());
									continue loop2;
								case 4:
									threeOptSwap(i, j1, k, j, i1, k1, solution);
									best_distance = solution.distance();
									continue loop2;
								case 5:
									threeOptSwap(i, k, j1, i1, j, k1, solution);
									best_distance = solution.distance();
									continue loop2;
								case 6:
									threeOptSwap(i, k, j1, j, i1, k1, solution);
									best_distance = solution.distance();
									continue loop2;
								case 7:
								}
								System.out.println("derp3");
							}
						}
						i = i1;
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


	public void opt(Solution solution, Deadline d){
		double best_distance = solution.distance();
		loop:
			while(true){
				int i = 0;
				int i1 = -1;

				double old_best_distance = best_distance;

				loop2:
					while(i1 != 0) {
						i1 = solution.links[i].getNext();

						for(Point p : solution.problem.points[i].nearbyPoints){
							int j = p.id;
							int j1 = solution.links[p.id].next;
							for(Point q : solution.problem.points[p.id].nearbyPoints){
//								System.out.println("Derp1");
								int k = q.id;
								int k1 = solution.links[k].next;

								if(d.TimeUntil()<200){
									break loop;
								}
								int caseJump = 7;

//								System.out.println("Derp2");
								double distance, 
								new_distance = threeOptSwapDifference(i, i1, j, j1,k,k1, solution);
								if(new_distance < 0){
									caseJump = 0;
								}
								distance = threeOptSwapDifference(i, j, i1, j1, k, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 1;
								}
								distance = threeOptSwapDifference(i, j, i1, k, j1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 2;
								}
								distance = threeOptSwapDifference(i, j1, k, i1, j, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 3;
								}
								distance = threeOptSwapDifference(i, j1, k, j, i1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 4;
								}
								distance = threeOptSwapDifference(i, k, j1, i1, j, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 5;
								}
								distance = threeOptSwapDifference(i, k, j1, j, i1, k1, solution);
								if(distance < new_distance){
									new_distance = distance;
									caseJump = 6;
								}
//								System.out.println((best_distance-new_distance));

								switch(caseJump){
								case 0:
									threeOptSwap(i, i1, j, j1,k,k1, solution);
									continue loop2;
								case 1:
									threeOptSwap(i, j, i1, j1, k, k1, solution);
									continue loop2;
								case 2:
									threeOptSwap(i, j, i1, k, j1, k1, solution);
									continue loop2;
								case 3:
									threeOptSwap(i, j1, k, i1, j, k1, solution);
									continue loop2;
								case 4:
									threeOptSwap(i, j1, k, j, i1, k1, solution);
									continue loop2;
								case 5:
									threeOptSwap(i, k, j1, i1, j, k1, solution);
									continue loop2;
								case 6:
									threeOptSwap(i, k, j1, j, i1, k1, solution);
									continue loop2;
								case 7:
								}
							}
						}
						i = i1;
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



	private void threeOptSwap(int from1, int to1, int from2, int to2,
			int from3, int to3, Solution solution) {
		
		solution.switchLinks(from1, to1, from2, to2, from3, to3);
		
	}



	private double threeOptSwapDifference(int a, int b, int c, int d,
			int e, int f, Solution solution) {
		return 	solution.problem.distance(a, d)-solution.problem.distance(a, b)+
				solution.problem.distance(c, f)-solution.problem.distance(c, d)+ 
				solution.problem.distance(e, b)-solution.problem.distance(e, f);
	}

}
