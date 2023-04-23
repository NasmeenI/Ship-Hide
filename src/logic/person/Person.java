package logic.person;

import ai.PathFinder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.ID;
import logic.base.Point;
import utilz.Obj;

import static utilz.Constants.Tile.*;

public abstract class Person extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected int Hp, bullets;
	protected boolean gun, knife;
	protected int used;
	protected int SpriteCnt, BulletTime, KnifeTime, ReloadTime;
	protected String direct, prv_direct = null;
	
	protected boolean onPath = false;
	public PathFinder pathFinder;

	/*
	1 --> Hand
	2 --> Knife
	3 --> Gun
	*/

	public Person(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h) {
		super(xPos, yPos, id, xDif, yDif, w, h);
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
	
	public void SearchPath(int endRow, int endCol) {
		
		Point mP = getMiddlePoint(getSolidArea());
		
		int startRow = (int) (mP.y / TILESIZE);
		int startCol = (int) (mP.x / TILESIZE);
		
		pathFinder.setNode(startRow, startCol, endRow, endCol);

		if(pathFinder.search()) {
			
			int nextX = pathFinder.pathList.get(0).col * TILESIZE;
			int nextY = pathFinder.pathList.get(0).row * TILESIZE;

			Obj.getClosePoint(this, (int) mP.x, (int) mP.y, nextX + 36, nextY + 36);
			
			int nextCol = pathFinder.pathList.get(0).col * TILESIZE;
			int nextRow = pathFinder.pathList.get(0).row * TILESIZE;
			
			if(nextCol == endCol && nextRow == endRow) {
				onPath = false;
			}
			 
		}
	}
	
	public void ShowPath(GraphicsContext gc) {
		for(int i = 0; i < pathFinder.pathList.size(); i++) {
			int Wx = pathFinder.pathList.get(i).col * TILESIZE;
			int Wy = pathFinder.pathList.get(i).row * TILESIZE;
			gc.setFill(Color.PURPLE);
			gc.fillRect(Wx, Wy, TILESIZE, TILESIZE);
		}
	}
	
	public boolean GunAvailable() {
		return gun;
	}
	
	public boolean KnifeAvailable() {
		return knife;
	}
	
	public Point getMiddlePoint(Rectangle A) {
		int xMid = (int) (A.getX() + A.getWidth() / 2);
		int yMid = (int) (A.getY() + A.getHeight());
		return new Point(xMid, yMid);
	}
	
	// Getters & Setters
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(PathFinder pathFinder) {
		this.pathFinder = pathFinder;
	}
	
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
