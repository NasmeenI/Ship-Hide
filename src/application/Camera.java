package application;


import logic.base.Handler;
import logic.person.Player;
import static utilz.Constants.Screen.*;

public class Camera {
	
	private double x, y;
	private Handler handler;
	private double Player_xPos = Player._CurxPos;
	private double Player_yPos = Player._CuryPos;
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		this.handler = Handler.getInstance();
		getPlayer();
	}
	
	public void getPlayer() {
		Player_xPos = Player._CurxPos;
		Player_yPos = Player._CuryPos;
		return ;
	}
	
	public void update() {
		getPlayer();
		x = Player_xPos - S_WIDTH_DEFAULT / 2 + 30;
		y = Player_yPos - S_HEIGHT_DEFAULT / 2 + 40;
		return ;
	}
	
	// Getters & Setters

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		return ;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		return ;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
		return ;
	}
	
}
