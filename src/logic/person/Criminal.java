package logic.person;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import logic.container.Gun;
import utilz.Checker;
import utilz.Obj;
import static utilz.Constants.Player.*;
import static utilz.Constants.Tile.*;

import ai.PathFinder;
import application.GameProcess;

import static utilz.Constants.Debug.*;

public class Criminal extends Person {
	
	private static final long serialVersionUID = 1L;
	
	private Gun gun;

	public Criminal(int xPos, int yPos, ID id, double xVelo, double yVelo) {
        super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
        setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(100);
        this.gun = new Gun(xPos, yPos, ID.Gun, 10000);
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
        setPathFinder(new PathFinder());
        
        // Tempt
        setGun(true);
        setUsed(3);
        onPath = true;
    }

	@Override
	public void update() {
		
		Obj.collision(this);
		if(getHp() == 0) Handler.getInstance().removeObject(this);
		
		if(onPath) {
			Point mP = getMiddlePoint(Handler.getInstance().Player.getSolidArea());
			SearchPath((int) (mP.y / TILESIZE), (int) (mP.x / TILESIZE));
			setDirect(Obj.getDirection(this, Handler.getInstance().Player));
		}
		
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
		
		setSolidArea(new Rectangle(getxPos() + getxDif(), getyPos() + getxDif(), getW(), getH()));
		
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
		ShowPath(gc);
		gc.setFill(Color.RED);
		gc.fillRect(getxPos() + getxDif(), getyPos() + getxDif(), getW(), getH());
		return ;
	}

	@Override
	public void shoot() {
		if(!GunAvailable() || Handler.getInstance().Player == null) return ;
		
		if(!gun.shootAble()) return ;
		
		if(Checker.InRange(getSolidArea().getX(), getSolidArea().getX() + getSolidArea().getWidth(), Handler.getInstance().Player.getSolidArea().getX() + Handler.getInstance().Player.getSolidArea().getWidth()/2)) {
			gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
			return ;
		}
		if(Checker.InRange(getSolidArea().getY(), getSolidArea().getY() + getSolidArea().getHeight(), Handler.getInstance().Player.getSolidArea().getY() + Handler.getInstance().Player.getSolidArea().getHeight()/2)) {
			gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
			return ;
		}
		
		return ;
	}

	@Override
	public void slash() {
		if(!KnifeAvailable() || Handler.getInstance().Player == null) return ;
		
		return ;
	}

	@Override
	public void initImg() {
		// TODO Auto-generated method stub
		
	}
	
}
