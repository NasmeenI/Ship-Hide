package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;
import application.GameProcess;
import Scenes.GameComplete;
import static utilz.Constants.GameState.*;
import static utilz.Constants.Debug.*;

public class Helicopter extends GameObject implements StableObject {
	
	private Handler handler;
	private Image imageInteract;
	private boolean interacted;
	
	public Helicopter(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -50, 0, 300, 150);
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
		GameProcess.stage.setScene(GameComplete.scene);
		GameProcess.setGameState(GAME_COMPLETE_STATE);
	}
	
	public void initImg() {
		imageInteract = LoadSave.GetSpriteAtlas(LoadSave.PRESS_E);
	}
	
	
	// Getter & Setter
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