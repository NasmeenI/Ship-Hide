package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import ui.PasswordPopUp;
import utilz.LoadSave;
import utilz.Obj;
import application.GameProcess;
import static utilz.Constants.Debug.*;

public class Computer extends GameObject implements StableObject {
	
	private static final long serialVersionUID = 1L;
	transient private Image imageInteract;
	private boolean interacted;
	private boolean saved = false;
	
	public Computer(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -50, 0, 150, 150);
		interacted = false;
		initImg();
	}

	public void update() {
		double dis = Obj.distance(this, Handler.getInstance().Player);
		if(dis < 4*48) setInteracted(true);
		else setInteracted(false);
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		if(interacted) gc.drawImage(imageInteract ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		PasswordPopUp ppu = new PasswordPopUp();
		Obj.gridPane = ppu.ShowPasswordScene();
		GameProcess.getRoot().getChildren().addAll(Obj.gridPane);
		
		if(!saved) {
			GameProcess.save();
			saved = true;
		}
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