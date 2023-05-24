package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePlayer;
import logic.person.Player;
import utilz.LoadSave;
import Scenes.MenuScene;
import application.sound.Pick;
import static utilz.Constants.Debug.*;

public class HpBottle extends GameObject implements InteractivePlayer {
	
	private static final long serialVersionUID = 1L;
	private int efficiency;
	private boolean picked;
	transient private Image image;
	
	public HpBottle(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 10, 10, 50, 60);
		setPicked(false);
		initImg();
		if(MenuScene.mode == 0) setEfficiency(1000);
		else if(MenuScene.mode == 1) setEfficiency(10000);
	}
	
	public void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.HPBOTTLE);
	}
	
	@Override
	public void update() {
		shift();
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		gc.drawImage(image ,getxPos() ,getyPos());
	}

	public void interact(Player player) {
		new Pick();
		setPicked(true);
		player.setHp(player.getHp() + getEfficiency());
		Handler.getInstance().removeObject(this);
	}
	
	// Getter & Setter
	
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
	
	public int getEfficiency() {
		return efficiency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}
}