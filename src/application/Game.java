package application;

import Scenes.MenuScene;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Game extends Application {
	
	public void start(Stage stage) throws Exception {  
		new MenuScene(stage);
		new Music();
		
		stage.setScene(MenuScene.startScene);
		stage.setResizable(false);
		stage.setTitle("Ship Hide");
		stage.show();
		Music.soundtrack[0].play();
		Music.soundtrack[0].setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}