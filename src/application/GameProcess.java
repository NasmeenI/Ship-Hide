package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.base.KeyInput;
import logic.base.Keys;
import logic.base.Map;
import logic.person.Captive;
import logic.person.Criminal;
import logic.person.Player;
import object.Helicopter;
import object.Label;
import object.Sculpture;
import ui.Ui;
import Scenes.GameOverScene;
import Scenes.MenuScene;
import Scenes.Shop;
import Scenes.GameComplete;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static utilz.Constants.Screen.*;
import static utilz.Constants.GameState.*;

public class GameProcess {
	
	public static Stage stage;
	public static Scene scene;
	private long lastUpdateTime;
		
	// BASE PROCESS
	transient public static KeyInput input = new KeyInput();
	private Camera cam;
	
	// SETTING MAP
	public static int renderType = 1;
	public static boolean[][] renderState = new boolean[60][110];

	// GAME STATE
	public static int gameState;
	
	// SCENE
	GameOverScene gameOverScene;
	GameComplete gameComplete;
	
	// PRESS ESC
	transient private Keys key;
	private boolean ESCState = false;
	
	// UI	
	public GraphicsContext gc;
	private AssetSetter aSetter;
	public Ui ui;
	public static StackPane root;
	
	// LOAD SAVE
	public static boolean load = false;
	
