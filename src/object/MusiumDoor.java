package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.ID;
import logic.base.Map;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Player.*;
import static utilz.Constants.GameProcess.*;
import static utilz.Constants.Debug.*;

public class MusiumDoor extends GameObject implements StableObject {
	
	private boolean opened;
	private Image imageOpen;
	private Image imageClose;
	private Sculpture[] sculpture;
	private int[][] password;
	
	public MusiumDoor(int xPos, int yPos, ID id, Sculpture[] sculpture) {
		super(xPos, yPos, id ,0 ,0 ,P_WIDTH ,P_HEIGHT);
		setOpened(false);
		this.sculpture = sculpture;
		initPassword();
		initImg();
	}
	
	@Override
	public void update() {
		int cnt = 0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if((int)sculpture[i].getxPos()/TILE_SIZE == password[i][0] && (int)sculpture[i].getyPos()/TILE_SIZE == password[i][1]) {
					cnt++;
					break;
				}				
			}
		}
		if(cnt == 4) {
			Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1, 1);
			Map.setStageMap((int)getxPos()/48, (int)getyPos()/48+1, 1);
			Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+2, 1);
			Map.setStageMap((int)getxPos()/48, (int)getyPos()/48+2, 1);
			setOpened(true);
		}
		else {
			Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+1, 0);
			Map.setStageMap((int)getxPos()/48, (int)getyPos()/48+1, 0);
			Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+2, 0);
			Map.setStageMap((int)getxPos()/48, (int)getyPos()/48+2, 0);
			setOpened(false);
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 0, 0);
		
		if(opened) gc.drawImage(imageOpen ,getxPos() ,getyPos());
		else gc.drawImage(imageClose ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		
	}
	
	private void initImg() {
		switch(getId()) {
			case MusiumDoor : {
				imageOpen = LoadSave.GetSpriteAtlas(LoadSave.MUSIUM_DOOR_OPEN);
				imageClose = LoadSave.GetSpriteAtlas(LoadSave.MUSIUM_DOOR_CLOSE);
				break;
			}
			default : break;
		}
	}
	
	private void initPassword() {
		password = new int[4][2];
		password[0][0] = 68;
		password[0][1] = 47;
		
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
	
}