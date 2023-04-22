package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.base.Handler;
import logic.base.ID;
import logic.base.KeyInput;
import logic.base.Keys;
import logic.base.Map;
import logic.person.Player;
import ui.Ui;
import Scenes.GameOverScene;
import Scenes.MenuScene;
import Scenes.GameComplete;
import static utilz.Constants.Screen.*;
import static utilz.Constants.GameState.*;

public class GameProcess {
	
	public static Stage stage;
	public static Scene scene;
	private long lastUpdateTime;
		
	// BASE PROCESS
	private Handler handler; 
	private KeyInput input = new KeyInput();
	private Camera cam;
	
	// SETTING MAP
	public static int renderType = 1;
	private Map map;
	
	// GAME STATE
	public static int gameState;
	
	// SCENE
	GameOverScene gameOverScene;
	GameComplete gameComplete;
	
	// PRESS ESC
	private Keys key;
	private boolean ESCState = false;
	
	// UI	
	public GraphicsContext gc;
	private AssetSetter aSetter;
	public Ui ui;
	public static StackPane root;
	private HBox box = new HBox();
	private ProgressBar pb = new ProgressBar(1);
	
	public GameProcess(Stage stage) {
		GameProcess.stage = stage;
		Canvas canvas = new Canvas(960, 640);
		root = new StackPane(canvas);
		scene = new Scene(root);
		gc = canvas.getGraphicsContext2D();
		
		run(gc);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			input.keyPressed(key);
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			input.keyReleased(key);
		});
		initial();
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
		map = new Map();
		ui = new Ui(this);
		handler = Handler.getInstance();
		cam = new Camera(0, 0);
		
		handler.Player = new Player(500, 500, ID.Player, input);
//		handler.addObject(new Criminal(2600, 1600, ID.Criminal, 2, 2, 100));

		aSetter = new AssetSetter();
		aSetter.setObject();
		
		// INITIAL SCENE
		MenuScene.initContinueScene(this);
		gameOverScene = new GameOverScene(stage);
		gameComplete = new GameComplete(stage);
		//GameOverScreen scene = new GameOverScreen(GameProcess.stage);
		gameState = PLAY_STATE;

		// initial HP Bar
		pb.setTranslateX(300);
		pb.setTranslateY(270);
		pb.setPrefWidth(300);
		pb.setPrefHeight(30);
		pb.setStyle("-fx-accent: green;");
		root.getChildren().addAll(pb);
	}
	
	private void update() {	
		setKey(input.key);
		checkPress();
		if(gameState == PAUSE_STATE || gameState == GAME_OVER_STATE || gameState == GAME_COMPLETE_STATE) {
			return;
		}
		
		// Inventory box
		root.getChildren().remove(box);
		box = ui.draw(cam ,handler.Player);
		box.setTranslateX(50);
		box.setTranslateY(530);
		root.getChildren().addAll(box);
		
		// HP Bar
		double hp = (double)handler.Player.getHp()/1000.0;
		pb.setProgress(hp);
		if(hp >= 0.7) pb.setStyle("-fx-accent: green;");
		else if(hp < 0.7 && hp > 0.3) pb.setStyle("-fx-accent: orange;");
		else if(hp <= 0.3) pb.setStyle("-fx-accent: red;");

		handler.update();
		cam.update();		

		return;
	}
	
	private void render(GraphicsContext gc) {
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, S_WIDTH_DEFAULT, S_HEIGHT_DEFAULT);
		gc.translate(-cam.getX(), -cam.getY());
		
		if(renderType == 1) render_format_1();
		else if(renderType == 2) render_format_2();

		gc.translate(cam.getX(), cam.getY());

	}
	
	public void render_format_1() {
		int xTile = (int) (handler.Player.getxPos()/48);
		int yTile = (int) (handler.Player.getyPos()/48);
		map.render_1(gc);
		map.render_0(gc ,xTile ,yTile);
		handler.renderStable(gc);
		map.render_2(gc ,xTile ,yTile);
		handler.render(gc);
	}
	
	public void render_format_2() {
		int xTile = (int) (handler.Player.getxPos()/48);
		int yTile = (int) (handler.Player.getyPos()/48);
		map.render_1(gc);
		map.render_0(gc ,xTile ,yTile);
		handler.renderStable(gc);
		handler.render(gc);
		map.render_2(gc ,xTile ,yTile); 
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
	
	// Getters & Setters
	
	public void setKey(Keys key) {
		this.key = key;
		return ;
	}
	
	public void setFalseKeyESC() {
		this.key.ESC = false;
	}
	
	public Handler getHandler() {
		return handler;
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
}