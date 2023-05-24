package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePerson;
import logic.person.Commander;
import logic.person.Criminal;
import logic.person.Person;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

import application.sound.Pick;

public class Knife extends GameObject implements Attackable, InteractivePerson {
	
	private static final long serialVersionUID = 1L;
	private int damage;
	private boolean picked;
	private boolean visible;
	transient private Image image ,imageUsed;
	
	public Knife(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 0, 0, 100, 50);
		setDamage(700);
		setPicked(false);
		setVisible(false);
		initImg();
	}
	
	public Knife(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id, 0, 0, 100, 50);
		setDamage(700);
		setPicked(false);
		setVisible(visible);
		initImg();
	}

	public void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.KNIFE);
		this.imageUsed = LoadSave.GetSpriteAtlas(LoadSave.KNIFE_USED);
	}
	 	
	public void update() {
		if(!isVisible()) Handler.getInstance().removeObject(this);
		shift();
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);

		if(!isVisible()) return ;
		gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public int damage() {
		return getDamage();
	}
	
	public int dpsDamage() {
		return 0;
	}
	
	public void interact(Person person) {
		if(person instanceof Player player) {
			if(!isVisible()) return;
			new Pick();
			setPicked(true);
			player.addItemInBag(this);
			player.setKnife(true);
			Handler.getInstance().removeObject(this);
		}
		else if(person instanceof Criminal criminal) {
			if(isVisible()) return;
			criminal.setHp(criminal.getHp() - damage());
			criminal.setChase();
		}
		else if(person instanceof Commander commander) {
			if(isVisible()) return;
			commander.setHp(commander.getHp() - damage());
			commander.setChase();
		}
	}
	
	// Getters & Setters
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		return ;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImageUsed() {
		return imageUsed;
	}

	public void setImageUsed(Image imageUsed) {
		this.imageUsed = imageUsed;
	}
}
