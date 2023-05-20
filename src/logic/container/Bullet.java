package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Map;
import utilz.Checker;
import utilz.LoadSave;

import static utilz.Constants.Debug.*;

public class Bullet extends GameObject implements Attackable {
	
	private static final long serialVersionUID = 1L;
	private int maxDamage, minDamage;
	private ID owner = null;
	
	public Bullet(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 7, 3, 16, 16);
		setxVelo(0);
		setyVelo(0);
		setMinDamage(30);
		setMaxDamage(50);
	}
	
	public Bullet(double xPos, double yPos, ID id, double xVelo, double yVelo) {
		super(xPos, yPos, id, 7, 3, 16, 16);
		setxVelo(xVelo);
		setyVelo(yVelo);
		setMinDamage(30);
		setMaxDamage(50);
	}

	public Bullet(double xPos, double yPos, ID id, double xVelo, double yVelo, int MxD, int MnD) {
		super(xPos, yPos, id, 7, 3, 16, 16);
		setxVelo(xVelo);
		setyVelo(yVelo);
		setMinDamage(MxD);
		setMaxDamage(MnD);
	}
	
	public Bullet(double xPos, double yPos, ID id, int MxD, int MnD) {
		super(xPos, yPos, id, 7, 3, 16, 16);
		setMinDamage(MxD);
		setMaxDamage(MnD);
	}
	
	public void initImg() {

	}

	@Override
	public void update() {
		
		double _Vx = getxVelo();
		double _Vy = getyVelo();
		
		int newXPos = 0 ,newYPos = 0;
		if(_Vx >= 0) newXPos = ((int)((getxPos() + _Vx)/48));
		else newXPos = ((int)((getxPos() + _Vx)/48));	
		
		if(_Vy >= 0) newYPos = ((int)((getyPos() + _Vy)/48)); 	
		else newYPos = ((int)((getyPos() + _Vy)/48));
		
		if(Map.getInstance().getMapTileNum()[newYPos][newXPos] == 0) {
			Handler.getInstance().removeObject(this);
		}
		
		setxPos(getxPos() + _Vx);
		setyPos(getyPos() + _Vy);
		
		setxVelo(_Vx);
		setyVelo(_Vy);
		
		setSolidArea(new Rectangle((int)getxPos() + 7, (int)getyPos() + 3, 16, 16));
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		switch(Checker.GetDirectionByVelo(getxVelo(), getyVelo())) {
			case "L" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Left), xPos, yPos); break;
			case "R" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Right), xPos, yPos); break;
			case "U" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Up), xPos, yPos); break;
			case "D" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Down), xPos, yPos); break;
			default : break;
		}
	}
	
	@Override
	public int damage() {
		return (int)((Math.random()*(getMaxDamage()-getMinDamage()+1)+getMinDamage())) * 10; 
	}

	@Override
	public int dps_damage() {
		return 0;
	}
	
	// Getter & Setter
	
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

	public ID getOwner() {
		return owner;
	}

	public void setOwner(ID owner) {
		this.owner = owner;
	}
}
