package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.person.Player;
import utilz.LoadSave;

public class Sculpture extends GameObject {
	
	private Handler handler;
	private Image image;
	
	public Sculpture(int xPos, int yPos, ID id) {
		super(xPos, yPos, id ,10 ,85 ,70 ,60);
		this.handler = Handler.getInstance();
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
		
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.SCULPTURE);
		
	}

	// Getter & Setter
	
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