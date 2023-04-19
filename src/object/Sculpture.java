package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Map;
import logic.person.Player;
import utilz.LoadSave;

public class Sculpture extends GameObject {
	
	private Handler handler;
	private Image image;
	
	public Sculpture(int xPos, int yPos, ID id) {
		super(xPos, yPos, id ,10 ,85 ,70 ,60);
		this.handler = Handler.getInstance();
		Map.setStageMap((int)getxPos()/48-1, (int)getyPos()/48+2, 0);
		Map.setStageMap((int)getxPos()/48, (int)getyPos()/48+2, 0);
		initImg();
	}

	public void update() {
		
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(image ,getxPos() ,getyPos());
		//ShowSolidArea(gc, 10, 85);

		return ;
	}
	
	public void interact(Player player) {
		setxPos(getxPos() + player.getxVelo());
		setyPos(getyPos() + player.getyVelo());
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.SCULPTURE);
	}

}