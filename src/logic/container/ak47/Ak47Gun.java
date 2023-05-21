package logic.container.ak47;

import java.util.ArrayList;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.container.Bullet;
import logic.container.Gun;
import utilz.LoadSave;

public class Ak47Gun extends Gun implements Attackable, StableObject {
	
	private static final long serialVersionUID = 1L;
	private int price;
	private String name;
	private Ak47Magazine magazine;
	
	public Ak47Gun(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, -10, -10, 120, 70, false, false);
		setMagazine(new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, 30));
		setName("Ak-47");
		setPrice(15);
		initImg();
	}
	
	public Ak47Gun(double xPos, double yPos, ID id, int bulletNum) {
		super(xPos, yPos, id, -10, -10, 120, 70, false, false);
		setMagazine(new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, bulletNum));
		setName("Ak-47");
		setPrice(15);
		initImg();
	}
	
	public Ak47Gun(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id, -10, -10, 120, 70, false, visible);
		setMagazine(new Ak47Magazine(xPos, yPos, ID.Ak47Magazine, 30));
		setName("Ak-47");
		setPrice(15);
		initImg();
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.AK47);
		imageUsed = LoadSave.GetSpriteAtlas(LoadSave.AK47_USED);
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
	public int dpsDamage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
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

	// Getter & Setter
	
	public Ak47Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Ak47Magazine magazine) {
		this.magazine = magazine;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPrice() {
		return this.price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}