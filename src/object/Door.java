package object;

import java.util.ArrayList;
import application.Music;
import application.sound.OpenDoor1;
import application.sound.OpenDoor2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePlayer;
import logic.base.Map;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class Door extends GameObject implements InteractivePlayer {
	
	private static final long serialVersionUID = 1L;
	private ID keyId;
	private int doorCount;
	private boolean opened;
	transient private Image imageOpen;
	transient private Image imageClose;
	
	public Door(int xPos, int yPos, ID id, ID keyId) {
		super(xPos, yPos, id);
		if(id == ID.Door1) {
			setSolidArea(new Rectangle(getxPos() - 50, getyPos(), 150, 130));
			setxDif(-50);
			setyDif(0);
			setW(150);	
			setH(130);
		}
		else {
			setSolidArea(new Rectangle(getxPos() - 50, getyPos()-10, 110, 150));	
			setxDif(-50);
			setyDif(-10);
			setW(110);
			setH(150);
		}
		setKeyId(keyId);
		setId(id);
		setOpened(false);
		setDoorCount(0);
		initImg();
	}
	
	public void initImg() {
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
	
	public void update() {
		if(isOpened()) {
			if(doorCount < 15) doorCount++;
			else {
				int x = 0;
				if(getId() == ID.Door1) x = 0;
				else if(getId() == ID.Door2) x = 1;
				Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1+x, 0);
			}
		}else {
			doorCount = 0;
		}
		return ;
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) {
			if(getId() == ID.Door1) ShowSolidArea(gc);
			if(getId() == ID.Door2) ShowSolidArea(gc);
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
				if(getId() == ID.Door1) {
					x = 0;
					if(Handler.progress == 0) {
						new OpenDoor1();
						Handler.progress = 1;
						Music.play();
					}
				}
				else if(getId() == ID.Door2) {
					x = 1;
					if(Handler.progress == 1) {
						new OpenDoor2();
						Handler.progress = 2;
						Music.play();
					}
				}
				Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1+x, 2);
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

	public int getDoorCount() {
		return doorCount;
	}

	public void setDoorCount(int doorCount) {
		this.doorCount = doorCount;
	}

	public Image getImageOpen() {
		return imageOpen;
	}

	public void setImageOpen(Image imageOpen) {
		this.imageOpen = imageOpen;
	}

	public Image getImageClose() {
		return imageClose;
	}

	public void setImageClose(Image imageClose) {
		this.imageClose = imageClose;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}