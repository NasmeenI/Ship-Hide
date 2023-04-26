package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Map;
import static utilz.Constants.Debug.*;

public class Bullet extends GameObject implements Attackable {
	
	private static final long serialVersionUID = 1L;
	private int maxDamage, minDamage;
	
	public Bullet(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 0, 0, 16, 16);
		setxVelo(0);
		setyVelo(0);
		setMinDamage(30);
		setMaxDamage(120);
	}
	
	public Bullet(double xPos, double yPos, ID id, double xVelo, double yVelo) {
		super(xPos, yPos, id, 0, 0, 16, 16);
		setxVelo(xVelo);
		setyVelo(yVelo);
		setMinDamage(30);
		setMaxDamage(120);
	}

	public Bullet(double xPos, double yPos, ID id, double xVelo, double yVelo, int MxD, int MnD) {
		super(xPos, yPos, id, 0, 0, 16, 16);
		setxVelo(xVelo);
		setyVelo(yVelo);
		setMinDamage(MxD);
		setMaxDamage(MnD);
	}
	
	public Bullet(double xPos, double yPos, ID id, int MxD, int MnD) {
		super(xPos, yPos, id, 0, 0, 16, 16);
		setMinDamage(MxD);
		setMaxDamage(MnD);
	}

	@Override
	public void update() {
//		move();
		
		double _Vx = getxVelo();
		double _Vy = getyVelo();
		
		int newXPos = 0 ,newYPos = 0;
		if(_Vx >= 0) newXPos = ((int)((getxPos() + _Vx)/48));
		else newXPos = ((int)((getxPos() + _Vx)/48));	
		
		if(_Vy >= 0) newYPos = ((int)((getyPos() + _Vy)/48)); 	
		else newYPos = ((int)((getyPos() + _Vy)/48));
		
//		Obj.collisionTwo(this);
		
		if(Map.getInstance().mapTileNum[newYPos][newXPos] != 0) {
			setxPos(getxPos() + _Vx);
			setyPos(getyPos() + _Vy);
		}
		else {
			Handler.getInstance().removeObject(this);
		}
		
		setxVelo(_Vx);
		setyVelo(_Vy);
		
		setSolidArea(new Rectangle((int)getxPos(), (int)getyPos(), 16, 16));
		return ;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.setFill(Color.BLACK);
		gc.fillOval((int)getxPos(), (int)getyPos(), 8, 8);
		return ;
	}
	
	@Override
	public int damage() {
		return (int)((Math.random()*(getMaxDamage()-getMinDamage()+1)+getMinDamage())) * 10; 
	}

	@Override
	public int dps_damage() {
		return 0;
	}
	
	@Override
	public void initImg() {
		// TODO Auto-generated method stub
		
	}
	
	
	// Getters & Setters
	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
		return ;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
		return ;
	}
}
