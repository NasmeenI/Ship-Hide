package logic.base;

public class Point {
	
	private double x, y;

	public Point(double x, double y) {
		setX(x);
		setY(y);
	}
	
	// Getter & Setter
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
