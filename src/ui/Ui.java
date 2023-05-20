package ui;

import java.util.ArrayList;
import application.Camera;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.base.GameObject;
import logic.base.Handler;
import logic.container.KeyLocker;
import logic.container.Knife;
import logic.container.ak47.Ak47Gun;
import logic.container.ak47.Ak47Magazine;
import logic.container.pistol.PistolMagazine;
import logic.container.pistol.PistolGun;
import utilz.LoadSave;

public class Ui {
	
	private HBox box;
	private Text text;
	private StackPane root;
	private ProgressBar pb;
	
	public Ui(StackPane root) {
		setRoot(root);
		setPb(new ProgressBar(1));
		setBox(new HBox());
		coin();
		hpBar();
	}
	
	public void update(Camera cam) {
		updateHpBar();
		updateCoin();
		updateInventoryBox(cam);
	};
	
	private void coin() {
		text = new Text("0");
		text.setStyle("-fx-font: 24 arial;");
		text.setTranslateX(360);
		text.setTranslateY(-250);
		
		ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setTranslateX(410);
        imageView.setTranslateY(-250);
        imageView.setImage(LoadSave.GetSpriteAtlas(LoadSave.COIN));
        
        root.getChildren().addAll(text, imageView);
	}
	
	private void hpBar() {
		pb.setTranslateX(300);
		pb.setTranslateY(270);
		pb.setPrefWidth(300);
		pb.setPrefHeight(30);
		pb.setStyle("-fx-accent: green;");
		
		root.getChildren().addAll(pb);
	}
	
	public void updateInventoryBox(Camera cam) {
		root.getChildren().remove(box);
		
		box = new HBox();
		Image image;
		ArrayList<GameObject> bag = Handler.getInstance().player.getBag();
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Ak47Gun) {
				if(Handler.getInstance().player.getUsed() == 4) image = ((Ak47Gun)bag.get(i)).getImageUsed();
				else image = ((Ak47Gun)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
				
				Ak47Magazine currentMagazine = ((Ak47Gun)bag.get(i)).getMagazine();
				int maxBullet;
				if(((Ak47Gun)bag.get(i)).getNumMagazine() == 0) maxBullet = currentMagazine.getNumBullet();
				else maxBullet = currentMagazine.getNumMaxBullet() + ((Ak47Gun)bag.get(i)).getNumMagazine() * currentMagazine.getNumMaxBullet();
				numBullet(box ,currentMagazine.getNumBullet(), maxBullet);	
			}
			if(bag.get(i) instanceof PistolGun) {
				if(Handler.getInstance().player.getUsed() == 3) image = ((PistolGun)bag.get(i)).getImageUsed();
				else image = ((PistolGun)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
				
				PistolMagazine currentMagazine = ((PistolGun)bag.get(i)).getMagazine();
				int maxBullet;
				if(((PistolGun)bag.get(i)).getNumMagazine() == 0) maxBullet = currentMagazine.getNumBullet();
				else maxBullet = currentMagazine.getNumMaxBullet() + ((PistolGun)bag.get(i)).getNumMagazine() * currentMagazine.getNumMaxBullet();
				numBullet(box ,currentMagazine.getNumBullet(), maxBullet);	
			}
		}
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof Knife) {
				if(Handler.getInstance().player.getUsed() == 2) image = ((Knife)bag.get(i)).getImageUsed();
				else image = ((Knife)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
			}
		}
		for(int i=0;i<bag.size();i++) {
			if(bag.get(i) instanceof KeyLocker) {
				image = ((KeyLocker)bag.get(i)).getImage();
				box.getChildren().add(new ImageView(image));
			}
		}
		
		box.setTranslateX(50);
		box.setTranslateY(530);
		root.getChildren().addAll(box);
	}
	
	private void numBullet(HBox box ,int numBullet ,int maxBullet) {
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
	
	private void updateHpBar() {
		double hp = (double)Handler.getInstance().player.getHp()/(double)Handler.getInstance().player.getHpMax();
		pb.setProgress(hp);
		if(hp >= 0.7) pb.setStyle("-fx-accent: green;");
		else if(hp < 0.7 && hp > 0.3) pb.setStyle("-fx-accent: orange;");
		else if(hp <= 0.3) pb.setStyle("-fx-accent: red;");
	}
	
	private void updateCoin() {
		text.setText(Integer.toString(Handler.getInstance().player.getCoin()));
	}

	// Getter & Setter
	
	public HBox getBox() {
		return box;
	}

	public void setBox(HBox box) {
		this.box = box;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public StackPane getRoot() {
		return root;
	}

	public void setRoot(StackPane root) {
		this.root = root;
	}

	public ProgressBar getPb() {
		return pb;
	}

	public void setPb(ProgressBar pb) {
		this.pb = pb;
	}
}