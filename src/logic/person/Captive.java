package logic.person;

import static utilz.Constants.Debug.SOLID_SHOW;
import static utilz.Constants.Player.P_HEIGHT;
import static utilz.Constants.Player.P_WIDTH;
import static utilz.Constants.Tile.TILESIZE;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Point;
import ui.PasswordPopUp;
import utilz.Checker;
import utilz.Obj;

public class Captive extends Person {
	
	private static final long serialVersionUID = 1L;
	private static boolean ready;
	
	public Captive(double xPos, double yPos, ID id, double xVelo, double yVelo) {
		super(xPos, yPos, id, 10, 5 , P_WIDTH , P_HEIGHT);
		setxVelo(xVelo);
        setyVelo(yVelo);
        setHp(100);
        setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
        initImg();
	}

	@Override
	public void initImg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
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
			SearchPath((int) (mP.y / TILESIZE), (int) (mP.x / TILESIZE));
			setDirect(Obj.getDirection(this, Handler.getInstance().player));
		}
		else randomWalk(60);
		if(Obj.distance(this, Handler.getInstance().player) <= 100) setReady(true);
		else setReady(false);
		
		setBeforeTwo(Obj.collisionTwo(this));
		setSolidArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif(), getW(), getH()));
		setFootArea(new Rectangle(getxPos() + getxDif(), getyPos() + getyDif() + P_HEIGHT - 10, getW()-10, 10));
		setRenderArea(new Rectangle(getxPos() + getxDif(), getyPos() +getyDif() + 40, getW(), getH()-40));	
	}
	
	@Override
	public void Animation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);

		gc.setFill(Color.PINK);
		gc.fillRect(getxPos() + getxDif(), getyPos() + getxDif(), getW(), getH());
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
