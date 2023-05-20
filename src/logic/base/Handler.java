package logic.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import logic.container.Bullet;
import logic.container.ak47.Ak47Gun;
import logic.container.ak47.Ak47Magazine;
import logic.container.pistol.PistolMagazine;
import logic.container.pistol.PistolGun;
import logic.person.Commander;
import logic.person.Criminal;
import logic.person.Person;
import logic.person.Player;
import utilz.Checker;

public class Handler implements Serializable {

	public static final long serialVersionUID = 1L;
	public static int code;
	public static int progress;
	public static Handler instance;
	public Player player;
	private LinkedList<GameObject> allObjects;
	
	public Handler() {
		Handler.progress = 0;
		Handler.code = 10000;
		setAllObjects(new LinkedList<GameObject>());
	}	
	
	public static Handler getInstance() {
        if(instance == null) instance = new Handler();
        return instance;
    }
	
	public void replace(Handler handler) {
		instance.player = handler.player;
		instance.allObjects = handler.allObjects;
	}
	
	public void update() {
		for(int i = 0; i < allObjects.size(); i++) {
			allObjects.get(i).update();
		}
		player.update();
	}
	
	public void render(GraphicsContext gc) {
		for(int i = 0; i < allObjects.size(); i++) {
			if(!Checker.isStableObject(allObjects.get(i).getId())) {
				allObjects.get(i).render(gc);
			}
		}
		player.render(gc);
	}
	
	public void updateAfterLoadSave(KeyInput input) {
		// Player
		Player player = Handler.getInstance().player;
		player.setForceStop(false);
		player.setAllArea();
		player.setKey(new Keys());
		player.setxVelo(0);
		player.setyVelo(0);
		player.setInput(input);
		player.initImg();
		if(getProgress() == 3) {
			player.setxPos(2500);
			player.setyPos(1600);
		}
		
	    for(int i=0;i<player.getBag().size();i++) {
	    	player.getBag().get(i).initImg();
	    }
	    for(int i=0;i<player.getBag().size();i++) {
	    	if(player.getBag().get(i) instanceof Ak47Gun) {
	    		ArrayList<Bullet> bullets = ((Ak47Gun)player.getBag().get(i)).getMagazine().getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
	    	else if(player.getBag().get(i) instanceof Ak47Magazine) {
	    		ArrayList<Bullet> bullets = ((Ak47Magazine)player.getBag().get(i)).getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
	    	else if(player.getBag().get(i) instanceof PistolGun) {
	    		ArrayList<Bullet> bullets = ((PistolGun)player.getBag().get(i)).getMagazine().getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
	    	else if(player.getBag().get(i) instanceof PistolMagazine) {
	    		ArrayList<Bullet> bullets = ((PistolMagazine)player.getBag().get(i)).getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
	    }
	    
	    // allObject
		for(int i = 0; i < allObjects.size(); i++) {
			GameObject C = allObjects.get(i);
			C.setSolidArea(new Rectangle(C.getxPos()+C.getxDif() ,C.getyPos()+C.getyDif() ,C.getW() ,C.getH()));
			C.initImg();
			
			if(allObjects.get(i) instanceof Person) {
				Person A = ((Person)allObjects.get(i));
				if(A instanceof Commander B) B.initGun();	
				if(A instanceof Criminal B) B.initGun();
				A.setAllArea();
			}
			
			if(allObjects.get(i) instanceof PistolGun) {
	    		ArrayList<Bullet> bullets = ((PistolGun)allObjects.get(i)).getMagazine().getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
	    	if(allObjects.get(i) instanceof PistolMagazine) {
	    		ArrayList<Bullet> bullets = ((PistolMagazine)allObjects.get(i)).getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			Bullet A = bullets.get(j);
	    			A.setSolidArea(new Rectangle(A.getxPos()+A.getxDif(), A.getyPos()+A.getyDif(), A.getW(), A.getH()));
	    		}
	    	}
		}
	}
	
	public void renderStable(GraphicsContext gc) {
		for(int i = 0; i < allObjects.size(); i++) {
			if(Checker.isStableObject(allObjects.get(i).getId())) {
				allObjects.get(i).render(gc);
			}
		}
	}
	
	public void addObject(GameObject object) {
		allObjects.add(object);
	}
	
	public void removeObject(GameObject object) {
		allObjects.remove(object);
	}
	
	// Getter & Setter
	
	public LinkedList<GameObject> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(LinkedList<GameObject> allObjects) {
		this.allObjects = allObjects;
	}
	
	public static int getCode() {
		return code;
	}

	public static void setCode(int code) {
		Handler.code = code;
	}

	public static int getProgress() {
		return progress;
	}

	public static void setProgress(int progress) {
		Handler.progress = progress;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setInstance(Handler instance) {
		Handler.instance = instance;
	}
}
