package Scenes;

import application.sound.Click;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import utilz.LoadSave;

public class GameComplete {
	
	public static Scene scene;
	
	public GameComplete() {
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
			new MenuScene.MenuItem("Exit" ,() -> {
				new Click();
				Platform.exit();
			})
		);
		box.setTranslateX(350);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}
}