package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;

public abstract class Gun extends GameObject {

	public Gun(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
	}
	
	public Gun(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h) {
		super(xPos, yPos, id, xDif, yDif, w, h);
	}

	public abstract void update();
	public abstract void initImg();
	public abstract void render(GraphicsContext gc);
	
	public abstract void shoot(int xPos, int yPos, String direct, ID owner);
	public abstract boolean shootAble();
	public abstract boolean reload();
	public abstract void reduceBullet();
	public abstract Bullet getBullet();
	public abstract int getNumMagazine();
	
}
