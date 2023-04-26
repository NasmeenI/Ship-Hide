package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class HpBottle extends GameObject implements StableObject {
	
	private static final long serialVersionUID = 1L;
	public boolean picked;
	transient private Image image;

	public HpBottle(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 10, 10, 50, 60);
		this.picked = false;
		initImg();
	}
	
	@Override
	public void update() {

	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		gc.drawImage(image ,getxPos() ,getyPos());
		
		return ;
	}

	public void interact(Player player) {
		setPicked(true);
		//player.addItemInBag(this);
		player.setHp(player.getHp() + 1000);
		Handler.getInstance().removeObject(this);
	}
	
	public void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.HPBOTTLE);
	}
	
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
}