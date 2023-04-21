package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.Attackable;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Pickable;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class Knife extends GameObject implements Attackable, StableObject, Pickable {
	
	public boolean picked;
	private boolean visible;
	private Handler handler;
	private Image image ,imageUsed;

	public Knife(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.visible = false;
		this.picked = false;
		setSolidArea(new Rectangle(getxPos(), getyPos(), 70, 100));
		initImg();
	}
	
	public Knife(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.visible = visible;
		this.picked = false;
		setSolidArea(new Rectangle(getxPos(), getyPos(), 70, 100));
		initImg();
	}

	public void update() {
		if(!isVisible()) handler.removeObject(this);
		return ;
	}

	private void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.KNIFE);
		this.image = LoadSave.GetSpriteAtlas(LoadSave.KNIFE_USED);
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 0, 0);
		
		if(!isVisible()) return ;
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		player.setKnife(true);
		handler.removeObject(this);
	}
	
	public int damage() {
		return 1200;
	}
	
	public int dps_damage() {
		return 0;
	}
	
	// Getters & Setters
	
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
