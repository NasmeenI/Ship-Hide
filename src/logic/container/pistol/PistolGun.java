package logic.container.pistol;

import java.util.ArrayList;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.container.Bullet;
import logic.container.Gun;
import utilz.LoadSave;

public class PistolGun extends Gun implements Attackable, StableObject {
	
	private static final long serialVersionUID = 1L;
	private PistolMagazine magazine;

	public PistolGun(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, -10, -10, 120, 70, false, false);
		setMagazine(new PistolMagazine(xPos, yPos, ID.PistolMagazine, 6));
		initImg();
	}
	
	public PistolGun(double xPos, double yPos, ID id, int bulletNum) {
		super(xPos, yPos, id, -10, -10, 120, 57, false, false);
		setMagazine(new PistolMagazine(xPos, yPos, ID.PistolMagazine, bulletNum));
		initImg();
	}
	
	public PistolGun(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id, -10, -10, 120, 70, false, visible);
		setMagazine(new PistolMagazine(xPos, yPos, ID.PistolMagazine, 6));
		initImg();
	}
	
	public boolean shootAble() {
		if(magazine.getMagazine().isEmpty() || magazine.getNumBullet() == 0) return false;
		return true;
 	}
	
	public boolean reload() { // More to fix (Delay)
		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
		boolean isReload = false;
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof PistolMagazine) {
				magazine = ((PistolMagazine)bag.get(i));
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
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.GUN);
		imageUsed = LoadSave.GetSpriteAtlas(LoadSave.GUN_USED);
	}
	
	public int getNumMagazine() {
		int numMagazine = 0;
		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof PistolMagazine) {
				numMagazine ++ ;
			}
		}
		return numMagazine;
	}

	// Getter & Setter

	public PistolMagazine getMagazine() {
		return magazine;
	}

	public void setMagazine(PistolMagazine magazine) {
		this.magazine = magazine;
	}
}