package kr.ac.kookmin.shape;

public class Point {
	// Implement here
	int x, y;
	
	Point() {
		x = y = 0;
	}
	
	Point(int px, int py) {
		x = px;
		y = py;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int nx) {
		x = nx;
	}
	
	public void setY(int ny) {
		y = ny;
	}
}
