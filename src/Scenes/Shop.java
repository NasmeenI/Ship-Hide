package Scenes;

import java.util.ArrayList;
import application.GameProcess;
import application.sound.Click;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.ID;
import logic.container.ak47.Ak47Gun;
import logic.container.ak47.Ak47Magazine;
import logic.person.Player;
import static utilz.Constants.GameState.PLAY_STATE;

public class Shop {
	
	public static Scene scene;
	public TilePane tilePane;
	private Player player;
	
	public Shop(Stage stage, GameProcess gameProcess, Player player) {	
		this.player = player;

		StackPane root = new StackPane();
		scene = new Scene(root);
		Canvas canvas = new Canvas(960, 640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(-100, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		Button backButton = new Button("Back");
		backButton.setTranslateX(-500);
		backButton.setTranslateY(-250);
        backButton.setOnAction(event -> {
        	new Click();
        	GameProcess.setGameState(PLAY_STATE); 
        	gameProcess.run(gameProcess.getGc());
			gameProcess.setESCState(false);
			GameProcess.input.setFalse();
    		GameProcess.stage.setScene(GameProcess.scene);
        });
		
		tilePane = new TilePane();
		tilePane.setPadding(new Insets(100));
		root.getChildren().addAll(canvas, tilePane, backButton);
		
		addGun();
		addMagazine();
	}
	
	public void addGun() {
		Ak47Gun item = new Ak47Gun(0, 0 ,ID.Ak47Gun);
		ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(80);
        imageView.setImage(item.getImage());

        Button button = new Button(item.getPrice() + "  Coins");
        button.setOnAction(event -> {
        	if(player.getCoin() >= item.getPrice()) {
        		boolean st = true;
        		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
        		for(int i=0;i<bag.size();i++) {
        			if(bag.get(i) instanceof Ak47Gun) {
        				st = false;
        				break;
        			}
        		}
        		if(st) {
        			new Click();
        			player.addItemInBag(new Ak47Gun(0, 0 ,ID.Ak47Gun));
        			player.setGun(true);
        			player.setCoin(player.getCoin() - item.getPrice());
        		}
        	}
        });

        Text text = new Text(item.getName());
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        HBox.setMargin(text, new Insets(0, 10, 0, 0));
        hbox.getChildren().addAll(text, button);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(imageView, hbox);
        tilePane.getChildren().add(vbox);
	}
	
	public void addMagazine() {
		Ak47Magazine item = new Ak47Magazine(0, 0 ,ID.Ak47Magazine);
		ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setImage(item.getImage());

        Button button = new Button(item.getPrice() + "  Coins");
        button.setOnAction(event -> {
        	if(player.getCoin() >= item.getPrice()) {
        		new Click();
        		player.addItemInBag(new Ak47Magazine(0, 0 ,ID.Ak47Magazine));
        		player.setCoin(player.getCoin() - item.getPrice());
        	}
        });

        Text text = new Text(item.getName());
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        HBox.setMargin(text, new Insets(0, 10, 0, 0));
        hbox.getChildren().addAll(text, button);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(imageView, hbox);
        tilePane.getChildren().add(vbox);
	}

	// Getter & Setter
	
	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		Shop.scene = scene;
	}

	public TilePane getTilePane() {
		return tilePane;
	}

	public void setTilePane(TilePane tilePane) {
		this.tilePane = tilePane;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}