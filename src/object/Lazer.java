package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.ID;
import logic.base.InteractivePlayer;
import logic.person.Player;
import utilz.LoadSave;
import application.sound.LazerAttack;
import static utilz.Constants.Debug.*;

public class Lazer extends GameObject implements Attackable, InteractivePlayer {
	
	private static final long serialVersionUID = 1L;
	private int damage;
	private int interval, intervalCount;
	private boolean show;
	transient private Image imageShow;
	transient private Image imageNotShow;
	
	public Lazer(int xPos, int yPos, ID id, int damage, int interval) {
		super(xPos, yPos, id);
		if(getId() == ID.Lazer1) {
			setSolidArea(new Rectangle(getxPos()+20, getyPos()+48, 5, 96));
			setxDif(20);
			setyDif(48);
			setW(5);
			setH(96);
		}
		else {
			setSolidArea(new Rectangle(getxPos(), getyPos()+120, 48, 5));
			setxDif(0);
			setyDif(120);
			setW(48);
			setH(5);
		}
		setDamage(damage);
		setInterval(interval);
		setShow(false);
		setIntervalCount(0);
		initImg();
	}
	
	public void initImg() {
		if(getId() == ID.Lazer1) {
			imageShow = LoadSave.GetSpriteAtlas(LoadSave.LAZER_1);
			imageNotShow = LoadSave.GetSpriteAtlas(LoadSave.LAZER_1);
		}
		else {
			imageShow = LoadSave.GetSpriteAtlas(LoadSave.LAZER_2);
			imageNotShow = LoadSave.GetSpriteAtlas(LoadSave.LAZER_2);
		}
	}
	
	public void update() {
		if(intervalCount == getInterval()) {
			setShow(isShow()^true);
			intervalCount = 0;
		}
		else intervalCount++;
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) {
			if(getId() == ID.Lazer1) ShowSolidArea(gc);
			if(getId() == ID.Lazer2) ShowSolidArea(gc);
		}
		
		if(isShow()) gc.drawImage(imageShow ,getxPos() ,getyPos());
	}
	
	public int damage() {
		return this.damage;
	}

	public int dpsDamage() {
		return this.damage;
	}
	
	public void interact(Player player) {
		if(!isShow()) return;
//		new LazerAttack();
		player.setHp(player.getHp() - dpsDamage());
	}

	// Getters & Setters
	
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Image getImageShow() {
		return imageShow;
	}

	public void setImageShow(Image imageShow) {
		this.imageShow = imageShow;
	}

	public Image getImageNotShow() {
		return imageNotShow;
	}

	public void setImageNotShow(Image imageNotShow) {
		this.imageNotShow = imageNotShow;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = Math.max(0, damage);
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = Math.max(0, interval);
	}

	public int getIntervalCount() {
		return intervalCount;
	}

	public void setIntervalCount(int intervalCount) {
		this.intervalCount = intervalCount;
	}
}