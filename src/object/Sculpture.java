package object;

import static utilz.Constants.Debug.SOLID_SHOW;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.person.Person;
import utilz.LoadSave;
import utilz.Obj;

public class Sculpture extends GameObject {
	
	private static final long serialVersionUID = 1L;
	transient private Image image;
	
	public Sculpture(int xPos, int yPos, ID id) {
		super(xPos, yPos, id ,10 ,85 ,70 ,60);
		initImg();
	}
	
	public void initImg() {
		image = LoadSave.GetSpriteAtlas(LoadSave.SCULPTURE);
	}

	public void update() {
		Obj.collision(this);
		
		for(int i = 0; i < Handler.getInstance().getAllObjects().size(); i++) {
            if(Handler.getInstance().getAllObjects().get(i).getCode() == getCode()) continue;
            if((Handler.getInstance().getAllObjects().get(i) instanceof Sculpture) && getSolidArea().intersects(Handler.getInstance().getAllObjects().get(i).getSolidArea().getBoundsInLocal())) {
                Obj.action(this, Handler.getInstance().getAllObjects().get(i));
            }
        }
		
		Obj.pushOffWall(this);
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 85, 70, 60));
	}

	@Override
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(GameObject X) {
		if(X instanceof Person) {
			Rectangle RA = new Rectangle(getSolidArea().getX() + X.getxVelo(), getSolidArea().getY() + X.getyVelo(), getSolidArea().getWidth(), getSolidArea().getHeight());
			if(Obj.collisionZeroRect(RA)) {
				Obj.pushOffFrom(X, this);
				return ;
			}else {
				setxPos(getxPos() + X.getxVelo());
				setyPos(getyPos() + X.getyVelo());
			}
			setBeforeTwo(Obj.collisionTwoSculpture(this));
			X.setxVelo(0);
			X.setyVelo(0);
		}
		if(X instanceof Sculpture) {
			Obj.pushOffFrom(X, this);
			System.out.println("Hi");
		}
	}

	// Getter & Setter

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}