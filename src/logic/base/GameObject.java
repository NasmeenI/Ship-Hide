package logic.base;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
	
	protected double xPos, yPos;
	protected double xVelo, yVelo;
	protected Rectangle solidArea;
	protected ID id;
	protected int Code;
	// ebug
	public static boolean solidShow = false;
	
	public GameObject(double xPos, double yPos, ID id) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVelo = 0;
		this.yVelo = 0;
		this.id = id;
		this.solidArea = new Rectangle(xPos ,yPos ,0 ,0);
		this.Code = Handler.Code++;
	}
	
	public GameObject(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVelo = 0;
		this.yVelo = 0;
		this.id = id;
		this.solidArea = new Rectangle(xPos+xDif ,yPos+yDif ,w ,h);
		this.Code = Handler.Code++;
	}

	public void move() {
		setxPos(getxPos() + getxVelo());
		setyPos(getyPos() + getyVelo());
	}
	
	public abstract void update();
	public abstract void render(GraphicsContext gc);
	
	public void ShowSolidArea(GraphicsContext gc, int Xdif, int Ydif) {
		gc.setFill(Color.PINK);
		gc.fillRect((int)getxPos() + Xdif, (int)getyPos() + Ydif, solidArea.getWidth(), solidArea.getHeight());
	}
	
	// Getters & Setters
	
	public Rectangle getSolidArea() {
		return solidArea;
	}

	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
		return ;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
		return ;
	}

	public double getxVelo() {
		return xVelo;
	}

	public void setxVelo(double xVelo) {
		this.xVelo = xVelo;
		return ;
	}

	public double getyVelo() {
		return yVelo;
	}

	public void setyVelo(double yVelo) {
		this.yVelo = yVelo;
		return ;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
		return ;
	}
	
	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
		return ;
	}
}
