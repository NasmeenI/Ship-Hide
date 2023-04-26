package logic.person;

import application.GameProcess;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.KeyInput;
import logic.base.Keys;
import logic.base.Map;
import logic.container.Gun;
import logic.container.Knife;
import object.Sculpture;
import utilz.Checker;
import utilz.LoadSave;
import utilz.Obj;
import java.util.ArrayList;
import Scenes.GameOverScene;
import static utilz.Constants.Player.*;
import static utilz.Constants.Debug.*;
import static utilz.Constants.GameState.*;

public class Player extends Person {
	
	private static final long serialVersionUID = 1L;
	// default
	private double _ac = .8f;
	private double _dc = .4f;
	transient private KeyInput input;

	transient private Keys key;

	public static double _CurxPos;
	public static double _CuryPos;

	transient private Image[] T_Up, T_Down, T_Left, T_Right;
	private final int defaultAni = 9;
	transient private Image currentAni, previousAni;
	
	public ArrayList<GameObject> bag;
	
	public Player(double xPos, double yPos, ID id, KeyInput input) {
		super(xPos, yPos, id, 10, 5, P_WIDTH, P_HEIGHT);
		this.input = input;
		_CurxPos = xPos;
		_CuryPos = yPos; 
		this.bag = new ArrayList<>();
		initImg();
		setHp(15000);
		setDirect("U");
		setPrv_direct("Z");
		setKey(new Keys());
		previousAni = T_Up[defaultAni];
		// Tempt
//		this.addItemInBag(new KeyLocker(0 ,0 ,ID.Key1));
//		this.addItemInBag(new KeyLocker(0 ,0 ,ID.Key2));
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
	
		return ;
	}

