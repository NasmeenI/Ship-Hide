package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePlayer;
import logic.person.Player;
import utilz.LoadSave;
import application.GameProcess;
import application.sound.Pick;
import static utilz.Constants.Debug.*;

public class KeyLocker extends GameObject implements InteractivePlayer {

	private static final long serialVersionUID = 1L;
	private boolean picked;
	transient private Image image;
	
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
		shift();
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		if(isPicked() == false) gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		new Pick();
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
