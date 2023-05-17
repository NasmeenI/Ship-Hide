package Scenes;

import static utilz.Constants.GameState.PLAY_STATE;

import Scenes.MenuScene.MenuItem;
import application.GameProcess;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilz.LoadSave;

public class GameOverScene {
	private Stage stage;
	private GameProcess gameProcess;
	public static Scene scene;
	
	public GameOverScene(Stage stage, GameProcess gameProcess) {
		this.stage = stage;
		this.gameProcess = gameProcess;
		initScene();
	}

	public void initScene() {
		StackPane root = new StackPane();
		scene = new Scene(root);
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.MISSION_FAIL) ,0 ,0 ,960 ,640);
		
		HBox box = new HBox(
			10,
			new MenuItem("New Game" ,() -> {
				new MenuScene(stage);
				stage.setScene(MenuScene.startScene);
			}),
			new MenuItem("Load Save" ,() -> {
				LoadingScene.loading();
				
				Thread loadSave = new Thread(() -> {
					GameProcess.loadSave();	
				});
				loadSave.start();
				
				new Thread(() ->{
					try {
						loadSave.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Platform.runLater(() -> {
						try {
							GameProcess.setGameState(PLAY_STATE); 
							gameProcess.run(gameProcess.getGc());
							gameProcess.setESCState(false);
							gameProcess.setFalseKeyESC();
							GameProcess.stage.setScene(GameProcess.scene);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}).start();
			}),
			new MenuScene.MenuItem("Exit" ,() -> Platform.exit())
		);
		box.setTranslateX(100);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}
}