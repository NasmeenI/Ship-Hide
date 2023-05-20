package application;

import logic.person.Player;
import static utilz.Constants.Screen.*;

public class Camera {
	
	private double x, y;
	private double playerXPos;
	private double playerYPos;
	
	public Camera(double x, double y) {
		setX(x);
		setY(y);
		setPlayerXPos(Player.getCurxPos());
		setPlayerYPos(Player.getCuryPos());
		getPlayer();
	}
	
	public void getPlayer() {
		setPlayerXPos(Player.getCurxPos());
		setPlayerYPos(Player.getCuryPos());
	}
	
	public void update() {
		getPlayer();
		x = getPlayerXPos() - S_WIDTH_DEFAULT / 2 + 30;
		y = getPlayerYPos() - S_HEIGHT_DEFAULT / 2 + 40;
	}

	// Getters & Setters
	
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

	public double getPlayerXPos() {
		return playerXPos;
	}

	public void setPlayerXPos(double playerXPos) {
		this.playerXPos = playerXPos;
	}

	public double getPlayerYPos() {
		return playerYPos;
	}

	public void setPlayerYPos(double playerYPos) {
		this.playerYPos = playerYPos;
	}
}
