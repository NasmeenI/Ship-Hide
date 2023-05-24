package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePlayer;
import logic.person.Player;
import static utilz.Constants.Debug.SOLID_SHOW;

import application.sound.Pick;

public abstract class Gun extends GameObject implements InteractivePlayer {
	
	private static final long serialVersionUID = 1L;
	private boolean picked;
	private boolean visible;
	transient protected Image image ,imageUsed;
	
	public Gun(double xPos, double yPos, ID id, boolean picked, boolean visible) {
		super(xPos, yPos, id);
		setPicked(picked);
		setVisible(visible);
	}
	
	public Gun(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h, boolean picked, boolean visible) {
		super(xPos, yPos, id, xDif, yDif, w, h);
		setPicked(picked);
		setVisible(visible);
	}

	public void update() {
		shift();
	}
	
	public void render(GraphicsContext gc) {
		if(!isVisible()) return;
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		new Pick();
		setPicked(true);
		player.addItemInBag(this);
		player.setGun(true);
		Handler.getInstance().removeObject(this);
	}
	
	public void shoot(int xPos, int yPos, String direct, ID owner) {
		if(!shootAble()) return ;
		Bullet bullet = getBullet();
		bullet.setOwner(owner);
		reduceBullet();
		
		switch(direct) {
			case "LEFT" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos); bullet.setxVelo(-20); break;
			case "RIGHT" : bullet.setxPos(xPos); bullet.setyPos(yPos); bullet.setxVelo(20); break;
			case "UP" : bullet.setxPos(xPos); bullet.setyPos(yPos); bullet.setyVelo(-20); break;
			case "DOWN" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos); bullet.setyVelo(20); break;
			default : {
				switch(direct) {
					case "LEFT" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos); bullet.setxVelo(-20); break;
					case "RIGHT" : bullet.setxPos(xPos); bullet.setyPos(yPos); bullet.setxVelo(20); break;
					case "UP" : bullet.setxPos(xPos); bullet.setyPos(yPos); bullet.setyVelo(-20); break;
					case "DOWN" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos); bullet.setyVelo(20); break;
					default : break;
				}
				break;
			}
		}
		
		Handler.getInstance().addObject(bullet);	
	}
	
	public abstract void initImg();
	public abstract void reduceBullet();
	public abstract int getNumMagazine();
	public abstract boolean shootAble();
	public abstract boolean reload();
	public abstract Bullet getBullet();
	
	// Getter & Setter
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Image getImageUsed() {
		return imageUsed;
	}

	public void setImageUsed(Image imageUsed) {
		this.imageUsed = imageUsed;
	}
	
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}