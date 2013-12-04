package graphics;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Edge;
import model.Point;
import model.Solution;
import model.TSPProblem;


public class Window extends JPanel {
	
	private static int numWindows = 0;
	
	Point [] points;
	Solution solution;
	double maxX=Double.MIN_VALUE;
	double maxY=Double.MIN_VALUE;
	double minX=Double.MAX_VALUE;
	double minY=Double.MAX_VALUE;
	
	double scale = 4;
	
	/**
	 * Fixes the window scale stuff
	 * @param X window width
	 * @param Y window height
	 */
	public void calculateScale(int X, int Y){
		maxX=Double.MIN_VALUE;
		maxY=Double.MIN_VALUE;
		minX=Double.MAX_VALUE;
		minY=Double.MAX_VALUE;
		for(Point p : points){
			if(p.x>maxX){
				maxX = p.x;
			}
			if(p.y>maxY){
				maxY = p.y;
			}
			if(p.x<minX){
				minX = p.x;
			}
			if(p.y<minY){
				minY = p.y;
			}
		}
		if(maxX>maxY){
			scale = (X-40)/(maxX);
		}
		else{
			scale = (Y-40)/(maxY);
		}
		minX = minX * scale-20;
		minY = minY * scale-20;
		
	}
	public Window(TSPProblem prob,int x, int y){
		super();
		points = prob.points;
        setBackground(Color.gray);
        JFrame frame = new JFrame("Yay graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(x, y);
        frame.setVisible(true);
        frame.setLocation((x+20)*(numWindows++)+100, 100);
        this.repaint();
	}

	public void paintComponent(Graphics g)  // draw graphics in the panel
    {
        int width = getWidth();             // width of window in pixels
        int height = getHeight();           // height of window in pixels
        calculateScale(width, height);
        super.paintComponent(g);            // call superclass to make panel display correctly
        g.setColor(Color.green);
        if(solution != null) {
        	ArrayList<Integer> seq = solution.getSequence();

            Point p0 = points[seq.get(0)];
        	Point prev = p0;
            for(int i = 1; i < seq.size() + 1; i++){
            	Point curr = points[seq.get(i%seq.size())];
            	g.drawLine(
            			(int)(prev.x*scale-minX), 
            			(int)(prev.y*scale-minY), 
            			(int)(curr.x*scale-minX), 
            			(int)(curr.y*scale-minY));
            	prev = curr;
            }            
        }
        for(Point p : points){
        	if(p!=null) {
                g.setColor(Color.red);
            	g.fillOval((int)(p.x*scale-3-minX), (int)(p.y*scale-3-minY), 6, 6);
                g.setColor(Color.blue);
        		g.drawString(""+p.id, (int)(p.x*scale - minX) + 3, (int)(p.y*scale - minY) - 3 );
        	}
        }
        
    }
//	public Window addEdges(Edge[] solution) {
//		edges = solution;
//		repaint();
//		return this;
//	}
	public Window addSolution(Solution _solution) {
		solution = _solution;
		repaint();
		return this;
	}

}