	public GameProcess(Stage stage) {
		GameProcess.stage = stage;
		Canvas canvas = new Canvas(960, 640);
		root = new StackPane(canvas);
		scene = new Scene(root);
		gc = canvas.getGraphicsContext2D();
		
		initial();

		run(gc);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			input.keyPressed(key);
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			input.keyReleased(key);
		});
	}
	
	public void run(GraphicsContext gc) {
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentUpdateTime) {

                long delta = currentUpdateTime - lastUpdateTime;

                lastUpdateTime = currentUpdateTime;
                
                if (delta >= 1_000_000_000 / FPS) {
                    update();
                }
                render(gc);
                return ;
            }
        };

        // Start the AnimationTimer
        timer.start();
	}
	
	private void initial() {
		ui = new Ui(root);
		cam = new Camera(0, 0);
		Map.getInstance();
		
		// * Start Point : 500, 500
		// * Knife Room : 1000, 2200
		// * Captive Room : 3450, 620
		// * Nearby Captive Room : 2600, 600
		// * Museum Room : 2700, 2000
		Handler.getInstance().player = new Player(500, 500, ID.Player, input);
		new Shop(stage ,this, Handler.getInstance().player);
		
		// INITIAL OBJECT
		aSetter = new AssetSetter();
		aSetter.setObject();
		if(MenuScene.mode == 0) aSetter.setForDetective();
		else if(MenuScene.mode == 1) aSetter.setForDesperate(Handler.getInstance().player);
		
		// INITIAL SCENE
		MenuScene.initContinueScene(this);
		gameOverScene = new GameOverScene(this);
		gameComplete = new GameComplete();
		
		// CHECK POINT
		gameState = PLAY_STATE;
	}	
	
	private void update() {			
		setKey(input.key);
		checkPress();
		if(gameState == PAUSE_STATE || gameState == GAME_OVER_STATE || gameState == GAME_COMPLETE_STATE) {
			return;
		}
		
		// Render State
		GameProcess.renderState = new boolean[60][110];

		Handler.getInstance().update();
		ui.update(cam);
		cam.update();
		new Shop(stage ,this, Handler.getInstance().player);
	}

	private void render(GraphicsContext gc) {
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, S_WIDTH_DEFAULT, S_HEIGHT_DEFAULT);
		gc.translate(-cam.getX(), -cam.getY());
		
		render_format();
		gc.translate(cam.getX(), cam.getY());
	}
	
	public void render_format() {
		int xTile = (int) (Handler.getInstance().player.getxPos()/48);
		int yTile = (int) (Handler.getInstance().player.getyPos()/48);
		Map.getInstance().render_1(gc);
		Map.getInstance().render_0(gc ,xTile ,yTile);
		Handler.getInstance().renderStable(gc);
		
		// Render before state 2
		for(int i=0;i<Handler.getInstance().getAllObjects().size();i++) {
			GameObject object = Handler.getInstance().getAllObjects().get(i);
			if(object instanceof Criminal A && object.isBeforeTwo() == true) A.render(gc);
			else if(object instanceof Captive A && object.isBeforeTwo() == true) A.render(gc);
			else if(object instanceof Sculpture A && object.isBeforeTwo() == true) A.render(gc);
			else object.render(gc);
		}
		if(Handler.getInstance().player.isBeforeTwo() == true) Handler.getInstance().player.render(gc);
		
		Map.getInstance().render_2(gc ,xTile ,yTile);
		
		// Render after state 2
		for(int i=0;i<Handler.getInstance().getAllObjects().size();i++) {
			GameObject object = Handler.getInstance().getAllObjects().get(i);
			if(object instanceof Criminal A && object.isBeforeTwo() == false) A.render(gc);
			else if(object instanceof Captive A && object.isBeforeTwo() == false) A.render(gc);
			else if(object instanceof Sculpture A && object.isBeforeTwo() == false) A.render(gc);
			else if(object instanceof Label A)  A.render(gc);
			else if(object instanceof Helicopter A)  A.render(gc);
		}
		if(Handler.getInstance().player.isBeforeTwo() == false) Handler.getInstance().player.render(gc);	
	}
		
	public void checkPress() {
		if(key.ESC) {
			if(!ESCState) {
				ESCState = true;
				stage.setScene(MenuScene.continueScene);	
				setGameState(PAUSE_STATE);
			}
		}
	}
	
	public static void loadSave() {
		load = false;
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("res/LoadSave/handler.ser"))) {
		    Handler newHandler = (Handler) objectInputStream.readObject();
		    for(int i=0;i<Handler.getInstance().allObjects.size();i++) {
		    	Handler.getInstance().allObjects.remove(0);
		    }
		    Handler.getInstance().replace(newHandler);
		    KeyInput inputTemp = input;
		    inputTemp.key = new Keys();
		    Handler.getInstance().updateAfterLoadSave(inputTemp);
		}catch (Exception e){
		    e.printStackTrace();
		} 
		
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("res/LoadSave/map.ser"))) {
		    Map newMap = (Map) objectInputStream.readObject();
		    Map.getInstance().replace(newMap);
		    Map.getInstance().updateAfterLoadSave();
		}catch (Exception e){
		    e.printStackTrace();
		}
		
		Music.play();
	}
	
	public static void save() {
		Thread save = new Thread(() -> {
			// saving handler
			try{
				FileOutputStream fileOutputStream = new FileOutputStream("res/LoadSave/handler.ser");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(Handler.getInstance());
				objectOutputStream.close();
			}catch(IOException e){

			}
			
			// saving map
			try{
				FileOutputStream fileOutputStream = new FileOutputStream("res/LoadSave/map.ser");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(Map.getInstance());
				objectOutputStream.close();
			}catch(IOException e){

			}	
		});
		save.start();
	}
	
	// Getters & Setters
	public void setKey(Keys key) {
		this.key = key;
		return ;
	}
	
	public void setFalseKeyESC() {
		this.key.ESC = false;
	}
	
	public static StackPane getRoot() {
		return root;
	}
	
	public static void removeGridRoot(GridPane x) {
		root.getChildren().remove(x);
	}
	
	public static void removeStackRoot(StackPane x) {
		root.getChildren().remove(x);
	}
	
	public static void setGameState(int gameState) {
		GameProcess.gameState = gameState;
	}
	
	public GraphicsContext getGc() {
		return this.gc;
	}
	
	public void setESCState(boolean ESCState) {
		this.ESCState = ESCState;
	}

	public int getGameState() {
		return gameState;
	}
	
	public boolean isLoad() {
		return load;
	}

	public static void setLoad(boolean load) {
		GameProcess.load = load;
	}
}