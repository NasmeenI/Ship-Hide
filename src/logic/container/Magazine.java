package logic.container;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.Pickable;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;
import static utilz.Constants.Player.*;

public class Magazine extends GameObject implements StableObject ,Pickable {
	public ArrayList<Bullet> magazine;
	public boolean picked;
	private boolean visible;
	private Handler handler;

	private Image image;
	private int numMaxBullet;
	
	public Magazine(double xPos, double yPos, ID id ,int numMaxBullet) {
		super(xPos ,yPos ,id);
		this.handler = Handler.getInstance();
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		setPicked(false);
		setVisible(true);
		initImg();
		this.numMaxBullet = numMaxBullet;
		
		magazine = new ArrayList<Bullet>();
		AddBulletsPack(numMaxBullet);
	}
	
	public Magazine(double xPos, double yPos, ID id ,int numBullet, int numMaxBullet) {
		super(xPos ,yPos ,id);
		this.handler = Handler.getInstance();
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, P_WIDTH, P_HEIGHT));
		setPicked(false);
		setVisible(false);
		initImg();
		this.numMaxBullet = numMaxBullet;
		
		magazine = new ArrayList<Bullet>();
		AddBulletsPack(numBullet);
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc, 10, 10);
		
		if(isVisible()) gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	@Override
	public void interact(Player player) {
		if(!isVisible()) return ;
		setPicked(true);
		player.addItemInBag(this);
		handler.removeObject(this);
	}
	
	private void initImg() {
		this.image = LoadSave.GetSpriteAtlas(LoadSave.MAGAZINE);
	}
	
	public void AddBullet(Bullet bullet) {
		magazine.add(bullet);
		return ;
	}
	
	public void AddBulletsPack(int count) {
		Bullet bulletTemp;
		for(int i = 0; i < count; i++) {
			bulletTemp = new Bullet(getxPos(), getyPos(), ID.Bullet);
			AddBullet(bulletTemp);
		}
	}
	
	public void reduceBullet() {
		magazine.remove(0);
	}
	
	// Getter & Setter
	
	public int getNumBullet() {
		return magazine.size();
	}
	
	public ArrayList<Bullet> getMagazine() {
		return magazine;
	}

	public void setMagazine(ArrayList<Bullet> magazine) {
		this.magazine = magazine;
	}
	
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
	
	public int getNumMaxBullet() {
		return numMaxBullet;
	}

	public void setNumMaxBullet(int numBullet) {
		this.numMaxBullet = numBullet;
	}
}