package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;
import static utilz.Constants.Player.*;

public class HpBottle extends GameObject implements StableObject {
	
	public boolean picked;
	private Handler handler;
	private Image image;

	public HpBottle(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
		this.handler = Handler.getInstance();
		this.picked = false;
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		initImg();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 10, 10);
		
		gc.drawImage(image ,getxPos() ,getyPos());
		
		return ;
	}

	public void interact(Player player) {
		setPicked(true);
		//player.addItemInBag(this);
		player.setHp(player.getHp() + 300);
		handler.removeObject(this);
	}
	
	private void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.HPBOTTLE);
	}
	
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}