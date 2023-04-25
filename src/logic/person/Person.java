package logic.person;

import ai.PathFinder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import utilz.Checker;
import utilz.Obj;

import static utilz.Constants.Tile.*;

public abstract class Person extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected int Hp, bullets;
	protected boolean gun, knife;
	protected int used;
	protected int SpriteCnt, BulletTime, KnifeTime, ReloadTime, randWalkTime;
	private int direction = 9;
	protected String direct, prv_direct = null;
	
	protected boolean chasing = false;
	public static PathFinder pathFinder = new PathFinder();

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
		setRandWalkTime(0);
	}

	public abstract void update();
	public abstract void render(GraphicsContext gc);
	public abstract void shoot();
	public abstract void slash();
	public abstract void Animation();
	
	public void SearchPath(int endRow, int endCol) {
		
		Point mP = getMiddlePoint(getSolidArea());
		
		int startRow = (int) ((mP.y + getSolidArea().getHeight() / 2) / TILESIZE);
		int startCol = (int) (mP.x / TILESIZE);
		
		pathFinder.setNode(startRow, startCol, endRow, endCol);

		if(pathFinder.search()) {
			
			int nextX = pathFinder.pathList.get(0).col * TILESIZE;
			int nextY = pathFinder.pathList.get(0).row * TILESIZE;

			Obj.getClosePoint(this, (int) mP.x, (int) mP.y, nextX + 36, nextY + 36);
			
			int nextCol = pathFinder.pathList.get(0).col * TILESIZE;
			int nextRow = pathFinder.pathList.get(0).row * TILESIZE;
			
			if(nextCol == endCol && nextRow == endRow) {
				chasing = false;
			}
			 
		}
		else {
			Point mPz = getMiddlePoint(Handler.getInstance().Player.getSolidArea());
			
			pathFinder.setNode(startRow, startCol,(int) (mPz.y + Handler.getInstance().Player.getSolidArea().getHeight() / 2) / TILESIZE,(int) mPz.x / TILESIZE);
			
			if(pathFinder.search()) {
				
				int nextX = pathFinder.pathList.get(0).col * TILESIZE;
				int nextY = pathFinder.pathList.get(0).row * TILESIZE;

				Obj.getClosePoint(this, (int) mP.x, (int) mP.y, nextX + 36, nextY + 36);
				
				int nextCol = pathFinder.pathList.get(0).col * TILESIZE;
				int nextRow = pathFinder.pathList.get(0).row * TILESIZE;
				
				if(nextCol == endCol && nextRow == endRow) {
					chasing = false;
				}
				 
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
		int yMid = (int) (A.getY() + A.getHeight() / 2);
		return new Point(xMid, yMid);
	}
	
	public void moveX() { setxPos(getxPos() + getxVelo()); }
	public void moveY() { setyPos(getyPos() + getyVelo()); }
	public void move() { setyPos(getyPos() + getyVelo()); setxPos(getxPos() + getxVelo()); }
	public void moveLeft() { setxPos(getxPos() - Math.abs(getxVelo())); }
	public void moveRight() { setxPos(getxPos() + Math.abs(getxVelo())); }
	public void moveUp() { setyPos(getyPos() - Math.abs(getyVelo())); }
	public void moveDown() { setyPos(getyPos() + Math.abs(getyVelo())); }
	
	public void randomWalk(int interval) {
		if(randWalkTime == 0) direction = Checker.Rand(1, 9);
		switch(direction) {
			case 1 : moveLeft(); break; // Left
			case 2 : moveRight(); break; // Right
			case 3 : moveUp(); break; // Up
			case 4 : moveDown(); break; // Down
			case 5 : moveLeft(); moveUp(); break; // Left-Up
			case 6 : moveRight(); moveUp(); break; // Right-Up
			case 7 : moveLeft(); moveDown(); break; // Left-Down
			case 8 : moveRight(); moveDown(); break; // Right-Down
			default : break; // Stick
		}
		randWalkTime++;
		randWalkTime %= (interval + Checker.Rand(-(interval / 5), interval / 5));
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

	public int getRandWalkTime() {
		return randWalkTime;
	}

	public void setRandWalkTime(int randWalkTime) {
		this.randWalkTime = Math.max(0, randWalkTime);
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
