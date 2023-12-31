package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.PressEable;
import logic.person.Player;
import ui.PasswordPopUp;
import utilz.LoadSave;
import utilz.Obj;
import application.GameProcess;
import static utilz.Constants.Debug.*;

public class Computer extends GameObject implements PressEable {
	
	private static final long serialVersionUID = 1L;
	private boolean interacted;
	private boolean saved;
	transient private Image imageInteract;
	
	public Computer(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -50, 0, 150, 150);
		setInteracted(false);
		setSaved(false);
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
		if(!saved) {
			GameProcess.save();
			saved = true;
		}
		PasswordPopUp ppu = new PasswordPopUp();
		Obj.gridPane = ppu.ShowPasswordScene();
		Obj.temp1 = ((Player)player).getAc();
		Obj.temp2 = ((Player)player).getDc();
		player.setAcDc(0, 0);
		player.setForceStop(true);
		GameProcess.getRoot().getChildren().addAll(Obj.gridPane);
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

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}