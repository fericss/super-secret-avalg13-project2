import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JPanel {
	
	Point [] points;
	Edge [] edges;
	double scale = 4;
	
	public Window(Point [] _points){
		super();
		points = _points;
        setBackground(Color.gray);
        JFrame frame = new JFrame("Yay graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(500, 500);
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

        super.paintComponent(g);            // call superclass to make panel display correctly
        g.setColor(Color.red);
        for(Point p : points){
        	g.fillOval((int)(p.x*scale-3), (int)(p.y*scale-3), 6, 6);
        }
        g.setColor(Color.green);
        if(edges!=null)
        for(Edge e : edges){
        	if(e!=null)
        	g.drawLine((int)(e.from.x*scale), (int)(e.from.y*scale), (int)(e.to.x*scale), (int)(e.to.y*scale));
        }
        
        // Drawing code goes here
    }
	public void addEdges(Edge[] solution) {
		// TODO Auto-generated method stub
		edges = solution;
		repaint();
	}

}
