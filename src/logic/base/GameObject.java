package logic.base;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utilz.Checker;

public abstract class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected double xPos, yPos;
	protected double xVelo, yVelo;
	protected double xDif, yDif, w, h;
	protected ID id;
	protected int code;
	protected boolean beforeTwo;
	private double littleMove;
	private double count;
	transient protected Rectangle solidArea;
	
	public GameObject(double xPos, double yPos, ID id) {
		setxPos(xPos);
		setyPos(yPos);
		setxDif(0);
		setyDif(0);
		setW(0);
		setH(0);
		setxVelo(0);
		setyVelo(0);
		setId(id);
		setSolidArea(new Rectangle(xPos ,yPos ,0 ,0));
		setCode(Handler.code++);
		setBeforeTwo(true);
		setLittleMove(Checker.rand(-1, 1));
		setCount(.05f);
	}
	
	public GameObject(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h) {
		setxPos(xPos);
		setyPos(yPos);
		setxDif(xDif);
		setyDif(yDif);
		setW(w);
		setH(h);
		setxVelo(0);
		setyVelo(0);
		setId(id);
		setSolidArea(new Rectangle(xPos+xDif ,yPos+yDif ,w ,h));
		setCode(Handler.code++);
		setBeforeTwo(true);
		setLittleMove(Checker.rand(-1, 1));
		setCount(.05f);
	}

	public void move() {
		setxPos(getxPos() + getxVelo());
		setyPos(getyPos() + getyVelo());
	}
	
	public Point getMiddlePoint(Rectangle A) {
		int xMid = (int) (A.getX() + A.getWidth() / 2);
		int yMid = (int) (A.getY() + A.getHeight() / 2);
		return new Point(xMid, yMid);
	}
	
	public void shift() {
		if(littleMove <= -1) count = 0.05f;
		if(littleMove >= 1) count = -0.05f;
		
		littleMove += count;
		
		setyPos(getyPos() + littleMove);
	}
	
	public void ShowSolidArea(GraphicsContext gc) {
		gc.setFill(Color.PINK);
		gc.fillRect((int)getxPos() + getxDif(), (int)getyPos() + getyDif(), solidArea.getWidth(), solidArea.getHeight());
	}
	
	public abstract void update();
	public abstract void initImg();
	public abstract void render(GraphicsContext gc);
	
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
	
	public double getxDif() {
		return xDif;
	}

	public void setxDif(double xDif) {
		this.xDif = xDif;
	}

	public double getyDif() {
		return yDif;
	}

	public void setyDif(double yDif) {
		this.yDif = yDif;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
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
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public boolean isBeforeTwo() {
		return beforeTwo;
	}

	public void setBeforeTwo(boolean beforeTwo) {
		this.beforeTwo = beforeTwo;
	}

	public double getLittleMove() {
		return littleMove;
	}

	public void setLittleMove(double littleMove) {
		this.littleMove = littleMove;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}
