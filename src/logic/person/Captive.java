package logic.person;

import static utilz.Constants.Debug.SOLID_SHOW;
import static utilz.Constants.GameState.GAME_OVER_STATE;
import static utilz.Constants.Player.P_HEIGHT;
import static utilz.Constants.Player.P_WIDTH;
import static utilz.Constants.Tile.TILESIZE;

import Scenes.GameOverScene;
import application.GameProcess;
import application.Music;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import ui.PasswordPopUp;
import utilz.Checker;
import utilz.LoadSave;
import utilz.Obj;

public class Captive extends Person {
	
	private static final long serialVersionUID = 1L;
	private static boolean ready;
	private final int defaultAni = 0;
	transient private Image currentAni, previousAni;
	transient private Image[] T_Up, T_Down, T_Left, T_Right;
	
	public Captive(double xPos, double yPos, ID id, double xVelo, double yVelo) {
		super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
		setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(3000);
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
        initImg();
	}

	@Override
	public void initImg() {
		T_Up = new Image[3];
		T_Up[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Up_Default);
		T_Up[0] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Up_1);
		T_Up[1] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Up_2);
		
		T_Down = new Image[3];
		T_Down[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Down_Default);
		T_Down[0] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Down_1);
		T_Down[1] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Down_2);

		T_Left = new Image[3];
		T_Left[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Left_Default);
		T_Left[0] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Left_1);
		T_Left[1] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Left_2);
		
		T_Right = new Image[3];
		T_Right[defaultAni] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Right_Default);
		T_Right[0] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Right_1);
		T_Right[1] = LoadSave.GetSpriteAtlas(LoadSave.Captive_Animation_Right_2);
	}

	@Override
	public void update() {
		if(getHp() == 0) {
			GameProcess.stage.setScene(GameOverScene.scene);
			GameProcess.setGameState(GAME_OVER_STATE);
			Music.stop();
			Music.gameOver.play();
		}
		
		Obj.collision(this);
		if(PasswordPopUp.accessGranted) {
			setChasing(true);
			setxVelo(3);
			setyVelo(3);
		}
		else {
			setxVelo(.5f);
			setyVelo(.5f);
		}
		if(isChasing()) {
			Point mP = getMiddlePoint(Handler.getInstance().player.getSolidArea());
			searchPath((int) (mP.getY() / TILESIZE), (int) (mP.getX() / TILESIZE));
		}
		else randomWalk(60);
		if(Obj.distance(this, Handler.getInstance().player) <= 100) setReady(true);
		else setReady(false);
		
		setBeforeTwo(Obj.collisionTwo(this));
		setAllArea();
		animation();
	}
	
	public void setAllArea() {
		setSolidArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif(), getW(), getH()));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW()-10, 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));
	}
	
	@Override
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
		gc.setFill(Color.GREEN);
		gc.fillRect(getxPos() + getxDif() - 10, getyPos() + getyDif() -10, (getHp()*70)/3000, 10);
	}

	@Override
	public void shoot() {
		return ;
	}

	@Override
	public void slash() {
		return ;
	}

	public static boolean isReady() {
		return ready;
	}

	public static void setReady(boolean ready) {
		Captive.ready = ready;
	}
}
