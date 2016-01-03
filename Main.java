import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main {
	
//	Set up basic GUI functions
    public static void main(String[] a) {

        JFrame window = new JFrame();
       
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().add(new MyCanvas());
        window.pack();
        window.setVisible(true);
    }
}

class MyCanvas extends JPanel {
	  // 400 as mid point
    int x1 = 310;
    int x2 = 490;
    int x3 = 550;
    int rotate = 90000;
    int counter = 0;

    List<Line> lines;

    Timer timer = null;
    public MyCanvas() {
        lines = new ArrayList<>();
       
        //Redudant trial use of iterator
//        ListIterator listIterator = lines.listIterator();

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rotate < -90000) {
                    ((Timer) e.getSource()).stop();
                } else {
                    lines.add(new Line(x1, x2, x3, rotate)); 
                    
                    repaint();
                    rotate--;
                    //Clear all additional lines to prevent trails
                    if (rotate < 89999){
                    	lines.remove(0);
                    }
                }
            }
        });
//Set up button to initiate
        JButton start = new JButton("Start the Magical Simulation");
        //Initialization of variable
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        add(start);

    }

    public Dimension getPreferredSize() {
//    	Create prefered dimension of window
        return new Dimension(808, 825);
    }

    private static final long serialVersionUID = 1L;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//      Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
//      Draw dots to mark out the 2 points
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.RED);
        g2.fill(new Ellipse2D.Double(310, 400, 2,5));
        g2.fill(new Ellipse2D.Double(490, 400, 2,5));
        g.drawString("-a,0", 300, 420);
        g.drawString(" a,0", 480, 420);
//      Draw the circle for visualization
        g2.setPaint(Color.ORANGE);
        g2.setStroke(new BasicStroke(3.0f));


//      Enhanced for loop to draw the lines
        for (Line line : lines) {
            line.drawLine(g);
//          Parameters : leftX, topY, leftX + width, topY + height ( for 2D function)
//          Remade the parameters to use the center of circle as reference point
//            where double newX = x - width / 2.0;
//           		double newY = y - height / 2.0;
            double newX = 550 - 240 / 2.0;
            double newY = 400 - 240 / 2.0;
            g2.draw(new Ellipse2D.Double(newX, newY, 240, 240));
            g.drawString("The circle created", 500, 250);
        }

    }

    class Line {

        int x1;
        int x2;
        int x3;
        int rotate;
      
        int y1 = 400;
        int y2 = 400;
        int y3 = 400;
       
        //TODO change flexible variables
        //From the textbook, pg 233, Q18, assuming simply A is 90, can be of any value
        
        final int A = 90;
        final int radPA = 180;
        final int radC = 120; // 4/3 * A

        public Line(int x1, int x2, int x3, int rotate) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
            this.rotate = rotate;
        }

        void drawLine(Graphics g) {
           // int Radius = (int) (Math.min(getWidth(), getHeight()) * 0.4);
        	int Radius = (int) (radPA);
        	//Length PA
            int sLength = (int) (Radius);
            // Length PB
            int sLength1 = (int) (Radius / 2);
            // Radius C
            int sLength2 = (int) (radC);
           
            int xSecond2 = (int) (x3 + sLength2 * Math.sin(rotate * (2 * Math.PI / 1000)));
            int ySecond2 = (int) (y3 - sLength2 * Math.cos(rotate * (2 * Math.PI / 1000)));
            int xSecond = (int) (xSecond2);
            int ySecond = (int) (ySecond2);
            int xSecond1 = (int) (xSecond2);
            int ySecond1 = (int) (ySecond2);
            
            g.setColor(Color.GREEN);
            g.drawLine(x1, y1, xSecond, ySecond);
            g.drawString("PA = 2PB", 250, 390);
            g.setColor(Color.BLUE);
            g.drawLine(x2, y2, xSecond1, ySecond1);
            g.drawString("PB ", 490 , 390);
            g.setColor(Color.YELLOW);
            g.drawLine(xSecond2,ySecond2, xSecond2, ySecond2);

//            g.setColor(Color.YELLOW);
//            g.drawLine(x3, y3, xSecond2, ySecond2);
        }
    }
}
