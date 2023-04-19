package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Pickable;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class KeyLocker extends GameObject implements StableObject, Pickable {

	public boolean picked;
	public Image image;
	private Handler handler;
	
	public KeyLocker(int xPos, int yPos, ID id) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		setSolidArea(new Rectangle(getxPos() - 10, getyPos() - 10, 60, 90));
		initImg();
	}
	
	private void initImg() {
		switch(getId()) {
			case Key1 : {
				image = LoadSave.GetSpriteAtlas(LoadSave.KEY1);
				break;
			}
			case Key2 : {
				image = LoadSave.GetSpriteAtlas(LoadSave.KEY2);
				break;
			}
			default : break;
		}
		
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, -10, -10);
		
		if(isPicked() == false) gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		handler.removeObject(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	// Getters & Setters
	
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
