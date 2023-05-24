package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.PressEable;
import logic.person.Captive;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;
import application.GameProcess;
import application.Music;
import Scenes.GameComplete;
import static utilz.Constants.GameState.*;
import static utilz.Constants.Debug.*;

public class Helicopter extends GameObject implements PressEable {
	
	private static final long serialVersionUID = 1L;
	private boolean interacted;
	transient private Image imageInteract;
	
	public Helicopter(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -50, 0, 300, 150);
		setInteracted(false);
		initImg();
	}
	
	public void initImg() {
		imageInteract = LoadSave.GetSpriteAtlas(LoadSave.PRESS_E);
	}

	public void update() {
		double dis = Obj.distance(this, Handler.getInstance().player);
		if(dis < 4*48) setInteracted(true);
		else setInteracted(false);
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		if(interacted) gc.drawImage(imageInteract ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		if(!Captive.isReady()) return;
		
		Music.stop();
		Music.gameComplete.play();
		GameProcess.stage.setScene(GameComplete.scene);
		GameProcess.setGameState(GAME_COMPLETE_STATE);
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