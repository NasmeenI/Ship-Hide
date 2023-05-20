package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.person.Player;
import static utilz.Constants.Debug.SOLID_SHOW;

public abstract class Gun extends GameObject {
	
	private static final long serialVersionUID = 1L;
	transient protected Image image ,imageUsed;
	public boolean picked;
	private boolean visible;
	private double littleMove = 1;
	private double count = 0;
	
	public Gun(double xPos, double yPos, ID id, boolean picked, boolean visible) {
		super(xPos, yPos, id);
		this.picked = picked;
		this.visible = visible;
	}
	
	public Gun(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h, boolean picked, boolean visible) {
		super(xPos, yPos, id, xDif, yDif, w, h);
		this.picked = picked;
		this.visible = visible;
	}

	public void update() {
		if(littleMove <= -1) count = 0.05f;
		if(littleMove >= 1) count = -0.05f;
		
		littleMove += count;
		
		setyPos(getyPos() + littleMove);
	}
	
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		player.setGun(true);
		Handler.getInstance().removeObject(this);
	}
	
	public void render(GraphicsContext gc) {
		if(!isVisible()) return;
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}

	public abstract void shoot(int xPos, int yPos, String direct, ID owner);
	public abstract void initImg();
	public abstract boolean shootAble();
	public abstract boolean reload();
	public abstract void reduceBullet();
	public abstract Bullet getBullet();
	public abstract int getNumMagazine();
	
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