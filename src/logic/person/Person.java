package logic.person;

import javafx.scene.canvas.GraphicsContext;
import logic.base.GameObject;
import logic.base.ID;

public abstract class Person extends GameObject {
	
	protected int Hp, bullets;
	protected boolean gun, knife;
	protected int used;
	protected int SpriteCnt, BulletTime, KnifeTime, ReloadTime;
	protected String direct, prv_direct = null;

	/*
	1 --> Hand
	2 --> Knife
	3 --> Gun
	*/

	public Person(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
		setxVelo(0);
		setyVelo(0);
		setUsed(1);
		setDirect("Z");
		setPrv_direct("Z");
		setHp(100);				// default
		setKnife(false);
		setGun(false);
		setBullets(0);
		setSpriteCnt(0);
		setBulletTime(0);
		setKnifeTime(0);
		setReloadTime(0);
	}
	
	public abstract void update();
	public abstract void render(GraphicsContext gc);
	public abstract void shoot();
	public abstract void slash();
	public abstract void Animation();
	
	public boolean GunAvailable() {
		return gun;
	}
	
	public boolean KnifeAvailable() {
		return knife;
	}
	
	// Getters & Setters
	
	public int getHp() {
		return Hp;
	}

	public void setHp(int hp) {
		Hp = Math.max(hp, 0);
		return ;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
		return ;
	}
	
	public boolean isGun() {
		return gun;
	}

	public void setGun(boolean gun) {
		this.gun = gun;
		return ;
	}

	public boolean isKnife() {
		return knife;
	}

	public void setKnife(boolean knife) {
		this.knife = knife;
		return ;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		if(used < 1 || used > 3) this.used = 1;
		else if(used == 2 && this.knife) this.used = 2;
		else if(used == 3 && this.gun) this.used = 3;
		else this.used = 1;
		return ;
	}
	
	public int getSpriteCnt() {
		return SpriteCnt;
	}

	public void setSpriteCnt(int spriteCnt) {
		SpriteCnt = Math.max(0, spriteCnt);
		return ;
	}

	public int getBulletTime() {
		return BulletTime;
	}

	public void setBulletTime(int bulletTime) {
		BulletTime = Math.max(0, bulletTime);
		return ;
	}

	public int getKnifeTime() {
		return KnifeTime;
	}

	public void setKnifeTime(int knifeTime) {
		KnifeTime = Math.max(0, knifeTime);
		return ;
	}

	public int getReloadTime() {
		return ReloadTime;
	}

	public void setReloadTime(int reloadTime) {
		ReloadTime = Math.max(0, reloadTime);
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
		return ;
	}

	public String getPrv_direct() {
		return prv_direct;
	}

	public void setPrv_direct(String prv_direct) {
		this.prv_direct = prv_direct;
		return ;
	}

}
