package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;
import static utilz.Constants.Player.*;

public class DoorJail extends GameObject implements StableObject {
	public static boolean opened;
	private Image imageOpen;
	private Image imageClose;
	
	public DoorJail(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, 0, 0, P_WIDTH, P_HEIGHT);
		setOpened(false);
		initImg();
	}
	
	private void initImg() {
		imageOpen = LoadSave.GetSpriteAtlas(LoadSave.DOOR3_OPEN);
		imageClose = LoadSave.GetSpriteAtlas(LoadSave.DOOR3_CLOSE);		
	}
	
	@Override
	public void update() {

	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 0, 0);
		
		if(isOpened()) gc.drawImage(imageOpen ,getxPos() ,getyPos());
		else gc.drawImage(imageClose ,getxPos() ,getyPos());
		return ;
	}
	
	@Override
	public void interact(Player player) {
		
	}
	
	// Getter & Setter

	public boolean isOpened() {
		return opened;
	}

	public static void setOpened(boolean opened) {
		DoorJail.opened = opened;
	}
}