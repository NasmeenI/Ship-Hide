package object;

import static utilz.Constants.Debug.*;
import static utilz.Constants.Player.*;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.ID;
import logic.base.Map;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;

public class Door extends GameObject implements StableObject {
	private boolean opened;
	private ID keyId;
	private Image imageOpen;
	private Image imageClose;
	
	private int doorCount = 0;
	
	public Door(int xPos, int yPos, ID id, ID keyId) {
		super(xPos, yPos, id);
		if(id == ID.Door1) setSolidArea(new Rectangle(getxPos() - 30, getyPos(), P_WIDTH + 60, P_HEIGHT + 40));
		else setSolidArea(new Rectangle(getxPos() - 50, getyPos(), P_WIDTH + 60, P_HEIGHT + 40));	
		setKeyId(keyId);
		setId(id);
		setOpened(false);
		initImg();
	}
	
	private void initImg() {
		switch(getId()) {
			case Door1 : {
				imageOpen = LoadSave.GetSpriteAtlas(LoadSave.DOOR1_OPEN);
				imageClose = LoadSave.GetSpriteAtlas(LoadSave.DOOR1_CLOSE);
				break;
			}
			case Door2 : {
				imageOpen = LoadSave.GetSpriteAtlas(LoadSave.DOOR2_OPEN);
				imageClose = LoadSave.GetSpriteAtlas(LoadSave.DOOR2_CLOSE);
				break;
			}
			default : break;
		}
		
	}
	
	@Override
	public void update() {
		if(isOpened()) {
			if(doorCount < 15) doorCount++;
			else {
				int x = 0;
				if(getId() == ID.Door1) x = 0;
				else if(getId() == ID.Door2) x = 1;
				Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1+x, 0);
			}
		}else {
			doorCount = 0;
		}
		return ;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) {
			if(getId() == ID.Door1) ShowSolidArea(gc, -30, 0);
			if(getId() == ID.Door2) ShowSolidArea(gc, -50, 0);
		}
		
		if(isOpened() && doorCount < 15) gc.drawImage(imageOpen ,getxPos() ,getyPos());
		else gc.drawImage(imageClose ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		ArrayList<GameObject> bag = player.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i).getId() == getKeyId()) {
				doorCount = 0;
				setOpened(true);
				int x = 0;
				if(getId() == ID.Door1) x = 0;
				else if(getId() == ID.Door2) x = 1;
				Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1+x, 2);
			}
		}
	}
	
	// Getters & Setters
	
	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public ID getKeyId() {
		return keyId;
	}

	public void setKeyId(ID keyId) {
		this.keyId = keyId;
	}

}
