package logic.base;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyInput {
	
	public Keys key = new Keys();
	
	public void keyPressed(KeyEvent x) {
		KeyCode keyInt = x.getCode();
	
		if(keyInt == KeyCode.W) key.W = true;
		if(keyInt == KeyCode.A) key.A = true;
		if(keyInt == KeyCode.S) key.S = true;
		if(keyInt == KeyCode.D) key.D = true;
		if(keyInt == KeyCode.R) key.R = true;
		if(keyInt == KeyCode.E) key.E = true;
		if(keyInt == KeyCode.LEFT) key.LEFT = true;
		if(keyInt == KeyCode.RIGHT) key.RIGHT = true;
		if(keyInt == KeyCode.UP) key.UP = true;
		if(keyInt == KeyCode.DOWN) key.DOWN = true;
		if(keyInt == KeyCode.SHIFT) key.SHIFT = true;
		if(keyInt == KeyCode.SPACE) key.SPACE = true;
		if(keyInt == KeyCode.DIGIT0) key.ZERO = true;
		if(keyInt == KeyCode.DIGIT1) key.ONE = true;
		if(keyInt == KeyCode.DIGIT2) key.TWO = true;
		if(keyInt == KeyCode.DIGIT3) key.THREE = true;
		if(keyInt == KeyCode.DIGIT4) key.FOUR = true;
		if(keyInt == KeyCode.DIGIT5) key.FIVE = true;
		if(keyInt == KeyCode.DIGIT6) key.SIX = true;
		if(keyInt == KeyCode.DIGIT7) key.SEVEN = true;
		if(keyInt == KeyCode.DIGIT8) key.EIGHT = true;
		if(keyInt == KeyCode.DIGIT9) key.NINE = true;
		if(keyInt == KeyCode.ESCAPE) key.ESC = true;
		
	}
	
	public void keyReleased(KeyEvent x) {
		KeyCode keyInt = x.getCode();
		
		if(keyInt == KeyCode.W) key.W = false;
		if(keyInt == KeyCode.A) key.A = false;
		if(keyInt == KeyCode.S) key.S = false;
		if(keyInt == KeyCode.D) key.D = false;
		if(keyInt == KeyCode.R) key.R = false;
		if(keyInt == KeyCode.E) key.E = false;
		if(keyInt == KeyCode.LEFT) key.LEFT = false;
		if(keyInt == KeyCode.RIGHT) key.RIGHT = false;
		if(keyInt == KeyCode.UP) key.UP = false;
		if(keyInt == KeyCode.DOWN) key.DOWN = false;
		if(keyInt == KeyCode.SHIFT) key.SHIFT = false;
		if(keyInt == KeyCode.SPACE) key.SPACE = false;
		if(keyInt == KeyCode.DIGIT0) key.ZERO = false;
		if(keyInt == KeyCode.DIGIT1) key.ONE = false;
		if(keyInt == KeyCode.DIGIT2) key.TWO = false;
		if(keyInt == KeyCode.DIGIT3) key.THREE = false;
		if(keyInt == KeyCode.DIGIT4) key.FOUR = false;
		if(keyInt == KeyCode.DIGIT5) key.FIVE = false;
		if(keyInt == KeyCode.DIGIT6) key.SIX = false;
		if(keyInt == KeyCode.DIGIT7) key.SEVEN = false;
		if(keyInt == KeyCode.DIGIT8) key.EIGHT = false;
		if(keyInt == KeyCode.DIGIT9) key.NINE = false;
		if(keyInt == KeyCode.ESCAPE) key.ESC = false;
	}
	
}
