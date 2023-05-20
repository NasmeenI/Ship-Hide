package application;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logic.base.Handler;

public class Music {
	public static MediaPlayer[] soundtrack = new MediaPlayer[5];
	public static MediaPlayer gameOver, gameComplete;
	public static int state = 0;
	
	public Music() {
		for(int i=0;i<5;i++) {
			Media media = new Media(new File("res/Music/state_" + i + ".mp3").toURI().toString());
			soundtrack[i] = new MediaPlayer(media);
		}
		
		Media media = new Media(new File("res/Music/gameOver.mp3").toURI().toString());
		gameOver = new MediaPlayer(media);
		
		media = new Media(new File("res/Music/gameComplete.mp3").toURI().toString());
		gameComplete = new MediaPlayer(media);
	}
	
	public static void play() {
		for(int i=0;i<5;i++) {
			if(i == Handler.progress) continue;
			Music.soundtrack[i].stop();
		}
		Music.gameOver.stop();
		Music.soundtrack[Handler.progress].play();
		Music.soundtrack[Handler.progress].setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public static void stop() {
		for(int i=0;i<5;i++) {
			Music.soundtrack[i].stop();
		}
	}
}