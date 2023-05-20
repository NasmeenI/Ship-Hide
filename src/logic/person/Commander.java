package logic.person;

import static utilz.Constants.Debug.SOLID_SHOW;
import static utilz.Constants.Player.P_HEIGHT;
import static utilz.Constants.Player.P_WIDTH;
import static utilz.Constants.Tile.TILESIZE;
import application.sound.KillCommander;
import application.sound.ShotCommander;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import logic.container.pistol.PistolGun;
import object.Coin;
import utilz.Checker;
import utilz.LoadSave;
import utilz.Obj;

public class Commander extends Person {
	
	private static final long serialVersionUID = 1L;
	private PistolGun gun;
	private int gunMod = 5;
	private final int defaultAni = 0;
	transient private Image currentAni, previousAni;
	transient private Image[] T_Up, T_Down, T_Left, T_Right;
	
	public Commander(int xPos, int yPos, ID id, double xVelo, double yVelo) {
        super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
        setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(5000);
        initGun();
        initImg();
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
    }
	
	@Override
	public void initImg() {
		T_Up = new Image[3];
		T_Up[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Up_Default);
		T_Up[0] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Up_1);
		T_Up[1] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Up_2);
		
		T_Down = new Image[3];
		T_Down[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Down_Default);
		T_Down[0] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Down_1);
		T_Down[1] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Down_2);

		T_Left = new Image[3];
		T_Left[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Left_Default);
		T_Left[0] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Left_1);
		T_Left[1] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Left_2);
		
		T_Right = new Image[3];
		T_Right[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Right_Default);
		T_Right[0] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Right_1);
		T_Right[1] = LoadSave.GetSpriteAtlas(LoadSave.Commander_Animation_Right_2);
	}

	public void initGun() {
		gun = new PistolGun(xPos, yPos, ID.PistolGun, 10000);
		setGun(true);
        setUsed(3);
	}

	@Override
	public void update() {
		
		if(getHp() == 0) {
			new KillCommander();
			Handler.getInstance().removeObject(this);
			Handler.getInstance().getAllObjects().add(new Coin((int)getxPos()+20 ,(int)getyPos()+50, ID.Coin));
			Handler.getInstance().getAllObjects().add(new Coin((int)getxPos() ,(int)getyPos()+50, ID.Coin));
			Handler.getInstance().getAllObjects().add(new Coin((int)getxPos()-20 ,(int)getyPos()+40, ID.Coin));
			Handler.getInstance().getAllObjects().add(new Coin((int)getxPos() ,(int)getyPos(), ID.Coin));
			Handler.getInstance().getAllObjects().add(new Coin((int)getxPos()+10 ,(int)getyPos()+20, ID.Coin));
		}
		Obj.collision(this);
		
		if(Obj.distance(this, Handler.getInstance().player) <= 300) {
			setChasing(true);
			setxVelo(1);
			setyVelo(1);
			setChasingTime(0);
		}
		else if(Obj.distance(this, Handler.getInstance().player) > 600 && getChasingTime() == 300) {
			setChasing(false);
			setxVelo(.5f);
			setyVelo(.5f);
		}
		
		if(isChasing()) {
			setChasingTime(getChasingTime() + 1);
			setChasingTime(Math.min(300, getChasingTime()));
			Point mP = getMiddlePoint(Handler.getInstance().player.getFootArea());
			searchPath((int) (mP.getY() / TILESIZE), (int) (mP.getX() / TILESIZE));
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
		
		setAllArea();
		animation();
	}
	
	public void setAllArea() {
		setSolidArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif(), getW(), getH()));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW(), 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));
	}
	
	public void animation() {
		if(direct != prv_direct) spriteCnt = 0;
		int frame = (spriteCnt / 15) % 2;
		
		walkAni(frame);

		spriteCnt++;
		if(direct != "Z") prv_direct = direct;
		previousAni = currentAni;
		spriteCnt %= 120;
	}
	
	private void walkAni(int frame) {
		switch(getDirect()) {
			case "L" : currentAni = T_Left[frame]; break;
			case "R" : currentAni = T_Right[frame]; break;
			case "U" : currentAni = T_Up[frame]; break;
			case "D" : currentAni = T_Down[frame]; break;
			case "LEFT" : currentAni = T_Left[frame]; break;
			case "RIGHT" : currentAni = T_Right[frame]; break;
			case "UP" : currentAni = T_Up[frame]; break;
			case "DOWN" : currentAni = T_Down[frame]; break;
			default : {
				switch(prv_direct) {
					case "LEFT" : currentAni = T_Left[defaultAni]; break;
					case "RIGHT" : currentAni = T_Right[defaultAni]; break;
					case "UP" : currentAni = T_Up[defaultAni]; break;
					case "DOWN" : currentAni = T_Down[defaultAni]; break;
					default : currentAni = previousAni; break;
				}
				break;
			}
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.drawImage(currentAni, xPos, yPos);
		gc.setFill(Color.RED);
		gc.fillRect(getxPos() + getxDif() - 10, getyPos() + getyDif() -10, (getHp()*70)/5000, 10);
		return ;
	}

	@Override
	public void shoot() {
		if(!gunAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		if(!gun.shootAble()) return ;
		
		Point middlePos = getMiddlePoint(this.getSolidArea());
		
		if(Checker.InRange(getSolidArea().getX() - 50, getSolidArea().getX() + getSolidArea().getWidth() + 50, Handler.getInstance().player.getSolidArea().getX() + Handler.getInstance().player.getSolidArea().getWidth()/2)) {
			gun.shoot((int)middlePos.getX(), (int)middlePos.getY(), getDirect(), getId());
			new ShotCommander();
		}
		else if(Checker.InRange(getSolidArea().getY() - 50, getSolidArea().getY() + getSolidArea().getHeight() + 50, Handler.getInstance().player.getSolidArea().getY() + Handler.getInstance().player.getSolidArea().getHeight()/2)) {
			gun.shoot((int)middlePos.getX(), (int)middlePos.getY(), getDirect(), getId());
			new ShotCommander();
		}

	}

	@Override
	public void slash() {
		if(!knifeAvailable() || !chasing || Handler.getInstance().player == null) return ;
		
		return ;
	}
}