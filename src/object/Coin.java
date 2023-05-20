package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class Coin extends GameObject implements StableObject {
	
	private static final long serialVersionUID = 1L;
	transient private Image image;
	
	public Coin(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, 0, 10, 35, 35);
		initImg();
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.COIN);
	}

	public void update() {
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		Handler.getInstance().player.setCoin(Handler.getInstance().player.getCoin() + 1);
		Handler.getInstance().removeObject(this);
	}

	// Getter & Setter

}