	@Override
	public void update() {
		if(getHp() == 0) {
			GameProcess.stage.setScene(GameOverScene.scene);
			GameProcess.setGameState(GAME_OVER_STATE);
		}
		Obj.collision(this);
		
		for(int i = 0; i < Handler.getInstance().allObjects.size(); i++) {
            if(Handler.getInstance().allObjects.get(i).getCode() == getCode()) continue;
            if((Handler.getInstance().allObjects.get(i) instanceof Sculpture) && getFootArea().intersects(Handler.getInstance().allObjects.get(i).getSolidArea().getBoundsInLocal())) {
                Obj.action(this, Handler.getInstance().allObjects.get(i));
            }
        }
		
		setKey(input.key);
		
		if(key.ONE) {
			setUsed(1);
			if(getUsed() == 1) setAc(.8f, .4f);
		}
		if(key.TWO) {
			setUsed(2);
			if(getUsed() == 2) setAc(.7f, .35f);
		}
		if(key.THREE) {
			setUsed(3);
			if(getUsed() == 3) setAc(.5f, .25f);
		}
		
		if(getUsed() == 1) setDirect(Checker.KeyWalkDirection(key));
		else setDirect(Checker.KeyDirection(key));
		
		Walk();
		setBeforeTwo(Obj.collisionTwo(this));
		
		if(getKnifeTime() < 30) setKnifeTime(getKnifeTime() + 1);
		if(getBulletTime() < 20) setBulletTime(getBulletTime() + 1);
		if(getReloadTime() < 30) setReloadTime(getReloadTime() + 1);
		
		if(getUsed() != 1) {
			if(key.SPACE && getUsed() == 2 && getKnifeTime() == 30) {
				slash();
				setKnifeTime(0);
			}
			if((key.LEFT || key.RIGHT || key.UP || key.DOWN) && getUsed() == 3 && getBulletTime() == 20) {
				shoot();
				setBulletTime(0);
			}
		}
		
		if(key.R && GunAvailable() && getUsed() == 3 && getReloadTime() == 30) { // Reload Option (Press R)
			ArrayList<GameObject> bag = this.getBag();
			for(int i=0;i<bag.size();i++) {
				if(bag.get(i) instanceof Gun) {
					((Gun)bag.get(i)).reload();
					break;
				}
			}
			setReloadTime(0);
		}
		
		
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 5, P_WIDTH, P_HEIGHT));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW(), 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));		
		Animation();
		
		return ;
	}
	
	public void Walk() {
		_CurxPos = getxPos();
		_CuryPos = getyPos();
		
		double _Vx = getxVelo();
		double _Vy = getyVelo();
		
		if(key.A) _Vx -= _ac;
		else if(key.D) _Vx += _ac;
		else {
			if(_Vx > 0) _Vx -= _dc;
			else if(_Vx < 0) _Vx += _dc;
		}
		
		if(key.W) _Vy -= _ac;
		else if(key.S) _Vy += _ac;
		else {
			if(_Vy > 0) _Vy -= _dc;
			else if(_Vy < 0) _Vy += _dc;
		}
		
		_Vx = cut(_Vx, -get_ac() * 4, get_ac() * 4);
		_Vy = cut(_Vy, -get_ac() * 4, get_ac() * 4);
		
		
		int newXPos = 0 ,newYPos = 0;
		if(_Vx >= 0) newXPos = ((int)((getxPos() + _Vx - 10)/48));			
		else newXPos = ((int)((getxPos() + _Vx - 20)/48));	
		
		if(_Vy >= 0) newYPos = ((int)((getyPos() + _Vy - 4)/48)) + 2;		 	
		else newYPos = ((int)((getyPos() + _Vy - 20)/48)) + 2;
		
		
//		if(Map.getInstance().mapTileNum[(int)((getyPos()-10)/48)+2][(int)((getxPos()-15)/48)] == 2) setBeforeTwo(true);
//		else setBeforeTwo(false);

//		setxPos(getxPos() + _Vx + (key.SHIFT ? _Vx : 0));
//		setyPos(getyPos() + _Vy + (key.SHIFT ? _Vy : 0));
		
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
		
		if(Map.getInstance().mapTileNum[newYPos][newXPos] != 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[newYPos][(int)(getxPos()-10)/48] != 0 && _Vx >= 0) {
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[newYPos][(int)(getxPos()-20)/48] != 0 && _Vx < 0) {
			setyPos(getyPos() + _Vy - (key.SHIFT ? _Vy / 2 : 0));
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[((int)(getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[((int)(getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
			setxPos(getxPos() + _Vx - (key.SHIFT ? _Vx / 2 : 0));
		}
		
		
		setxVelo(_Vx);
		setyVelo(_Vy);
		
	}
	
	public void Animation() {
		if(direct != prv_direct) SpriteCnt = 0;
		int frame = (SpriteCnt / 5) % 8;
		
		switch(getUsed()) {
			case 1 : WalkAni(frame); break;
			case 2 : KnifeAni(frame); break;
			case 3 : GunAni(frame); break;
			default : WalkAni(frame); break;
		}
		
		SpriteCnt++;
		if(direct != "Z") prv_direct = direct;
		previousAni = currentAni;
		SpriteCnt %= 40;
		return ;
	}
	
	public void shoot() {
		if(!GunAvailable() || Handler.getInstance().Player == null) return ;
		
		Gun gun = null;
		ArrayList<GameObject> bag = this.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Gun) {
				gun = ((Gun)bag.get(i));
				break;
			}
		}
		
		if(!gun.shootAble()) return ;
		
		gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
		
		return ;
	}
	
	public void slash() {
		if(!KnifeAvailable() || Handler.getInstance().Player == null) return ;
		
		Handler.getInstance().addObject(new Knife(getxPos(), getyPos(), ID.Knife, false));
		
		return ;
	}
	
	private double cut(double val, double low, double high) {
		if(val > high) return high;
		if(val < low) return low;
		return val;
	}
	
//	private boolean inFramex(double xPos) {
//		return xPos <= Game.WIDTH - 44 && xPos >= 0 ? true : false;
//	}
//	
//	private boolean inFramey(double yPos) {
//		return yPos <= Game.HEIGHT - 64 && yPos >= 0 ? true : false;
//	}

	@Override
	public void render(GraphicsContext gc) { // Set Player Graphics
		if(SOLID_SHOW) ShowSolidArea(gc);
		showFootArea(gc);

		gc.drawImage(currentAni, xPos, yPos);
		return ;
	}
	
	private void WalkAni(int frame) {
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
	
	private void KnifeAni(int frame) { // TODO
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

	private void GunAni(int frame) { // TODO
		if(Checker.UnpressedWalkDirection(key) && Checker.UnpressedHitDirection(key)) {
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
		else if(Checker.UnpressedWalkDirection(key)) {
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
		else if(Checker.UnpressedHitDirection(key)) {
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
	
	// Getters & Setters

	public void setAc(double _ac, double _dc) {
		set_ac(_ac);
		set_dc(_dc);
		return ;
	}
	
	public double get_ac() {
		return _ac;
	}

	public void set_ac(double _ac) {
		this._ac = _ac;
		return ;
	}

	public double get_dc() {
		return _dc;
	}

	public void set_dc(double _dc) {
		this._dc = _dc;
		return ;
	}

	public KeyInput getInput() {
		return input;
	}

	public void setInput(KeyInput input) {
		this.input = input;
		return ;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
		return ;
	}

	public Keys getKey() {
		return key;
	}

	public void setKey(Keys key) {
		this.key = key;
		return ;
	}
	
	public void addItemInBag(GameObject Item) {
		bag.add(Item);
	}
	
	public ArrayList<GameObject> getBag(){
		return this.bag;
	}	
}