package logic.container;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Pickable;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Player.*;
import static utilz.Constants.Debug.*;

public class Gun extends GameObject implements Attackable, StableObject, Pickable {
	
	private Magazine magazine;
	public boolean picked;
	private boolean visible;
	private Handler handler;
	private Image image;

	public Gun(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.magazine = new Magazine(xPos, yPos, ID.Magazine, 0, 6);
		this.visible = false;
		this.picked = false;
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		initImg();
	}
	
	public Gun(double xPos, double yPos, ID id, int bulletNum) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.magazine = new Magazine(xPos, yPos, ID.Magazine, bulletNum, bulletNum);
		this.visible = false;
		this.picked = false;
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		initImg();
	}
	
	public Gun(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.magazine = new Magazine(xPos, yPos, ID.Magazine, 0, 6);
		this.visible = visible;
		this.picked = false;
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		initImg();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	private void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.GUN);
	}

	@Override
	public void render(GraphicsContext gc) {
		if(!isVisible()) return ;
		if(SOLID_SHOW) ShowSolidArea(gc, 10, 10);
		
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	@Override
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		player.setGun(true);
		handler.removeObject(this);
	}
	
	public boolean shootAble() {
		if(magazine.getMagazine().isEmpty() || magazine.getNumBullet() == 0) return false;
		return true;
 	}
	
	public boolean reload() { // More to fix (Delay)
		ArrayList<GameObject> bag = handler.Player.getBag();
		boolean isReload = false;
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Magazine) {
				magazine = ((Magazine)bag.get(i));
				handler.Player.getBag().remove(bag.get(i));
				isReload = true;
				break;
			}
		}
		return isReload;
	}

	@Override
	public int damage() {
		if(!shootAble()) return 0;
		return magazine.getMagazine().get(0).damage();
	}
	
	public void shoot(int xPos, int yPos, String direct) {
		if(!shootAble()) return ;
		Bullet bullet = getBullet();
		reduceBullet();
		
		switch(direct) {
			case "LEFT" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos + 40); bullet.setxVelo(-20); break;
			case "RIGHT" : bullet.setxPos(xPos + 70); bullet.setyPos(yPos + 40); bullet.setxVelo(20); break;
			case "UP" : bullet.setxPos(xPos + 50); bullet.setyPos(yPos - 10); bullet.setyVelo(-20); break;
			case "DOWN" : bullet.setxPos(xPos + 10); bullet.setyPos(yPos + 90); bullet.setyVelo(20); break;
			default : {
				switch(direct) {
					case "LEFT" : bullet.setxPos(xPos - 10); bullet.setyPos(yPos + 40); bullet.setxVelo(-20); break;
					case "RIGHT" : bullet.setxPos(xPos + 70); bullet.setyPos(yPos + 40); bullet.setxVelo(20); break;
					case "UP" : bullet.setxPos(xPos + 50); bullet.setyPos(yPos - 10); bullet.setyVelo(-20); break;
					case "DOWN" : bullet.setxPos(xPos + 10); bullet.setyPos(yPos + 90); bullet.setyVelo(20); break;
					default : break;
				}
				break;
			}
		}
		
		handler.addObject(bullet);
		
		return ;
		
	}
	
	public void reduceBullet() {
		if(magazine.getNumBullet() == 0) return;
		magazine.reduceBullet();
	}
	
	public Bullet getBullet() {
		Bullet OnGun = null;
		try {
			OnGun = magazine.getMagazine().get(0);
			return OnGun;
		}
		catch(Exception x) {
			// No bullets left in current magazine
		}
		return OnGun;
	}

	@Override
	public int dps_damage() {
		// TODO Auto-generated method stub
		return 0;
	}

	// Getter & Setter
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}
}
