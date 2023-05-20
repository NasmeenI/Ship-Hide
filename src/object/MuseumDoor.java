package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Map;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import application.GameProcess;
import application.Music;
import application.sound.OpenMusiumDoor;

import static utilz.Constants.GameProcess.*;
import static utilz.Constants.Debug.*;

public class MuseumDoor extends GameObject implements StableObject {
	
	private static final long serialVersionUID = 1L;
	private int[][] password;
	private boolean opened;
	private boolean saved;
	private Sculpture[] sculpture;
	transient private Image imageOpen;
	transient private Image imageClose;
	
	public MuseumDoor(int xPos, int yPos, ID id, Sculpture[] sculpture) {
		super(xPos, yPos, id ,0 ,10 ,100 ,100);
		setOpened(false);
		setSaved(false);
		setSculpture(sculpture);
		initPassword();
		initImg();
	}
	
	public void initImg() {
		switch(getId()) {
			case MusiumDoor : {
				imageOpen = LoadSave.GetSpriteAtlas(LoadSave.MUSIUM_DOOR_OPEN);
				imageClose = LoadSave.GetSpriteAtlas(LoadSave.MUSIUM_DOOR_CLOSE);
				break;
			}
			default : break;
		}
	}
	
	@Override
	public void update() {
		int cnt = 0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(Math.abs(sculpture[i].getxPos()/TILE_SIZE - password[j][0]) <= 2 && Math.abs(sculpture[i].getyPos()/TILE_SIZE - password[j][1]) <= 2) {
					cnt++;
					break;
				}				
			}
		}
		if(cnt == 4) {
			Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1, 1);
			Map.getInstance().setStageMap((int)getxPos()/48, (int)getyPos()/48+1, 1);
			Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+2, 1);
			Map.getInstance().setStageMap((int)getxPos()/48, (int)getyPos()/48+2, 1);
			setOpened(true);
			if(!saved) {
				GameProcess.save();
				saved = true;
				new OpenMusiumDoor();
			}
			Handler.progress = 3;
			Music.play();
		}
		else {
			Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1, 0);
			Map.getInstance().setStageMap((int)getxPos()/48, (int)getyPos()/48+1, 0);
			Map.getInstance().setStageMap((int)getxPos()/48-1, (int)getyPos()/48+2, 0);
			Map.getInstance().setStageMap((int)getxPos()/48, (int)getyPos()/48+2, 0);
			setOpened(false);
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		if(opened) gc.drawImage(imageOpen ,getxPos() ,getyPos());
		else gc.drawImage(imageClose ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		// Nothing happened
	}
	
	private void initPassword() {
		password = new int[4][2];
		password[0][0] = 68;
		password[0][1] = 46;
		
		password[1][0] = 52;
		password[1][1] = 40;

		password[2][0] = 72;
		password[2][1] = 41;
		
		password[3][0] = 58;
		password[3][1] = 42;
	}
	
	// Getters & Setters
	
	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public int[][] getPassword() {
		return password;
	}

	public void setPassword(int[][] password) {
		this.password = password;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public Sculpture[] getSculpture() {
		return sculpture;
	}

	public void setSculpture(Sculpture[] sculpture) {
		this.sculpture = sculpture;
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