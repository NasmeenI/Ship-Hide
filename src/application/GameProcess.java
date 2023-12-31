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
import logic.person.Person;
import logic.person.Player;
import object.Helicopter;
import object.Label;
import object.Sculpture;
import ui.Ui;
import Scenes.GameOverScene;
import Scenes.MenuScene;
import Scenes.Shop;
import ai.PathFinder;
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
	transient public static KeyInput input;
	private Camera cam;
	
	// SETTING MAP
	private static boolean[][] renderState = new boolean[60][110];

	// GAME STATE
	private static int gameState;
	
	// SCENE
	private GameOverScene gameOverScene;
	private GameComplete gameComplete;
	
	// PRESS ESC
	transient private Keys key;
	private boolean ESCState;
	
	// UI	
	private Ui ui;
	private GraphicsContext gc;
	private AssetSetter aSetter;
	public static StackPane root;
	Shop shop;
	// LOAD SAVE
	public static boolean load;
	
	public GameProcess(Stage stage) {
		GameProcess.stage = stage;
		Canvas canvas = new Canvas(960, 640);
		root = new StackPane(canvas);
		scene = new Scene(root);
		gc = canvas.getGraphicsContext2D();
		input = new KeyInput();
		setESCState(false);
		setLoad(false);
		
		initial();

		run(gc);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			input.keyPressed(key);
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			input.keyReleased(key);
		});
	}
	
	public void initial() {
		ui = new Ui(root);
		cam = new Camera(0, 0);
		Map.getInstance();
		
		// * Start Point : 500, 500
		// * Knife Room : 1000, 2200
		// * Captive Room : 3450, 620
		// * Nearby Captive Room : 2600, 600
		// * Museum Room : 2700, 2000
		Handler.getInstance().player = new Player(500, 500, ID.Player, input);
		shop = new Shop(stage ,this, Handler.getInstance().player);
		
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
	
	public void update() {	
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

	public void render(GraphicsContext gc) {
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
		    for(int i=0;i<Handler.getInstance().getAllObjects().size();i++) {
		    	Handler.getInstance().getAllObjects().remove(0);
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
		
		Person.pathFinder = new PathFinder();
		Music.play();
	}
	
	public static void save() {
		Thread save = new Thread(() -> {
			// Saving handler
			try{
				FileOutputStream fileOutputStream = new FileOutputStream("res/LoadSave/handler.ser");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(Handler.getInstance());
				objectOutputStream.close();
			}catch(IOException e){
				
			}
			
			// Saving map
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
	
	public static void removeStackRoot(StackPane stackPane) {
		root.getChildren().remove(stackPane);
	}
	
	public static void removeGridRoot(GridPane gridPane) {
		root.getChildren().remove(gridPane);
	}

	// Getters & Setters
	
	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		GameProcess.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		GameProcess.scene = scene;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public static KeyInput getInput() {
		return input;
	}

	public static void setInput(KeyInput input) {
		GameProcess.input = input;
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
	}

	public static boolean[][] getRenderState() {
		return renderState;
	}

	public static void setRenderState(boolean[][] renderState) {
		GameProcess.renderState = renderState;
	}

	public static int getGameState() {
		return gameState;
	}

	public static void setGameState(int gameState) {
		GameProcess.gameState = gameState;
	}

	public GameOverScene getGameOverScene() {
		return gameOverScene;
	}

	public void setGameOverScene(GameOverScene gameOverScene) {
		this.gameOverScene = gameOverScene;
	}

	public GameComplete getGameComplete() {
		return gameComplete;
	}

	public void setGameComplete(GameComplete gameComplete) {
		this.gameComplete = gameComplete;
	}

	public Keys getKey() {
		return key;
	}

	public void setKey(Keys key) {
		this.key = key;
	}

	public boolean isESCState() {
		return ESCState;
	}

	public void setESCState(boolean eSCState) {
		ESCState = eSCState;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public AssetSetter getaSetter() {
		return aSetter;
	}

	public void setaSetter(AssetSetter aSetter) {
		this.aSetter = aSetter;
	}

	public Ui getUi() {
		return ui;
	}

	public void setUi(Ui ui) {
		this.ui = ui;
	}

	public static StackPane getRoot() {
		return root;
	}

	public static void setRoot(StackPane root) {
		GameProcess.root = root;
	}

	public static boolean isLoad() {
		return load;
	}

	public static void setLoad(boolean load) {
		GameProcess.load = load;
	}
}