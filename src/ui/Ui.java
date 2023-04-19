package ui;

import java.util.ArrayList;
import application.Camera;
import application.GameProcess;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.base.GameObject;
import logic.container.Gun;
import logic.container.KeyLocker;
import logic.container.Knife;
import logic.container.Magazine;
import logic.person.Player;

public class Ui {

	public Ui(GameProcess gp) {

	}
	
	public HBox draw(Camera cam ,Player player) {
		HBox box = new HBox();
		ArrayList<GameObject> bag = player.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof KeyLocker) {
				Image image = ((KeyLocker)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
			}
			else if(bag.get(i) instanceof Knife) {
				Image image = ((Knife)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
			}
			else if(bag.get(i) instanceof Gun) {
				Image image = ((Gun)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
				
				Magazine currentMagazine = ((Gun)bag.get(i)).getMagazine();
				numBullet(box ,currentMagazine.getNumBullet() ,currentMagazine.getNumMaxBullet());	
			}
		}
		
		return box;
	}
	
	public void numBullet(HBox box ,int numBullet ,int maxBullet) {
		Rectangle line = new Rectangle(5 ,25);
		Text text = new Text(Integer.toString(numBullet) + "/" + Integer.toString((maxBullet)));
		text.setFont(Font.font(17.0));
		BorderPane borderPane = new BorderPane();
		StackPane stackPane = new StackPane();
		stackPane.setPrefSize(8, 8);
		HBox box1 = new HBox(15 ,line ,text);
		stackPane.getChildren().addAll(box1);
		stackPane.setAlignment(Pos.CENTER_LEFT);
		borderPane.setTop(stackPane);
		borderPane.setTranslateY(20);
		
		box.getChildren().add(borderPane);
	}
}