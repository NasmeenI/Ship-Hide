package logic.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import application.GameProcess;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import object.Tile;
import utilz.LoadSave;

public class Map implements Serializable {

	private static final long serialVersionUID = 2L;
	// 0 = wall 
	// 1 = floor
	// 2 = superimposed object
	public int mapTileNum[][];
	public Tile mapTile[][];
	transient private Image map_1;
	private static Map instance;
	
	public Map() {
		initImg();
		mapTileNum = new int[60][110];
		mapTile = new Tile[60][110];
		try {
			InputStream is = getClass().getResourceAsStream("/Maps/value.txt");
			BufferedReader br = new BufferedReader (new InputStreamReader(is)); 
			
			int x = 0;
			int y = 0;
			while(y < 60 && x < 110) {
				String line = br.readLine();
				
				while(x < 110) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[x]);
					mapTileNum[y][x] = num;
					x++;
				}
				if(x == 110) {
					x = 0;
					y++;
				}
			}
			br.close(); 
			
			for(y=0;y<60;y++) {
				for(x=0;x<110;x++) {
					Tile temp = new Tile(x*48 ,y*48 ,ID.Tile ,y ,x ,mapTileNum[y][x]);
					mapTile[y][x] = temp;
				}
			}
		
		}catch(Exception e) {
			
		}
	}
	
	public static Map getInstance() {
        if(instance == null) instance = new Map();
        return instance;
    }
	
	public void replace(Map map) {
		instance = map;
	}

	public void render_0(GraphicsContext gc ,int xTile ,int yTile) {
		for(int y=Math.max(yTile ,8)-8;y<Math.min(yTile, 51)+9;y++) {
			for(int x=Math.max(xTile, 12)-12;x<Math.min(xTile, 98)+12;x++) {
				if(mapTile[y][x].getImage0() == null) continue;
				gc.drawImage(mapTile[y][x].getImage0() ,x*48 ,y*48);
			}
		}
	}
	
	public void render_1(GraphicsContext gc) {
		gc.drawImage(map_1 , 0, 0);
	}

	public void render_2(GraphicsContext gc ,int xTile ,int yTile) {
		for(int y=Math.max(yTile, 8)-8;y<Math.min(yTile, 51)+9;y++) {
			for(int x=Math.max(xTile, 12)-12;x<Math.min(xTile, 98)+12;x++) {
				if(GameProcess.renderState[y][x] == true) {
					gc.drawImage(mapTile[y][x].getImage2_0() ,x*48 ,y*48);
				}
				else {
					gc.drawImage(mapTile[y][x].getImage2() ,x*48 ,y*48);
				}
			}
		}
	}
	
	public void updateAfterLoadSave() {
		initImg();
		for(int y=0;y<60;y++) {
			for(int x=0;x<110;x++) {
				mapTile[y][x].initImg();
			}
		}
	}
	
	public void initImg() {
		this.map_1 = LoadSave.GetSpriteAtlas(LoadSave.MAP_1);
	}

	
	// Getter & Setter
	public void setStageMap(int x ,int y ,int stage) {
		mapTile[y][x].setState(stage);
		mapTileNum[y][x] = stage;
	}
	
	public int[][] getMapTileNum() {
		return mapTileNum;
	}

	public void setMapTileNum(int[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}

	public Tile[][] getMapTile() {
		return mapTile;
	}

	public void setMapTile(Tile[][] mapTile) {
		this.mapTile = mapTile;
	}

	public Image getMap_1() {
		return map_1;
	}

	public void setMap_1(Image map_1) {
		this.map_1 = map_1;
	}
}