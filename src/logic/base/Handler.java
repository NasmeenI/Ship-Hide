package logic.base;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import logic.person.Player;
import utilz.Checker;

public class Handler {

	public LinkedList<GameObject> allObjects = new LinkedList<GameObject>();
	public Player Player;
	public static int Code = 10000;
	
	// instance
	
	private static Handler instance = null; 
	
	public Handler() {
		allObjects = new LinkedList<GameObject>();
	}
	
	public static Handler getInstance() {
		if(instance == null) instance = new Handler();
		return instance;
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
