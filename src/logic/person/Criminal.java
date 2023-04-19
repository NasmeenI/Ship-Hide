package logic.person;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.Handler;
import logic.base.ID;
import logic.container.Gun;
import utilz.Checker;
import utilz.Obj;
import static utilz.Constants.Player.*;
import static utilz.Constants.Debug.*;

public class Criminal extends Person {
	
	private Handler handler;
	private double init_xPos, init_yPos, disc;
	private int status = 0;
	
	private Gun gun;

	public Criminal(int xPos, int yPos, ID id, double xVelo, double yVelo, double disc) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		setxVelo(xVelo);
		setyVelo(yVelo);
		init_xPos = xPos;
		init_yPos = yPos;
		this.disc = disc;
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 5, P_WIDTH, P_HEIGHT));
		setHp(100);
		this.gun = new Gun(xPos, yPos, ID.Gun, 10000);
		setDirect(Checker.GetDirectionByVelo(getxVelo(), getyVelo()));
		
		// Tempt
		
		setGun(true);
		setUsed(3);
		
	}

	@Override
	public void update() {
		
		Obj.collision(this);
		if(getHp() == 0) handler.removeObject(this);
		
		Obj.follow(this, handler.Player);
		setDirect(Obj.getDirection(this, handler.Player));
		
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
		
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		
		return ;
	}
	
	public void Animation() { // WAITING...
		if(getDirect() != "Z") setPrv_direct(getDirect());
		return ;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 10, 5);
		
		gc.setFill(Color.RED);
		gc.fillRect(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT);
		return ;
	}

	@Override
	public void shoot() {
		if(!GunAvailable() || handler.Player == null) return ;
		
		if(!gun.shootAble()) return ;
		
		System.out.println("shoot!");
		
		gun.shoot((int)getxPos(), (int)getyPos(), getDirect());
		
		return ;
	}

	@Override
	public void slash() {
		if(!KnifeAvailable() || handler.Player == null) return ;
		
		return ;
	}
	
}
