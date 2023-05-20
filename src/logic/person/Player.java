package logic.person;

import application.GameProcess;
import application.Music;
import application.sound.Reload;
import application.sound.ShotPlayer;
import application.sound.Slash;
import application.sound.outOfBullet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.KeyInput;
import logic.base.Keys;
import logic.base.Map;
import logic.base.Point;
import logic.container.Knife;
import logic.container.ak47.Ak47Gun;
import logic.container.pistol.PistolGun;
import object.Sculpture;
import utilz.Checker;
import utilz.LoadSave;
import utilz.Obj;
import java.util.ArrayList;
import Scenes.GameOverScene;
import Scenes.MenuScene;
import static utilz.Constants.Player.*;
import static utilz.Constants.Debug.*;
import static utilz.Constants.GameState.*;

public class Player extends Person {
	
	private static final long serialVersionUID = 1L;
	private int coin;
	private final int defaultAni = 9;
	private double ac;
	private double dc;
	private boolean swaped; // for Q button
	private boolean forceStop;
	private ArrayList<GameObject> bag;
	
	public static double curxPos;
	public static double curyPos;
	
	transient private Keys key;
	transient private KeyInput input;
	transient private Image[] T_Up, T_Down, T_Left, T_Right;
	transient private Image currentAni, previousAni;
	
	public Player(double xPos, double yPos, ID id, KeyInput input) {
		super(xPos, yPos, id, 10, 5, P_WIDTH, P_HEIGHT);
		setInput(input);
		setCurxPos(xPos);
		setCuryPos(yPos);
		setBag(new ArrayList<>());
		setCoin(0);
		setAc(0.8f);
		setDc(0.4f);
		initImg();
		setSwaped(false);
		setForceStop(false);
		if(MenuScene.mode == 0) setHpMax(5000);
		else if(MenuScene.mode == 1) setHpMax(50000);
		setHp(getHpMax());
		setDirect("U");
		setPrv_direct("Z");
		setKey(new Keys());
		setPreviousAni(T_Up[defaultAni]);
	}
	
