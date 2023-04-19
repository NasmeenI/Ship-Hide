package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;
import static utilz.Constants.Debug.*;

public class Computer extends GameObject implements StableObject {
	
	private Handler handler;
	private Image imageBase;
	private Image imageInteract;
	private boolean interacted;
	
	public Computer(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -50, 0, 150, 150);
		this.handler = Handler.getInstance();
		interacted = false;
		initImg();
	}

	public void update() {
		double dis = Obj.distance(this, handler.Player);
		if(dis < 4*48) setInteracted(true);
		else setInteracted(false);
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, -50, 0);
		if(interacted) gc.drawImage(imageInteract ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		
	}
	
	public void initImg() {
		imageInteract = LoadSave.GetSpriteAtlas(LoadSave.PRESS_E);
	}

	public Image getImageBase() {
		return imageBase;
	}

	public void setImageBase(Image imageBase) {
		this.imageBase = imageBase;
	}

	public Image getImageInteract() {
		return imageInteract;
	}

	public void setImageInteract(Image imageInteract) {
		this.imageInteract = imageInteract;
	}
	
	public boolean isInteracted() {
		return interacted;
	}

	public void setInteracted(boolean interacted) {
		this.interacted = interacted;
	}

}