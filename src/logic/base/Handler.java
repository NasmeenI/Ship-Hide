package logic.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import application.GameProcess;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import logic.container.Bullet;
import logic.container.Gun;
import logic.container.Magazine;
import logic.person.Player;
import utilz.Checker;

public class Handler implements Serializable {

	private static final long serialVersionUID = 1L;
	public LinkedList<GameObject> allObjects = new LinkedList<GameObject>();
	public Player Player;
	public static int Code = 10000;
	public static Handler instance;
	
	public Handler() {
		allObjects = new LinkedList<GameObject>();
	}	
	
	public static Handler getInstance() {
        if(instance == null) instance = new Handler();
        return instance;
    }
	
	public void replace(Handler handler) {
		instance.Player = handler.Player;
		instance.allObjects = handler.allObjects;
	}
	
	public void update() {
		for(int i = 0; i < allObjects.size(); i++) {
			allObjects.get(i).update();
		}
		Player.update();
	}
	
	public void render(GraphicsContext gc) {
		for(int i = 0; i < allObjects.size(); i++) {
			if(!Checker.IsStableObject(allObjects.get(i).getId())) allObjects.get(i).render(gc);
		}
		Player.render(gc);
		return ;
	}
	
	public void updateAfterLoadSave(KeyInput input) {
		for(int i = 0; i < allObjects.size(); i++) {
			allObjects.get(i).setSolidArea(new Rectangle(allObjects.get(i).getxPos() ,allObjects.get(i).getyPos() ,0 ,0));
			allObjects.get(i).initImg();
		}
		Player player = Handler.getInstance().Player;
		player.setSolidArea(new Rectangle(player.getxPos()+player.getxDif() ,player.getyPos()+player.getyDif() ,player.getW() ,player.getH()));
		player.setKey(new Keys());
		player.setInput(input);
		player.initImg();
	    for(int i=0;i<player.getBag().size();i++) {
	    	player.getBag().get(i).initImg();
	    }
	    for(int i=0;i<allObjects.size();i++) {
	    	GameObject object = allObjects.get(i);
	    	object.initImg();
			object.setSolidArea(new Rectangle(object.getxPos()+object.getxDif(), object.getyPos()+object.getyDif(), object.getW(), object.getH()));
	    }
	    for(int i=0;i<player.getBag().size();i++) {
	    	if(player.getBag().get(i) instanceof Gun) {
	    		ArrayList<Bullet> bullets = ((Gun)player.getBag().get(i)).getMagazine().getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			bullets.get(j).setSolidArea(new Rectangle(bullets.get(j).getxPos()+bullets.get(j).getxDif(), bullets.get(j).getyPos()+bullets.get(j).getyDif(), bullets.get(j).getW(), bullets.get(j).getH()));
	    		}
	    	}
	    	if(player.getBag().get(i) instanceof Magazine) {
	    		ArrayList<Bullet> bullets = ((Magazine)player.getBag().get(i)).getMagazine();
	    		for(int j=0;j<bullets.size();j++) {
	    			bullets.get(j).setSolidArea(new Rectangle(bullets.get(j).getxPos()+bullets.get(j).getxDif(), bullets.get(j).getyPos()+bullets.get(j).getyDif(), bullets.get(j).getW(), bullets.get(j).getH()));
	    		}
	    	}
	    }
		return ;
	}
	
	public void renderStable(GraphicsContext gc) {
		for(int i = 0; i < allObjects.size(); i++) {
			if(Checker.IsStableObject(allObjects.get(i).getId())) allObjects.get(i).render(gc);
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

}