	public void initImg() {
		T_Up = new Image[12];
		T_Up[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_Default);
		T_Up[0] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_0);
		T_Up[1] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_1);
		T_Up[2] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_2);
		T_Up[3] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_3);
		T_Up[4] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_4);
		T_Up[5] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_5);
		T_Up[6] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_6);
		T_Up[7] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Up_7);
		
		T_Down = new Image[12];
		T_Down[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_Default);
		T_Down[0] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_0);
		T_Down[1] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_1);
		T_Down[2] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_2);
		T_Down[3] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_3);
		T_Down[4] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_4);
		T_Down[5] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_5);
		T_Down[6] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_6);
		T_Down[7] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Down_7);
		
		T_Left = new Image[12];
		T_Left[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_Default);
		T_Left[0] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_0);
		T_Left[1] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_1);
		T_Left[2] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_2);
		T_Left[3] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_3);
		T_Left[4] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_4);
		T_Left[5] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_5);
		T_Left[6] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_6);
		T_Left[7] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Left_7);
		
		T_Right = new Image[12];
		T_Right[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_Default);
		T_Right[0] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_0);
		T_Right[1] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_1);
		T_Right[2] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_2);
		T_Right[3] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_3);
		T_Right[4] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_4);
		T_Right[5] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_5);
		T_Right[6] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_6);
		T_Right[7] = LoadSave.GetSpriteAtlas(LoadSave.Player_Animation_Right_7);
	}

	@Override
	public void update() {
//		System.out.println(getxPos() + "  " + getyPos());
		if(getHp() == 0) {
			GameProcess.stage.setScene(GameOverScene.scene);
			GameProcess.setGameState(GAME_OVER_STATE);
			Music.stop();
			Music.gameOver.play();
		}
		Obj.collision(this);

		for(int i = 0; i < Handler.getInstance().getAllObjects().size(); i++) {
            if(Handler.getInstance().getAllObjects().get(i).getCode() == getCode()) continue;
            if((Handler.getInstance().getAllObjects().get(i) instanceof Sculpture) && getFootArea().intersects(Handler.getInstance().getAllObjects().get(i).getSolidArea().getBoundsInLocal())) {
                Obj.action(this, Handler.getInstance().getAllObjects().get(i));
            }
        }
		
		setKey(input.key);
		
		if(key.ONE && getUsed() != 1) {
			setPrvUsed(getUsed());
			setUsed(1);
		}
		if(key.TWO && getUsed() != 2) {
			setPrvUsed(getUsed());
			setUsed(2);
			setKnifeTime(90);
		}
		if(key.THREE && getUsed() != 3) {
			setPrvUsed(getUsed());
			setUsed(3);
			setBulletTime(20);
		}
		if(key.FOUR && getUsed() != 4) {
			setPrvUsed(getUsed());
			setUsed(4);
			setBulletTime(5);
		}
		
		if(key.Q && !swaped) {
			int temp = getUsed();
			setUsed(getPrvUsed());
			setPrvUsed(temp);
			swaped = true;
		}
		else if(!key.Q) swaped = false;
		
		if(!forceStop && getUsed() == 1) setAcDc(.8f, .4f);
		if(!forceStop && getUsed() == 2) setAcDc(.7f, .35f);
		if(!forceStop && getUsed() == 3) setAcDc(.5f, .25f);
		if(!forceStop && getUsed() == 4) setAcDc(.5f, .25f);
		
		if(getUsed() == 1) setDirect(Checker.keyWalkDirection(key));
		else setDirect(Checker.keyDirection(key));
		
		walk();
		setBeforeTwo(Obj.collisionTwo(this));
	
		if(getKnifeTime() < 90) setKnifeTime(getKnifeTime() + 1);
		if(getBulletTime() < 20) setBulletTime(getBulletTime() + 1);
		if(getReloadTime() < 30) setReloadTime(getReloadTime() + 1);
		
		if(getUsed() != 1) {
			if(key.SPACE && getUsed() == 2 && getKnifeTime() == 90) {
				slash();
				setKnifeTime(0);
			}
			if((key.LEFT || key.RIGHT || key.UP || key.DOWN) && getUsed() == 3 && getBulletTime() == 20) {
				shoot();
				setBulletTime(0);
			}
			if((key.LEFT || key.RIGHT || key.UP || key.DOWN) && getUsed() == 4 && getBulletTime() >= 5) {
				shoot();
				setBulletTime(0);
			}
		}
		
		if(key.R && gunAvailable() && getUsed() == 3 && getReloadTime() == 30) { // Reload Option (Press R)
			ArrayList<GameObject> bag = this.getBag();
			for(int i=0;i<bag.size();i++) {
				if(bag.get(i) instanceof PistolGun) {
					if(((PistolGun)bag.get(i)).reload()) new Reload();;
					break;
				}
			}
			setReloadTime(0);
		}
		if(key.R && gunAvailable() && getUsed() == 4 && getReloadTime() == 30) { // Reload Option (Press R)
			ArrayList<GameObject> bag = this.getBag();
			for(int i=0;i<bag.size();i++) {
				if(bag.get(i) instanceof Ak47Gun) {
					if(((Ak47Gun)bag.get(i)).reload()) new Reload();;
					break;
				}
			}
			setReloadTime(0);
		}
		
		setAllArea();
		animation();
	}
	
	public void setAllArea() {
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 5, P_WIDTH, P_HEIGHT));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW(), 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));		
	}
	
	public void walk() {
		setCurxPos(getxPos());
		setCuryPos(getyPos());
		
		double _Vx = getxVelo();
		double _Vy = getyVelo();
		
		if(key.A) _Vx -= ac;
		else if(key.D) _Vx += ac;
		else {
			if(_Vx > 0) _Vx -= dc;
			else if(_Vx < 0) _Vx += dc;
		}
		
		if(key.W) _Vy -= ac;
		else if(key.S) _Vy += ac;
		else {
			if(_Vy > 0) _Vy -= dc;
			else if(_Vy < 0) _Vy += dc;
		}
		
		_Vx = cut(_Vx, -getAc() * 4, getAc() * 4);
		_Vy = cut(_Vy, -getAc() * 4, getAc() * 4);
		
		
		int newXPos = 0 ,newYPos = 0;
		if(_Vx >= 0) newXPos = ((int)((getxPos() + _Vx - 10)/48));			
		else newXPos = ((int)((getxPos() + _Vx - 20)/48));
		
		if(_Vy 	>= 0) newYPos = ((int)((getyPos() + _Vy - 4)/48)) + 2;		 	
		else newYPos = ((int)((getyPos() + _Vy - 20)/48)) + 2;
		
		
//		if(Map.getInstance().mapTileNum[(int)((getyPos()-10)/48)+2][(int)((getxPos()-15)/48)] == 2) setBeforeTwo(true);
//		else setBeforeTwo(false);

//		setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		
//		if(mapTileNum[newYPos][newXPos] != 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-10)/48] != 0 && _Vx >= 0) {
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-20)/48] != 0 && _Vx < 0) {
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		}
		
