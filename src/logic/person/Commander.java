package logic.person;

import static utilz.Constants.Debug.SOLID_SHOW;
import static utilz.Constants.Player.P_HEIGHT;
import static utilz.Constants.Player.P_WIDTH;
import static utilz.Constants.Tile.TILESIZE;

import application.sound.KillCommander;
import application.sound.ShotCommander;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import logic.container.Gun;
import utilz.Checker;
import utilz.Obj;

public class Commander extends Person {
	
	private static final long serialVersionUID = 1L;
	
	private Gun gun;
	
	private int gunMod = 5;

	public Commander(int xPos, int yPos, ID id, double xVelo, double yVelo) {
        super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
        setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(5000);
        setGun();
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
        
        // Tempt
        setGun(true);
        setUsed(3);
    }

	@Override
	public void update() {
		
		if(getHp() == 0) {
			new KillCommander();
			Handler.getInstance().removeObject(this);
		}
		Obj.collision(this);
		
		if(Obj.distance(this, Handler.getInstance().player) <= 300) {
			chasing = true;
			setxVelo(1);
			setyVelo(1);
		}
		else if(Obj.distance(this, Handler.getInstance().player) > 600) {
			chasing = false;
			setxVelo(.5f);
			setyVelo(.5f);
		}
		
		if(chasing) {
			Point mP = getMiddlePoint(Handler.getInstance().player.getFootArea());
			SearchPath((int) (mP.y / TILESIZE), (int) (mP.x / TILESIZE));
			setDirect(Obj.getDirection(this, Handler.getInstance().player));
		}
		else randomWalk(120);
		
		setBeforeTwo(Obj.collisionTwo(this));
		
		if(getKnifeTime() < 40) setKnifeTime(getKnifeTime() + 1);
		if(getBulletTime() < 120) setBulletTime(getBulletTime() + 1);
		
		if(getUsed() == 2 && getKnifeTime() == 40) {
			slash();
			setKnifeTime(0);
		}
		if(getUsed() == 3 && getBulletTime() % gunMod == 0) {
			shoot();
			if(getBulletTime() >= 15) gunMod = 999;
		}
		
		if(getBulletTime() >= 120) {
			setBulletTime(0);
			gunMod = 3;
		}
		
		setSolidArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif(), getW(), getH()));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW(), 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));		
		return ;
	}
	
	public void Animation() { // WAITING...
		if(getDirect() != "Z") setPrv_direct(getDirect());
		return ;
	}
	
	public void MoveByDirection() {
		switch(getDirect()) {
			case "LEFT" : setxPos(getxPos() - getxVelo()); break;
			case "RIGHT" : setxPos(getxPos() + getxVelo()); break;
			case "UP" : setyPos(getyPos() - getyVelo()); break;
			case "DOWN" : setyPos(getyPos() - getyVelo()); break;
			default : break;
		}
		return ;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.setFill(Color.PURPLE);
		gc.fillRect(getxPos() + getxDif(), getyPos() + getyDif(), getW(), getH());
		gc.fillRect(getxPos() + getxDif() - 10, getyPos() + getyDif() -10, (getHp()*70)/5000, 10);
		return ;
	}

	@Override
	public void shoot() {
		if(!GunAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		if(!gun.shootAble()) return ;
		
		if(Checker.InRange(getSolidArea().getX() - 50, getSolidArea().getX() + getSolidArea().getWidth() + 50, Handler.getInstance().player.getSolidArea().getX() + Handler.getInstance().player.getSolidArea().getWidth()/2)) {
			gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
			new ShotCommander();
		}
		else if(Checker.InRange(getSolidArea().getY() - 50, getSolidArea().getY() + getSolidArea().getHeight() + 50, Handler.getInstance().player.getSolidArea().getY() + Handler.getInstance().player.getSolidArea().getHeight()/2)) {
			gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
			new ShotCommander();
		}

	}

	@Override
	public void slash() {
		if(!KnifeAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		return ;
	}

	@Override
	public void initImg() {
		// TODO Auto-generated method stub
		
	}

	public void setGun() {
		// TODO Auto-generated method stub
		this.gun = new Gun(xPos, yPos, ID.Gun, 10000);
	}
}