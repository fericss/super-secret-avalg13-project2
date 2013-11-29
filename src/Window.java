import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JPanel {
	
	Point [] points;
	Edge [] edges;
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
			scale = (X-20)/(maxX);
		}
		else{
			scale = (Y-20)/(maxY);
		}
		minX = minX * scale-20;
		minY = minY * scale-20;
		
	}
	public Window(Point [] _points){
		super();
		points = _points;
        setBackground(Color.gray);
        JFrame frame = new JFrame("Yay graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        this.repaint();
	}
	public Window(Point [] _points, Edge [] _edges){
		super();
		points = _points;
		edges = _edges;
        setBackground(Color.gray);
        JFrame frame = new JFrame("Yay graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(500, 500);
        frame.setVisible(true);
        this.repaint();
	}
	
	public void paintComponent(Graphics g)  // draw graphics in the panel
    {
        int width = getWidth();             // width of window in pixels
        int height = getHeight();           // height of window in pixels
        calculateScale(width, height);
        super.paintComponent(g);            // call superclass to make panel display correctly
        g.setColor(Color.red);
        for(Point p : points){
        	if(p!=null)
        	g.fillOval((int)(p.x*scale-3-minX), (int)(p.y*scale-3-minY), 6, 6);
        }
        g.setColor(Color.green);
        if(edges!=null)
        for(Edge e : edges){
        	if(e!=null)
        	g.drawLine(
        			(int)(e.from.x*scale-minX), 
        			(int)(e.from.y*scale-minY), 
        			(int)(e.to.x*scale-minX), 
        			(int)(e.to.y*scale-minY));
        }
        
    }
	public void addEdges(Edge[] solution) {
		edges = solution;
		repaint();
	}

}
