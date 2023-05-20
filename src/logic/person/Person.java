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
	protected int Hp, HpMax, bullets;
	protected boolean gun, knife;
	protected int used, prvUsed;
	protected int SpriteCnt, BulletTime, KnifeTime, ReloadTime, randWalkTime, chasingTime, interval;
	private int direction = 9;
	protected String direct, prv_direct = null;
	
	transient protected Rectangle footArea;
	transient protected Rectangle renderArea;
	
	protected boolean chasing = false;
	public static PathFinder pathFinder = new PathFinder();
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
		setHp(100);				// default
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
	
	public void SearchPath(int endRow, int endCol) {
		
		Point mP = getMiddlePoint(getFootArea());
		
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
				chasing = false;
			}
			 
		}
		else {
			Point mPz = getMiddlePoint(Handler.getInstance().player.getSolidArea());
			
			pathFinder.setNode(startRow, startCol,(int) mPz.y / TILESIZE,(int) mPz.x / TILESIZE);
			
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
	
	public void HealthBar(GraphicsContext gc) {

        
//        gc.getCanvas().setWidth(scene.getWidth());
//        gc.getCanvas().setHeight(scene.getHeight());
//		gc.setFill(Color.RED);
//		gc.fillRect((int)getxPos() + getxDif() - 10, (int)getyPos() + getyDif() -10, solidArea.getWidth() + 20, 10);
	}
	
	// Getters & Setters
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(PathFinder pathFinder) {
		Person.pathFinder = pathFinder;
	}
	
	public int getHp() {
		return Hp;
	}

	public void setHp(int hp) {
		Hp = Math.max(hp, 0);
		return ;
	}
	
	public int getHpMax() {
		return HpMax;
	}

	public void setHpMax(int hpMax) {
		HpMax = Math.max(hpMax, 0);
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
		if(used < 1 || used > 4) this.used = 1;
		else if(used == 2 && this.knife) this.used = 2;
		else if(used == 3 && this.gun) this.used = 3;
		else if(used == 4 && this.gun) this.used = 4;
		else this.used = 1;
		return ;
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
		return ;
	}

	public String getPrv_direct() {
		return prv_direct;
	}

	public void setPrv_direct(String prv_direct) {
		this.prv_direct = prv_direct;
		return ;
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