package logic.person;

import ai.PathFinder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Map;
import logic.base.Point;
import utilz.Checker;
import utilz.Obj;

import static utilz.Constants.Tile.*;

public abstract class Person extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected int hp, hpMax, bullets;
	protected int used, prvUsed;
	protected int spriteCnt, bulletTime, knifeTime, reloadTime, randWalkTime, chasingTime, interval;
	protected boolean gun, knife;
	protected boolean chasing = false;
	protected String direct, prv_direct = null;
	
	transient protected Rectangle footArea;
	transient protected Rectangle renderArea;
	
	public static PathFinder pathFinder = new PathFinder();
	
	private int direction = 9;
	private int[][] mapTileNum = Map.getInstance().getMapTileNum();

	/*
	1 --> Hand
	2 --> Knife
	3 --> Gun
	*/

	public Person(double xPos, double yPos, ID id ,double xDif ,double yDif ,double w ,double h) {
		super(xPos, yPos, id, xDif, yDif, w, h);
		setFootArea(new Rectangle(xPos + xDif, yPos + yDif + h - 10, w, 10));
		setRenderArea(new Rectangle(xPos + xDif, yPos + yDif + 40, w, h - 40));
		setUsed(1);
		setPrvUsed(1);
		setDirect("Z");
		setPrv_direct("Z");
		setHp(100);				
		setKnife(false);
		setGun(false);
		setBullets(0);
		setSpriteCnt(0);
		setBulletTime(0);
		setKnifeTime(0);
		setReloadTime(0);
		setRandWalkTime(0);
		setChasingTime(300);
		setInterval(0);
	}

	public abstract void update();
	public abstract void render(GraphicsContext gc);
	public abstract void shoot();
	public abstract void slash();
	public abstract void animation();
	
	public void searchPath(int endRow, int endCol) {
		
		Point mP = getMiddlePoint(getFootArea());
		
		int startRow = (int) (mP.getY() / TILESIZE);
		int startCol = (int) (mP.getX() / TILESIZE);
		
		pathFinder.setNode(startRow, startCol, endRow, endCol);

		if(pathFinder.search()) {
			
			int nextX = pathFinder.getPathList().get(0).getCol() * TILESIZE;
			int nextY = pathFinder.getPathList().get(0).getRow() * TILESIZE;

			Obj.getClosePoint(this, (int) mP.getX(), (int) mP.getY(), nextX + 36, nextY + 36);
			
			int nextCol = pathFinder.getPathList().get(0).getCol() * TILESIZE;
			int nextRow = pathFinder.getPathList().get(0).getRow() * TILESIZE;
			
			if(nextCol == endCol && nextRow == endRow) {
				chasing = false;
			}
			 
		}
		else {
			Point mPz = getMiddlePoint(Handler.getInstance().player.getSolidArea());
			
			pathFinder.setNode(startRow, startCol,(int) mPz.getY() / TILESIZE,(int) mPz.getX() / TILESIZE);
			
			if(pathFinder.search()) {
				
				int nextX = pathFinder.getPathList().get(0).getCol() * TILESIZE;
				int nextY = pathFinder.getPathList().get(0).getRow() * TILESIZE;

				Obj.getClosePoint(this, (int) mP.getX(), (int) mP.getY(), nextX + 36, nextY + 36);
				
				int nextCol = pathFinder.getPathList().get(0).getCol() * TILESIZE;
				int nextRow = pathFinder.getPathList().get(0).getRow() * TILESIZE;
				
				if(nextCol == endCol && nextRow == endRow) {
					chasing = false;
				}
				 
			}
		}
	}
	
	public void showPath(GraphicsContext gc) {
		for(int i = 0; i < pathFinder.getPathList().size(); i++) {
			int Wx = pathFinder.getPathList().get(i).getCol() * TILESIZE;
			int Wy = pathFinder.getPathList().get(i).getRow() * TILESIZE;
			gc.setFill(Color.PURPLE);
			gc.fillRect(Wx, Wy, TILESIZE, TILESIZE);
		}
	}
	
	public boolean gunAvailable() {
		return gun;
	}
	
	public boolean knifeAvailable() {
		return knife;
	}
	
	public void moveX() { movePass(getxVelo(), 0); }
    public void moveY() { movePass(0, getyVelo()); }
    public void move() { movePass(getxVelo(), getyVelo()); }
    public void moveLeft() { 
    	movePass(- Math.abs(getxVelo()), 0); 
    	setDirect("L");
    }
    public void moveRight() { 
    	movePass(Math.abs(getxVelo()), 0); 
    	setDirect("R");
    }
    public void moveUp() { 
    	movePass(0, - Math.abs(getyVelo())); 
    	setDirect("U");
    }
    public void moveDown() { 
    	movePass(0, Math.abs(getyVelo())); 
    	setDirect("D");
    }
    
    public void movePass(double xVelo, double yVelo) {
        int newXPos = 0 ,newYPos = 0;
        if(xVelo >= 0) newXPos = ((int)((getxPos() + xVelo - 10)/48));            
        else newXPos = ((int)((getxPos() + xVelo - 20)/48));    
        
        if(yVelo >= 0) newYPos = ((int)((getyPos() + yVelo - 4)/48)) + 2;             
        else newYPos = ((int)((getyPos() + yVelo - 20)/48)) + 2;
        
        if(mapTileNum[newYPos][newXPos] != 0) {
            setxPos(getxPos() + xVelo);
            setyPos(getyPos() + yVelo);
        }
        else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-10)/48] != 0 && xVelo >= 0) {
            setyPos(getyPos() + yVelo);
        }
        else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-20)/48] != 0 && xVelo < 0) {
            setyPos(getyPos() + yVelo);
        }
        else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-4)/48) + 2][newXPos] != 0 && yVelo >= 0) {
            setxPos(getxPos() + xVelo);
        }
        else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-20)/48) + 2][newXPos] != 0 && yVelo < 0) {
            setxPos(getxPos() + xVelo);
        }
    }
	
	public void randomWalk(int interval) {
		if(randWalkTime == 0) {
			direction = Checker.Rand(1, 9);
			setInterval(interval + Checker.Rand(-(interval / 5), interval / 5));
		}
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
		randWalkTime %= getInterval();
	}
	
	public void showFootArea(GraphicsContext gc) {
		gc.setFill(Color.PINK);
		gc.fillRect((int)footArea.getX(), (int)footArea.getY(), footArea.getWidth(), footArea.getHeight());
	}
	
	// Getters & Setters
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(PathFinder pathFinder) {
		Person.pathFinder = pathFinder;
	}
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = Math.max(hp, 0);
	}
	
	public int getHpMax() {
		return this.hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = Math.max(hpMax, 0);
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	public boolean isGun() {
		return gun;
	}

	public void setGun(boolean gun) {
		this.gun = gun;
	}

	public boolean isKnife() {
		return knife;
	}

	public void setKnife(boolean knife) {
		this.knife = knife;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		if(used < 1 || used > 4) this.used = 1;
		else if(used == 2 && this.knife) this.used = 2;
		else if(used == 3 && this.gun) this.used = 3;
		else if(used == 4 && this.gun) this.used = 4;
		else this.used = 1;
	}
	
	public int getPrvUsed() {
		return prvUsed;
	}

	public void setPrvUsed(int prvUsed) {
		this.prvUsed = prvUsed;
	}

	public boolean isChasing() {
		return chasing;
	}

	public void setChasing(boolean chasing) {
		this.chasing = chasing;
	}

	public int getSpriteCnt() {
		return spriteCnt;
	}

	public void setSpriteCnt(int spriteCnt) {
		this.spriteCnt = Math.max(0, spriteCnt);
	}

	public int getBulletTime() {
		return bulletTime;
	}

	public void setBulletTime(int bulletTime) {
		this.bulletTime = Math.max(0, bulletTime);
	}

	public int getKnifeTime() {
		return knifeTime;
	}

	public void setKnifeTime(int knifeTime) {
		this.knifeTime = Math.max(0, knifeTime);
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = Math.max(0, reloadTime);
	}

	public int getRandWalkTime() {
		return randWalkTime;
	}

	public void setRandWalkTime(int randWalkTime) {
		this.randWalkTime = Math.max(0, randWalkTime);
	}
	
	public int getChasingTime() {
		return chasingTime;
	}

	public void setChasingTime(int chasingTime) {
		this.chasingTime = Math.max(0, chasingTime);
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = Math.max(0, interval);
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getPrv_direct() {
		return prv_direct;
	}

	public void setPrv_direct(String prv_direct) {
		this.prv_direct = prv_direct;
	}

	public Rectangle getFootArea() {
		return footArea;
	}

	public void setFootArea(Rectangle footArea) {
		this.footArea = footArea;
	}
	
	public Rectangle getRenderArea() {
		return renderArea;
	}

	public void setRenderArea(Rectangle renderArea) {
		this.renderArea = renderArea;
	}
}