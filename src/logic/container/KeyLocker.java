package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import application.GameProcess;
import static utilz.Constants.Debug.*;

public class KeyLocker extends GameObject implements StableObject {

	private static final long serialVersionUID = 1L;
	public boolean picked;
	transient public Image image;
	
	private double littleMove = 1;
	private double count = 0;
	
	public KeyLocker(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -10, -10, 60, 90);
		initImg();
	}
	
	public void initImg() {
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
	
	public void update() {
		if(littleMove <= -1) count = 0.05f;
		if(littleMove >= 1) count = -0.05f;
		
		littleMove += count;
		
		setyPos(getyPos() + littleMove);
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		if(isPicked() == false) gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		setPicked(true);
		player.addItemInBag(this);
		Handler.getInstance().removeObject(this);
		GameProcess.save();
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
