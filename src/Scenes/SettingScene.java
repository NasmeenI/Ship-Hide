package Scenes;

import Scenes.MenuScene.MenuItem;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        
        VBox box = new VBox(
    			10,
    			new MenuItem("Detective" ,() -> {
    				MenuScene.mode = 0;
    				System.out.println(MenuScene.mode);
    				stage.setScene(MenuScene.getScene());
    			}),
    			new MenuItem("Desperate" ,() -> {
    				MenuScene.mode = 1;
    				System.out.println(MenuScene.mode);
    				stage.setScene(MenuScene.getScene());
    			})
    		);
    		box.setTranslateX(100);
    		box.setTranslateY(200);
		
		root.getChildren().addAll(box);	
	}
	
	public Scene getScene() {
		return scene;
	}
}