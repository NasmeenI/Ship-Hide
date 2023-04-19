package Scenes;

import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.GameProcess;

public class MenuScene {
	public static Scene scene;
	public MenuScene(Stage stage) {
		StackPane root = new StackPane();
		scene = new Scene(root);
		
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		String image_path = "file:res/background.jpg";
		gc.drawImage(new Image(image_path) ,0 ,0 ,960 ,640);
		
		VBox box = new VBox(
			10,
			new MenuItem("Start" ,() -> {
				try {
					GameProcess gameScene = new GameProcess();
					stage.setScene(gameScene.getScene());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}),
			new MenuItem("Custom" ,() -> {
				CustomScene customScene = new CustomScene(stage ,this);
				stage.setScene(customScene.getScene());
			}),
			new MenuItem("Tutorial" ,() -> {
				TutorialScene tutorialScene = new TutorialScene(stage ,this);
				stage.setScene(tutorialScene.getScene());
			}),
			new MenuItem("Setting" ,() -> {
				SettingScene settingScene = new SettingScene(stage ,this);
				stage.setScene(settingScene.getScene());
			}),
			new MenuItem("Exit" ,() -> Platform.exit())
		);
		box.setTranslateX(100);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}


	private static class MenuItem extends StackPane {
		MenuItem(String name ,Runnable action){
			LinearGradient gradient = new LinearGradient(
					0 ,0.5 ,1 ,0.5 ,true ,CycleMethod.NO_CYCLE,
					new Stop(0.1 ,Color.web("black" ,0.75)),
					new Stop(1.0 ,Color.web("black" ,0.15))
			);
			Rectangle bg0 = new Rectangle(250 ,40 ,gradient);
			Rectangle bg1 = new Rectangle(250 ,40 ,Color.web("black" ,0.6));
			
			FillTransition ft = new FillTransition(Duration.seconds(1), bg1 ,Color.web("black" ,0.2) ,Color.web("white" ,0.3));
			
			ft.setAutoReverse(true);
			ft.setCycleCount(Integer.MAX_VALUE);
			
			hoverProperty().addListener((o ,oldValue ,isHovering) -> {
				if(isHovering) {
					ft.playFromStart();
				}
				else {
					ft.stop();
					bg1.setFill(Color.web("black",0.2));
				}
			});
			
			Rectangle line = new Rectangle(5 ,40);
			line.widthProperty().bind(
					Bindings.when(hoverProperty())
					.then(8)
					.otherwise(5)
			);
			line.fillProperty().bind(
					Bindings.when(hoverProperty())
					.then(Color.RED)
					.otherwise(Color.GRAY)
			);
			
			Text text = new Text(name);
			text.setFont(Font.font(22.0));
			text.fillProperty().bind(
					Bindings.when(hoverProperty())
					.then(Color.WHITE)
					.otherwise(Color.GRAY)
			);
			
			setOnMouseClicked(e -> action.run());
			setOnMousePressed(e -> bg0.setFill(Color.WHITE));
			setOnMouseReleased(e -> bg0.setFill(gradient));
			
			setAlignment(Pos.CENTER_LEFT);
			
			HBox box = new HBox(15 ,line ,text);
			box.setAlignment(Pos.CENTER_LEFT);
			
			getChildren().addAll(bg0 ,bg1 ,box);
		}
	}
	public Scene getScene() {
		return scene;
	}
}