package object;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.PressEable;
import logic.person.Player;
import utilz.LoadSave;
import utilz.Obj;
import static utilz.Constants.Debug.*;

import application.GameProcess;

public class Label extends GameObject implements PressEable {
	
	private static final long serialVersionUID = 1L;
	private boolean interacted;
	transient private Image imageInteract, imagePuzzle;
	
	public Label(int xPos, int yPos, ID id) {
		super(xPos, yPos, id, -30, 10, 130, 90);
		setInteracted(false);
		initImg();
	}
	
	public void initImg() {
		imageInteract = LoadSave.GetSpriteAtlas(LoadSave.PRESS_E);
		switch(getId()) {
			case Label1 : {
				imagePuzzle = LoadSave.GetSpriteAtlas(LoadSave.PUZZLE1);
				break;
			}
			case Label2 : {
				imagePuzzle = LoadSave.GetSpriteAtlas(LoadSave.PUZZLE2);
				break;
			}
			case Label3 : {
				imagePuzzle = LoadSave.GetSpriteAtlas(LoadSave.PUZZLE3);
				break;
			}
			case Label4 : {
				imagePuzzle = LoadSave.GetSpriteAtlas(LoadSave.PUZZLE4);
				break;
			}
			default : break;
		}
	}

	public void update() {
		double dis = Obj.distance(this, Handler.getInstance().player);
		if(dis < 4*48) setInteracted(true);
		else setInteracted(false);
	}

	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		if(interacted) gc.drawImage(imageInteract ,getxPos() ,getyPos());
		return ;
	}
	
	public void interact(Player player) {
		if(Obj.pressed == false) {
			Obj.time = 0;
		 	Obj.pressed = true;
			Obj.stackPane = new StackPane();
			Obj.temp1 = ((Player)player).getAc();
			Obj.temp2 = ((Player)player).getDc();
			player.setAcDc(0, 0);
			player.setForceStop(true);

	        ImageView imageView = new ImageView(getImagePuzzle());
			Obj.stackPane.getChildren().addAll(imageView);
			Obj.stackPane.setAlignment(Pos.CENTER);
	        GameProcess.getRoot().getChildren().addAll(Obj.stackPane);
		}
		else {
			Obj.time = 0;
			Obj.pressed = false;
			player.setAcDc(Obj.temp1, Obj.temp2);
			player.setForceStop(false);
			GameProcess.removeStackRoot(Obj.stackPane);
		}
	}

	// Getter & Setter

	public Image getImagePuzzle() {
		return imagePuzzle;
	}

	public void setImagePuzzle(Image imagePuzzle) {
		this.imagePuzzle = imagePuzzle;
	}

	public Image getImageInteract() {
		return imageInteract;
	}

	public void setImageInteract(Image imageInteract) {
		this.imageInteract = imageInteract;
	}
	
	public boolean isInteracted() {
		return interacted;
	}

	public void setInteracted(boolean interacted) {
		this.interacted = interacted;
	}
}