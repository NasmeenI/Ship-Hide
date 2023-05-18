package logic.base;

import static utilz.Constants.Player.P_HEIGHT;
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

	private static final long serialVersionUID = 1L;
	public static int progress;
	public static int Code;
	public Player player;
	public static Handler instance;
	public LinkedList<GameObject> allObjects;
	private int coins;
	
	public Handler() {
		Handler.progress = 0;
		Handler.Code = 10000;
		allObjects = new LinkedList<GameObject>();
		this.coins = 0;
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
			if(!Checker.IsStableObject(allObjects.get(i).getId())) {
				allObjects.get(i).render(gc);
			}
		}
		player.render(gc);
		return ;
	}
	
	public void updateAfterLoadSave(KeyInput input) {
		// Player
		Player player = Handler.getInstance().player;
		player.setSolidArea(new Rectangle(player.getxPos()+player.getxDif() ,player.getyPos()+player.getyDif() ,player.getW() ,player.getH()));
		player.setFootArea(new Rectangle(player.getxPos() + player.getxDif(), player.getyPos() + player.getyDif() + P_HEIGHT - 10, player.getW(), 10));
		player.setRenderArea(new Rectangle(player.getxPos() + player.getxDif(), player.getyPos() + player.getyDif() + 40, player.getW(), player.getH()-40));	
		player.setKey(new Keys());
		player.setInput(input);
		player.initImg();
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
				A.setSolidArea(new Rectangle(A.getxPos() + A.getxDif(), A.getyPos() + A.getyDif(), A.getW(), A.getH()));
				A.setFootArea(new Rectangle(A.getxPos() + A.getxDif(), A.getyPos() + A.getyDif() + P_HEIGHT - 10, A.getW(), 10));
				A.setRenderArea(new Rectangle(A.getxPos() + A.getxDif(), A.getyPos() + A.getyDif() + 40, A.getW(), A.getH()-40));	
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
		return ;
	}
	
	public void renderStable(GraphicsContext gc) {
		for(int i = 0; i < allObjects.size(); i++) {
			if(Checker.IsStableObject(allObjects.get(i).getId())) {
				allObjects.get(i).render(gc);
			}
		}
		return ;
	}
	
	public void addObject(GameObject object) {
		allObjects.add(object);
		return ;
	}
	
	public void removeObject(GameObject object) {
		allObjects.remove(object);
		return ;
	}
	
	public LinkedList<GameObject> getAllObjects() {
		return allObjects;
	}

	public void setAllObjects(LinkedList<GameObject> allObjects) {
		this.allObjects = allObjects;
	}

}
