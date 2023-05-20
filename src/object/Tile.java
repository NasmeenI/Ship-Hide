package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.ID;
import logic.person.Player;
import utilz.LoadSave;

public class Tile extends GameObject {
	
	private static final long serialVersionUID = 1L;
	private int x ,y;
	private int state;
	transient private Image image0 ,image2, image2_0, image3;
	
	public Tile(int xPos, int yPos, ID id ,int x ,int y ,int state) {
		super(xPos, yPos, id);
		setY(x);
		setX(y);
		setState(state);
		initImg();
	}

	public void initImg() {
		if(getState() == 1) {
			image0 = null;
			image2 = null;
			image3 = null;
		}
		image0 = LoadSave.GetSpriteAtlas("Maps/project_" + 0 +"/tile_" + x + "_" + y + ".png");
		image2 = LoadSave.GetSpriteAtlas("Maps/project_" + 2 +"/tile_" + x + "_" + y + ".png");
		image2_0 = LoadSave.GetSpriteAtlas("Maps/project_" + 2 +"_0/tile_" + x + "_" + y + ".png");
	}
	
	@Override
	public void update() {
	}

	@Override
	public void render(GraphicsContext gc) {
	}
	
	public void interact(Player player) {
	}
	
	// Getters & Setters
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public Image getImage0() {
		return image0;
	}

	public void setImage0(Image image0) {
		this.image0 = image0;
	}

	public Image getImage2() {
		return image2;
	}

	public void setImage2(Image image2) {
		this.image2 = image2;
	}
	public Image getImage2_0() {
		return image2_0;
	}

	public void setImage2_0(Image image2_0) {
		this.image2_0 = image2_0;
	}
	
	public Image getImage3() {
		return image3;
	}

	public void setImage3(Image image3) {
		this.image3 = image3;
	}
}