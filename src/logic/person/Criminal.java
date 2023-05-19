package logic.person;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import logic.container.pistol.PistolGun;
import utilz.Checker;
import utilz.LoadSave;
import utilz.Obj;
import static utilz.Constants.Player.*;
import static utilz.Constants.Tile.*;

import application.sound.ShotCommander;
import application.sound.ShotCriminal;

import static utilz.Constants.Debug.*;

public class Criminal extends Person {
	
	private static final long serialVersionUID = 1L;
	private PistolGun gun;
	
	transient private Image T_Temp;

	public Criminal(int xPos, int yPos, ID id, double xVelo, double yVelo) {
        super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
        setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(1000);
        initGun();
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
        initImg();
    }
	
	public void initImg() {
		T_Temp = LoadSave.GetSpriteAtlas(LoadSave.Criminal_Animation_Temp);
	}
	
	public void initGun() {
		gun = new PistolGun(xPos, yPos, ID.PistolGun, 10000);
		setGun(true);
        setUsed(3);
	}

	@Override
	public void update() {

		if(getHp() == 0) Handler.getInstance().removeObject(this);
		Obj.collision(this);
		
		if(Obj.distance(this, Handler.getInstance().player) <= 200) {
			setChasing(true);
			setxVelo(2);
			setyVelo(2);
			setChasingTime(0);
		}
		else if(Obj.distance(this, Handler.getInstance().player) > 450 && getChasingTime() == 300) {
			setChasing(false);
			setxVelo(.5f);
			setyVelo(.5f);
		}
		
		if(isChasing()) {
			setChasingTime(getChasingTime() + 1);
			setChasingTime(Math.min(300, getChasingTime()));
			Point mP = getMiddlePoint(Handler.getInstance().player.getFootArea());
			SearchPath((int) (mP.y / TILESIZE), (int) (mP.x / TILESIZE));
			setDirect(Obj.getDirection(this, Handler.getInstance().player));
		}
		else randomWalk(120);
		
		setBeforeTwo(Obj.collisionTwo(this));
		
		if(getKnifeTime() < 40) setKnifeTime(getKnifeTime() + 1);
		if(getBulletTime() < 30) setBulletTime(getBulletTime() + 1);
		
		if(getUsed() == 2 && getKnifeTime() == 40) {
			slash();
			setKnifeTime(0);
		}
		if(getUsed() == 3 && getBulletTime() == 30) {
			shoot();
			setBulletTime(0);
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

		gc.drawImage(T_Temp, xPos, yPos);
		gc.setFill(Color.RED);
		gc.fillRect(getxPos() + getxDif() - 10, getyPos() + getyDif() -10, (getHp()*70)/1000, 10);
	}

	@Override
	public void shoot() {
		if(!GunAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		if(!gun.shootAble()) return ;
		
		Point middlePos = getMiddlePoint(this.getSolidArea());
		
		if(Checker.InRange(getSolidArea().getX() - 50, getSolidArea().getX() + getSolidArea().getWidth() + 50, Handler.getInstance().player.getSolidArea().getX() + Handler.getInstance().player.getSolidArea().getWidth()/2)) {
			gun.shoot((int)middlePos.x, (int)middlePos.y, getDirect(), getId());
			new ShotCommander();
		}
		else if(Checker.InRange(getSolidArea().getY() - 50, getSolidArea().getY() + getSolidArea().getHeight() + 50, Handler.getInstance().player.getSolidArea().getY() + Handler.getInstance().player.getSolidArea().getHeight()/2)) {
			gun.shoot((int)middlePos.x, (int)middlePos.y, getDirect(), getId());
			new ShotCommander();
		}
	}

	@Override
	public void slash() {
		if(!KnifeAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		return ;
	}
	
}