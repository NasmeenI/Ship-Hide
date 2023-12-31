package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.PressEable;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;
import Scenes.Shop;
import application.GameProcess;
import static utilz.Constants.GameState.PAUSE_STATE;
import static utilz.Constants.Debug.SOLID_SHOW;

public class Trader extends GameObject implements PressEable {
	
	private static final long serialVersionUID = 1L;
	private boolean interacted;
	transient private Image image;
	
	public Trader(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -30, 10, 130, 90);
		setInteracted(false);
		initImg();
	}

	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.PRESS_E);
	}
	
	public void update() {
		double dis = Obj.distance(this, Handler.getInstance().player);
		if(dis < 4*48) setInteracted(true);
		else setInteracted(false);
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		if(interacted) gc.drawImage(image ,getxPos() ,getyPos());
	}
	
	public void interact(Player player) {
		GameProcess.setGameState(PAUSE_STATE);
	    GameProcess.stage.setScene(Shop.scene);
	}
	
	// Getter & Setter
	
	public boolean isInteracted() {
		return interacted;
	}

	public void setInteracted(boolean interacted) {
		this.interacted = interacted;
	}
}