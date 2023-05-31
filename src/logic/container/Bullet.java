package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.Chaseable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePerson;
import logic.base.Map;
import logic.person.Captive;
import logic.person.Commander;
import logic.person.Criminal;
import logic.person.Person;
import utilz.Checker;
import utilz.LoadSave;

import static utilz.Constants.Debug.*;

public class Bullet extends GameObject implements Attackable, InteractivePerson {
	
	private static final long serialVersionUID = 1L;
	private int maxDamage, minDamage;
	private ID owner = null;
	private String direct;
	
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
		direct = Checker.getDirectionByVelo(getxVelo(), getyVelo());	
		
		setSolidArea(new Rectangle((int)getxPos() + 7, (int)getyPos() + 3, 16, 16));
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		switch(Checker.getDirectionByVelo(getxVelo(), getyVelo())) {
			case "L" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Left), xPos, yPos); break;
			case "R" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Right), xPos, yPos); break;
			case "U" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Up), xPos, yPos); break;
			case "D" : gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.Player_Bullet_Down), xPos, yPos); break;
			case "null" : break;
			default : break;
		}
	}
	
	public int damage() {
		return (int)((Math.random()*(getMaxDamage()-getMinDamage()+1)+getMinDamage())) * 10; 
	}

	public int dpsDamage() {
		return 0;
	}
	
	public void interact(Person person) {
		if(getOwner() == person.getId()) return;
		if(person instanceof Criminal && getOwner() == ID.Commander) return;
		if(person instanceof Commander && getOwner() == ID.Criminal) return;
		if(person instanceof Captive && getOwner() == ID.Player) return;
		
		person.setHp(person.getHp() - damage());
		Handler.getInstance().removeObject(this);
		
		if(person instanceof Chaseable chaseable) chaseable.setChase();
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
	
	public String getDirect() {
		return this.direct;
	}
}
