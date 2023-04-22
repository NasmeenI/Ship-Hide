package object;

import static utilz.Constants.Debug.SOLID_SHOW;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.ID;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;

public class Sculpture extends GameObject {
	
	private static final long serialVersionUID = 1L;
	transient private Image image;
	
	public Sculpture(int xPos, int yPos, ID id) {
		super(xPos, yPos, id ,10 ,85 ,70 ,60);
		initImg();
	}

	public void update() {
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 85, 70, 60));
		Obj.pushOffWall(this);
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		Rectangle RA = new Rectangle(getSolidArea().getX() + player.getxVelo(), getSolidArea().getY() + player.getyVelo(), getSolidArea().getWidth(), getSolidArea().getHeight());
		if(Obj.collisionZeroRect(RA)) {
			Obj.pushOffFrom(player, this);
			return ;
		}else {
			setxPos(getxPos() + player.getxVelo());
			setyPos(getyPos() + player.getyVelo());
		}
		player.setxVelo(0);
		player.setyVelo(0);
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.SCULPTURE);
		
	}

	// Getter & Setter

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}