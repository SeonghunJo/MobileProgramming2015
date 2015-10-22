package kr.ac.kookmin.shape;

public abstract class Shape {
    protected Point center;

    Shape(Point c)
    {
    	center = c;
    }
    
    public void draw(Graphics g)
    {
    	
    }
    
    public Rectangle getBounds()
    {
    	return null;
    }	
    
    public Point getCenter()
    {
    	return center;
    }
}
