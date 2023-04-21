package utilz;

import javafx.scene.image.Image;

public class LoadSave {

	public static final String MAP_0 = "Maps/project_0.png";
	public static final String MAP_1 = "Maps/project_1.png";
	public static final String MAP_2 = "Maps/project_2.png";
	
	public static final String Player_Animation_Up_Default = "Animation/T_Up_9.png";
	public static final String Player_Animation_Up_0 = "Animation/T_Up_0.png";
	public static final String Player_Animation_Up_1 = "Animation/T_Up_1.png";
	public static final String Player_Animation_Up_2 = "Animation/T_Up_2.png";
	public static final String Player_Animation_Up_3 = "Animation/T_Up_3.png";
	public static final String Player_Animation_Up_4 = "Animation/T_Up_4.png";
	public static final String Player_Animation_Up_5 = "Animation/T_Up_5.png";
	public static final String Player_Animation_Up_6 = "Animation/T_Up_6.png";
	public static final String Player_Animation_Up_7 = "Animation/T_Up_7.png";
	
	public static final String Player_Animation_Down_Default = "Animation/T_Down_9.png";
	public static final String Player_Animation_Down_0 = "Animation/T_Down_0.png";
	public static final String Player_Animation_Down_1 = "Animation/T_Down_1.png";
	public static final String Player_Animation_Down_2 = "Animation/T_Down_2.png";
	public static final String Player_Animation_Down_3 = "Animation/T_Down_3.png";
	public static final String Player_Animation_Down_4 = "Animation/T_Down_4.png";
	public static final String Player_Animation_Down_5 = "Animation/T_Down_5.png";
	public static final String Player_Animation_Down_6 = "Animation/T_Down_6.png";
	public static final String Player_Animation_Down_7 = "Animation/T_Down_7.png";
	                           
	public static final String Player_Animation_Left_Default = "Animation/T_Left_9.png";
	public static final String Player_Animation_Left_0 = "Animation/T_Left_0.png";
	public static final String Player_Animation_Left_1 = "Animation/T_Left_1.png";
	public static final String Player_Animation_Left_2 = "Animation/T_Left_2.png";
	public static final String Player_Animation_Left_3 = "Animation/T_Left_3.png";
	public static final String Player_Animation_Left_4 = "Animation/T_Left_4.png";
	public static final String Player_Animation_Left_5 = "Animation/T_Left_5.png";
	public static final String Player_Animation_Left_6 = "Animation/T_Left_6.png";
	public static final String Player_Animation_Left_7 = "Animation/T_Left_7.png";
	                           
	public static final String Player_Animation_Right_Default = "Animation/T_Right_9.png";
	public static final String Player_Animation_Right_0 = "Animation/T_Right_0.png";
	public static final String Player_Animation_Right_1 = "Animation/T_Right_1.png";
	public static final String Player_Animation_Right_2 = "Animation/T_Right_2.png";
	public static final String Player_Animation_Right_3 = "Animation/T_Right_3.png";
	public static final String Player_Animation_Right_4 = "Animation/T_Right_4.png";
	public static final String Player_Animation_Right_5 = "Animation/T_Right_5.png";
	public static final String Player_Animation_Right_6 = "Animation/T_Right_6.png";
	public static final String Player_Animation_Right_7 = "Animation/T_Right_7.png";
	
	public static final String PRESS_E = "Objects/pressE.jpg";
	public static final String KNIFE = "Objects/knife.png";
	public static final String KNIFE_USED = "Objects/knifeUsed.png";
	public static final String GUN = "Objects/gun.png";
	public static final String GUN_USED = "Objects/gunUsed.png";
	public static final String MAGAZINE = "Objects/magazine.png";
	public static final String KEY1 = "Objects/key1.png";
	public static final String KEY2 = "Objects/key2.png";
	public static final String HPBOTTLE = "Objects/hpBottle.png";
	public static final String DOOR1_OPEN = "Objects/door1_open.png";
	public static final String DOOR1_CLOSE = "Objects/door1_close.png";
	public static final String DOOR2_OPEN = "Objects/door2_open.png";
	public static final String DOOR2_CLOSE = "Objects/door2_close.png";
	public static final String DOOR3_OPEN = "Objects/door3_open.png";
	public static final String DOOR3_CLOSE = "Objects/door3_close.png";
	public static final String MUSIUM_DOOR_CLOSE = "Objects/musiumDoor_close.png";
	public static final String MUSIUM_DOOR_OPEN = "Objects/musiumDoor_open.png";
	public static final String LAZER_1 = "Objects/lazer1.png";
	public static final String LAZER_2 = "Objects/lazer2.png";
	public static final String SCULPTURE = "Objects/sculpture.png";
	public static final String PUZZLE1 = "Objects/puzzle1.png";
	public static final String PUZZLE2 = "Objects/puzzle2.png";
	public static final String PUZZLE3 = "Objects/puzzle3.png";
	public static final String PUZZLE4 = "Objects/puzzle4.png";

	public static final String SPIDER = "Objects/spider.png";
	
	public static Image GetSpriteAtlas(String fileName) {
		Image image = new Image("/" + fileName);
		return image;
	}
}