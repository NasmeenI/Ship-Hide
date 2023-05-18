package Scenes;

import static utilz.Constants.GameState.PLAY_STATE;

import application.GameProcess;
import application.sound.Click;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilz.LoadSave;

public class Shop {
	public static Scene scene;
	public TilePane tilePane;
	
	public Shop(Stage stage ,GameProcess gameProcess) {	
		StackPane root = new StackPane();
		scene = new Scene(root);
		Canvas canvas = new Canvas(960, 640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		Button backButton = new Button("Back");
		backButton.setTranslateX(-400);
		backButton.setTranslateY(-250);
        backButton.setOnAction(event -> {
        	new Click();
        	GameProcess.setGameState(PLAY_STATE); 
        	gameProcess.run(gameProcess.getGc());
			gameProcess.setESCState(false);
			gameProcess.setFalseKeyESC();
			GameProcess.input.setFalse();
    		GameProcess.stage.setScene(GameProcess.scene);
        });
		
		tilePane = new TilePane();
		tilePane.setPadding(new Insets(100));
		root.getChildren().addAll(canvas, tilePane, backButton);
		
		addItem(LoadSave.GetSpriteAtlas(LoadSave.AK47), 20, "Ak-47");
		addItem(LoadSave.GetSpriteAtlas(LoadSave.GUN), 20, "Ak-47");
	}
	
	public void addItem(Image image, int price, String name) {
		ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(80);
        imageView.setImage(image);

        Button button = new Button(price + "  Coins");
        Text text = new Text(name);
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        HBox.setMargin(text, new Insets(0, 10, 0, 0));
        hbox.getChildren().addAll(text, button);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(imageView, hbox);
        tilePane.getChildren().add(vbox);
	}
}