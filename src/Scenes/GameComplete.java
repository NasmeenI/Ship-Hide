package Scenes;

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

public class GameComplete {
	private Stage stage;
	public static Scene scene;
	
	public GameComplete(Stage stage) {
		this.stage = stage;
		initScene();
	}

	public void initScene() {
		StackPane root = new StackPane();
		scene = new Scene(root);
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.MISSION_COMPLETE) ,0 ,0 ,960 ,640);
		
		HBox box = new HBox(
			10,
			new MenuItem("New Game" ,() -> {
				LoadingScene.loading();
				MenuScene.start = true;
				Thread loadGame = new Thread(() -> {
					new GameProcess(stage);
				});
				loadGame.start();
				
				new Thread(() -> {
					try {
						loadGame.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Platform.runLater(() -> stage.setScene(GameProcess.scene));
				}).start();
			}),
			new MenuScene.MenuItem("Exit" ,() -> Platform.exit())
		);
		box.setTranslateX(225);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}
}