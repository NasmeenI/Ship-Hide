package logic.container;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.StableObject;
import logic.person.Player;
import utilz.LoadSave;
import static utilz.Constants.Debug.*;

public class Magazine extends GameObject implements StableObject {
	
	private static final long serialVersionUID = 1L;
	protected int numMaxBullet;
	protected double littleMove;
	protected double count;
	protected boolean picked;
	protected boolean visible;
	protected ArrayList<Bullet> magazine;
	transient protected Image image;
		
	public Magazine(double xPos, double yPos, ID id ,int numBullet) {
		super(xPos, yPos, id, -10, -10, 50, 50);
		setPicked(false);
		setVisible(false);
		setLittleMove(1);
		setCount(0);
		initImg();
		setNumMaxBullet(numBullet);
		setMagazine(new ArrayList<Bullet>());
		AddBulletsPack(numBullet);
	}

	public void update() {
		if(littleMove <= -1) count = 0.05f;
		if(littleMove >= 1) count = -0.05f;
		
		littleMove += count;
		
		setyPos(getyPos() + littleMove);
	}
	
	public void render(GraphicsContext gc) {
		if(SOLID_SHOW) ShowSolidArea(gc);
		
		if(isVisible()) gc.drawImage(image ,getxPos() ,getyPos());
		return ;
	}
	
	@Override
	public void interact(Player player) {
		if(!isVisible()) return ;
		setPicked(true);
		player.addItemInBag(this);
		Handler.getInstance().removeObject(this);
	}
	
	public void initImg() {
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

	public double getLittleMove() {
		return littleMove;
	}

	public void setLittleMove(double littleMove) {
		this.littleMove = littleMove;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}