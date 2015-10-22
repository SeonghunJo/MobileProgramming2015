package kr.ac.kookmin.shape;

public class Rectangle extends Shape{
	
	private int width, height;
	
	public Rectangle(Point point, int i, int j) {
		// TODO Auto-generated constructor stub
		center = point;
		width = i;
		height = j;
	}
	
	public Rectangle getBounds() {
		return this;
	}
	@Override
	public void draw(Graphics g) {
		g.draw(this);
	}
	@Override
	public String toString() {
		return "Rectangle";
	}
}