//		if(mapTileNum[newYPos][newXPos] != 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-10)/48] != 0 && _Vx >= 0) {
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[newYPos][(int)(getxPos()-20)/48] != 0 && _Vx < 0) {
//			setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		}
//		else if(mapTileNum[newYPos][newXPos] == 0 && mapTileNum[((int)(getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
//			setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		}
		
		if(Map.getInstance().getMapTileNum()[newYPos][newXPos] != 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().getMapTileNum()[newYPos][newXPos] == 0 && Map.getInstance().getMapTileNum()[newYPos][(int)(getxPos()-10)/48] != 0 && _Vx >= 0) {
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().getMapTileNum()[newYPos][newXPos] == 0 && Map.getInstance().getMapTileNum()[newYPos][(int)(getxPos()-20)/48] != 0 && _Vx < 0) {
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().getMapTileNum()[newYPos][newXPos] == 0 && Map.getInstance().getMapTileNum()[((int)(getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
		}
		else if(Map.getInstance().getMapTileNum()[newYPos][newXPos] == 0 && Map.getInstance().getMapTileNum()[((int)(getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
		}
		
		setxVelo(_Vx);
		setyVelo(_Vy);
		
	}
	
	public void animation() {
		if(direct != prv_direct) spriteCnt = 0;
		int frame = (spriteCnt / 5) % 8;
		
		switch(getUsed()) {
			case 1 : walkAni(frame); break;
			case 2 : knifeAni(frame); break;
			case 3 : gunAni(frame); break;
			case 4 : gunAni(frame); break;
			default : walkAni(frame); break;
		}
		
		spriteCnt++;
		if(direct != "Z") prv_direct = direct;
		previousAni = currentAni;
		spriteCnt %= 40;
	}
	
	public void shoot() {
		if(!gunAvailable() || Handler.getInstance().player == null) return ;
		
		Point middlePos = getMiddlePoint(this.getSolidArea());
		
		PistolGun pistolGun = null;
		Ak47Gun ak47Gun = null;
		
		ArrayList<GameObject> bag = this.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof PistolGun) {
				pistolGun = ((PistolGun)bag.get(i));
			}
			if(bag.get(i) instanceof Ak47Gun) {
				ak47Gun = ((Ak47Gun)bag.get(i));
			}
		}
		if(getUsed() == 3 && pistolGun != null) {
			if(!pistolGun.shootAble()) {
				new outOfBullet();
				return ;
			}
			pistolGun.shoot((int)middlePos.getX(), (int)middlePos.getY(), getDirect(), getId());
			new ShotPlayer();
		}
		else if(getUsed() == 4 && ak47Gun != null) {
			if(!ak47Gun.shootAble()) {
				new outOfBullet();
				return ;
			}
			ak47Gun.shoot((int)middlePos.getX(), (int)middlePos.getY(), getDirect(), getId());
			new ShotPlayer();
		}
	}
	
	public void slash() {
		if(!knifeAvailable() || Handler.getInstance().player == null) return ;
		
		Handler.getInstance().addObject(new Knife(getxPos(), getyPos(), ID.Knife, false));
		new Slash();
	}
	
	private double cut(double val, double low, double high) {
		if(val > high) return high;
		if(val < low) return low;
		return val;
	}
	
	@Override
	public void render(GraphicsContext gc) { // Set Player Graphics
		if(SOLID_SHOW) ShowSolidArea(gc);

		gc.drawImage(currentAni, xPos, yPos);
		return ;
	}
	
	private void walkAni(int frame) {
		switch(direct) {
			case "L" : currentAni = T_Left[frame]; break;
			case "R" : currentAni = T_Right[frame]; break;
			case "U" : currentAni = T_Up[frame]; break;
			case "D" : currentAni = T_Down[frame]; break;
			default : {
				switch(prv_direct) {
					case "L" : currentAni = T_Left[defaultAni]; break;
					case "R" : currentAni = T_Right[defaultAni]; break;
					case "U" : currentAni = T_Up[defaultAni]; break;
					case "D" : currentAni = T_Down[defaultAni]; break;
					default : currentAni = previousAni; break;
				}
				break;
			}
		}
	}
	
	private void knifeAni(int frame) { // TODO
		switch(direct) {
			case "L" : currentAni = T_Left[frame]; break;
			case "R" : currentAni = T_Right[frame]; break;
			case "U" : currentAni = T_Up[frame]; break;
			case "D" : currentAni = T_Down[frame]; break;
			default : {
				switch(prv_direct) {
					case "L" : currentAni = T_Left[defaultAni]; break;
					case "R" : currentAni = T_Right[defaultAni]; break;
					case "U" : currentAni = T_Up[defaultAni]; break;
					case "D" : currentAni = T_Down[defaultAni]; break;
					default : currentAni = previousAni; break;
				}
				break;
			}
		}
	}

	private void gunAni(int frame) { // TODO
		if(Checker.unpressedWalkDirection(key) && Checker.unpressedHitDirection(key)) {
			switch(direct) {
				case "LEFT" : currentAni = T_Left[frame]; break;
				case "RIGHT" : currentAni = T_Right[frame]; break;
				case "UP" : currentAni = T_Up[frame]; break;
				case "DOWN" : currentAni = T_Down[frame]; break;
				case "L" : currentAni = T_Left[frame]; break;
				case "R" : currentAni = T_Right[frame]; break;
				case "U" : currentAni = T_Up[frame]; break;
				case "D" : currentAni = T_Down[frame]; break;
				default : {
					switch(prv_direct) {
						case "LEFT" : currentAni = T_Left[defaultAni]; break;
						case "RIGHT" : currentAni = T_Right[defaultAni]; break;
						case "UP" : currentAni = T_Up[defaultAni]; break;
						case "DOWN" : currentAni = T_Down[defaultAni]; break;
						case "L" : currentAni = T_Left[defaultAni]; break;
						case "R" : currentAni = T_Right[defaultAni]; break;
						case "U" : currentAni = T_Up[defaultAni]; break;
						case "D" : currentAni = T_Down[defaultAni]; break;
						default : currentAni = previousAni; break;
					}
					break;
				}
			}
		}
		else if(Checker.unpressedWalkDirection(key)) {
			switch(direct) {
				case "LEFT" : currentAni = T_Left[defaultAni]; break;
				case "RIGHT" : currentAni = T_Right[defaultAni]; break;
				case "UP" : currentAni = T_Up[defaultAni]; break;
				case "DOWN" : currentAni = T_Down[defaultAni]; break;
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
		else if(Checker.unpressedHitDirection(key)) {
			switch(direct) {
				case "L" : currentAni = T_Left[frame]; break;
				case "R" : currentAni = T_Right[frame]; break;
				case "U" : currentAni = T_Up[frame]; break;
				case "D" : currentAni = T_Down[frame]; break;
				default : {
					switch(prv_direct) {
						case "L" : currentAni = T_Left[defaultAni]; break;
						case "R" : currentAni = T_Right[defaultAni]; break;
						case "U" : currentAni = T_Up[defaultAni]; break;
						case "D" : currentAni = T_Down[defaultAni]; break;
						default : currentAni = previousAni; break;
					}
					break;
				}
			}
		}
		else switch(direct) {
			case "LEFT" : currentAni = T_Left[frame]; break;
			case "RIGHT" : currentAni = T_Right[frame]; break;
			case "UP" : currentAni = T_Up[frame]; break;
			case "DOWN" : currentAni = T_Down[frame]; break;
			case "L" : currentAni = T_Left[frame]; break;
			case "R" : currentAni = T_Right[frame]; break;
			case "U" : currentAni = T_Up[frame]; break;
			case "D" : currentAni = T_Down[frame]; break;
			default : {
				switch(prv_direct) {
					case "LEFT" : currentAni = T_Left[defaultAni]; break;
					case "RIGHT" : currentAni = T_Right[defaultAni]; break;
					case "UP" : currentAni = T_Up[defaultAni]; break;
					case "DOWN" : currentAni = T_Down[defaultAni]; break;
					case "L" : currentAni = T_Left[defaultAni]; break;
					case "R" : currentAni = T_Right[defaultAni]; break;
					case "U" : currentAni = T_Up[defaultAni]; break;
					case "D" : currentAni = T_Down[defaultAni]; break;
					default : currentAni = previousAni; break;
				}
				break;
			}
		}
	}
	
	public void addItemInBag(GameObject Item) {
		bag.add(Item);
	}
	
	public void setAcDc(double _ac, double _dc) {
		setAc(_ac);
		setDc(_dc);
	}
	
	// Getters & Setters

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public double getAc() {
		return ac;
	}

	public void setAc(double ac) {
		this.ac = ac;
	}

	public double getDc() {
		return dc;
	}

	public void setDc(double dc) {
		this.dc = dc;
	}

	public boolean isSwaped() {
		return swaped;
	}

	public void setSwaped(boolean swaped) {
		this.swaped = swaped;
	}

	public boolean isForceStop() {
		return forceStop;
	}

	public void setForceStop(boolean forceStop) {
		this.forceStop = forceStop;
	}

	public ArrayList<GameObject> getBag() {
		return bag;
	}

	public void setBag(ArrayList<GameObject> bag) {
		this.bag = bag;
	}

	public static double getCurxPos() {
		return curxPos;
	}

	public static void setCurxPos(double curxPos) {
		Player.curxPos = curxPos;
	}

	public static double getCuryPos() {
		return curyPos;
	}

	public static void setCuryPos(double curyPos) {
		Player.curyPos = curyPos;
	}

	public Keys getKey() {
		return key;
	}

	public void setKey(Keys key) {
		this.key = key;
	}

	public KeyInput getInput() {
		return input;
	}

	public void setInput(KeyInput input) {
		this.input = input;
	}

	public Image[] getT_Up() {
		return T_Up;
	}

	public void setT_Up(Image[] t_Up) {
		T_Up = t_Up;
	}

	public Image[] getT_Down() {
		return T_Down;
	}

	public void setT_Down(Image[] t_Down) {
		T_Down = t_Down;
	}

	public Image[] getT_Left() {
		return T_Left;
	}

	public void setT_Left(Image[] t_Left) {
		T_Left = t_Left;
	}

	public Image[] getT_Right() {
		return T_Right;
	}

	public void setT_Right(Image[] t_Right) {
		T_Right = t_Right;
	}

	public Image getCurrentAni() {
		return currentAni;
	}

	public void setCurrentAni(Image currentAni) {
		this.currentAni = currentAni;
	}

	public Image getPreviousAni() {
		return previousAni;
	}

	public void setPreviousAni(Image previousAni) {
		this.previousAni = previousAni;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getDefaultAni() {
		return defaultAni;
	}
}