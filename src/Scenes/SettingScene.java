package Scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilz.LoadSave;

public class SettingScene {
	public static Scene scene;
	
	public SettingScene(Stage stage) {
		StackPane root = new StackPane();
		scene = new Scene(root);
		
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.MENU_SCENE) ,0 ,0 ,960 ,640);
		
		Button backButton = new Button("Back");
		backButton.setTranslateX(-370);
		backButton.setTranslateY(-250);
        backButton.setOnAction(event -> {
        	if(!MenuScene.start) stage.setScene(MenuScene.startScene);
        	else stage.setScene(MenuScene.continueScene);
        });
		
		root.getChildren().addAll(backButton);	
	}
	
	public Scene getScene() {
		return scene;
	}
}