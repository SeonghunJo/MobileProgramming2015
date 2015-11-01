package kr.ac.kookmin.shape;

public abstract class Shape {
    protected Point center;

    public Shape(Point c)
    {
    	center = c;
    }
    
    abstract public void draw(Graphics g);
    
    abstract public Rectangle getBounds();
    
    public Point getCenter()
    {
    	return center;
    }
}
