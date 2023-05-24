package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.InteractivePerson;
import logic.person.Person;
import utilz.LoadSave;
import utilz.Obj;
import static utilz.Constants.Debug.SOLID_SHOW;

public class Sculpture extends GameObject implements InteractivePerson {
	
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

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Person person) {
		Rectangle RA = new Rectangle(getSolidArea().getX() + person.getxVelo(), getSolidArea().getY() + person.getyVelo(), getSolidArea().getWidth(), getSolidArea().getHeight());
		if(Obj.collisionZeroRect(RA)) {
			Obj.pushOffFrom(person, this);
			return ;
		}else {
			setxPos(getxPos() + person.getxVelo());
			setyPos(getyPos() + person.getyVelo());
		}
		setBeforeTwo(Obj.collisionTwoSculpture(this));
		person.setxVelo(0);
		person.setyVelo(0);
	}

	// Getter & Setter

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}