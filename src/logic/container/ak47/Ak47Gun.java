package logic.container.ak47;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Pickable;
import logic.base.StableObject;
import logic.container.Bullet;
import logic.container.Gun;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class Ak47Gun extends Gun implements Attackable, StableObject, Pickable {
	
	private static final long serialVersionUID = 1L;
	private Ak47Magazine magazine;
	Handler handler = Handler.getInstance();
	public boolean picked;
	private boolean visible;
	transient private Image image ,imageUsed;
	
	private double littleMove = 1;
	private double count = 0;

	public Ak47Gun(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, -10, -10, 120, 70);
		this.magazine = new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, 30);
		this.visible = false;
		this.picked = false;
		initImg();
	}
	
	public Ak47Gun(double xPos, double yPos, ID id, int bulletNum) {
		super(xPos, yPos, id, -10, -10, 120, 57);
		this.magazine = new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, bulletNum);
		this.visible = false;
		this.picked = false;
		initImg();
	}
	
	public Ak47Gun(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id, -10, -10, 120, 70);
		this.magazine = new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, 30);
		this.visible = visible;
		this.picked = false;
		initImg();
	}
	
	@Override
	public void update() {
		if(littleMove <= -1) count = 0.05f;
		if(littleMove >= 1) count = -0.05f;
		
		littleMove += count;
		
		setyPos(getyPos() + littleMove);
	}

	public void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.AK47);
		this.imageUsed = LoadSave.GetSpriteAtlas(LoadSave.AK47_USED);
	}

	@Override
	public void render(GraphicsContext gc) {
		if(!isVisible()) return;
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	@Override
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		player.setGun(true);
		Handler.getInstance().removeObject(this);
	}
	
	public boolean shootAble() {
		if(magazine.getMagazine().isEmpty() || magazine.getNumBullet() == 0) return false;
		return true;
 	}
	
	public boolean reload() { // More to fix (Delay)
		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
		boolean isReload = false;
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Ak47Magazine) {
				magazine = ((Ak47Magazine)bag.get(i));
				Handler.getInstance().player.getBag().remove(bag.get(i));
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
	
	public int getNumMagazine() {
		int numMagazine = 0;
		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Ak47Magazine) {
				numMagazine ++ ;
			}
		}
		return numMagazine;
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
	
	public Ak47Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Ak47Magazine magazine) {
		this.magazine = magazine;
	}
}
