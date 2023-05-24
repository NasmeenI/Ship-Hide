package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.ID;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;
import static utilz.Constants.Player.*;

public class DoorJail extends GameObject {
	
	private static final long serialVersionUID = 1L;
	public static boolean opened;
	transient private Image imageOpen;
	transient private Image imageClose;
	
	public DoorJail(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, 0, 0, P_WIDTH, P_HEIGHT);
		setOpened(false);
		initImg();
	}
	
	public void initImg() {
		imageOpen = LoadSave.GetSpriteAtlas(LoadSave.DOOR3_OPEN);
		imageClose = LoadSave.GetSpriteAtlas(LoadSave.DOOR3_CLOSE);		
	}
	
	public void update() {
		// Nothing to update
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		if(isOpened()) gc.drawImage(imageOpen ,getxPos() ,getyPos());
		else gc.drawImage(imageClose ,getxPos() ,getyPos());
		return ;
	}
	
	// Getter & Setter

	public boolean isOpened() {
		return opened;
	}

	public static void setOpened(boolean opened) {
		DoorJail.opened = opened;
	}
}